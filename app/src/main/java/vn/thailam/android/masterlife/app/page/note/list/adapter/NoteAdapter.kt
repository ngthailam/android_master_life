package vn.thailam.android.masterlife.app.page.note.list.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.thailam.android.masterlife.app.utils.DateUtils
import vn.thailam.android.masterlife.data.entity.NoteEntity
import vn.thailam.android.masterlife.databinding.ItemNoteBinding

class NoteAdapter(
    private val interactionInterface: NoteItemInteraction
) :
    ListAdapter<NoteEntity, NoteAdapter.NoteViewHolder>(NoteEntityDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding, interactionInterface)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoteViewHolder(
        private val binding: ItemNoteBinding,
        private val interactionInterface: NoteItemInteraction
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var item: NoteEntity? = null

        init {
            binding.run {
                root.setOnClickListener {
                    interactionInterface.onClick(item!!)
                }
            }
        }

        fun bind(item: NoteEntity) {
            this.item = item
            binding.run {
                tvNoteTitle.text = item.title ?: ""
                tvNoteDesc.text = item.desc ?: ""
                tvCreatedAt.text =
                    DateUtils.formatMyTime(item.createdAt)
            }
        }
    }
}

interface NoteItemInteraction {
    fun onClick(note: NoteEntity)
}

class NoteEntityDiffUtil : DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem == newItem
    }
}
