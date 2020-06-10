package com.github.hachimori.kointestsample

import android.app.Application
import com.github.hachimori.kointestsample.network.GitHubService
import com.github.hachimori.kointestsample.repositories.GitHubRepository
import com.github.hachimori.kointestsample.ui.input_form.InputFormViewModel
import com.github.hachimori.kointestsample.ui.repository_detail.RepositoryDetailViewModel
import com.github.hachimori.kointestsample.usecases.GetRepositoryCommitListUsecase
import com.github.hachimori.kointestsample.usecases.GetUserUsecase
import com.github.hachimori.kointestsample.usecases.GetUsersRepositoryListUsecase
import com.github.hachimori.kointestsample.utils.Constants
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class KoinTestTestApplication: Application() {

    val module = module {
        // single { GitHubRepository(GitHubService.getService()) }
        single {
            val mockedInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val responseString = when (chain.request().url.toString()) {
                        "https://api.github.com/users/Hachimori" -> """
                        {
                            "login": "Hachimori",
                            "url": "https://api.github.com/users/Hachimori",
                            "name": "Ben Hachimori",
                            "company": null,
                            "blog": "",
                            "location": null,
                            "email": null,
                            "created_at": "",
                            "updated_at": ""
                        }
                        """
                        "https://api.github.com/users/Hachimori/repos" -> """
                        [
                            {
                                "id": 62282899,
                                "name": "AndroidSamples",
                                "full_name": "Hachimori/AndroidSamples",
                                "owner": {
                                  "login": "Hachimori"
                                },
                                "description": "Android sample programs",
                                "url": "https://api.github.com/repos/Hachimori/AndroidSamples"
                            }
                        ]
                        """
                        "https://api.github.com/repos/Hachimori/AndroidSamples/commits" -> """
                        [
                            {
                                "sha": "b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
                                "url": "https://api.github.com/repos/Hachimori/AndroidSamples/commits/b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
                                "commit": {
                                    "committer": {
                                        "name": "Ben Hachimori",
                                        "email": "ben.shooter2@gmail.com",
                                        "date": "2016-08-17T16:09:19Z"
                                    },
                                    "message": "Forgot to add some files"
                                }
                            },
                            {
                                "sha": "10a74e27fda9034374e0e30804e816a3dee7b483",
                                "url": "https://api.github.com/repos/Hachimori/AndroidSamples/commits/10a74e27fda9034374e0e30804e816a3dee7b483",
                                "commit": {
                                    "committer": {
                                        "name": "Ben Hachimori",
                                        "email": "ben.shooter2@gmail.com",
                                        "date": "2016-08-17T16:03:59Z"
                                    },
                                    "message": "RetrofitSample first commit"
                                }
                            }
                        ]
                        """
                        else -> ""
                    }.trimIndent()

                    return chain.proceed(chain.request())
                        .newBuilder()
                        .code(200)
                        .protocol(Protocol.HTTP_2)
                        .message(responseString)
                        .body(
                            responseString.toByteArray()
                                .toResponseBody("application/json".toMediaTypeOrNull())
                        )
                        .addHeader("content-type", "application/json")
                        .build()
                }
            }

            val client = OkHttpClient
                .Builder()
                .addInterceptor(mockedInterceptor)
                .build()

            val retrofit = Retrofit
                .Builder()
                .client(client)
                .baseUrl(Constants.GITHUB_API_ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            retrofit.create(GitHubService::class.java)
        }
        single { GitHubRepository(get())}
        single { GetUserUsecase(get()) }
        /*
        factory {
            val mock = mockk<GetUserUsecase>()
            coEvery { mock("Hachimori") } returns User(
                login="Hachimori",
                name="Ben Hachimori",
                company=null,
                email=null,
                bio=null,
                created_at="",
                updated_at=""
            )
            mock
        }
        */
        single { GetUsersRepositoryListUsecase(get()) }
        single { GetRepositoryCommitListUsecase(get()) }
        viewModel { InputFormViewModel(get(), get()) }
        viewModel { RepositoryDetailViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@KoinTestTestApplication)
            modules(module)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}