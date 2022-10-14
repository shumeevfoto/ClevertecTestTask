package com.second.kotlintest.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.second.kotlintest.data.GetClevertecRepository
import com.second.kotlintest.di.RetroServiceInterface
import com.second.kotlintest.model.PostForm
import com.second.kotlintest.model.RecyclerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var mService: RetroServiceInterface

    val state: MutableLiveData<State> = MutableLiveData()
    val liveDataList: MutableLiveData<RecyclerList> = MutableLiveData()
    val liveDataResult: MutableLiveData<String> = MutableLiveData()


    init {
        (application as MyApplication).getRetroComponent().inject(this)
    }

    fun makeApiCall() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.postValue(State.Loading)
                delay(1000)
                val repository = GetClevertecRepository(mService)
                val response = repository.getParamRecyclerList()
                liveDataList.postValue(response)
                state.postValue(State.Loaded)
            } catch (e: Exception) {
                state.postValue(State.Error)
            }

        }

    }


    fun sendPost(list: List<String>) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.postValue(State.Loading)
                delay(1000)
                val post = PostForm(PostForm.Form(list[0], list[1], list[2]))
                val res = mService.sendRequest(post)
                val results = res.result
                liveDataResult.postValue(results)
                state.postValue(State.Loaded)
            } catch (e: Exception) {
                state.postValue(State.Error)
            }

        }

    }

    sealed class State {
        object Loading : State()
        object Loaded : State()
        object Error : State()
    }

}