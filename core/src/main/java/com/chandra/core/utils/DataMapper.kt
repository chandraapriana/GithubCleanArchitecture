package com.chandra.core.utils
import com.chandra.core.data.source.local.entity.UsersEntity
import com.chandra.core.data.source.remote.response.UserResponse
import com.chandra.core.domain.model.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(data: List<UserResponse>): Flow<List<Users>> {
        val dataArray = ArrayList<Users>()
        data.map {
            val user = Users(
                it.id.toString(),
                it.login,
                it.avatarUrl,
                it.name,
                it.location,
                it.publicRepos,
                it.followers,
                it.following,
                it.company,
                false

            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

    fun mapResponseToDomain(input: UserResponse): Flow<Users> {
        return flowOf(
            Users(
                input.id.toString(),
                input.login,
                input.avatarUrl,
                input.name,
                input.location,
                input.publicRepos,
                input.followers,
                input.following,
                input.company,
                false
            )
        )
    }

    fun mapEntitiesToDomain(data: List<UsersEntity>) :List<Users>{
        return data.map { user->
            Users(
                user.id,
                user.login.toString(),
                user.avatarUrl.toString(),
                user.name.toString(),
                user.location.toString(),
                user.publicRepos!!,
                user.followers!!,
                user.following!!,
                user.company.toString(),
                user.isFavorite!!
            )
        }
    }

    fun mapEntityToDomain(data:UsersEntity?):Users{
        return  Users(
            data?.id,
            data?.login.toString(),
            data?.avatarUrl.toString(),
            data?.name.toString(),
            data?.location.toString(),
            data?.publicRepos,
            data?.followers,
            data?.following,
            data?.company.toString(),
            data?.isFavorite
        )
    }

    fun mapDomainToEntity(data: Users) = UsersEntity(
        data.id.toString(),
        data.login,
        data.avatarUrl,
        data.name,
        data.location,
        data.publicRepos,
        data.followers,
        data.following,
        data.company,
        data.isFavorite
    )


}