package com.ntt.oneappprototyping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Right
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.ntt.oneappprototyping.home.HomeFragmentViewModel
import com.ntt.oneappprototyping.model.Success
import com.ntt.oneappprototyping.model.User
import com.ntt.oneappprototyping.networking.OnlineRepository
import com.ntt.oneappprototyping.networking.StubboRetrofitFactory
import com.ntt.oneappprototyping.networking.rest.StubboOneAppApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProvaTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private var api: StubboOneAppApi = mock()
    private var repo = OnlineRepository(api)

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner

    @Mock
    private lateinit var stringObserver: Observer<String>

    val vm = HomeFragmentViewModel(repo)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        vm.liveUser.observe(lifeCycleTestOwner, stringObserver)
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }

    @Test
    fun chenneso() {
        testCoroutineRule.testCoroutineDispatcher.runBlockingTest {
            doReturn(Right(User("1", "Pippo", "Lone")))
                .`when`(api).getUser()

            lifeCycleTestOwner.onResume()
            vm.onButtonClicked()
            verify(stringObserver).onChanged("Pippo Lone")
        }
    }
}