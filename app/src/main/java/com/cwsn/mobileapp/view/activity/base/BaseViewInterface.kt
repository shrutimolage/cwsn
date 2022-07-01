package com.cwsn.mobileapp.view.activity.base

import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

/**
Created by  on 20,June,2022
 **/
interface BaseViewInterface
{
    fun onUserBackPressed(){

    }
    fun onToolbarBackArrowPress(){

    }
    fun isFullScreenActivity():Boolean{
        return false
    }
    fun getContext(): Context?{
        return null
    }
    fun getToolBar(): Toolbar?{
        return null
    }
    fun isToolBarEnable():Boolean{
        return false
    }
    fun getToolBarTitleView(): TextView?
    {
        return null
    }
    fun getToolBartTitle(): String?
    {
        return null
    }
    fun updateToolBarTitle(title:String){

    }
    fun setToolBarTitle(title:String){

    }
    fun isBackArrowEnabled(): Boolean
    {
        return false
    }
    fun getToolBarBackArrowView():ImageView?
    {
        return null
    }
    fun getToolBarImageView(): ImageView?{
        return null
    }
    fun isSetUpProgressDialog():Boolean
    {
        return false
    }
    fun setProgressDialogTitle():String?
    {
        return null
    }
    fun getProgressDialog(): Dialog?{
        return null
    }
    fun showProgressDialog() {

    }
    fun hideProgressDialog()
    {

    }
    fun onActStart(){

    }
    fun onActCreate(){

    }
    fun onActResume(){

    }
    fun onActPause(){

    }
    fun onActStop(){

    }
}