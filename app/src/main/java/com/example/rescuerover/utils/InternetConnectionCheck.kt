package com.example.rescuerover.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.example.rescuerover.R


class InternetConnectionCheck {

    companion object{
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            } else {
                Toast.makeText(context, context.getString(R.string.error_network),Toast.LENGTH_LONG).show()
                return false
            }
        }
    }
}