package org.take365

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.google.gson.Gson

import org.take365.Network.Models.Response.BaseResponse
import org.take365.Network.Models.Response.ErrorResponse
import org.take365.Network.Models.Response.LoginResponse.LoginResult

import java.io.IOException

import retrofit2.Response

/**
 * Created by divere on 04/11/2016.
 */

open class Take365Activity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentUser = Take365App.getCurrentUser()
        if (currentUser != null) {
            outState.putSerializable("currentUser", Take365App.getCurrentUser())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentUser = savedInstanceState.getSerializable("currentUser") as LoginResult?
        if (currentUser != null) {
            Take365App.setCurrentUser(currentUser)
        }
    }

    @JvmOverloads
    fun showAskDialog(question: String, positiveClickListener: DialogInterface.OnClickListener, negativeClickListener: DialogInterface.OnClickListener? = null) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(question).setPositiveButton("Продолжить", positiveClickListener)
                .setNegativeButton("Отмена", negativeClickListener).show()
    }

    fun showAlertDialog(message: String, callback: (() -> Unit)? = null) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message).setPositiveButton("Продолжить") { _, _ -> callback?.invoke() }.show()
    }

    fun showApiError(response: Response<*>) {
        var responseString: String? = null

        try {
            responseString = response.errorBody().string()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (responseString == null) {
            Log.e("take365", "Failed get response string from response.")
            return
        }

        var errorMessage: String? = null
        try {
            val errorResponse = Gson().fromJson(responseString, BaseResponse::class.java)
            errorMessage = errorResponse.errors!![0].value
            if (errorMessage != null) {
                showAlertDialog(errorMessage, null)
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val errorResponse = Gson().fromJson(responseString, ErrorResponse::class.java)
            errorMessage = errorResponse.errors!![0]
            if (errorMessage != null) {
                showAlertDialog(errorMessage, null)
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }



        Log.e("take365", "Failed parse error from response")
    }

    fun showConnectionError() {
        showAlertDialog("Не удалось установить соединение с сервером. Попробуйте позднее.", null)
    }

    fun showProgressDialog(message: String) {
        progressDialog = ProgressDialog.show(this, null, message, true, false)
    }

    fun hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.cancel()
        }
    }
}
