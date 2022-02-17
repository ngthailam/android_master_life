package vn.thailam.android.masterlife.app.page.inventory.create.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.databinding.ItemRoomChooserBinding

class RoomAdapter(
    private val onChosen: (RoomChooserUiModel) -> Unit
) :
    ListAdapter<RoomChooserUiModel, RoomAdapter.RoomViewHolder>(RoomUiModelDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemRoomChooserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoomViewHolder(binding, onChosen)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RoomViewHolder(
        private val binding: ItemRoomChooserBinding,
        private val onChosen: (RoomChooserUiModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var item: RoomChooserUiModel? = null

        init {
            binding.run {
                root.setOnClickListener {
                    if (item != null) {
                        onChosen.invoke(item!!)
                    }
                }
            }
        }

        fun bind(item: RoomChooserUiModel) {
            this.item = item
            binding.run {
                tvRoomType.text = item.data.name ?: ""
                val colorRes = if (item.isChosen) R.color.blue200 else R.color.white500
                tvRoomType.setBackgroundColor(root.resources.getColor(colorRes, null))
                tvRoomType.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    if (item.isChosen) ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_24baseline_check
                    ) else null,
                    null
                )
            }
        }
    }
}

class RoomUiModelDiffUtil : DiffUtil.ItemCallback<RoomChooserUiModel>() {
    override fun areItemsTheSame(
        oldItem: RoomChooserUiModel,
        newItem: RoomChooserUiModel
    ): Boolean {
        return oldItem.data.id == newItem.data.id
    }

    override fun areContentsTheSame(
        oldItem: RoomChooserUiModel,
        newItem: RoomChooserUiModel
    ): Boolean {
        return oldItem == newItem
    }
}
