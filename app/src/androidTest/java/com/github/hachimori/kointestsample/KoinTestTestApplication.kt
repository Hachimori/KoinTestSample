package com.github.hachimori.kointestsample

import android.app.Application
import com.github.hachimori.kointestsample.model.*
import com.github.hachimori.kointestsample.network.GitHubService
import com.github.hachimori.kointestsample.repositories.GitHubRepository
import com.github.hachimori.kointestsample.ui.input_form.InputFormViewModel
import com.github.hachimori.kointestsample.ui.repository_detail.RepositoryDetailViewModel
import com.github.hachimori.kointestsample.usecases.GetRepositoryCommitListUsecase
import com.github.hachimori.kointestsample.usecases.GetUserUsecase
import com.github.hachimori.kointestsample.usecases.GetUsersRepositoryListUsecase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.experimental.builder.single
import timber.log.Timber

class KoinTestTestApplication: Application() {
    val appModule = module {
        single { GitHubRepository(GitHubService.getService()) }
        /*
        single {
            val mock = mockk<GitHubRepository>()
            coEvery { mock.getUser("Hachimori") } returns User(
                login="Hachimori",
                name="Ben Hachimori",
                company=null,
                email=null,
                bio=null,
                created_at="",
                updated_at=""
            )

            coEvery { mock.listRepos(any()) } returns
                    listOf(
                        Repos(
                            id = 10,
                            name = "Ben Hachimori",
                            full_name = "Ben Hachimori",
                            url = "",
                            description = "",
                            owner = Repos.Owner(login = "")
                        )
                    )

            coEvery { mock.listCommit(any(), any()) } returns
                listOf(
                    Commits(
                        url = "https://api.github.com/repos/Hachimori/AndroidSamples/commits/b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
                        sha = "b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
                        commit = Commit(
                            message = "Forgot to add some files",
                            committer = Committer(
                                name = "Ben Hachimori",
                                email = "ben.shooter2@gmail.com",
                                date = "2016-08-17T16:09:19Z"
                            )
                        )
                    )
                )

            mock
        }
         */
        /*
        single {
            val mock = mockk<GitHubRepository>(relaxed = true)
            coEvery { mock.getUser("Hachimori") } returns User(
                login="Hachimori",
                name="Ben Hachimori",
                company=null,
                email=null,
                bio=null,
                created_at="",
                updated_at=""
            )
            mock
        }*/

        single { GetUserUsecase(get()) }

        /*
        single{
            val mock = mockk<GetUserUsecase>(relaxed = true)
            coEvery { mock("Hachimori") } returns User(
                login="Hachimori",
                name="Ben Hachimori",
                company=null,
                email=null,
                bio=null,
                created_at="",
                updated_at=""
            )
            mock as GetUserUsecase
        }
        */
        single { GetUsersRepositoryListUsecase(get()) }
        single { GetRepositoryCommitListUsecase(get()) }
        /*
        factory {
            val mock = mockk<GetRepositoryCommitListUsecase> {

                coEvery { this@mockk(any(), any()) } coAnswers {
                    listOf(
                        Commits(
                            url = "https://api.github.com/repos/Hachimori/AndroidSamples/commits/b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
                            sha = "b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
                            commit = Commit(
                                message = "Forgot to add some files",
                                committer = Committer(
                                    name = "Ben Hachimori",
                                    email = "ben.shooter2@gmail.com",
                                    date = "2016-08-17T16:09:19Z"
                                )
                            )
                        )
                    )
                }
            }
            mock
        }
        */
        /*
        single {
            val usecase = mockkClass(GetRepositoryCommitListUsecase::class) // mockk<GetRepositoryCommitListUsecase>()

            coEvery { usecase(any(), any()) } returns
                listOf(
                    Commits(
                        url = "https://api.github.com/repos/Hachimori/AndroidSamples/commits/b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
                        sha = "b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
                        commit = Commit(
                            message = "Forgot to add some files",
                            committer = Committer(
                                name = "Ben Hachimori",
                                email = "ben.shooter2@gmail.com",
                                date = "2016-08-17T16:09:19Z"
                            )
                        )
                    )
                )

            usecase
        }
         */
        viewModel { InputFormViewModel(get(), get()) }
        viewModel { RepositoryDetailViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        for (i in 1 until 100) println("aaabbb")

        Timber.i("bobyoy")
        startKoin{
            androidLogger()
            androidContext(this@KoinTestTestApplication)
            modules(appModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}