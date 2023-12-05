package com.danielnascimento.anotai.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.databinding.BottomSheetInsertNoteBinding
import com.danielnascimento.anotai.databinding.FragmentInsertNoteBinding
import com.danielnascimento.anotai.presentation.viewmodel.NoteViewModel
import com.danielnascimento.anotai.presentation.viewmodel.NoteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet(private val noteViewModel: NoteViewModel) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetInsertNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetInsertNoteBinding.inflate(inflater, container, false)
        setImage(noteViewModel.color)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnNote1.setOnClickListener {
                noteViewModel.updateColor(R.color.pastel_green)
                noteViewModel.color = "green"
                dismiss()
            }

            btnNote2.setOnClickListener {
                noteViewModel.updateColor(R.color.pastel_pink)
                noteViewModel.color = "pink"
                dismiss()
            }

            btnNote3.setOnClickListener {
                noteViewModel.updateColor(R.color.pastel_blue)
                noteViewModel.color = "blue"
                dismiss()
            }

            btnNote4.setOnClickListener {
                noteViewModel.updateColor(R.color.pastel_brown)
                noteViewModel.color = "brown"
                dismiss()
            }

            btnNote5.setOnClickListener {
                noteViewModel.updateColor(R.color.pastel_orange)
                noteViewModel.color = "orange"
                dismiss()
            }

            btnNote6.setOnClickListener {
                noteViewModel.updateColor(R.color.pastel_purple)
                noteViewModel.color = "purple"
                dismiss()
            }

            btnNote7.setOnClickListener {
                noteViewModel.updateColor(R.color.white)
                noteViewModel.color = "white"
                ivNote7.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_black
                    )
                )
                dismiss()
            }
        }
    }

    private fun setImage(color: String) {
        val noteImageViews = arrayOf(
            binding.ivNote1,
            binding.ivNote2,
            binding.ivNote3,
            binding.ivNote4,
            binding.ivNote5,
            binding.ivNote6,
            binding.ivNote7
        )

        val noteColors = arrayOf("green", "pink", "blue", "brown", "orange", "purple", "white")

        val noteDrawables = arrayOf(
            R.drawable.ic_check,
            R.drawable.ic_check,
            R.drawable.ic_check,
            R.drawable.ic_check,
            R.drawable.ic_check,
            R.drawable.ic_check,
            R.drawable.ic_check
        )

        when (color) {
            in noteColors -> {
                resetImages(noteImageViews)
                noteImageViews[noteColors.indexOf(color)]
                    .setImageResource(noteDrawables[noteColors.indexOf(color)])
            }
        }
    }

    private fun resetImages(imageViews: Array<ImageView>) {
        imageViews.forEach { it.setImageResource(0) }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}