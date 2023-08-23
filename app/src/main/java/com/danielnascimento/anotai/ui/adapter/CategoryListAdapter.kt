package com.danielnascimento.anotai.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.databinding.CategoryItemListBinding

class CategoryListAdapter(
    private val categorySelected: (CategoryEntity, Int) -> Unit
) : ListAdapter<CategoryEntity, CategoryListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val RETURN_ID: Int = 1
        const val SELECT_REMOVE: Int = 2
        var isEditingCategory: Boolean = false
        var selectedPosition = RecyclerView.NO_POSITION

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryEntity>() {
            override fun areItemsTheSame(
                oldItem: CategoryEntity,
                newItem: CategoryEntity
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: CategoryEntity,
                newItem: CategoryEntity
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.name == newItem.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CategoryItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val category = getItem(position)
        holder.binding.tvTitle.text = category.name
        holder.binding.body.setOnClickListener { categorySelected(category, RETURN_ID) }
        holder.binding.btnDelete.setOnClickListener { categorySelected(category, SELECT_REMOVE) }

        setDeleteButtonVisibility(isEditingCategory, holder)

        holder.itemView.apply {
            if (!isEditingCategory) {
                if (position == selectedPosition) {
                    holder.binding.body.setBackgroundResource(R.drawable.bg_category_selected)
                    holder.binding.tvTitle.setTextColor(context.getColor(R.color.main_secondary))
                } else {
                    holder.binding.body.setBackgroundResource(R.drawable.bg_category)
                    val textColorResId = if (checkAppTheme(context)) android.R.color.white else android.R.color.black
                    holder.binding.tvTitle.setTextColor(context.getColor(textColorResId))
                }

                setOnClickListener {
                    val previouslySelectedPosition = selectedPosition
                    selectedPosition = holder.adapterPosition
                    notifyItemChanged(previouslySelectedPosition)
                    notifyItemChanged(selectedPosition)
                    categorySelected(category, RETURN_ID)
                }
            }
        }
    }

    private fun checkAppTheme(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun setDeleteButtonVisibility(isEditing: Boolean, holder: MyViewHolder) {
        if (!isEditing) {
            holder.binding.btnDelete.isVisible = false
        }
    }

    fun setSelectedPositionAndNotify(position: Int) {
        val previouslySelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previouslySelectedPosition)
        notifyItemChanged(selectedPosition)
    }

    inner class MyViewHolder(val binding: CategoryItemListBinding) :
        RecyclerView.ViewHolder(binding.root)
}