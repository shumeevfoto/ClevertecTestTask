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

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state:LiveData<State> = _state
    private val _liveDataList: MutableLiveData<RecyclerList> = MutableLiveData()
    val liveDataList:LiveData<RecyclerList> = _liveDataList
    private val _liveDataResult: MutableLiveData<String> = MutableLiveData()
    val liveDataResult:LiveData<String> = _liveDataResult


    init {
        (application as MyApplication).getRetroComponent().inject(this)
    }

    fun makeApiCall() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(State.Loading)
                delay(500)
                val repository = GetClevertecRepository(mService)
                val response = repository.getParamRecyclerList()
                _liveDataList.postValue(response)
                _state.postValue(State.Loaded)
            } catch (e: Exception) {
                _state.postValue(State.Error)
            }

        }

    }


    fun sendPost(list: List<String>) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(State.Loading)
                delay(500)
                val post = PostForm(PostForm.Form(list[0], list[1], list[2]))
                val res = mService.sendRequest(post)
                val results = res.result
                _liveDataResult.postValue(results)
                _state.postValue(State.Loaded)
            } catch (e: Exception) {
                _state.postValue(State.Error)
            }

        }

    }

    sealed class State {
        object Loading : State()
        object Loaded : State()
        object Error : State()
    }

}