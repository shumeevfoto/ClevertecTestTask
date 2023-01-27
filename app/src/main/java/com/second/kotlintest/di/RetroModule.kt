package com.second.kotlintest.di

//import okhttp3.logging.HttpLoggingInterceptor

import com.google.gson.*
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.second.kotlintest.model.Values
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class RetroModule {

    private val baseURL = "http://test.clevertec.ru/tt/"

    @Provides
    @Singleton
    fun provideOkkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun getRetroServiceInterface(retrofit: Retrofit): RetroServiceInterface {
        return retrofit.create(RetroServiceInterface::class.java)
    }

    class ResponseDeserializer : JsonDeserializer<Values> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext,
        ): Values {
            val response: Values = Gson().fromJson(json, Values::class.java)
            val ma = response.valt
            var map: Map<String, String> = HashMap()
            map = Gson().fromJson(json, map.javaClass) as Map<String, String>
            response.setStringNumberMap(map)
            return response
        }
    }

    private val builder: GsonBuilder = GsonBuilder()
        .registerTypeAdapter(
            object : TypeToken<Map<String, String>>() {}.type,
            ResponseDeserializer())
    val gson: Gson = builder.create()



    @Singleton
    @Provides
    fun getRetroFitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}