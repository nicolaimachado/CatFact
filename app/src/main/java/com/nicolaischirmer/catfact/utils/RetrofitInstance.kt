package com.nicolaischirmer.catfact.utils

import com.nicolaischirmer.catfact.data.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api : ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}