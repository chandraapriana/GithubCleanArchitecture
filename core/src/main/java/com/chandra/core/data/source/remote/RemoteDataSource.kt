package com.chandra.core.data.source.remote

import com.chandra.core.data.source.remote.network.ApiResponse
import com.chandra.core.data.source.remote.network.ApiService
import com.chandra.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

   suspend fun getUsers():Flow<ApiResponse<List<UserResponse>>>{
        return flow {

            try {
                val response = apiService.getUser()

                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getDetailUsers(username:String):Flow<ApiResponse<UserResponse>>{
        return flow {
            try {
                val response = apiService.getDetailUser(username)
                emit(ApiResponse.Success(response))
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }


}