package com.github.hachimori.kointestsample.mock.usecases

import com.github.hachimori.kointestsample.model.*
import com.github.hachimori.kointestsample.usecases.GetRepositoryCommitListUsecase
import com.github.hachimori.kointestsample.usecases.GetUserUsecase
import com.github.hachimori.kointestsample.usecases.GetUsersRepositoryListUsecase
import io.mockk.coEvery
import io.mockk.mockk

fun getMockedGetUserUsecase(): GetUserUsecase {
    val mock = mockk<GetUserUsecase>()

    coEvery { mock("Hachimori") } returns User(
        login = "Hachimori",
        name = "Ben Hachimori",
        company = null,
        email = null,
        bio = null,
        created_at = "aa",
        updated_at = "bbcc"
    )

    return mock
}

fun getMockedGetUsersRepositoryListUsecase(): GetUsersRepositoryListUsecase {
    val mock = mockk<GetUsersRepositoryListUsecase>()

    coEvery { mock("Hachimori") } returns listOf(
        Repos(
            id=10,
            name="AndroidSamples",
            full_name="Hachimori/AndroidSamples",
            url="https://api.github.com/users/Hachimori",
            description="Android sample programs",
            owner=Repos.Owner(login="Hachimori")
        )
    )

    return mock
}

fun getMockedGetRepositoryCommitListUsecase(): GetRepositoryCommitListUsecase {
    val mock = mockk<GetRepositoryCommitListUsecase>()

    coEvery { mock("Hachimori", "AndroidSamples") } returns listOf(
        Commits(
            sha="b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
            url="https://api.github.com/repos/Hachimori/AndroidSamples/commits/b5308e31b0b10e36acd9e1ae2d1a64604e82e8bd",
            commit= Commit(
                message="Forgot to add some files",
                committer= Committer(
                    name="Ben Hachimori",
                    email="ben.shooter2@gmail.com",
                    date="2016-08-17T16:09:19Z"
                )
            )
        ),
        Commits(
            sha="10a74e27fda9034374e0e30804e816a3dee7b483",
            url="https://api.github.com/repos/Hachimori/AndroidSamples/commits/10a74e27fda9034374e0e30804e816a3dee7b483",
            commit=Commit(
                message="RetrofitSample first commit",
                committer=Committer(
                    name="Ben Hachimori",
                    email="ben.shooter2@gmail.com",
                    date="2016-08-17T16:03:59Z"
                )
            )
        )
    )

    return mock
}