package com.nicolaischirmer.catfact.data

import com.nicolaischirmer.catfact.models.CatFacts
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/fact")
    suspend fun getRandomFact(): CatFacts

}