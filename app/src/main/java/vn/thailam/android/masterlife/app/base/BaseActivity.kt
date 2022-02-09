package vn.thailam.android.masterlife.app.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseVBActivity<B : ViewBinding> : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding: B
        get() = _binding!!

    abstract val bindingInflater: (LayoutInflater) -> B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
    }

    open fun setupUI() {
        setupClickListeners()
    }
    open fun setupClickListeners() = Unit
    open fun setupViewModel() = Unit
}