package com.ravi

import android.content.Context
import android.content.res.AssetManager
import com.ravi.diagnal.api.loadJsonFile
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

class HomeListingTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var assetManager: AssetManager

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
            Assert.assertEquals("Romantic Comedy", sut?.page?.title)
            Assert.assertEquals(2, sut?.page?.contentItems?.content?.size)
        }
    }

    @After
    fun tearDown(){

    }
}