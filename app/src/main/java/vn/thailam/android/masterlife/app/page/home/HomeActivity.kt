package vn.thailam.android.masterlife.app.page.home

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.masterlife.app.base.BaseVBActivity
import vn.thailam.android.masterlife.app.page.home.utility.HomeUtilityAdapter
import vn.thailam.android.masterlife.app.page.passsaver.PassSaverActivity
import vn.thailam.android.masterlife.data.entity.UtilityCode
import vn.thailam.android.masterlife.data.entity.UtilityEntity
import vn.thailam.android.masterlife.databinding.ActivityHomeBinding

class HomeActivity : BaseVBActivity<ActivityHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    private val viewModel by viewModel<HomeViewModel>()
    private val utilitiesAdapter = HomeUtilityAdapter(::onUtilityItemClick)

    override fun setupUI() {
        super.setupUI()
        setupUtilitiesRecycler()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            utilityEntities.observe(this@HomeActivity, {
              utilitiesAdapter.submitList(it)
            })

            getUtilities()
        }
    }

    private fun setupUtilitiesRecycler() {
        binding.rvUtilities.run {
            layoutManager = GridLayoutManager(this@HomeActivity, 4)
            adapter = utilitiesAdapter
            setHasFixedSize(true)
        }
    }

    private fun onUtilityItemClick(utility: UtilityEntity) {
        when (utility.code) {
            UtilityCode.PASS_SAVER -> {
                val intent = Intent(this, PassSaverActivity::class.java)
                startActivity(intent)
            }
            else -> {
                // TODO: handle error cases
            }
        }
    }
}