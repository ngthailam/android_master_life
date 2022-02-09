package vn.thailam.android.masterlife.app.page.passsaver

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.app.base.BaseVBActivity
import vn.thailam.android.masterlife.app.page.passsaver.create.PassSaverCreateFragment
import vn.thailam.android.masterlife.app.page.passsaver.passlist.PassSaverAdapter
import vn.thailam.android.masterlife.app.page.passsaver.passlist.PassSaverItemInteraction
import vn.thailam.android.masterlife.app.utils.applySlideInUp
import vn.thailam.android.masterlife.databinding.ActivityPassSaverBinding

class PassSaverActivity : BaseVBActivity<ActivityPassSaverBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityPassSaverBinding
        get() = ActivityPassSaverBinding::inflate

    private val viewModel by viewModel<PassSaverViewModel>()

    private val listItemInteraction: PassSaverItemInteraction by lazy(LazyThreadSafetyMode.NONE) {
        object : PassSaverItemInteraction {
            override fun onTogglePasswordClick(password: PassUiModel) {
                viewModel.onPasswordClick(password)
            }
        }
    }
    private val passSaverAdapter = PassSaverAdapter(listItemInteraction)

    override fun setupUI() {
        super.setupUI()
        setupPassRecycler()
        setupClickListeners()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            displayedPasswords.observe(this@PassSaverActivity, ::onPasswordListChanged)

            getPasswords()
        }
    }

    private fun onPasswordListChanged(list: List<PassUiModel>) {
        binding.informViewPassSaver.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        passSaverAdapter.submitList(list)
    }

    private fun setupPassRecycler() {
        binding.rvPasswords.run {
            layoutManager = LinearLayoutManager(this@PassSaverActivity)
            setHasFixedSize(true)
            adapter = passSaverAdapter
        }
    }

    private fun setupClickListeners() {
        binding.run {
            fabCreate.setOnClickListener {
                goToCreatePage()
            }
        }
    }

    private fun goToCreatePage() {
        supportFragmentManager.beginTransaction()
            .applySlideInUp()
            .addToBackStack(null)
            .add(R.id.clPassSaverContainer, PassSaverCreateFragment.newInstance(), null)
            .commit()
    }
}