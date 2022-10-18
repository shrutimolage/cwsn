package com.cwsn.mobileapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.TaskFormListDialogBinding
import com.cwsn.mobileapp.model.task.AllTaskList
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.adapter.TaskActivityAdapter
import com.cwsn.mobileapp.view.callback.ITaskActivityCallback
import com.cwsn.mobileapp.view.callback.ITaskDialogCallback
import com.google.gson.Gson

class TaskFormListDialog:DialogFragment()
{
    private var allTaskData: AllTaskList?=null
    private var _binding: TaskFormListDialogBinding?=null
    private val binding get() = _binding!!
    private var callback:ITaskDialogCallback?=null
    companion object {
        val TAG = TaskFormListDialog::class.simpleName
        val TASK_PARAMS="task_params"
        fun newInstance(taskData:String):TaskFormListDialog{
            val bundle=Bundle()
            bundle.putString(TASK_PARAMS,taskData)
            val taskDialog=TaskFormListDialog()
            taskDialog.arguments=bundle
            return taskDialog
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    fun registerTaskDialogCallback(listener:ITaskDialogCallback){
        this.callback=listener
    }

    override fun onDetach() {
        super.onDetach()
        this.callback=null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=TaskFormListDialogBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getString(TASK_PARAMS)
        allTaskData= Gson().fromJson(data,AllTaskList::class.java)
        allTaskData?.data?.forEachIndexed { index, taskData ->
            LoggerUtils.error("TAG TASK","${taskData.formatName}")
        }
        allTaskData?.data?.let {
            binding.rclyTaskActvyList.apply {
                layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                adapter=TaskActivityAdapter(it,object:ITaskActivityCallback{
                    override fun onTaskItemClicked(id: Int?) {
                        callback?.gotoQuestionsScreen(id)
                    }
                })
            }
        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }
}