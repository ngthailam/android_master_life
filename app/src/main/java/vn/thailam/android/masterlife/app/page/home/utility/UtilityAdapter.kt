package vn.thailam.android.masterlife.app.page.home.utility

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.thailam.android.masterlife.data.entity.UtilityEntity
import vn.thailam.android.masterlife.databinding.ItemHomeUtilityBinding

class HomeUtilityAdapter(
    private val onItemClick: (entity: UtilityEntity) -> Unit
) :
    ListAdapter<UtilityEntity, HomeUtilityAdapter.UtilityViewHolder>(UtilityDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtilityViewHolder {
        val binding = ItemHomeUtilityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UtilityViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: UtilityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UtilityViewHolder(
        private val binding: ItemHomeUtilityBinding,
        private val onItemClick: (entity: UtilityEntity) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var item: UtilityEntity? = null

        init {
            binding.root.setOnClickListener {
                item?.let { utilityEntity ->
                    onItemClick.invoke(utilityEntity)
                }
            }
        }

        fun bind(item: UtilityEntity?) {
            this.item = item
            binding.run {
                tvUltiName.text = item!!.name
            }
        }
    }
}

class UtilityDiffUtil : DiffUtil.ItemCallback<UtilityEntity>() {
    override fun areItemsTheSame(oldItem: UtilityEntity, newItem: UtilityEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UtilityEntity, newItem: UtilityEntity): Boolean {
        return oldItem == newItem
    }
}
