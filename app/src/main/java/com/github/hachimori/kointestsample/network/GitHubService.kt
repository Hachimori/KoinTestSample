package com.github.hachimori.kointestsample.network

import com.github.hachimori.kointestsample.BuildConfig
import com.github.hachimori.kointestsample.model.Commits
import com.github.hachimori.kointestsample.model.Repos
import com.github.hachimori.kointestsample.model.User
import com.github.hachimori.kointestsample.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {

    /**
     * Get user information
     *   - https://developer.github.com/v3/users/#get-a-single-user
     */
    @GET("users/{username}")
    suspend fun getUser(@Path("username") user: String): User

    /**
     * Get user's repository list
     *   - https://developer.github.com/v3/repos/#list-user-repositories
     */
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repos>

    /**
     * Get commit list of specified repository
     *   - https://developer.github.com/v3/repos/commits/#list-commits-on-a-repository
     */
    @GET("repos/{owner}/{repos}/commits")
    suspend fun listCommit(@Path("owner") owner: String, @Path("repos") repos: String): List<Commits>


    companion object {
        private val INSTANCE: GitHubService

        init {
            val httpClientBuilder = OkHttpClient.Builder()

            // Display the result of Web API request on Logcat
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BASIC
                httpClientBuilder.addInterceptor(interceptor)
            }

            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.GITHUB_API_ENDPOINT)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            INSTANCE = retrofit.create(GitHubService::class.java)
        }

        fun getService() = INSTANCE
    }
}