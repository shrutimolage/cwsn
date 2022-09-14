package com.cwsn.mobileapp.view.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.view.callback.IAlertDialogCallback

/**
Created by  on 30,June,2022
 **/
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB:ViewBinding>(private val inflate: Inflate<VB>):Fragment(),BaseViewInterface
{
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun getContext(): Context? {
        return requireActivity()
    }

    fun showAppAlert(_context:Context,title:String,mesg:String,listener: IAlertDialogCallback?){
        val builder= AlertDialog.Builder(_context)
        val dialogView=LayoutInflater.from(_context).inflate(R.layout.alert_dialog_ok_button,null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val alertDialog=builder.create()
        val titleView: TextView =dialogView.findViewById(R.id.tv_dialogTitle)
        val messageView: TextView =dialogView.findViewById(R.id.tv_dialogMessage)
        val alertButton: TextView =dialogView.findViewById(R.id.tv_alertButton)
        titleView.text=title
        messageView.text=mesg
        alertButton.setOnClickListener {
            listener?.onPositiveButtonClick()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    fun showAppAlert(_context:Context,positiveLabel:String,negativeLabel:String,title:String,mesg:String,listener:IAlertDialogCallback?){
        val builder= AlertDialog.Builder(_context)
        val dialogView=LayoutInflater.from(_context).inflate(R.layout.alert_dialog_two_button,null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val alertDialog=builder.create()
        val titleView: TextView =dialogView.findViewById(R.id.tv_dialogTitle)
        val messageView: TextView =dialogView.findViewById(R.id.tv_dialogMessage)
        val negativeButton: TextView =dialogView.findViewById(R.id.tv_negativeButton)
        val positiveButton: TextView =dialogView.findViewById(R.id.tv_positiveButton)
        negativeButton.text=negativeLabel
        positiveButton.text=positiveLabel
        titleView.text=title
        messageView.text=mesg
        negativeButton.setOnClickListener {
            alertDialog.dismiss()
            listener?.onNegativeButtonClick()
        }
        positiveButton.setOnClickListener {
            alertDialog.dismiss()
            listener?.onPositiveButtonClick()
        }
        alertDialog.show()
    }

    fun showAppConfirmationDialog(_context:Context,title:String,mesg:String,listener: IAlertDialogCallback?){
        val builder= AlertDialog.Builder(_context)
        val dialogView=LayoutInflater.from(_context).inflate(R.layout.alert_dialog_two_button,null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val alertDialog=builder.create()
        val titleView: TextView =dialogView.findViewById(R.id.tv_dialogTitle)
        val messageView: TextView =dialogView.findViewById(R.id.tv_dialogMessage)
        val tv_negativeButton: TextView =dialogView.findViewById(R.id.tv_negativeButton)
        val tv_positiveButton: TextView =dialogView.findViewById(R.id.tv_positiveButton)
        titleView.text=title
        messageView.text=mesg
        tv_positiveButton.setOnClickListener {
            alertDialog.dismiss()
            listener?.onPositiveButtonClick()
        }
        tv_negativeButton.setOnClickListener {
            alertDialog.dismiss()
            listener?.onNegativeButtonClick()
        }
        alertDialog.show()
    }

    fun showCustomToast(_context:Context,mesg:String){
        val toast= Toast(_context)
        val toastView=LayoutInflater.from(_context).inflate(R.layout.toast_layout,null)
        val tvMessage: TextView =toastView.findViewById(R.id.tvMessage)
        tvMessage.text = mesg
        toast.view=toastView
        toast.show()
    }
}