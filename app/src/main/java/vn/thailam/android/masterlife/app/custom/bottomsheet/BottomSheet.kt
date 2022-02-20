package vn.thailam.android.masterlife.app.custom.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.thailam.android.masterlife.R
import vn.thailam.android.masterlife.databinding.ViewBottomSheetBinding

class BottomSheet<T> : BottomSheetDialogFragment() {
    private lateinit var builder: Builder<T>
    private var listenerToBottomSheet: BottomSheetListener? = null
    private var bottomSheetDraggingEnable = true
    private var dismissEvent = BottomSheetListener.DISMISS_EVENT_MANUAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DSBottomSheetDialog)
    }

    private lateinit var binding: ViewBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::builder.isInitialized.not()) {
            dismissAllowingStateLoss()
        } else {
            dialog?.setOnShowListener(
                DialogInterface.OnShowListener {
                    if (view.parent == null) return@OnShowListener
                    val params =
                        (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
                    val behavior = params.behavior
                    // Should always be the case
                    if (behavior is BottomSheetBehavior<*>) {
                        if (!bottomSheetDraggingEnable) {
                            behavior.isHideable = false
                        }
                        behavior.addBottomSheetCallback(object :
                            BottomSheetBehavior.BottomSheetCallback() {
                            override fun onStateChanged(bottomSheet: View, state: Int) {
                                if (!bottomSheetDraggingEnable && state == BottomSheetBehavior.STATE_DRAGGING) {
                                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                                } else {
                                    if (builder.collapsePosition > 0) {
                                        when (state) {
                                            BottomSheetBehavior.STATE_HIDDEN -> {
                                                dismissEvent =
                                                    BottomSheetListener.DISMISS_EVENT_SWIPE
                                                dismiss()
                                            }
                                            else -> Unit
                                        }
                                    } else {
                                        when (state) {
                                            BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_HIDDEN,
                                            BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                                                dismissEvent =
                                                    BottomSheetListener.DISMISS_EVENT_SWIPE
                                                dismiss()
                                            }
                                            else -> Unit
                                        }
                                    }
                                }
                            }

                            override fun onSlide(bottomSheet: View, slideOffSet: Float) {
                                // NO-OP
                            }
                        })
                        builder.peekHeight?.let {
                            behavior.peekHeight = it
                        }
                        if (builder.collapsePosition > 0) {
                            behavior.peekHeight = builder.collapsePosition
                        }
                        if (builder.autoExpandOnShow) {
                            behavior.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }
                }
            )

            dialog?.setCanceledOnTouchOutside(builder.dismissOnTappingBackground)
            builder.isNavigationBarHidden.let {
                if (it) {
                    binding.navigationBar.visibility = View.GONE
                    binding.divider.visibility = View.GONE
                } else {
                    binding.navigationBar.visibility = View.VISIBLE
                    binding.divider.visibility = View.VISIBLE
                }
            }

            builder.fragmentContent?.let {
                (it as? BottomSheetContent)?.let { bottomSheetContent ->
                    bottomSheetContent.parentBottomSheet(dsCallBackContent)
                    bottomSheetContent.setBottomSheetDialog(dialog)
                }

                (it as? BottomSheetLeftIconContent)?.leftIconContent(dsLeftIconCallbackContent)

                val transaction = childFragmentManager.beginTransaction()
                transaction.replace(R.id.contentLayout, it).commit()
            }
            builder.expandPosition.let {
                if (it > 0) {
                    val params = binding.rootView.layoutParams
                    params.height = it
                    binding.rootView.layoutParams = params
                }
            }
            // Code for title
            builder.title?.let {
                if (builder.description.isNullOrEmpty()) {
                    binding.tvTitle.gravity = Gravity.CENTER
                    binding.tvDescription.visibility = View.GONE
                }
                binding.tvTitle.text = it
            }
            builder.description?.let { binding.tvDescription.text = it }

            builder.leftIcon.let { leftIcon ->
                if (leftIcon > 0) {
                    binding.imvCloseLeft.visibility = View.VISIBLE
                    binding.layoutButtonRight.visibility = View.INVISIBLE
                    binding.imvCloseLeft.setImageResource(leftIcon)
                    binding.imvCloseLeft.setOnClickListener {
                        listenerToBottomSheet?.onNavigationBarLeftClick()
                        val onClickListener =
                            childFragmentManager.fragments.find { it is BottomSheetOnClickListener }
                        if (onClickListener as? BottomSheetOnClickListener != null) onClickListener.onLeftIconClick()
                        else dismissAllowingStateLoss()
                    }
                }
            }
            builder.rightIcon.let {
                if (it > 0) {
                    binding.imvRight.setImageResource(it)
                    binding.imvRight.visibility = View.VISIBLE
                    binding.layoutButtonRight.visibility = View.VISIBLE
                    binding.imvRight.setOnClickListener {
                        listenerToBottomSheet?.onNavigationBarRightClick()
                        childFragmentManager.fragments.forEach { fr ->
                            (fr as? BottomSheetContent)?.onRightIconClick()
                        }
                    }
                }
            }
            builder.resButtonRight.let {
                if (it > 0) {
                    binding.tvRight.text = getText(it)
                    binding.tvRight.visibility = View.VISIBLE
                    binding.imvRight.visibility = View.GONE
                    binding.layoutButtonRight.visibility = View.VISIBLE
                }
            }
            binding.tvRight.setOnClickListener {
                listenerToBottomSheet?.onNavigationBarRightClick()
                childFragmentManager.fragments.forEach { fr ->
                    (fr as? BottomSheetContent)?.onRightTextClick()
                }
            }

            builder.titleIcon.let {
                if (it > 0) {
                    binding.ivIcon.setImageResource(it)
                    binding.cardIcon.visibility = View.VISIBLE
                    binding.tvTitle.gravity = Gravity.START
                } else {
                    binding.cardIcon.visibility = View.GONE
                }
            }
            listenerToBottomSheet?.onSheetShown(this, builder.item)
            builder.cancelable.let {
                if (it) {
                    binding.indicator.visibility = View.VISIBLE
                } else {
                    binding.indicator.visibility = View.GONE
                }
                bottomSheetDraggingEnable = builder.cancelable
                this.isCancelable = builder.cancelable
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (::builder.isInitialized) {
            childFragmentManager.fragments.forEach { fr ->
                (fr as? BottomSheetContent)?.onDismissed(this)
            }
            listenerToBottomSheet?.onSheetDismissed(builder.item, dismissEvent)
        }
        listenerToBottomSheet = null
        super.onDismiss(dialog)
    }

    private val dsLeftIconCallbackContent = object :
        BottomSheetLeftIconCallBack {
        override fun setLeftIcon(@DrawableRes leftIcon: Int) {
            binding.imvCloseLeft.visibility = View.VISIBLE
            binding.layoutButtonRight.visibility = View.INVISIBLE
            binding.imvCloseLeft.setImageResource(leftIcon)
        }
    }

    private val dsCallBackContent = object : BottomSheetCallback {
        override fun onSheetEvent(item: Any?) {
            listenerToBottomSheet?.onSheetEvent(item)
        }

        override fun setNavigationStatus(title: String?, description: String?) {
            binding.tvTitle.text = title
            binding.tvDescription.text = description
            if (description.isNullOrEmpty()) {
                binding.tvTitle.gravity = Gravity.CENTER
                binding.tvDescription.visibility = View.GONE
            }
        }

        override fun setNavigationIcon(@DrawableRes icon: Int?) {
            icon?.let {
                binding.ivIcon.setImageResource(it)
                binding.tvTitle.gravity = Gravity.START
                binding.cardIcon.visibility = View.VISIBLE
            }
        }

        override fun setDividerVisible(isVisible: Boolean?) {
            isVisible?.let {
                if (it) {
                    binding.divider.visibility = View.VISIBLE
                } else {
                    binding.divider.visibility = View.GONE
                }
            }
        }

        override fun setButtonRightStatus(isVisible: Boolean?, title: String?) {
            isVisible?.let {
                if (it) {
                    binding.tvRight.visibility = View.VISIBLE
                    binding.imvRight.visibility = View.INVISIBLE
                    binding.layoutButtonRight.visibility = View.VISIBLE
                } else {
                    binding.tvRight.visibility = View.INVISIBLE
                    binding.layoutButtonRight.visibility = View.INVISIBLE
                }
                title?.let { text ->
                    binding.tvRight.text = text
                }
            }
        }

        override fun onDismiss() {
            dismissEvent = BottomSheetListener.DISMISS_EVENT_IN_CONTENT
            dismissAllowingStateLoss()
        }
    }

    /**
     * Builder factory used for creating [BottomSheet]
     */
    class Builder<T> @JvmOverloads constructor(
        cancelable: Boolean = true,
        listener: BottomSheetListener? = null,
        any: Any? = null
    ) {
        var cancelable: Boolean = cancelable; private set
        var listener: BottomSheetListener? = listener; private set
        var item: Any? = any; private set
        var title: String? = null; private set
        var description: String? = null; private set
        var isNavigationBarHidden: Boolean = false; private set
        var dismissOnTappingBackground: Boolean = true; private set
        var peekHeight: Int? = 0; private set
        var autoExpandOnShow: Boolean = true; private set

        @DrawableRes
        var leftIcon: Int = 0
            private set

        @DrawableRes
        var rightIcon: Int = 0
            private set

        @StringRes
        var resButtonRight: Int = 0
            private set

        var fragmentContent: Fragment? = null
            private set

        var collapsePosition: Int = 0
            private set
        var expandPosition: Int = 0
            private set

        @DrawableRes
        var titleIcon: Int = 0
            private set

        /**
         * Sets whether the [BottomSheet] is cancelable with keyboard back.
         *
         * @param cancelable If the dialog can be canceled
         * @return
         */
        fun setCancelable(cancelable: Boolean): Builder<T> {
            this.cancelable = cancelable
            return this
        }

        /**
         * Sets the [BottomSheetListener] to receive callbacks
         *
         * @param listener The [BottomSheetListener] to receive callbacks for
         * @return
         */
        fun setListener(listener: BottomSheetListener?): Builder<T> {
            this.listener = listener
            return this
        }

        /**
         * Sets the [Object] to be passed with the [BottomSheet]
         *
         * @param any
         * @return
         */
        fun any(any: Any?): Builder<T> {
            this.item = any
            return this
        }

        /**
         * Creates the [BottomSheet] but does not show it.
         *
         * @return
         */
        fun create(): BottomSheet<T> {
            val bottomSheetDialogFragment = BottomSheet<T>()
            bottomSheetDialogFragment.builder = this
            bottomSheetDialogFragment.listenerToBottomSheet = this.listener
            return bottomSheetDialogFragment
        }

        /**
         * Creates the [BottomSheet] and shows it.
         *
         * @param manager @link FragmentManager} the [BottomSheetMenuDialogFragment] will be added to
         * @param tag Optional tag for the [BottomSheetDialogFragment]
         */
        @JvmOverloads
        fun show(manager: FragmentManager, tag: String? = null) {
            create().show(manager, tag)
        }

        /**
         * Set title to navigation bar [BottomSheet]
         * @param title
         */
        fun setTitle(title: String): Builder<T> {
            this.title = title
            return this
        }

        /**
         * Set description to navigation bar [BottomSheet] this is a text below title
         * @param description
         */
        fun setDescription(description: String): Builder<T> {
            this.description = description
            return this
        }

        /**
         * Set left icon to navigation bar [BottomSheet] this is a DrawableRes
         * @param @DrawableRes leftIcon
         */
        fun setLeftIcon(@DrawableRes leftIcon: Int): Builder<T> {
            this.leftIcon = leftIcon
            return this
        }

        /**
         * Set right icon to navigation bar [BottomSheet] this is a DrawableRes
         * @param @DrawableRes rightIcon
         */
        fun setRightIcon(@DrawableRes rightIcon: Int): Builder<T> {
            this.rightIcon = rightIcon
            return this
        }

        /**
         * Set a Text button to the right navigation bar [BottomSheet] this is a StringRes
         * @param @StringRes resButtonRight
         */
        fun setTextButtonRight(@StringRes resButtonRight: Int): Builder<T> {
            this.resButtonRight = resButtonRight
            return this
        }

        /**
         * Set State to [BottomSheet]
         * @param collapse
         * @param expand
         * VD: setState(100,150)
         */
        fun setState(collapse: Int, expand: Int): Builder<T> {
            this.collapsePosition = collapse
            this.expandPosition = expand
            return this
        }

        /**
         * Set Hidden or Visible NavigationBar [BottomSheet]
         * @param isNavigationBarHidden true or false
         */
        fun setNavigationBarHidden(isNavigationBarHidden: Boolean): Builder<T> {
            this.isNavigationBarHidden = isNavigationBarHidden
            return this
        }

        /** Set Icon in NavigationBar like avatar [BottomSheet]
         * @param icon @DrawableRes
         */
        fun setIconTitle(@DrawableRes icon: Int): Builder<T> {
            this.titleIcon = icon
            return this
        }

        /** Canceled On TouchOutside [BottomSheet]
         * @param dismissOnTappingBackground true or false
         */
        fun setDismissOnTappingBackground(dismissOnTappingBackground: Boolean): Builder<T> {
            this.dismissOnTappingBackground = dismissOnTappingBackground
            return this
        }

        /** Set content Fragment in [BottomSheet]
         * @param fragment inflater in content view
         * the fragment can implement BottomSheetContent
         */
        fun setContentFragment(fragment: Fragment): Builder<T> {
            fragmentContent = fragment
            return this
        }

        fun setPeekHeight(peekHeight: Int?): Builder<T> {
            this.peekHeight = peekHeight
            return this
        }

        fun setAutoExpand(autoExpandOnShow: Boolean): Builder<T> {
            this.autoExpandOnShow = autoExpandOnShow
            return this
        }
    }
}
