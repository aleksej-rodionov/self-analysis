package space.rodionov.selfanalysis.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.selfanalysis.data.Note
import space.rodionov.selfanalysis.databinding.RecyclerItemBinding
import space.rodionov.selfanalysis.util.ModeForAdapter
import space.rodionov.selfanalysis.util.fetchColors
import space.rodionov.selfanalysis.util.fetchTheme
import space.rodionov.selfanalysis.util.redrawViewGroup

class NotesAdapter(private val listener: OnItemClickListener) : ListAdapter<Note, NotesAdapter.NotesViewHolder>(NotesDiff()),
    ModeForAdapter {

    companion object {
        const val TAG_NOTES_ADAPTER = "notesAdapter"
    }

    var modeNotesAdapter = 0

    override fun updateMode(mode: Int) {
        modeNotesAdapter = mode
    }

    override fun getTag(): String = TAG_NOTES_ADAPTER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.setMode(modeNotesAdapter)
    }

    inner class NotesViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var modeNotesViewHolder = 0
            set(value) {
                field = value
                theme = fetchTheme(value, itemView.resources)
                colors = fetchTheme(value, itemView.resources).fetchColors()
            }
        var theme = fetchTheme(modeNotesViewHolder, itemView.resources)
        var colors = theme.fetchColors()

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

        fun setMode(mode: Int) {
            modeNotesViewHolder = mode
            if (itemView is ViewGroup) itemView.redrawViewGroup(mode)
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