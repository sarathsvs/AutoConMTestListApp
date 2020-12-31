package com.svs.autoconmtest.network

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("GetAllNumbers")
    suspend fun getNumbers(
        @Query("intStartNo") intStartNo:String,
        @Query("intEndNo") intEndNo:String)
        : Response<List<String>>

    companion object{
        const val APP_BASE_URL: String = "http://waypointsystems.dyndns.org:25410/Api/DemoApi/"
    }

}