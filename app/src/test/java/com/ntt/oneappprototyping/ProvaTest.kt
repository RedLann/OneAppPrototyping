package com.ntt.oneappprototyping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ntt.oneappprototyping.home.HomeFragmentViewModel
import com.ntt.oneappprototyping.model.User
import com.ntt.oneappprototyping.networking.OnlineRepository
import com.ntt.oneappprototyping.networking.StubboRetrofitFactory
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProvaTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: ApiHelper

    val repo = OnlineRepository(StubboRetrofitFactory.createService())

    @Mock
    private lateinit var stringObserver: Observer<String>

    @Test
    fun chenneso() {
        testCoroutineRule.runBlockingTest {
            val viewModel = HomeFragmentViewModel(repo)
            viewModel.liveUser.observeForever {
                assertEquals("Pippo Lone", it)
            }
            viewModel.onButtonClicked()
        }
    }
}

interface ApiHelper {
    suspend fun getUser(): User
}