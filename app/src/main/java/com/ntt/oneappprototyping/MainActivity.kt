package com.ntt.oneappprototyping

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.ntt.oneappprototyping.base_components.BaseActivity
import com.ntt.oneappprototyping.databinding.ActivityMainBinding
import com.ntt.oneappprototyping.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val vm: ViewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.inTransaction {
            add(R.id.fragment_container, HomeFragment())
        }
    }
}