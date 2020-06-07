package com.github.hachimori.kointestsample.ui.repository_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.hachimori.kointestsample.model.Commits
import com.github.hachimori.kointestsample.model.Repos
import com.github.hachimori.kointestsample.network.ApiResponse
import com.github.hachimori.kointestsample.usecases.GetRepositoryCommitListUsecase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class RepositoryDetailViewModel constructor(private val getRepositoryCommitListUsecase: GetRepositoryCommitListUsecase): ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val commitsList: MutableLiveData<ApiResponse<List<Commits>>> = MutableLiveData()

    fun getListCommit(repos: Repos) {
        uiScope.launch {
            commitsList.value = try {
                val response = getRepositoryCommitListUsecase(repos.owner.login, repos.name)
                ApiResponse.Success(response)
            } catch (e: Exception) {
                ApiResponse.Error(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
