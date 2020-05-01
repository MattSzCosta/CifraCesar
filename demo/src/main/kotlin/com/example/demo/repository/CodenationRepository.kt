package com.example.demo.repository

import com.example.demo.model.AnswerModel
import com.example.demo.model.ScoreDTO
import okhttp3.MultipartBody
import org.springframework.stereotype.Repository
import retrofit2.Call
import retrofit2.http.*

@Repository
interface CodenationRepository {

    @GET("generate-data")
    fun getAnswer(@Query("token") token: String): Call<AnswerModel>

    @Multipart
    @POST("submit-solution")
    fun submit(@Query("token")token: String, @Part filePart: MultipartBody.Part ): Call<ScoreDTO>


}