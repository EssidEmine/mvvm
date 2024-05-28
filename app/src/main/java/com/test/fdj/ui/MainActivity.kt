package com.test.fdj.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.fdj.ui.screens.leagues.LeaguesScreen
import com.test.fdj.ui.screens.teams.TeamsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Destination.Leagues.route,
            ) {
                composable(route = Destination.Leagues.route) {
                    LeaguesScreen(
                        onBackPressed = {
                            finish()
                        },
                        onShowTeams = { leagueName ->
                            navController.navigate(
                                route = Destination.Teams.buildUri(leagueName)
                            )
                        },
                    )
                }

                composable(
                    route = Destination.Teams.route,
                    arguments = listOf(
                        Destination.Teams.teamsTypeNavArgument()
                    )
                ) {
                    TeamsScreen(
                        onBackPressed = {
                            navController.popBackStack(
                                route = Destination.Leagues.route,
                                inclusive = false
                            )
                        },
                    )
                }
            }
        }
    }
}
