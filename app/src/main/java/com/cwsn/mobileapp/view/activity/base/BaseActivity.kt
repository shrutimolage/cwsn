package com.cwsn.mobileapp.view.activity.base

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.cwsn.mobileapp.R

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
        progressDialog?.show()
    }

    override fun hideProgressDialog() {
       progressDialog?.isShowing?.let {
           progressDialog?.hide()
       }
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

    abstract fun inflateLayout(layoutInflater: LayoutInflater) : VB
}