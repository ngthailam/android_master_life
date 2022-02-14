package vn.thailam.android.masterlife.app.page.passsaver.list.adapter

import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.text.method.TransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.app.page.passsaver.list.PassUiModel
import vn.thailam.android.masterlife.app.utils.copyToClipboard
import vn.thailam.android.masterlife.app.utils.toast
import vn.thailam.android.masterlife.databinding.ItemPassSaverBinding

class PassSaverAdapter(
    private val interactionInterface: PassSaverItemInteraction
) :
    ListAdapter<PassUiModel, PassSaverAdapter.PassSaverViewHolder>(PassUiModelDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassSaverViewHolder {
        val binding = ItemPassSaverBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PassSaverViewHolder(binding, interactionInterface)
    }

    override fun onBindViewHolder(holder: PassSaverViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PassSaverViewHolder(
        private val binding: ItemPassSaverBinding,
        private val interactionInterface: PassSaverItemInteraction
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var item: PassUiModel? = null

        init {
            binding.run {
                val ctx = binding.root.context
                icEye.setOnClickListener {
                    item?.let { passUiModel ->
                        interactionInterface.onTogglePasswordClick(passUiModel)
                    }
                }
                ivCopyAccName.setOnClickListener {
                    ctx.copyToClipboard(item?.entity?.accName ?: "")
                    ctx.toast("Account name copied")
                }
                ivCopyPassword.setOnClickListener {
                    if (item?.showPassword == true) {
                        ctx.copyToClipboard(item?.entity?.decryptedPassword ?: "")
                        ctx.toast("Password copied")
                    } else {
                        ctx.toast("Password must be visible to be copied. Please click the eye icon")
                    }
                }
            }
        }

        fun bind(item: PassUiModel) {
            this.item = item
            binding.run {
                tvName.text = item.entity.name ?: ""
                tvAccName.text = item.entity.accName ?: ""
                tvPass.text = item.entity.decryptedPassword ?: ""
                icEye.setImageResource(getEyeIcon())
                tvPass.transformationMethod = getTvPassTransformationMethod()

            }
        }

        private fun getEyeIcon(): Int =
            if (item!!.showPassword) R.drawable.ic_24outline_visibility_off else R.drawable.ic_24outline_visibility_on

        private fun getTvPassTransformationMethod(): TransformationMethod =
            if (item!!.showPassword) SingleLineTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
    }
}

interface PassSaverItemInteraction {
    fun onTogglePasswordClick(password: PassUiModel)
}

class PassUiModelDiffUtil : DiffUtil.ItemCallback<PassUiModel>() {
    override fun areItemsTheSame(oldItem: PassUiModel, newItem: PassUiModel): Boolean {
        return oldItem.entity.id == newItem.entity.id
    }

    override fun areContentsTheSame(oldItem: PassUiModel, newItem: PassUiModel): Boolean {
        return oldItem == newItem
    }
}
