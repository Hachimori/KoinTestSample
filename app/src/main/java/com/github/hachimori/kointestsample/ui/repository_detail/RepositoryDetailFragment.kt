package com.github.hachimori.kointestsample.ui.repository_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.hachimori.kointestsample.R
import com.github.hachimori.kointestsample.network.ApiResponse
import kotlinx.android.synthetic.main.fragment_repository_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class RepositoryDetailFragment : Fragment() {


    companion object {
        fun newInstance() = RepositoryDetailFragment()
    }

    val viewModel: RepositoryDetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
