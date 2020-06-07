package com.github.hachimori.kointestsample.ui.input_form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.hachimori.kointestsample.R
import com.github.hachimori.kointestsample.network.ApiResponse
import com.github.hachimori.kointestsample.network.GitHubService
import com.github.hachimori.kointestsample.repositories.GitHubRepository
import com.github.hachimori.kointestsample.ui.repository_detail.RepositoryDetailFragmentArgs
import com.github.hachimori.kointestsample.usecases.GetUserUsecase
import com.github.hachimori.kointestsample.usecases.GetUsersRepositoryListUsecase
import com.github.hachimori.kointestsample.utils.doubleArgViewModelFactory
import kotlinx.android.synthetic.main.input_form_fragment.*
import timber.log.Timber

class InputFormFragment : Fragment() {

    companion object {
        fun newInstance() = InputFormFragment()
    }

    private lateinit var viewModel: InputFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.input_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = doubleArgViewModelFactory(::InputFormViewModel) (
            GetUserUsecase(GitHubRepository(GitHubService.getService())),
            GetUsersRepositoryListUsecase(GitHubRepository(GitHubService.getService()))
        )
        viewModel = ViewModelProvider(this, factory).get(InputFormViewModel::class.java)

        viewModel.user.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val user = response.data
                    input_form_user_info.text = getString(
                        R.string.input_form_user_info,
                        user.name, user.company, user.email, user.bio, user.created_at, user.updated_at
                    )
                    input_form_user_info.visibility = View.VISIBLE
                    input_form_repository_list.visibility = View.GONE
                }
                is ApiResponse.Error -> Timber.d(response.exception)
            }

        })

        input_form_repository_list.layoutManager = LinearLayoutManager(context)
        //input_form_repository_list.adapter = RepositoryAdapter(viewModel, viewModel.repos.value)

        viewModel.repos.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val reposList = response.data
                    //viewModel.reposList.clear()
                    //viewModel.reposList.addAll(reposList)

                    input_form_repository_list.adapter = RepositoryAdapter(reposList) { repos ->
                        val args = RepositoryDetailFragmentArgs.Builder(repos).build()
                        findNavController().navigate(R.id.repositoryDetailFragment, args.toBundle())
                    }

                    input_form_repository_list.visibility = View.VISIBLE
                    input_form_user_info.visibility = View.GONE
                }
                is ApiResponse.Error -> Timber.d(response.exception)
            }
        })

        input_form_get_user_info.setOnClickListener {
            val userName = input_form_github_name.text.toString()
            viewModel.getUser(userName)
        }

        input_form_get_repository.setOnClickListener {
            val userName = input_form_github_name.text.toString()
            viewModel.getUserReposList(userName)
        }
    }
}