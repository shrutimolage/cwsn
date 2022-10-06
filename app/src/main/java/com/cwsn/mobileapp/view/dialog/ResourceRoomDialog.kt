package com.cwsn.mobileapp.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.cwsn.mobileapp.databinding.DialogResourceRoomDialogBinding

class ResourceRoomDialog: DialogFragment()
{
    private var detailsItemCount:Int=0
    private var detailsName: String?=null
    private var _binding: DialogResourceRoomDialogBinding?=null
    private val binding get() = _binding!!
    companion object{
        private val PARAM_ITEMCOUNT: String="itemCount"
        private val PARAM_ITEMNAME: String="itemName"
        val TAG: String? =ResourceRoomDialog::class.simpleName
        fun newInstance(itemName:String,itemCount:Int): ResourceRoomDialog {
            val fragment=ResourceRoomDialog()
            val bundle=Bundle()
            bundle.putString(PARAM_ITEMNAME,itemName)
            bundle.putInt(PARAM_ITEMCOUNT,itemCount)
            fragment.arguments=bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onStart() {
        super.onStart()
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
        _binding=DialogResourceRoomDialogBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsName = arguments?.getString(PARAM_ITEMNAME)
        detailsItemCount=arguments?.getInt(PARAM_ITEMCOUNT,0)!!
        binding.imgCloseDialog.setOnClickListener {
            dismiss()
        }
        binding.tvItemName.text=detailsName
        binding.tvItemCount.text="Total : $detailsItemCount"
    }
}