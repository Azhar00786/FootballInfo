package com.example.footballinfo.view.components

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import com.example.footballinfo.R

class NetworkChecker {
    companion object {
        fun isOnline(context: Context): Boolean {
            var result: Boolean = false
            val connMgr =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connMgr.run {
                connMgr.getNetworkCapabilities(connMgr.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            }
            Log.d("NetworkAvailable", result.toString())
            return result
        }


        fun errorHandler(resId: Int, context: Context, navController: NavController) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("No Internet Connection")
            builder.setMessage("Check Internet connectivity")
            builder.setIcon(R.drawable.ic_baseline_error_24)

            builder.setPositiveButton("RETRY") { dialogInterface, which: Int ->
                navController.navigate(resId, null, null)
            }
            builder.setNeutralButton("EXIT") { dialog: DialogInterface?, which: Int ->
                System.exit(-1)
            }

            val alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}