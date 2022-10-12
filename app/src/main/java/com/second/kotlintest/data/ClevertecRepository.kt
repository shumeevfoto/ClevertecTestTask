package com.second.kotlintest.data

import com.second.kotlintest.di.RetroServiceInterface
import com.second.kotlintest.model.RecyclerList


interface ClevertecRepository {
    suspend fun getParamRecyclerList():RecyclerList
}

class GetClevertecRepository(private val mServise:RetroServiceInterface):ClevertecRepository{
    override suspend fun getParamRecyclerList(): RecyclerList {
        return mServise.getDataFromAPI()
    }

}