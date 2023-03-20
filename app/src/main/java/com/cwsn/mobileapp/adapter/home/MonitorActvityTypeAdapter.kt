package com.cwsn.mobileapp.adapter.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.model.home.AllActData
import com.cwsn.mobileapp.view.callback.IActivityTypeItemClick
import com.cwsn.mobileapp.view.fragment.MonitoringFragment


class MonitorActvityTypeAdapter(
    private val activty: MonitoringFragment,
    private val data: List<AllActData>,
    private val listner: IActivityTypeItemClick
) :
    RecyclerView.Adapter<MonitorActvityTypeAdapter.CourseViewHolder>() {
    private var selectedItemPosition: Int?=null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MonitorActvityTypeAdapter.CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.actlist_row,
            parent, false
        )


        return CourseViewHolder(itemView)

    }


    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged", "Range")
    override fun onBindViewHolder(holder: MonitorActvityTypeAdapter.CourseViewHolder, @SuppressLint("RecyclerView") position: Int) {


        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged()

        }
        if(selectedItemPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2FF"))

            if (data.get(position).id == 2) {
                //    holder.courseIV.setColorFilter(R.color.selecteditem_color)

                listner.getActvityTypeId(data.get(position).id)
                activty.fecthcluster()

            }
            else{
//                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                activty.inoffice()

            }


        } else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }


//            if (data.get(position).id == 2) {
//                //    holder.courseIV.setColorFilter(R.color.selecteditem_color)
//
//                listner.getActvityTypeId(data.get(position).id)
//                activty.fecthcluster()
//
//            }
//            else{
//                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
//                activty.inoffice()
//
//            }







    if (data.get(position).id == 2) {
        holder.courseIV.setImageResource(R.drawable.field_visit)

    } else {
        holder.courseIV.setImageResource(R.drawable.in_office)
    }
    holder.courseNameTV.text = data.get(position).name




    }




    override fun getItemCount(): Int {
        return data.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNameTV: TextView = itemView.findViewById(R.id.tv_Fieldvisit)
        val courseIV: ImageView = itemView.findViewById(R.id.Fieldvisit)
        val linear:LinearLayout=itemView.findViewById(R.id.linear)
    }
}
