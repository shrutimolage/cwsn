package com.cwsn.mobileapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.FragmentSchoolDetailsBinding
import com.cwsn.mobileapp.model.school.SchoolCountData
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.view.adapter.SchoolAllDataAdapter
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.ISchoolListCallback
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import org.koin.android.ext.android.inject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SchoolDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("MoveLambdaOutsideParentheses")
class SchoolDetailsFragment : BaseFragment<FragmentSchoolDetailsBinding>(FragmentSchoolDetailsBinding::inflate) {
    private var param1: String? = null
    private var param2: String? = null
    private var listener:ISchoolListCallback?=null
    private val homeViewModel by inject<HomeViewModel>()
    private var schoolCountList:MutableList<SchoolCountData> = mutableListOf()

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
            if(context is ISchoolListCallback){
                listener=context
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
            throw ClassCastException(context.toString()
                    + " must implement ISchoolListCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.schoolToolbar.toolbarTitle.text="SCHOOL DETAILS"
        binding.schoolToolbar.navigationBar.setOnClickListener {
            listener?.onUserBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getAllSchoolDetailCount().observe(viewLifecycleOwner, { response->
            when(response.status){
                Status.LOADING->{
                    listener?.showProgress()
                }
                Status.SUCCESS->{
                    listener?.hideProgress()
                    response.data?.body()?.data?.let {
                        schoolCountList.add(SchoolCountData("Cwsn Enrollment",it.cwsn_enrollment, R.drawable.ic_visited_school_icon))
                        schoolCountList.add(SchoolCountData("School Having Cwsn",it.schoolHavingCWSN,R.drawable.ic_school_icon))
                        schoolCountList.add(SchoolCountData("School Having Ramps",it.schoolHavingRamps,R.drawable.ic_school_icon))
                        schoolCountList.add(SchoolCountData("School Having Cwsn Toilet",it.totalCwsnToilet,R.drawable.ic_school_icon))
                        binding.rclySchoolDetails.apply {
                            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL, false)
                            adapter=SchoolAllDataAdapter(schoolCountList as ArrayList<SchoolCountData>)
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
       /* homeViewModel.getSchoolData(requireActivity(),R.raw.school_api).observe(viewLifecycleOwner,
            { response->
                when(response.status){
                    Status.LOADING->{
                        listener?.showProgress()
                    }
                    Status.SUCCESS->{
                        listener?.hideProgress()
                        response.data?.details?.let { dataList->
                            if(dataList.size>0){
                                binding.rclySchoolDetails.apply {
                                    layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL, false)
                                    adapter=SchoolAllDataAdapter(dataList)
                                }
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
            })*/
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SchoolDetailsFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SchoolDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}