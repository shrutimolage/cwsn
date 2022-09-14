package com.cwsn.mobileapp.view.base

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.view.callback.IAlertDialogCallback

/**
Created by  on 20,June,2022
 **/
@Suppress("DEPRECATION")
abstract class BaseActivity<VB:ViewBinding> : AppCompatActivity(),BaseViewInterface
{
    lateinit var binding : VB
    private var toolbar: Toolbar? = null
    private var toolbarBackArw: ImageView? = null
    private var progressDialog: Dialog? = null

    override fun onStart() {
        super.onStart()
        onActStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isFullScreenActivity()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.hide(WindowInsets.Type.statusBars())
                window.setDecorFitsSystemWindows(false)
            }
            else{
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
        binding=inflateLayout(layoutInflater)

        setContentView(binding.root)
        if (isToolBarEnable()) {
            setSupportActionBar(getToolBar())
            toolbar = getToolBar()
            getToolBarTitleView()?.text = getToolBartTitle()
            supportActionBar?.setDisplayShowTitleEnabled(false)
            if (isBackArrowEnabled()) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            else{
                toolbarBackArw = getToolBarBackArrowView()
                toolbarBackArw?.setOnClickListener {
                    onToolbarBackArrowPress()
                }
            }
            toolbar?.setNavigationOnClickListener {
                onToolbarBackArrowPress()
            }
        }
        else{
            supportActionBar?.hide()
        }
        if (isSetUpProgressDialog()) {
            getContext()?.let {
                progressDialog=setUpProgressDialog(it)
            }

        }
        onActCreate()
    }

    override fun onResume() {
        super.onResume()
        onActResume()
    }

    override fun onPause() {
        super.onPause()
        onActPause()
    }

    override fun onStop() {
        super.onStop()
        onActStop()
    }

    override fun showProgressDialog() {
        if(!progressDialog?.isShowing!!){
            progressDialog?.show()
        }
    }

    override fun getProgressDialog(): Dialog? {
        return progressDialog
    }

    override fun hideProgressDialog() {
       progressDialog?.isShowing?.let {
           if(it){
               progressDialog?.hide()
           }
       }
    }

    fun showCustomToast(_context:Context,mesg:String){
        val toast= Toast(_context)
        val toastView=LayoutInflater.from(_context).inflate(R.layout.toast_layout,null)
        val tvMessage:TextView=toastView.findViewById(R.id.tvMessage)
        tvMessage.text = mesg
        toast.view=toastView
        toast.show()
    }
     fun setUpProgressDialog(context: Context): Dialog? {
         val progressDialog = Dialog(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.progress_dialog_bar, null)
        val title = view.findViewById<TextView>(R.id.progress_title)
        title.text = setProgressDialogTitle()
        progressDialog.setContentView(view)
        progressDialog.setCancelable(false)
        progressDialog.getWindow()?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(context, R.color.transparent)
            )
        )
        return progressDialog
    }

    override fun closeSoftKeyboard(view: View) {
        val imm = getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showAppAlert(_context:Context,title:String,mesg:String,listener: IAlertDialogCallback?){
        val builder= AlertDialog.Builder(_context)
        val dialogView=LayoutInflater.from(_context).inflate(R.layout.alert_dialog_ok_button,null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val alertDialog=builder.create()
        val titleView:TextView=dialogView.findViewById(R.id.tv_dialogTitle)
        val messageView:TextView=dialogView.findViewById(R.id.tv_dialogMessage)
        val alertButton:TextView=dialogView.findViewById(R.id.tv_alertButton)
        titleView.text=title
        messageView.text=mesg
        alertButton.setOnClickListener {
            listener?.onPositiveButtonClick()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    fun showAppConfirmationDialog(_context:Context,title:String,mesg:String,listener: IAlertDialogCallback?){
        val builder= AlertDialog.Builder(_context)
        val dialogView=LayoutInflater.from(_context).inflate(R.layout.alert_dialog_two_button,null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val alertDialog=builder.create()
        val titleView:TextView=dialogView.findViewById(R.id.tv_dialogTitle)
        val messageView:TextView=dialogView.findViewById(R.id.tv_dialogMessage)
        val tv_negativeButton:TextView=dialogView.findViewById(R.id.tv_negativeButton)
        val tv_positiveButton:TextView=dialogView.findViewById(R.id.tv_positiveButton)
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

    abstract fun inflateLayout(layoutInflater: LayoutInflater) : VB
}