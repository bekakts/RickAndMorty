package com.example.rickandmortytest

import android.util.Log
import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful){
                val body = response.body()
                if (body != null || response.code() in 200 .. 299){
                    return Resource.Success(body)
                }
            }else{
                return Resource.Error(response.message())
            }
        }catch (e: java.lang.Exception){
           return Resource.Error(e.message ?: e.toString())
        }
       return Resource.Error(call().message())
    }
}

