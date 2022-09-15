package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowSideDrawerItemBinding
import com.cwsn.mobileapp.model.home.SlideModel
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.callback.IDrawerItemCallback

class SlidePanelAdapter(
    private var drawerList: MutableList<SlideModel>,
    private val listener: IDrawerItemCallback
) : RecyclerView.Adapter<SlidePanelAdapter.SlideViewHolder>() {
    inner class SlideViewHolder(private val binding: RowSideDrawerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(slideModel: SlideModel, listener: IDrawerItemCallback) {
            LoggerUtils.error("TAG", "item ${slideModel.itemTitle}")
            binding.txtDrawerTitle.text = slideModel.itemTitle
            binding.imgDrawerIcon.setImageResource(slideModel.selectedIcon)
            binding.llDrawerItem.setOnClickListener {
                listener.onItemClicked(slideModel.itemTitle)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding =
            RowSideDrawerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlideViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val slideModel = drawerList[position]
        holder.bindItems(slideModel, listener)
    }

    override fun getItemCount(): Int {
        return drawerList.size
    }

    fun refreshAdapter(drawerList: MutableList<SlideModel>) {
        this.drawerList = drawerList
        notifyDataSetChanged()
    }
}