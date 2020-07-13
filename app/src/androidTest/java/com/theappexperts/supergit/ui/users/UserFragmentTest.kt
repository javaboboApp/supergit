package com.theappexperts.supergit.ui.users

import androidx.lifecycle.Lifecycle.State


import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.ui.BaseFragment
import com.theappexperts.supergit.utils.Event
import com.theappexperts.supergit.utils.Resource
import com.theappexperts.supergit.utils.TestUtils.TEST_ERROR_GET_USERS
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4ClassRunner::class)
class UserFragmentTest : KoinTest {
    private lateinit var fragmentScenerio: FragmentScenario<UserFragment>


    @Mock
    private lateinit var userViewModel: UserViewModel

    @Mock
    private lateinit var uiComunicator: BaseFragment.CommunicatorsInterface

    private var repoLiveData: MediatorLiveData<Resource<List<GitUser>>>? = null

    private fun initFragment(): FragmentScenario<UserFragment> {
        return launchFragmentInContainer(
            fragmentArgs = null, // Bundle
            themeResId = R.style.AppTheme
        )
    }

    @Before
    fun setUp() {
        repoLiveData = MediatorLiveData<Resource<List<GitUser>>>()
        MockitoAnnotations.initMocks(this);
        Mockito.doReturn(repoLiveData).`when`(userViewModel).getCurrentUsers()
        //INIT KOIN AND OVERRIDE THE VIEWMODEL
        loadKoinModules(
            module {

                viewModel(override = true) {
                    userViewModel
                }
            })

        // Create a graphical FragmentScenario
        fragmentScenerio = initFragment()
        fragmentScenerio.moveToState(State.STARTED)

        fragmentScenerio.onFragment { fragment ->
            fragment.uiCommunicatorInterface = uiComunicator
        }

    }

    @After
    fun doAfter() {
        repoLiveData = null
    }

    @Test
    fun test_loading_is_called_when_get_users_called() {

        repoLiveData?.postValue(Resource.loading(null))
        //verify uiComunicator.showProgressBar has been called
        Mockito.verify(uiComunicator).showProgressBar()
    }

    @Test
    fun test_hide_progress_bar_is_called_when_get_users_return_error() {
        repoLiveData?.postValue(Resource.error(TEST_ERROR_GET_USERS, null))
        //verify uiComunicator.hideProgressBar has been called
        Mockito.verify(uiComunicator).hideProgressBar()
    }


    @Test
    fun test_verify_user_fragment_is_visible() {
        fragmentScenerio.moveToState(State.RESUMED)

        Espresso.onView(withId(R.id.fragment_user))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}