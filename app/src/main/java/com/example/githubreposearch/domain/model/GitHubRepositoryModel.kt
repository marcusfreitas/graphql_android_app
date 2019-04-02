package com.example.githubreposearch.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepositoryModel(
    val avatarUrl: String, val name: String,
    val description: String, val forksCount: Int
) : Parcelable