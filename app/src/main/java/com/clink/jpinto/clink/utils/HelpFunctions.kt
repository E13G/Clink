package com.clink.jpinto.clink.utils

import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.util.Log
import com.clink.jpinto.clink.R
import com.clink.jpinto.clink.network.HttpGetRequest
import java.util.concurrent.ExecutionException


fun sendRequest(gateKey:String): String? {

    //Some url endpoint that you may have
    val myUrl = "https://api.thingspeak.com/update?api_key=" + gateKey +
            "&field1=1&field2=1"

    //String to place our result in
    var result: String?
    //Instantiate new instance of our class
    val getRequest = HttpGetRequest()
    //Perform the doInBackground method, passing in our url
    try {
        result = getRequest.execute(myUrl).get()

    } catch (e: InterruptedException) {
        e.printStackTrace()
        result = "Interrupt Error"

    } catch (e: ExecutionException) {
        e.printStackTrace()
        result = "Execution Error"
    }

    return result
}

fun getSavedTheme(activity: AppCompatActivity): Int {
    Log.e("helpFunctions","getSavedTheme")
    val theme = activity.getSharedPreferences("Theme", Context.MODE_PRIVATE).getString("theme", "darkRed")
    Log.e("helpFunctions",theme)
    when (theme) {
        "darkRed" -> return R.style.AppTheme_NoActionBar_DarkRed
        "indigo" -> return R.style.AppTheme_NoActionBar_Indigo
        else -> return R.style.AppTheme_NoActionBar_Indigo
    }
}