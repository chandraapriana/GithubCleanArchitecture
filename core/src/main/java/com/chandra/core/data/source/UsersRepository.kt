package com.chandra.core.data.source

import com.chandra.core.data.source.local.LocalDataSource
import com.chandra.core.data.source.remote.NetworkOnlyResource
import com.chandra.core.data.source.remote.RemoteDataSource
import com.chandra.core.data.source.remote.network.ApiResponse
import com.chandra.core.data.source.remote.response.UserResponse
import com.chandra.core.domain.model.Users
import com.chandra.core.domain.repository.IUsersAppRepository
import com.chandra.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersRepository(private val remoteDataSource: RemoteDataSource ,private val localDataSource: LocalDataSource):IUsersAppRepository {
    override fun getUsers(): Flow<Resource<List<Users>>> {
        return object :NetworkOnlyResource<List<Users>,List<UserResponse>>(){
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<Users>> {
                return DataMapper.mapResponsesToDomain(data)

            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getUsers()
            }
        }.asFlow()
    }

    override fun getDetailUser(username:String): Flow<Resource<Users>> {
        return object : NetworkOnlyResource<Users, UserResponse>(){
            override fun loadFromNetwork(data: UserResponse): Flow<Users> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.getDetailUsers(username)
            }
        }.asFlow()

    }

    override fun getFavoriteUsers(): Flow<List<Users>> {
        return localDataSource.getFavoriteUsers().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteUsersByUname(username: String): Flow<Users>? {
       return localDataSource.getFavoriteUserByUname(username)?.map {
           DataMapper.mapEntityToDomain(it)
       }
    }

    override suspend fun insertUsers(users: Users) {
        val domainUsers  = DataMapper.mapDomainToEntity(users)
        return localDataSource.insertUsers(domainUsers)
    }

    override suspend fun deleteUsers(users: Users): Int {
        val domainUsers  = DataMapper.mapDomainToEntity(users)
        return localDataSource.deleteUsers(domainUsers)
    }


}