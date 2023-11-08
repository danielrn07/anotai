package com.danielnascimento.anotai.presentation.fragments.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.databinding.FragmentNotesBinding
import com.danielnascimento.anotai.presentation.fragments.categoryList.adapter.CategoryListAdapter
import com.danielnascimento.anotai.presentation.viewmodel.CategoryViewModel
import com.danielnascimento.anotai.presentation.viewmodel.ViewModelFactory
import com.danielnascimento.anotai.utils.nav

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryListAdapter: CategoryListAdapter

    private val categoryViewModel: CategoryViewModel by viewModels {
        ViewModelFactory(requireContext())
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
        setupRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        categoryViewModel.getAllCategories()
    }

    private fun setupClickListeners() {
        binding.btnEditCategories.setOnClickListener {
            nav(R.id.action_notesFragment_to_categoryListFragment)
        }
    }

    private fun observeViewModel() {
        categoryViewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->

            val newCategory = CategoryEntity(0, "All")
            val allCategoriesList = mutableListOf(newCategory)
            allCategoriesList.addAll(categoryList)
            categoryListAdapter.submitList(allCategoriesList)

            categoryListAdapter.setSelectedPositionAndNotify(newCategory.id.toInt())
        }
    }

    private fun setupRecyclerView() {

        categoryListAdapter = CategoryListAdapter { category, option ->
            optionSelected(category, option)
        }

        with(binding.recyclerview) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
            adapter = categoryListAdapter
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