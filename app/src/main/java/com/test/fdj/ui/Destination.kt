package com.test.fdj.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destination(
    val route: String
) {

    data object Leagues : Destination("leagues")
    data object Teams : Destination("teams/{$TEAMS_ARGS}") {

        fun buildUri(teamsArgs: String) = "teams/$teamsArgs"

        fun teamsTypeNavArgument() = navArgument(TEAMS_ARGS) {
            type = NavType.StringType
            nullable = false
        }
    }

    companion object {

        const val TEAMS_ARGS = "leagueName"
    }
}
