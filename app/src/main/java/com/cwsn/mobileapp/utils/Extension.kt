package com.cwsn.mobileapp.utils

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

/**
Created by  on 21,June,2022
 **/

fun Context.toast(mesg:String){
    Toast.makeText(this,mesg,Toast.LENGTH_LONG).show()
}

fun toast(mesg: String, ctx: Context){
    Toast.makeText(ctx,mesg,Toast.LENGTH_LONG).show()
}

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
}