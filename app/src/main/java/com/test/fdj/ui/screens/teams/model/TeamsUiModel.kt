package com.test.fdj.ui.screens.teams.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamsUiModel(
    val isLoading: Boolean = false,
    val error: ErrorUiModel? = null,
    val teams: List<TeamUiModel> = emptyList()
) : Parcelable

@Parcelize
data class TeamUiModel(
    val imageUrl: String? = null,
    val imageAccessibilityLabel: String? = null,
) : Parcelable

@Parcelize
data class ErrorUiModel(
    val label: String,
) : Parcelable
