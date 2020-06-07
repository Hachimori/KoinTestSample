package com.github.hachimori.kointestsample.repositories

import com.github.hachimori.kointestsample.model.Commits
import com.github.hachimori.kointestsample.model.Repos
import com.github.hachimori.kointestsample.model.User
import com.github.hachimori.kointestsample.network.GitHubService

class GitHubRepository(
    private val service: GitHubService
) {

    suspend fun getUser(user: String): User = service.getUser(user)

    suspend fun listRepos(user: String): List<Repos> = service.listRepos(user)

    suspend fun listCommit(owner: String, repos: String): List<Commits> =
        service.listCommit(owner, repos)
}
