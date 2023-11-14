package com.danielnascimento.anotai.presentation.fragments.categoryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.databinding.FragmentCategoryListBinding
import com.danielnascimento.anotai.presentation.adapters.CategoryListAdapter
import com.danielnascimento.anotai.presentation.viewmodel.CategoryViewModel
import com.danielnascimento.anotai.presentation.viewmodel.CategoryViewModelFactory
import com.danielnascimento.anotai.utils.hideKeyboard
import com.danielnascimento.anotai.utils.listEmpty
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class CategoryListFragment : Fragment() {
    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryListAdapter: CategoryListAdapter

    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(requireContext())
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

        if (categoryName.isNotEmpty()) {
            val category = CategoryEntity(
                name = categoryName
            )
            categoryViewModel.insertCategory(category)
            binding.inputCategoryName.setText("")
            requireContext().hideKeyboard(requireView())
        } else {
            binding.inputCategoryName.error = getText(R.string.empty_category_name_error)
        }
    }

    private fun observeViewModel() {
        categoryViewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->
            categoryListAdapter.submitList(categoryList)
            binding.tvInfo.text = listEmpty(categoryList)
        }

        categoryViewModel.categoryStateMessage.observe(viewLifecycleOwner) { message ->
            if (message == R.string.success_in_deleting_category) {
                val snackbar = Snackbar.make(requireView(), getString(message), Snackbar.LENGTH_LONG)
                snackbar.setTextColor(ContextCompat.getColor(requireContext(),R.color.main_success))
                snackbar.setAction(getString(R.string.undo)) {
                    categoryViewModel.insertCategory(categoryListAdapter.recentlyDeletedCategory)
                }
                snackbar.show()
            }
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
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}