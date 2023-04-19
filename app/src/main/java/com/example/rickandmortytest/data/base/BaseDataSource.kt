package com.example.rickandmortytest.data.base

import android.util.Log
import com.example.rickandmortytest.domain.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful){
                val body = response.body()
                if (body != null || response.code() in 200 .. 299) {
                    Log.e("ololo","BaseDataSource: success")
                    return Resource.Success(body)
                }
            }else{

                Log.e("ololo","BaseDataSource: Error1 -> ${response.message()} , ${response.code()}")
                return  Resource.Error(response.message())
            }
        }catch (e: java.lang.Exception){
            Log.e("ololo","BaseDataSource: Error2 -> ${e.message?: e.toString()}")
            return Resource.Error(e.message ?: e.toString())
        }
        Log.e("ololo","BaseDataSource: Error3 -> ${call.invoke().message()}")
        return Resource.Error(call().message())
    }
}