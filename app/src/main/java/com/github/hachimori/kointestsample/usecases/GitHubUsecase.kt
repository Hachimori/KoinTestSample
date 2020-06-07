package com.github.hachimori.kointestsample.usecases

import com.github.hachimori.kointestsample.model.Commits
import com.github.hachimori.kointestsample.model.Repos
import com.github.hachimori.kointestsample.model.User
import com.github.hachimori.kointestsample.repositories.GitHubRepository


class GetUserUsecase(private val githubRepository: GitHubRepository) {
    suspend operator fun invoke(user: String): User = githubRepository.getUser(user)
}

class GetUsersRepositoryListUsecase(private val githubRepository: GitHubRepository) {
    suspend operator fun invoke(user: String): List<Repos> = githubRepository.listRepos(user)
}

class GetRepositoryCommitListUsecase(private val githubRepository: GitHubRepository) {
    suspend operator fun invoke(owner: String, repos: String): List<Commits> = githubRepository.listCommit(owner, repos)
}
