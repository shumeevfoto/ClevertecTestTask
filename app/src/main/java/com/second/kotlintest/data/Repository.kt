package com.second.kotlintest.data

import com.second.kotlintest.di.RetroServiceInterface
import com.second.kotlintest.model.RecyclerList


interface Repository {
    suspend fun getParamRecyclerList():RecyclerList
}

class GetRepository(private val mServise:RetroServiceInterface):Repository{
    override suspend fun getParamRecyclerList(): RecyclerList {
        return mServise.getDataFromAPI()
    }

}