package vn.thailam.android.masterlife.app.page.inventory

import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.app.base.BaseVBActivity
import vn.thailam.android.masterlife.app.page.inventory.adapter.InventoryAdapter
import vn.thailam.android.masterlife.app.page.inventory.adapter.InventoryItemInteraction
import vn.thailam.android.masterlife.app.page.inventory.create.InventoryCreateFragment
import vn.thailam.android.masterlife.app.utils.SpacingItemDecoration
import vn.thailam.android.masterlife.app.utils.applySlideInUp
import vn.thailam.android.masterlife.app.utils.debounce
import vn.thailam.android.masterlife.app.utils.toast
import vn.thailam.android.masterlife.data.entity.InventoryEntity
import vn.thailam.android.masterlife.databinding.ActivityInventoryBinding

class InventoryActivity : BaseVBActivity<ActivityInventoryBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityInventoryBinding
        get() = ActivityInventoryBinding::inflate

    private val viewModel by viewModel<InventoryViewModel>()

    private val adapterInteraction = object : InventoryItemInteraction {
        override fun onClick(inventory: InventoryEntity) {
            toast("Clicked on item")
        }
    }
    private val inventoryAdapter = InventoryAdapter(adapterInteraction)

    override fun setupUI() {
        super.setupUI()
        setupInventoryRecycler()
        setupSearchView()
    }

    override fun setupClickListeners() {
        super.setupClickListeners()
        binding.run {
            tbInventory.setNavigationOnClickListener { finish() }
            fabCreateInventory.setOnClickListener { goToCreateInventory() }
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.run {
            displayedInventories.observe(this@InventoryActivity, ::onInventoriesChanged)

            initialize()
        }
    }

    private fun onInventoriesChanged(list: List<InventoryEntity>) {
        inventoryAdapter.submitList(list)
    }

    private fun setupInventoryRecycler() {
        binding.rvInventory.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@InventoryActivity)
            adapter = inventoryAdapter
            val spacing = resources.getDimensionPixelSize(R.dimen.dp_4)
            addItemDecoration(SpacingItemDecoration(spacing, spacing, spacing, spacing))
        }
    }

    private fun setupSearchView() {
        binding.svInventory.run {
            val onSearchTextChanged: (String?) -> Unit = debounce(
                300L,
                lifecycleScope,
                viewModel::onSearch
            )

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    onSearchTextChanged(newText)
                    return false
                }
            })
        }
    }

    private fun goToCreateInventory(inventoryId: Int? = null) {
        supportFragmentManager.beginTransaction()
            .applySlideInUp()
            .addToBackStack(null)
            .add(R.id.clInvenContainer, InventoryCreateFragment.newInstance(inventoryId), null)
            .commit()
    }
}
