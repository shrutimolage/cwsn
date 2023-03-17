package com.cwsn.mobileapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.ClusterNamelistBinding
import com.cwsn.mobileapp.model.home.ClusterData
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.activity.FieldActivity
import com.cwsn.mobileapp.view.activity.VisitActivity
import com.cwsn.mobileapp.view.callback.IClusterListItemClick
import com.cwsn.mobileapp.view.fragment.MonitoringFragment

class ClusterAdapterlist(
    val activity: FragmentActivity, var clusterlist: List<ClusterData>,
    type_id: Int?,
    private val listener: IClusterListItemClick
) : RecyclerView.Adapter<ClusterAdapterlist.ViewHolder>() {

    var typeid: Int? = null
    var clusterListFiltered: List<ClusterData> = clusterlist

    init {
        clusterListFiltered = clusterlist as ArrayList<ClusterData>
        typeid = type_id

    }


    //   val activity: Context?=null
    inner class ViewHolder(private val binding: ClusterNamelistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(clusterdata: ClusterData) {

            binding.clustername.text = clusterdata.name.toString()

            binding.clustername.setOnClickListener {

//                   schoolList.get(position).id?.let { it1 ->
//                       activity.deatailsList((schoolList.get(position).name),
//                           it1
//                       )
//                   }
            }
            binding.imgStartSurvey.setOnClickListener {
                listener.IClusterListItemClick(
                    clusterlist.get(position).name.toString(),
                    clusterlist.get(position).id
                )
                //  listener.showDialog(clusterlist.get(position).id)
                LoggerUtils.error("CLUSTERID", "" + clusterlist.get(position).id)

                val intent = Intent(itemView.context, VisitActivity::class.java)
                intent.putExtra("typeid", typeid)
                itemView.context.startActivity(intent)
                //    clusterlist.get(position).id?.let { it1 ->
                //      activity.showVisitingListDialog()
            }


//                    val id=schoolList.get(position).id
//                    if (id != null) {
//                        activity.deatailsList(id)

        }


//                    val id=schoolList.get(position).id
//                    intent.putExtra("title",id)


        // Toast.makeText(Context,""+binding.clustername.id.toString(),Toast.LENGTH_LONG).show()

//                binding.imgStartSurvey.setOnClickListener {
//
//                    val dialog = activity?.let { it1 -> Dialog(it1.applicationContext) }
//                    if (dialog != null) {
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                    }
//                    if (dialog != null) {
//                        dialog.setContentView(R.layout.dialog_visitingmode)
//                    };
//                    if (dialog != null) {
//                        dialog.show()
//                    };
//
//
//                }

    }

    fun filterList(filterlist: ArrayList<ClusterData>) {
        // below line is to add our filtered
        // list in our course array list.
        clusterlist = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ClusterNamelistBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(clusterListFiltered.get(position))


    }

    override fun getItemCount(): Int {
        return clusterListFiltered.size
    }
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charSearch = constraint.toString()
//                if (charSearch.isEmpty()) {
//                    clusterListFiltered = clusterlist as ArrayList<ClusterData>
//                } else {
//                    val resultList = ArrayList<ClusterData>()
//                    for (row in clusterlist) {
//                        if (row.name?.toLowerCase()?.contains(constraint.toString().toLowerCase()) == true) {
//                            resultList.add(row)
//                        }
//                    }
//                   clusterListFiltered = resultList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = clusterListFiltered
//                return filterResults
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//               clusterListFiltered = results?.values as ArrayList<ClusterData>
//                notifyDataSetChanged()
//            }
//        }
//    }


}