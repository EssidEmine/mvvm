package com.test.fdj.ui.screens.leagues.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeaguesUiModel(
    val isLoading: Boolean = false,
    val error: LeaguesErrorUiModel? = null,
    val leagues: List<LeagueUiModel>? = null,
    val isSearching: Boolean = false,
    val navigation: LeaguesNavigation = LeaguesNavigation.NONE
) : Parcelable

@Parcelize
data class LeagueUiModel(
    val name: String,
) : Parcelable

@Parcelize
data class LeaguesErrorUiModel(
    val label: String,
    val type: LeaguesErrorUiModelType
) : Parcelable

enum class LeaguesErrorUiModelType {
    NETWORK,
    UNKNOWN
}

@Parcelize
sealed interface LeaguesNavigation : Parcelable {
    @Parcelize
    data object NONE : LeaguesNavigation

    @Parcelize
    data class ShowTeams(val leagueName: String) : LeaguesNavigation
}
