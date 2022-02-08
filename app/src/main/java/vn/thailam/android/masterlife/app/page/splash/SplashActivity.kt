package vn.thailam.android.masterlife.app.page.splash

import android.content.Intent
import android.view.LayoutInflater
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.masterlife.app.base.BaseVBActivity
import vn.thailam.android.masterlife.app.page.home.HomeActivity
import vn.thailam.android.masterlife.data.entity.AuthStatus
import vn.thailam.android.masterlife.databinding.ActivitySplashBinding

class SplashActivity : BaseVBActivity<ActivitySplashBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    private val viewModel by viewModel<SplashViewModel>()

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            userStatus.observe(this@SplashActivity, ::onUserStatusChanged)

            initialize()
        }
    }

    private fun onUserStatusChanged(authStatus: AuthStatus) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

        when (authStatus) {
            AuthStatus.REGISTERED -> {

            }
            AuthStatus.UNREGISTERED -> {

            }
        }
    }
}