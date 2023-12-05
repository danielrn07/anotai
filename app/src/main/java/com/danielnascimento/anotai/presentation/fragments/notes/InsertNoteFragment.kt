package com.danielnascimento.anotai.presentation.fragments.notes

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.entity.NoteEntity
import com.danielnascimento.anotai.databinding.FragmentInsertNoteBinding
import com.danielnascimento.anotai.presentation.viewmodel.NoteViewModel
import com.danielnascimento.anotai.presentation.viewmodel.NoteViewModelFactory
import com.danielnascimento.anotai.utils.ModalBottomSheet
import com.danielnascimento.anotai.utils.getTextInput
import com.danielnascimento.anotai.utils.navUp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.todayIn
import java.time.format.DateTimeFormatter

class InsertNoteFragment : Fragment() {
    private var _binding: FragmentInsertNoteBinding? = null
    private val binding get() = _binding!!

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsertNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etInputText.inputType = android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or
                android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                android.text.InputType.TYPE_CLASS_TEXT

        setupClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        noteViewModel.colorObservable.observe(viewLifecycleOwner) { novaCor ->
            binding.view.setBackgroundColor(ContextCompat.getColor(requireContext(), novaCor))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupClickListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                validateData()
            }

            btnBack.setOnClickListener {
                navUp()
            }

            btnMore.setOnClickListener {
                val modalBottomSheet = ModalBottomSheet(noteViewModel)
                modalBottomSheet.show(childFragmentManager, ModalBottomSheet.TAG)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateData() {
        val noteTitle = getTextInput(binding.etInputTitle)
        val noteText = getTextInput(binding.etInputText)

        val date: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val javaLocalDate = date.toJavaLocalDate()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = javaLocalDate.format(formatter)

        if (noteText.isNotEmpty()) {
            val note = NoteEntity(
                title = noteTitle,
                text = noteText,
                date = formattedDate,
                color = noteViewModel.color
            )
            noteViewModel.insertNote(note)
            navUp()
        } else {
            binding.etInputText.error = getText(R.string.empty_note_text_error)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}