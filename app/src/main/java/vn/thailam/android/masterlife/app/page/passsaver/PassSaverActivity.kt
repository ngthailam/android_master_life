package vn.thailam.android.masterlife.app.page.passsaver

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.masterlife.app.base.BaseVBActivity
import vn.thailam.android.masterlife.app.page.passsaver.passlist.PassSaverAdapter
import vn.thailam.android.masterlife.databinding.ActivityPassSaverBinding

class PassSaverActivity : BaseVBActivity<ActivityPassSaverBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityPassSaverBinding
        get() = ActivityPassSaverBinding::inflate

    private val viewModel by viewModel<PassSaverViewModel>()
    private val passSaverAdapter = PassSaverAdapter(::onPassItemClick)

    override fun setupUI() {
        super.setupUI()
        setupPassRecycler()
    }

    private fun setupPassRecycler() {
        binding.rvPasswords.run {
            layoutManager = LinearLayoutManager(this@PassSaverActivity)
            setHasFixedSize(true)
            adapter = passSaverAdapter
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.getPasswords()
    }

    private fun onPassItemClick(passUiModel: PassUiModel) {

    }
}