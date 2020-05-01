package com.example.demo.config

import com.example.demo.repository.CodenationRepository
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component
class Config{
    @Bean
    fun getRepository(): CodenationRepository {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.codenation.dev/v1/challenge/dev-ps/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(CodenationRepository::class.java)
    }
}