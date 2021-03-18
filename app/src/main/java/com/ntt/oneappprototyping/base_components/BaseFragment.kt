package com.ntt.oneappprototyping.base_components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.ntt.oneappprototyping.bindingInflate

abstract class BaseFragment<BindingType : ViewDataBinding>(@LayoutRes private val layout: Int): Fragment(){
    protected lateinit var binding: BindingType
    abstract val vm: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflate(inflater, layout, container)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}