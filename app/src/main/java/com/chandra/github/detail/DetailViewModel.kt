package com.chandra.github.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.chandra.core.domain.model.Users
import com.chandra.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val userUseCase: UserUseCase):ViewModel() {
    fun detailUsers(username: String) = userUseCase.getDetailUser(username).asLiveData()
    fun getFavoriteUsersByUname(username: String) = userUseCase.getFavoriteUsersByUname(username)?.asLiveData()

    fun insertFavorite(user: Users) = viewModelScope.launch {
        userUseCase.insertUser(user)
        //favorite.value = true
    }

    fun deleteFavorite(user: Users) = viewModelScope.launch {
        userUseCase.deleteUser(user)
        //favorite.value = false
    }
}