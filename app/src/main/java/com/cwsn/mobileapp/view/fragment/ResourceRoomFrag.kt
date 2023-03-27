@file:Suppress("MoveLambdaOutsideParentheses")

package com.cwsn.mobileapp.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.FragmentResourceRoomBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.view.adapter.ResrceRoomAdapter
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.IResRoomListCallback
import com.cwsn.mobileapp.view.callback.IResourceRoomCallback
import com.cwsn.mobileapp.view.callback.ISchoolListCallback
import com.cwsn.mobileapp.view.dialog.ResourceRoomDialog
import com.cwsn.mobileapp.viewmodel.resourceroom.ResRoomViewModel
import org.koin.android.ext.android.inject
import java.lang.Exception

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResourceRoomFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResourceRoomFrag : BaseFragment<FragmentResourceRoomBinding>(FragmentResourceRoomBinding::inflate) {
    private var param1: String? = null
    private var param2: String? = null
    private var listener:IResourceRoomCallback?=null
    private val resRoomViewModel by inject<ResRoomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            if(context is IResourceRoomCallback){
                listener=context
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
            throw ClassCastException(context.toString()
                    + " must implement IResourceRoomCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.toolbarTitle.text="Resource Room"
        binding.toolbar.navigationBar.setOnClickListener {
            listener?.onUserBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        resRoomViewModel.getAllResourceRoomDetails(requireActivity(),R.raw.resource_room)
            .observe(viewLifecycleOwner, { response->
                when(response.status)
                {
                    Status.LOADING->{
                        listener?.showProgress()
                    }
                    Status.SUCCESS->{
                        listener?.hideProgress()
                        response.data?.resRoomList?.let { resList->
                            binding.rclyResourceRoom.apply {
                                layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                                adapter= ResrceRoomAdapter(resList,object:IResRoomListCallback{
                                    override fun onItemClick(itemName: String, itemCount: Int) {
                                        val resRoomDialog=ResourceRoomDialog.newInstance(itemName,itemCount)
                                        resRoomDialog.show(requireActivity().supportFragmentManager,ResourceRoomDialog.TAG)
                                    }
                                })
                            }
                        }
                    }
                    Status.ERROR->{
                        listener?.hideProgress()
                        response.message?.let {
                            showAppAlert(requireActivity(),"Alert",it,null)
                        }
                    }
                }
            })
    }


    companion object {
        val TAG: String="GrievanceFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GreivanceFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResourceRoomFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}