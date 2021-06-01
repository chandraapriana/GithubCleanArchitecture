package com.chandra.github.di

import com.chandra.core.domain.usecase.UserInteractor
import com.chandra.core.domain.usecase.UserUseCase
import com.chandra.github.detail.DetailViewModel
import com.chandra.github.users.UsersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { UsersViewModel(get()) }
    viewModel { DetailViewModel(get()) }


}