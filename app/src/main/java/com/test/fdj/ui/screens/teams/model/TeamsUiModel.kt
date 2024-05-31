package com.test.fdj.ui.screens.teams.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamsUiModel(
    val isLoading: Boolean = false,
    val error: TeamsErrorUiModel? = null,
    val teams: List<TeamUiModel> = emptyList()
) : Parcelable

@Parcelize
data class TeamUiModel(
    val imageUrl: String? = null,
    val imageAccessibilityLabel: String? = null,
) : Parcelable

@Parcelize
data class TeamsErrorUiModel(
    val label: String,
    val type: TeamsErrorUiModelType
) : Parcelable

enum class TeamsErrorUiModelType {
    NETWORK,
    UNKNOWN,
    GENERIC,
}
