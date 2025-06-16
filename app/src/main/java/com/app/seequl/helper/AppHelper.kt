package com.app.seequl.helper

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import com.app.seequl.data.database.AppDatabase
import com.app.seequl.interfaces.ISharedStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AppHelper @Inject constructor(
    @ApplicationContext val context: Context,
    private val coroutineDispatcher: CoroutineDispatcher,
    val storage: ISharedStorage
) {

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }


    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun toastShort(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun toast(resourceId: Int) {
        toast(context.getString(resourceId))
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        return result
    }

    fun isNotificationEnabled(): Boolean =
        NotificationManagerCompat.from(context).areNotificationsEnabled()


//    fun cleanDB() {
//        val db = AppDatabase.getDatabase(context)
//        db.clearAllTables()
//    }
}