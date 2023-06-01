package com.ravi.diagnal.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ravi.diagnal.DiagnalApplication
import com.ravi.diagnal.databinding.ActivityHomeListingBinding
import com.ravi.diagnal.util.*
import com.ravi.libapi.response.Content
import com.ravi.libapi.util.Status
import javax.inject.Inject

class HomeListingActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val mViewModel by lazy { getViewModel<HomeListingViewModel>() }
    private lateinit var mBinding: ActivityHomeListingBinding
    private lateinit var mAdapter: ContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeListingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        (this.application as DiagnalApplication).appComponent.inject(this)

        setUpUi()
        setUpObserver()
    }

    private fun setUpUi() {

        val gridLayoutManager = GridLayoutManager(this, getGridColumnCount(this))
        mBinding.rvContent.layoutManager = gridLayoutManager
        mAdapter = ContentAdapter(this,
            arrayListOf()
        )

        mBinding.rvContent.adapter = mAdapter

        mBinding.ivBack.setOnClickListener {
            finish()
        }

        mBinding.ivSearchCancel.setOnClickListener {
            mBinding.clHeaderTitle.visibility = View.VISIBLE
            mBinding.clHeaderSearch.visibility = View.GONE
            mBinding.edtSearch.text.clear()
            mAdapter.filter.filter("")
            mBinding.edtSearch.hideKeyboard()
            mAdapter.notifyDataSetChanged()
        }

        mBinding.ivSearch.setOnClickListener {
            mBinding.clHeaderTitle.visibility = View.GONE
            mBinding.clHeaderSearch.visibility = View.VISIBLE
            mBinding.edtSearch.showKeyboard()
        }

        mBinding.rvContent.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
            override fun loadMoreItems() {
                if(!mViewModel.lastPage && mViewModel.queryText.value.isNullOrEmpty())
                    mViewModel.fetchContentData()
            }

            override val isLastPage: Boolean = mViewModel.lastPage
        })

        mBinding.edtSearch.addTextChangedListener(mViewModel.queryTextListener)
    }

    private fun setUpObserver() {

        mViewModel.contentData.observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let {data ->
                        mBinding.tvTitle.text = data.title
                        renderList(data.contentItems.content)
                    }
                }

                Status.LOADING -> {}

                Status.ERROR -> {}
            }
        }

        mViewModel.queryText.observe(this) {
            mAdapter.filter.filter(it)
        }
    }
    private fun renderList(data: ArrayList<Content>) {
        mAdapter.addData(data)
    }
}