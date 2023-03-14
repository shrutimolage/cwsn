package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    inner class ViewHolder(private val binding: RowTaskActivityLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(acvityList: Actlist_data /* = java.util.ArrayList<com.cwsn.mobileapp.model.home.Actlist_data> */) {
            binding.tvFieldName.text = acvityList.name
            binding.tvFieldName.setOnClickListener {
                listener.getTaskId(acvityList.id)
//                val intent  = Intent (TedPermissionProvider.context, QuestionActivity::class.java)
//                intent.putExtra("schoolname",schoolList.name)
//                intent.putExtra("schooladress",schoolList.address)
//                intent.putExtra("id",schoolList.id)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                TedPermissionProvider.context.startActivity(intent)


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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(ActvityList[position])
    }

    override fun getItemCount(): Int {
        return ActvityList.size
    }
}