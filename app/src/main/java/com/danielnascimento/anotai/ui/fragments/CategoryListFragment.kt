package com.danielnascimento.anotai.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.AppDatabase
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.data.db.repository.CategoryRepository
import com.danielnascimento.anotai.databinding.FragmentCategoryListBinding
import com.danielnascimento.anotai.ui.adapter.CategoryListAdapter
import com.danielnascimento.anotai.ui.viewmodel.CategoryViewModel
import com.danielnascimento.anotai.utils.snackbar
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class CategoryListFragment : Fragment() {
    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryListAdapter: CategoryListAdapter

    private val categoryViewModel: CategoryViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {

                    val database = AppDatabase.getDatabase(requireContext())

                    val repository = CategoryRepository(database.categoryDao())

                    @Suppress("UNCHECKED_CAST")
                    return CategoryViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CategoryListAdapter.isEditingCategory = true
        setupClickListeners()
        setupRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        categoryViewModel.getAllCategories()
    }

    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val categoryName = binding.inputCategoryName.text.toString().trim()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        if (categoryName.isNotEmpty()) {
            val category = CategoryEntity()
            category.name = categoryName
            categoryViewModel.insertCategory(category)

            findNavController().popBackStack()

        } else {
            binding.inputCategoryName.error = getText(R.string.empty_category_name_error)
        }
    }

    private fun observeViewModel() {
        categoryViewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->
            categoryListAdapter.submitList(categoryList)
        }

        categoryViewModel.categoryStateMessage.observe(viewLifecycleOwner) { message ->
            val snackbar = Snackbar.make(requireView(), getString(message), Snackbar.LENGTH_LONG)
            snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_success))
            snackbar.setAction(getString(R.string.undo)) {
                categoryViewModel.insertCategory(categoryListAdapter.recentlyDeletedCategory)
            }
            snackbar.show()
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

            CategoryListAdapter.SELECT_REMOVE -> {
                categoryViewModel.deleteCategory(category.id)
//                categoryViewModel.getAllCategories()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}