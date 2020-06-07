package com.github.hachimori.kointestsample.ui.input_form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.hachimori.kointestsample.model.Repos
import com.github.hachimori.kointestsample.model.User
import com.github.hachimori.kointestsample.network.ApiResponse
import com.github.hachimori.kointestsample.usecases.GetUserUsecase
import com.github.hachimori.kointestsample.usecases.GetUsersRepositoryListUsecase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class InputFormViewModel(private val getUserUsecase: GetUserUsecase, private val getUsersRepositoryListUsecase: GetUsersRepositoryListUsecase): ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val user: MutableLiveData<ApiResponse<User>> = MutableLiveData()
    val repos: MutableLiveData<ApiResponse<List<Repos>>> = MutableLiveData()

    fun getUser(name: String) {
        uiScope.launch {
            user.value = try {
                val response = getUserUsecase(name)
                ApiResponse.Success(response)
            } catch (e: Exception) {
                ApiResponse.Error(e)
            }
        }
    }

    fun getUserReposList(user: String) {
        uiScope.launch {
            repos.value = try {
                val response = getUsersRepositoryListUsecase(user)
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