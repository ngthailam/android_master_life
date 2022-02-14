package vn.thailam.android.masterlife.app.page.passsaver.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.japanwritinghelper.app.base.BaseViewBindingFragment
import vn.thailam.android.masterlife.app.utils.OpStatus
import vn.thailam.android.masterlife.app.utils.hideKeyboard
import vn.thailam.android.masterlife.databinding.FragmentPassSaverCreateBinding

class PassSaverCreateFragment : BaseViewBindingFragment<FragmentPassSaverCreateBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPassSaverCreateBinding
        get() = FragmentPassSaverCreateBinding::inflate

    private val viewModel by viewModel<PassSaverCreateViewModel>()

    override fun onStop() {
        super.onStop()
        requireActivity().hideKeyboard()
    }

    override fun setupUI() {
        super.setupUI()
        setupTextInputs()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            createOpStatus.observe(viewLifecycleOwner, ::onCreateOpChanged)
        }
    }

    override fun setupClickListeners() {
        binding.run {
            btnCreate.setOnClickListener {
                viewModel.createPass()
            }
        }
    }

    private fun setupTextInputs() {
        binding.run {
            etName.doAfterTextChanged { editable ->
                viewModel.onNameChanged(editable.toString())
            }
            etAccName.doAfterTextChanged { editable ->
                viewModel.onAccNameChanged(editable.toString())
            }
            etPassword.doAfterTextChanged { editable ->
                viewModel.onPasswordChanged(editable.toString())
            }
        }
    }

    private fun onCreateOpChanged(opStatus: OpStatus) {
        requireActivity().hideKeyboard()
        when (opStatus) {
            is OpStatus.Loading -> {
                // TODO: do something here
            }
            is OpStatus.Success -> {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
            is OpStatus.Error -> {
                Toast.makeText(requireContext(), "Error ${opStatus.e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PassSaverCreateFragment().apply {
            arguments = Bundle().apply {
                // Arguments
            }
        }
    }
}