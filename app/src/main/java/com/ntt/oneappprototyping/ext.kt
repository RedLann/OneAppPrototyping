package com.ntt.oneappprototyping

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import arrow.core.Either

fun <T : ViewDataBinding> bindingInflate(
    inflater: LayoutInflater,
    layout: Int,
    container: ViewGroup?
): T {
    return DataBindingUtil.inflate(inflater, layout, container, false)
}

fun <T : ViewDataBinding> Activity.bindingInflate(layout: Int): T {
    return DataBindingUtil.setContentView(this, layout)
}

fun visibility(visibility: Int, vararg views: View?) {
    views.forEach {
        it?.visibility = visibility
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

inline fun <A, B, C> Either<A, B>.networkFold(ifFail: (A) -> C, ifSuccess: (B) -> C): C = when (this) {
    is Either.Right -> ifSuccess(b)
    is Either.Left -> ifFail(a)
}

inline fun <A, B, C> Either<A, B>.ifSuccess(f: (B) -> C): Either<A, C> = map(f)
inline fun <A, B, C> Either<A, B>.ifSailure(f: (A) -> C): Either<C, B> = mapLeft(f)
