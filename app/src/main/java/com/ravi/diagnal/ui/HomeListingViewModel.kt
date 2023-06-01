package com.ravi.diagnal.ui

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.*
import com.ravi.diagnal.api.loadJsonFile
import com.ravi.libapi.response.ListingData
import com.ravi.libapi.util.Resource
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeListingViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val _contentData = MutableLiveData<Resource<ListingData>>()
    val contentData: LiveData<Resource<ListingData>> = _contentData

    private val _queryText = MutableLiveData<String?>()
    val queryText: LiveData<String?> = _queryText

    private var searchJob: Job? = null
    private val DEBOUNCE_DELAY = 200L
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    var currentFileIndex = 1
    var lastPage = false

    init {
        fetchContentData()
    }

    fun fetchContentData() {
        viewModelScope.launch {
            try {
                val fileName = "CONTENTLISTINGPAGE-PAGE${currentFileIndex}.json"
                val jsonArray = withContext(Dispatchers.IO) {
                    loadJsonFile(getApplication(),fileName)
                }

                jsonArray?.page?.let {
                    if (currentFileIndex <=  it.pageNum.toInt()){
                        // Parse the loaded JSON data
                        _contentData.postValue(Resource.success(it))
                        // Increment the page index for the next lazy loading step
                        currentFileIndex++
                    }else
                        lastPage = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val queryTextListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            _queryText.postValue(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Cancel previous search job if it's running
            searchJob?.cancel()

            // Start a new search job with debounce delay
            if(s.toString().length >= 3) {
                searchJob = coroutineScope.launch {
                    delay(DEBOUNCE_DELAY)
                    _queryText.postValue(s.toString())
                }
            }
        }
    }
}