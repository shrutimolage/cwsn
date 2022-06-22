package com.cwsn.mobileapp.utils

import android.content.Context
import android.widget.Toast

/**
Created by  on 21,June,2022
 **/

fun Context.toast(mesg:String){
    Toast.makeText(this,mesg,Toast.LENGTH_LONG).show()
}