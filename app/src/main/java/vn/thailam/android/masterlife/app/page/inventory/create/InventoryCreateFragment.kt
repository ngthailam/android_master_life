package vn.thailam.android.masterlife.app.page.inventory.create

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.japanwritinghelper.app.base.BaseViewBindingFragment
import vn.thailam.android.masterlife.app.custom.bottomsheet.BottomSheet
import vn.thailam.android.masterlife.app.page.inventory.create.room.RoomChooserFragment
import vn.thailam.android.masterlife.app.page.inventory.create.room.RoomChooserListener
import vn.thailam.android.masterlife.app.utils.OpStatus
import vn.thailam.android.masterlife.app.utils.hideKeyboard
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity
import vn.thailam.android.masterlife.databinding.FragmentInventoryCreateBinding

class InventoryCreateFragment : BaseViewBindingFragment<FragmentInventoryCreateBinding>(),
    RoomChooserListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInventoryCreateBinding
        get() = FragmentInventoryCreateBinding::inflate

    private val viewModel by viewModel<InventoryCreateViewModel>()

    private val inventoryId by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getInt(ARG_INVENTORY_ID, 0) ?: 0
    }
    private var roomChooserBottomSheet: BottomSheet<String>? = null

    override fun setupUI() {
        super.setupUI()
        setupTextChangeListeners()
    }

    override fun setupClickListeners() {
        super.setupClickListeners()
        binding.run {
            btnCreate.setOnClickListener {
                viewModel.createInventory()
            }
            ipsCount.setValueDidChangeListener { newValue, _ ->
                viewModel.onCountChanged(newValue)
            }
            vRoomNameClickZone.setOnClickListener {
                showRoomPicker()
            }
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            createOpStatus.observe(viewLifecycleOwner, ::onCreateOpStatusChanged)

            initialize(inventoryId)
        }
    }

    private fun onCreateOpStatusChanged(opStatus: OpStatus) {
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

    private fun setupTextChangeListeners() {
        binding.run {
            etLocDesc.doAfterTextChanged { viewModel.onLocDescChanged(it.toString()) }
            etName.doAfterTextChanged { viewModel.onNameChanged(it.toString()) }
        }
    }

    private fun showRoomPicker() {
        if (roomChooserBottomSheet?.isVisible == true) {
            return
        }
        roomChooserBottomSheet = BottomSheet.Builder<String>()
            .setTitle("Choose room")
            .setDismissOnTappingBackground(true)
            .setContentFragment(
                RoomChooserFragment.newInstance(viewModel.inventory.roomTypeId).setListener(this)
            )
            .setCancelable(true)
            .create()
        roomChooserBottomSheet?.show(childFragmentManager, null)
    }

    override fun onChosen(room: RoomTypeEntity) {
        roomChooserBottomSheet?.dismiss()
        binding.etRoomName.setText(room.name ?: "")
        viewModel.onRoomChanged(room)
    }

    companion object {
        const val ARG_INVENTORY_ID = "ARG_INVENTORY_ID"

        fun newInstance(inventoryId: Int? = null): InventoryCreateFragment =
            InventoryCreateFragment().apply {
                arguments = bundleOf(
                    ARG_INVENTORY_ID to (inventoryId ?: 0)
                )
            }
    }
}