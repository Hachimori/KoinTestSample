package com.github.hachimori.kointestsample

import android.app.Application
import com.github.hachimori.kointestsample.mock.network.getMockedGitHubService
import com.github.hachimori.kointestsample.repositories.GitHubRepository
import com.github.hachimori.kointestsample.ui.input_form.InputFormViewModel
import com.github.hachimori.kointestsample.ui.repository_detail.RepositoryDetailViewModel
import com.github.hachimori.kointestsample.usecases.GetRepositoryCommitListUsecase
import com.github.hachimori.kointestsample.usecases.GetUserUsecase
import com.github.hachimori.kointestsample.usecases.GetUsersRepositoryListUsecase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class KoinTestTestApplication: Application() {

    val module = module {
        single { getMockedGitHubService() }
        single { GitHubRepository(get())}

        single { GetUserUsecase(get()) }
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