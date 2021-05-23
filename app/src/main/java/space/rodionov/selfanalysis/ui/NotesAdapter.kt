package space.rodionov.selfanalysis.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.selfanalysis.data.Note
import space.rodionov.selfanalysis.databinding.RecyclerItemBinding

class NotesAdapter(private val listener: OnItemClickListener) : ListAdapter<Note, NotesAdapter.NotesViewHolder>(NotesDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class NotesViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        listener.onItemClick(note)
                    }
                }
            }
        }

        fun bind(note: Note) {
            binding.apply {
                tvPreview.text = note.situation
                tvMainDate.text = note.date.toString()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    class NotesDiff : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem == newItem
    }
}