package com.github.hachimori.kointestsample.ui.repository_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.hachimori.kointestsample.R
import com.github.hachimori.kointestsample.network.ApiResponse
import com.github.hachimori.kointestsample.network.GitHubService
import com.github.hachimori.kointestsample.repositories.GitHubRepository
import com.github.hachimori.kointestsample.usecases.GetRepositoryCommitListUsecase
import com.github.hachimori.kointestsample.utils.singleArgViewModelFactory
import kotlinx.android.synthetic.main.fragment_repository_detail.*
import timber.log.Timber

class RepositoryDetailFragment : Fragment() {


    companion object {
        fun newInstance() = RepositoryDetailFragment()
    }
    private lateinit var viewModel: RepositoryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = singleArgViewModelFactory(::RepositoryDetailViewModel) (
            GetRepositoryCommitListUsecase(GitHubRepository(GitHubService.getService()))
        )
        viewModel = ViewModelProvider(this, factory).get(RepositoryDetailViewModel::class.java)

        repository_detail_commit_list.layoutManager = LinearLayoutManager(context)
        //repository_detail_commit_list.adapter = CommitAdapter(viewModel.commitsList)
        //repository_detail_commit_list.addItemDecoration(DividerItemDecoration(activity, LinearLayout.VERTICAL))

        viewModel.commitsList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val commitsList = response.data

                    //viewModel.commitsList.clear()
                    //viewModel.commitsList.addAll(commitsList)

                    // (repository_detail_commit_list.adapter as CommitAdapter).notifyDataSetChanged()
                    repository_detail_commit_list.adapter = CommitAdapter(commitsList)
                }
                is ApiResponse.Error -> Timber.d(response.exception)
            }
        })

        arguments?.let {
            val safeArgs = RepositoryDetailFragmentArgs.fromBundle(it)
            val repos = safeArgs.repos

            repository_detail_repository.text = repos.name
            repository_detail_description.text = repos.description

            viewModel.getListCommit(repos)
        }
    }
}
