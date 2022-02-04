package vn.thailam.android.masterlife.app.page.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.masterlife.app.base.BaseVBActivity
import vn.thailam.android.masterlife.databinding.ActivitySplashBinding

class SplashActivity : BaseVBActivity<ActivitySplashBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    private val viewModel by viewModel<SplashViewModel>()

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.initialize()
    }
}