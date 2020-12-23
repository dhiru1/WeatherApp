package com.example.weatherapp.network


import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.weatherapp.GetWeather
import org.json.JSONException


class PresenterClass(view: GetWeather) : NetworkInterface.PresenterToModel {

    private var view: NetworkInterface.PresenterToView? = null

    init {
        this.view = view
    }

    override fun loadData(context: Context, url: String) {
        getNewsData(context,url)
    }
    @Throws(JSONException::class)
    private fun getNewsData(context: Context,url: String) {

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                view?.onSuccess(response)
            },
            Response.ErrorListener {
                view?.onFailed()
            }
        )
        MyVolleyNetwork.getInstance(context).requestQueue
        MyVolleyNetwork.getInstance(context).addToRequestQueue(request)


    }
}

