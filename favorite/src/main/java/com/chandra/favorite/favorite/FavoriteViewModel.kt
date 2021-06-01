package com.chandra.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.chandra.core.domain.usecase.UserUseCase

class FavoriteViewModel(userUseCase: UserUseCase):ViewModel() {
    val favoriteUsers = userUseCase.getFavoriteUsers().asLiveData()
}