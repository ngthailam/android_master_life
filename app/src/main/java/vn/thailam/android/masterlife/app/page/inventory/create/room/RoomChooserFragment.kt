package vn.thailam.android.masterlife.app.page.inventory.create.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.japanwritinghelper.app.base.BaseViewBindingFragment
import vn.thailam.android.masterlife.app.custom.bottomsheet.BottomSheet
import vn.thailam.android.masterlife.app.page.inventory.create.room.create.RoomCreateFragment
import vn.thailam.android.masterlife.app.page.inventory.create.room.create.RoomCreateFragmentListener
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity
import vn.thailam.android.masterlife.databinding.FragmentRoomChooserBinding

interface RoomChooserListener {
    fun onChosen(room: RoomTypeEntity)
}

class RoomChooserFragment : BaseViewBindingFragment<FragmentRoomChooserBinding>(),
    RoomCreateFragmentListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRoomChooserBinding
        get() = FragmentRoomChooserBinding::inflate

    private val viewModel by viewModel<RoomChooserViewModel>()
    private val roomAdapter = RoomAdapter {
        viewModel.onChosen(it.data)
    }
    private val preChosenRoomId by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getInt(ARG_PRE_CHOSEN_ID, 0) ?: 0
    }
    private var listener: RoomChooserListener? = null
    private var createRoomBottomSheet: BottomSheet<String>? = null

    override fun setupUI() {
        super.setupUI()
        setupRoomRecycler()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            rooms.observe(viewLifecycleOwner, {
                roomAdapter.submitList(it)
            })
            chosenRoom.observe(viewLifecycleOwner, {
                listener?.onChosen(it)
            })

            initialize(preChosenRoomId)
        }
    }

    override fun setupClickListeners() {
        super.setupClickListeners()
        binding.run {
            btnAddRoom.setOnClickListener {
                showCreateRoomBottomSheet()
            }
        }
    }

    private fun setupRoomRecycler() {
        binding.rvRooms.run {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomAdapter
        }
    }

    private fun showCreateRoomBottomSheet() {
        createRoomBottomSheet = BottomSheet.Builder<String>()
            .setCancelable(true)
            .setNavigationBarHidden(true)
            .setDismissOnTappingBackground(true)
            .setTitle("Add a new room")
            .setContentFragment(RoomCreateFragment.newInstance().setListener(this))
            .create()
        createRoomBottomSheet?.show(childFragmentManager, null)
    }

    override fun onComplete() {
        createRoomBottomSheet?.dismiss()
    }

    fun setListener(listener: RoomChooserListener?): RoomChooserFragment {
        this.listener = listener
        return this
    }

    companion object {
        const val ARG_PRE_CHOSEN_ID = "ARG_PRE_CHOSEN_ID"

        fun newInstance(preChosenRoomId: Int? = null) = RoomChooserFragment().apply {
            arguments = bundleOf(
                ARG_PRE_CHOSEN_ID to preChosenRoomId
            )
        }
    }
}