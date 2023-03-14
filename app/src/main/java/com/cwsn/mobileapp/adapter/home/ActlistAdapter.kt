package com.cwsn.mobileapp.adapter.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.RowDashboardGridItemLayoutBinding
import com.cwsn.mobileapp.model.home.AllActData
import com.cwsn.mobileapp.model.home.ItemCount
import com.cwsn.mobileapp.view.activity.FieldActivity
import com.cwsn.mobileapp.view.activity.QuestionActivity
import com.gun0912.tedpermission.provider.TedPermissionProvider
import kotlin.contracts.contract

class ActlistAdapter(private val data: List<AllActData>) :
    RecyclerView.Adapter<ActlistAdapter.CourseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActlistAdapter.CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.actlist_row,
            parent, false
        )

        return CourseViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ActlistAdapter.CourseViewHolder, position: Int) {
        holder.courseNameTV.text = data.get(position).name
        holder.courseIV.setOnClickListener {
            if (data.get(position).id == 2) {
                val intent = Intent(TedPermissionProvider.context, FieldActivity::class.java)
                intent.putExtra("typeid", 2)
//            intent.putExtra("schooladress",schoolList.address)
//            intent.putExtra("id",schoolList.id)

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                TedPermissionProvider.context.startActivity(intent)
            }

        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNameTV: TextView = itemView.findViewById(R.id.tv_Fieldvisit)
        val courseIV: ImageView = itemView.findViewById(R.id.Fieldvisit)
    }
}
