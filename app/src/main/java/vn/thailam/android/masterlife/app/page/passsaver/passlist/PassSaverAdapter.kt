package vn.thailam.android.masterlife.app.page.passsaver.passlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.thailam.android.masterlife.app.page.passsaver.PassUiModel
import vn.thailam.android.masterlife.databinding.ItemPassSaverBinding

class PassSaverAdapter(
    private val onItemClick: (entity: PassUiModel) -> Unit
) :
    ListAdapter<PassUiModel, PassSaverAdapter.UtilityViewHolder>(PassUiModelDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtilityViewHolder {
        val binding = ItemPassSaverBinding.inflate(
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
        binding: ItemPassSaverBinding,
        private val onItemClick: (entity: PassUiModel) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var item: PassUiModel? = null

        init {
            binding.root.setOnClickListener {
                item?.let { PassUiModel ->
                    onItemClick.invoke(PassUiModel)
                }
            }
        }

        fun bind(item: PassUiModel?) {
            this.item = item
        }
    }
}

class PassUiModelDiffUtil : DiffUtil.ItemCallback<PassUiModel>() {
    override fun areItemsTheSame(oldItem: PassUiModel, newItem: PassUiModel): Boolean {
        return oldItem.entity.id == newItem.entity.id
    }

    override fun areContentsTheSame(oldItem: PassUiModel, newItem: PassUiModel): Boolean {
        return oldItem == newItem
    }
}
