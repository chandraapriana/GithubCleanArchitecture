package com.chandra.core.di

import androidx.room.Room
import com.chandra.core.BuildConfig.API_KEY
import com.chandra.core.data.source.UsersRepository
import com.chandra.core.data.source.local.LocalDataSource
import com.chandra.core.data.source.local.room.UsersDatabase
import com.chandra.core.data.source.remote.RemoteDataSource
import com.chandra.core.data.source.remote.network.ApiService
import com.chandra.core.domain.repository.IUsersAppRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.github.com"
private const val HOST_NAME = "api.github.com"

val networkModule = module {

    single {
        val certificatePinner = CertificatePinner.Builder()
            .add(HOST_NAME, "sha256/ORtIOYkm5k6Nf2tgAK/uwftKfNhJB3QS0Hs608SiRmE=")
            .add(HOST_NAME, "sha256/k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=")
            .add(HOST_NAME, "sha256/WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=")
            .build()
        OkHttpClient.Builder().certificatePinner(certificatePinner).addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", API_KEY)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    val myPassPhrase: ByteArray = SQLiteDatabase.getBytes("chandra developer".toCharArray())
    val factory = SupportFactory(myPassPhrase)
    factory {
        get<UsersDatabase>().userDao()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java, "db_users"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    single<IUsersAppRepository> {
        UsersRepository(get(), get())
    }
}

