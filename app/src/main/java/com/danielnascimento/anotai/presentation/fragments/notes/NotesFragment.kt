package com.danielnascimento.anotai.presentation.fragments.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.data.db.entity.NoteEntity
import com.danielnascimento.anotai.databinding.FragmentNotesBinding
import com.danielnascimento.anotai.presentation.adapters.CategoryListAdapter
import com.danielnascimento.anotai.presentation.adapters.NoteListAdapter
import com.danielnascimento.anotai.presentation.viewmodel.CategoryViewModel
import com.danielnascimento.anotai.presentation.viewmodel.CategoryViewModelFactory
import com.danielnascimento.anotai.presentation.viewmodel.NoteViewModel
import com.danielnascimento.anotai.presentation.viewmodel.NoteViewModelFactory
import com.danielnascimento.anotai.utils.nav

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryListAdapter: CategoryListAdapter
    private lateinit var noteListAdapter: NoteListAdapter

    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(requireContext())
    }

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CategoryListAdapter.isEditingCategory = false

        setupClickListeners()

        observeCategoryViewModel()
        observeNoteViewModel()

        setupCategoryRecyclerView()
        setupNoteRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        categoryViewModel.getAllCategories()
        noteViewModel.getAllNotes()
    }

    private fun setupClickListeners() {
        binding.btnEditCategories.setOnClickListener {
            nav(R.id.action_notesFragment_to_categoryListFragment)
        }
    }

    private fun observeCategoryViewModel() {
        categoryViewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->

            val newCategory = CategoryEntity(0, "All")
            val allCategoriesList = mutableListOf(newCategory)
            allCategoriesList.addAll(categoryList)
            categoryListAdapter.submitList(allCategoriesList)

            categoryListAdapter.setSelectedPositionAndNotify(newCategory.id.toInt())
        }
    }

    private fun observeNoteViewModel() {
        noteViewModel.noteList.observe(viewLifecycleOwner) { noteList ->
            noteListAdapter.submitList(noteList)
        }
    }

    private fun setupCategoryRecyclerView() {
        categoryListAdapter = CategoryListAdapter { category, option ->
            optionSelected(category, option)
        }

        with(binding.rvCategory) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
            adapter = categoryListAdapter
        }
    }

    private fun setupNoteRecyclerView() {
        noteListAdapter = NoteListAdapter()

        with(binding.rvNote) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(false)
            adapter = noteListAdapter
        }
    }

    private fun optionSelected(category: CategoryEntity, option: Int) {
        when (option) {
            CategoryListAdapter.RETURN_ID -> {
                Toast.makeText(requireContext(), "${category.id}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}