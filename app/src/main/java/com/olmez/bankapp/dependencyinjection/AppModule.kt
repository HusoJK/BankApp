package com.olmez.bankapp.dependencyinjection

/**
 * API çağrısını yapacak olan retrofit nesnesinin oluşturulduğu class
 */
import com.olmez.bankapp.api.CallAPI
import com.olmez.bankapp.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectCallApi() : CallAPI {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(CallAPI::class.java)

    }

}

