package com.danielnascimento.anotai.presentation.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.entity.NoteEntity
import com.danielnascimento.anotai.databinding.NoteItemListBinding

class NoteListAdapter(private val context: Context) : ListAdapter<NoteEntity, NoteListAdapter.MyViewHolder>(DIFF_CALLBACK) {
//    var recentlyDeletedNote: NoteEntity = NoteEntity()

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NoteEntity>() {
            override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
                oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        NoteItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = getItem(position)

        holder.binding.apply {
            if (note.title.isEmpty())
                tvTitle.isVisible = false

            when (note.color) {
                "green" -> cvNote.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pastel_green))
                "pink" -> cvNote.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pastel_pink))
                "blue" -> cvNote.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pastel_blue))
                "brown" -> cvNote.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pastel_brown))
                "orange" -> cvNote.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pastel_orange))
                "purple" -> cvNote.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pastel_purple))
                else -> cvNote.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }

            tvTitle.text = note.title
            tvNoteText.text = note.text
            tvDate.text = note.date
        }
    }

    inner class MyViewHolder(val binding: NoteItemListBinding) :
        RecyclerView.ViewHolder(binding.root)
}