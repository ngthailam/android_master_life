package vn.thailam.android.masterlife.app.page.note.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.thailam.android.masterlife.app.page.note.list.NoteUiModel
import vn.thailam.android.masterlife.app.utils.DateUtils
import vn.thailam.android.masterlife.databinding.ItemNoteBinding

class NoteAdapter(
    private val interactionInterface: NoteItemInteraction
) :
    ListAdapter<NoteUiModel, NoteAdapter.NoteViewHolder>(NoteUiModelDiffUtil()) {
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

        private var item: NoteUiModel? = null

        init {
            binding.run {
                root.setOnClickListener {
                    interactionInterface.onClick(item!!)
                }
                root.setOnLongClickListener {
                    interactionInterface.onLongPressed(item!!)
                }
            }
        }

        fun bind(item: NoteUiModel) {
            this.item = item
            binding.run {
                val data = item.entity
                nvNote.setTitle(data.title ?: "")
                    .setDesc(data.desc ?: "")
                    .setCreatedAt(DateUtils.formatMyTime(data.createdAt))
                    .setNoteId(data.id!!)
                    .isPinned(data.pin != null)
                    .enableCheck(item.showSelectBox)
                    .setIsChecked(item.isSelected)
            }
        }
    }
}

interface NoteItemInteraction {
    fun onClick(note: NoteUiModel)
    fun onLongPressed(note: NoteUiModel): Boolean = false
}

class NoteUiModelDiffUtil : DiffUtil.ItemCallback<NoteUiModel>() {
    override fun areItemsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
        return oldItem.entity.id == newItem.entity.id
    }

    override fun areContentsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
        return oldItem == newItem
    }
}
