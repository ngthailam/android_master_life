package vn.thailam.android.masterlife.app.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import vn.thailam.android.masterlife.R

fun FragmentTransaction.applySlideInUp(): FragmentTransaction {
    return this
        .setCustomAnimations(R.anim.slide_in_up, 0, 0, R.anim.slide_out_down)
}
