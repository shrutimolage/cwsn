package com.cwsn.mobileapp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.RowTaskActivityLayoutBinding
import com.cwsn.mobileapp.model.home.Actlist_data
import com.cwsn.mobileapp.model.school.SchoolData
import com.cwsn.mobileapp.view.activity.VisitActivity
import com.cwsn.mobileapp.view.callback.IActivityTypeCallback
import org.koin.android.ext.android.get

class ActvityLitstAdapterList(
    private val Actvity: VisitActivity,
    private val ActvityList: ArrayList<Actlist_data>,
    private val listener: IActivityTypeCallback
) : RecyclerView.Adapter<ActvityLitstAdapterList.ViewHolder>() {
    private var selectedItemPosition: Int?=null
    inner class ViewHolder(private val binding: RowTaskActivityLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(acvityList: Actlist_data /* = java.util.ArrayList<com.cwsn.mobileapp.model.home.Actlist_data> */) {
            binding.tvFieldName.text = acvityList.name

            if(position==0){
                binding.imageView3.setImageResource(R.drawable.schoolactivity)
            }
            if(position==1){
                binding.imageView3.setImageResource(R.drawable.kgbv)
            }
            if(position==2){
                binding.imageView3.setImageResource(R.drawable.resource_activity)

            }
            if(position==3){
                binding.imageView3.setImageResource(R.drawable.homebased_education)

            }



            binding.tvFieldName.setOnClickListener {
                listener.getTaskId(acvityList.id)

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowTaskActivityLayoutBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItems(ActvityList[position])
        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2FF"))
        }
        else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return ActvityList.size
    }
}