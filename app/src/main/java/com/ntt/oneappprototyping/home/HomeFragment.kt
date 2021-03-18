package com.ntt.oneappprototyping.home

import android.os.Bundle
import android.view.View
import arrow.core.validNel
import com.ntt.oneappprototyping.R
import com.ntt.oneappprototyping.base_components.BaseFragment
import com.ntt.oneappprototyping.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val vm by viewModel<HomeFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = vm
    }
}