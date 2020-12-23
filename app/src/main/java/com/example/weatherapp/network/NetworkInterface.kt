package com.example.weatherapp.network

import android.content.Context
import org.json.JSONObject

class NetworkInterface {

    interface PresenterToView{
        fun onSuccess(jsonObject: JSONObject)
        fun onFailed()
    }


    interface PresenterToModel{
        fun loadData(context: Context,url:String)
    }
}