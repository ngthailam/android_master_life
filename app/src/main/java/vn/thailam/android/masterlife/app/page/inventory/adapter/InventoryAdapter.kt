package vn.thailam.android.masterlife.app.page.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.thailam.android.masterlife.app.utils.visibility
import vn.thailam.android.masterlife.data.entity.InventoryEntity
import vn.thailam.android.masterlife.databinding.ItemInventoryBinding

class InventoryAdapter(
    private val interactionInterface: InventoryItemInteraction? = null
) :
    ListAdapter<InventoryEntity, InventoryAdapter.InventoryViewHolder>(InventoryUiModelDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = ItemInventoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InventoryViewHolder(binding, interactionInterface)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class InventoryViewHolder(
        private val binding: ItemInventoryBinding,
        private val interactionInterface: InventoryItemInteraction?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var item: InventoryEntity? = null

        init {
            binding.run {
                root.setOnClickListener {
                    if (item != null) {
                        interactionInterface?.onClick(item!!)
                    }
                }
            }
        }

        fun bind(item: InventoryEntity) {
            this.item = item
            binding.run {
                tvName.text = item.name ?: ""
                tvRoomName.text = item.roomEntity?.name ?: ""
                tvDescName.text = item.locationDesc ?: ""
                tvCount.text = "x${item.count}"
                tvStatus.text = item.condition // TODO: beautify this
                tvDescName.visibility(item.locationDesc?.isNotEmpty() == true)
            }
        }
    }
}

interface InventoryItemInteraction {
    fun onClick(inventory: InventoryEntity)
}

class InventoryUiModelDiffUtil : DiffUtil.ItemCallback<InventoryEntity>() {
    override fun areItemsTheSame(oldItem: InventoryEntity, newItem: InventoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: InventoryEntity, newItem: InventoryEntity): Boolean {
        return oldItem == newItem
    }
}
