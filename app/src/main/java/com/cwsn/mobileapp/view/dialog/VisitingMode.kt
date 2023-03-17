package com.cwsn.mobileapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.DialogVisitingmodeBinding
import com.cwsn.mobileapp.model.home.Actist_Data
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.adapter.ActvityLitstAdapterList
import com.cwsn.mobileapp.view.callback.IMonitoringFragCallback
import com.cwsn.mobileapp.view.callback.VisitCallback
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.monitoring.MonitorViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class VisitingMode : DialogFragment() {
    private var clusterid: Int? = null
    private var _binding: com.cwsn.mobileapp.databinding.DialogVisitingmodeBinding? = null
    private val binding get() = _binding!!
    private var selectedFormId: Int? = null
    private val monitorViewModel by inject<MonitorViewModel>()
    private val homeViewModel by inject<HomeViewModel>()
    private var allTaskData: Actist_Data? = null
    private var listener: IMonitoringFragCallback? = null
    private var callback: VisitCallback? = null

    companion object {
        val TAG = VisitingMode::class.simpleName
        const val TASK_PARAMS = "task_params"
        fun newInstance(taskData: String): VisitingMode {
            val bundle = Bundle()
            bundle.putString(TASK_PARAMS, taskData)
            val taskDialog = VisitingMode()
            taskDialog.arguments = bundle
            return taskDialog
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        try {
//            if (context is IForgetPwdCallback) {
//                listener = context
//            }
//        } catch (ex: Exception) {
//            throw ClassCastException("$context must implement IForgetPwdCallback")
//
//        }
//        val bundle = arguments
//        val imageLink = bundle!!.getString("TEXT", "")


//        }
//        val extras = getExtras()
//        if (extras != null) {
//
//            val id = extras.getInt("id")
//            val Name = extras?.getString("name")
//            LoggerUtils.error("id", id.toString())
//            LoggerUtils.error("name", Name)
//
//        }
    }

    //
    fun registerVisitDialogCallback(listener: VisitCallback) {
        this.callback = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(R.color.semiTransparentColor)
        dialog.setCanceledOnTouchOutside(false)
        return dialog


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        clusterid = this.arguments?.getInt("id")
        LoggerUtils.error("id", id.toString())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogVisitingmodeBinding.inflate(inflater, container, false)
        return _binding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val data = arguments?.getString(TASK_PARAMS)
//        allTaskData = Gson().fromJson(data, Actist_Data::class.java)
//
//        allTaskData?.let {
//        allTaskData?.data?.forEachIndexed { index, taskData ->
//            LoggerUtils.error("TAG TASK", taskData.name)
//        }
//            allTaskData.let {
//                binding.rclyActvyList.apply {
//                    layoutManager =
//                        LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
//                    adapter = ActvityLitstAdapterList(it)
//                }
//            }




        // val  id = arguments?.getInt("id",0)
//     val   Name = arguments?.getString("name")

//        binding.imgCloseDialog.setOnClickListener {
//            dismiss()
//        }
//
//        binding.tvSchool.setOnClickListener{
//            val intent=Intent(context,SchoolTypeActivity::class.java)
//            intent.putExtra("id",clusterid)
//            startActivity(intent)
//        }
//
//        binding.tvHome.setOnClickListener{
//            val intent=Intent(context,UserProfileActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.tvResorces.setOnClickListener{
//            val intent=Intent(context,UserProfileActivity::class.java)
//            startActivity(intent)
//        }
//
//
//
//
//
//    }
//
//    private fun showToastMessage(message: String) {
//        val toast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
//        toast.show()
//    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding=null
//    }



                }
        }






