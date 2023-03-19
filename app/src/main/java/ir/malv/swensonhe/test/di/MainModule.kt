package ir.malv.swensonhe.test.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.malv.swensonhe.test.repository.WeatherRepository
import ir.malv.swensonhe.test.repository.WeatherRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    @Provides
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModuleBinder {
    @Binds
    abstract fun bindWeatherRepo(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
