package com.ravi

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ravi.diagnal.api.loadJsonFile
import com.ravi.diagnal.ui.HomeListingViewModel
import com.ravi.libapi.response.Content
import com.ravi.libapi.response.ContentResponse
import com.ravi.libapi.response.DiagnalResponse
import com.ravi.libapi.response.ListingData
import com.ravi.libapi.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

class HomeListingTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var assetManager: AssetManager

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        // Initialize Mockito
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testJsonFile(){
        // Stream object
        val testStream = HomeListingTest::class.java.getResourceAsStream("/listing.json")
        // Linking both context ans assetManager to open file
        doReturn(assetManager).`when`(context).assets
        Mockito.`when`(context.assets.open(anyString())).thenReturn(testStream)

        runTest {
            val sut = loadJsonFile(context, "")
            assertEquals("Romantic Comedy", sut?.page?.title)
            assertEquals(2, sut?.page?.contentItems?.content?.size)
        }
    }

    @Test
    fun testSearchFunctionality() = runBlocking{
        // Create an instance of the ViewModel
        val viewModel = HomeListingViewModel(application)

        // Set up the mock page data
        val mockPage = ListingData(
            title = "Mock Page",
            totalContentItems = "10",
            pageNum = "1",
            pageSize = "5",
            contentItems = ContentResponse(
                content = arrayListOf(
                    Content(name = "Item 1", posterImage = "poster1.jpg"),
                    Content(name = "Item 2", posterImage = "poster2.jpg")
                )
            )
        )

        viewModel._contentData.value = Resource.success(mockPage)

        // Perform the search
        val result = viewModel.contentData.getOrAwaitValue().data?.contentItems?.content?.filter {
            it.name.contains("Item", ignoreCase = true)
        }

        // Verify the expected search results
        assertEquals("Item 1", result?.get(0)?.name)
    }
}