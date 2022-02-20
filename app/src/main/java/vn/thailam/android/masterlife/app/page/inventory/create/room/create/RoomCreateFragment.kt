package vn.thailam.android.masterlife.app.page.inventory.create.room.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import vn.thailam.android.japanwritinghelper.app.base.BaseViewBindingFragment
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity
import vn.thailam.android.masterlife.data.repo.RoomTypeRepo
import vn.thailam.android.masterlife.databinding.FragmentRoomCreateBinding

interface RoomCreateFragmentListener {
    fun onComplete()
}

class RoomCreateFragment : BaseViewBindingFragment<FragmentRoomCreateBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRoomCreateBinding
        get() = FragmentRoomCreateBinding::inflate

    private val roomRepo = get<RoomTypeRepo>()
    private var listener: RoomCreateFragmentListener? = null

    override fun setupUI() {
        super.setupUI()
        binding.run {
            btnCreateRoom.setOnClickListener {
                createRoom()
            }
        }
    }

    fun setListener(listener: RoomCreateFragmentListener?): RoomCreateFragment {
        this.listener = listener
        return this
    }

    private fun createRoom() {
        lifecycleScope.launchWhenStarted {
            // TODO: add try catch or smt
            withContext(Dispatchers.IO) {
                roomRepo.insert(RoomTypeEntity(name = binding.etName.text.toString()))
            }
            listener?.onComplete()
        }
    }

    companion object {
        fun newInstance() = RoomCreateFragment()
    }
}