package com.ntt.oneappprototyping.base_components

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.ntt.oneappprototyping.bindingInflate

abstract class BaseActivity<BindingType : ViewDataBinding>(@LayoutRes private val layout: Int) :
    AppCompatActivity() {
    protected lateinit var binding: BindingType
    abstract val vm: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflate(layout)
        binding.lifecycleOwner = this
    }
}