package com.chandra.github.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.chandra.core.data.source.Resource
import com.chandra.core.domain.model.Users
import com.chandra.core.domain.usecase.UserUseCase

class UsersViewModel(private val userUseCase: UserUseCase): ViewModel() {


 fun getUsers():LiveData<Resource<List<Users>>> = userUseCase.getUsers().asLiveData()



}