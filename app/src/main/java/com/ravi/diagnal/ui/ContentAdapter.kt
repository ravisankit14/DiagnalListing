package com.ravi.diagnal.ui

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ravi.diagnal.R
import com.ravi.diagnal.databinding.LayoutItemsBinding
import com.ravi.diagnal.util.highlightText
import com.ravi.libapi.response.Content

class ContentAdapter(private val mContext: Context, private var contentList: ArrayList<Content>):
    RecyclerView.Adapter<ContentAdapter.DataViewHolder>(), Filterable {

    var filterListOfObjects: ArrayList<Content> = ArrayList()
    var tempListOriginal: ArrayList<Content> = ArrayList()

    var searchQuery = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = LayoutItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding, mContext, searchQuery)
    }

    override fun getItemCount(): Int  = contentList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(contentList[position], position)
    }

    fun addData(data: ArrayList<Content>) {
        contentList.addAll(data)
        tempListOriginal.addAll(data)
        filterListOfObjects.addAll(data)

        notifyDataSetChanged()
    }

    class DataViewHolder(private val binding: LayoutItemsBinding,private val mContext: Context, private val searchQuery: String): BaseViewHolder(binding.root){
        override fun bind(lot: Content, position: Int) {

            if(searchQuery.isNotEmpty()) {
                val highlightedText = highlightText(lot.name, searchQuery)
                binding.tvName.text = highlightedText
            }else{
                binding.tvName.text = lot.name
            }

            Glide.with(mContext)
                .load(Uri.parse("file:///android_asset/${lot.posterImage}"))
                .placeholder(R.drawable.placeholder_for_missing_posters)
                .fitCenter()
                .into(binding.tvPoster)
        }

    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(lot: Content, position: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                contentList = filterResults.values as? ArrayList<Content> ?: ArrayList()
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                searchQuery = charSequence?.toString() ?: ""

                val filterResults = FilterResults()

                val filteredItems = if (searchQuery.isEmpty())
                    tempListOriginal
                else
                    filterListOfObjects.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }

                filterResults.values = ArrayList(filteredItems)
                return filterResults
            }
        }
    }
}