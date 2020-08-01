package com.ramadan.ui

import Failure
import androidx.lifecycle.*
import com.ramadan.ResultState
import com.ramadan.data.TruecallerDataRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.net.UnknownHostException


class MainActivityViewModel : ViewModel() {
     private val tenthChar = MutableLiveData<ResultState>()
     val  tenthCharData :LiveData<ResultState>
        get() = tenthChar

    private val everyTenthChar = MutableLiveData<ResultState>()
    val  everyTenthCharData :LiveData<ResultState>
        get() = everyTenthChar

    private val wordCount = MutableLiveData<ResultState>()
    val  wordCountData :LiveData<ResultState>
        get() = wordCount

    private val truecallerDataRepository: TruecallerDataRepositoryImpl =
        TruecallerDataRepositoryImpl()

     fun setFailure(throwable: Throwable): ResultState {
        return when (throwable) {
            is UnknownHostException -> ResultState.Error(Failure.NetworkConnection)
            else -> ResultState.Error(Failure.UnExpectedError)
        }
    }

    fun getTenthCharRequest(){
        viewModelScope.launch {
            supervisorScope{
                try {
                    val data = async(Dispatchers.IO) { truecallerDataRepository.get10thCharacter() }

                    val result = data.await()
                    tenthChar.value = ResultState.Success(result)
                }catch (e:Throwable){
                    tenthChar.value =setFailure(e)
                }
            }

        }
    }

    fun getEveryTenthCharRequest(){
        viewModelScope.launch {
            supervisorScope{
                val data = async(Dispatchers.IO) { truecallerDataRepository.getEvery10thCharacter() }
                try {
                    val result = data.await()
                    everyTenthChar.value = ResultState.Success(result)
                }catch (e:Throwable){
                    everyTenthChar.value = setFailure(e)
                }
            }
        }
    }

    fun getWordCountRequest(){
        viewModelScope.launch {
            supervisorScope {
                val data = async(Dispatchers.IO) { truecallerDataRepository.getWordsCount() }
                try {
                    val result = data.await()
                    wordCount.value = ResultState.Success(result)
                }catch (e:Throwable){
                    wordCount.value = setFailure(e)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        truecallerDataRepository.inMemoryData = ""
    }

}