package com.danielnascimento.anotai.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielnascimento.anotai.data.db.entity.NoteEntity
import com.danielnascimento.anotai.databinding.NoteItemListBinding

class NoteListAdapter() : ListAdapter<NoteEntity, NoteListAdapter.MyViewHolder>(DIFF_CALLBACK) {
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
            tvTitle.text = note.title
            tvNoteText.text = note.noteText
            tvDate.text = note.date
        }
    }

    inner class MyViewHolder(val binding: NoteItemListBinding) :
        RecyclerView.ViewHolder(binding.root)
}