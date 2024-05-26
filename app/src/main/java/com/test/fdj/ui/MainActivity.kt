package com.test.fdj.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                startDestination = Leagues.route,
            ) {
                composable(route = Leagues.route) {
                    LeaguesScreen(
                        onBackPressed = {
                            finish()
                        },
                        onShowTeams = { leagueName ->
                            navController.navigateTo(
                                route = Teams.route,
                                argument = leagueName
                            )
                        },
                    )
                }

                composable(
                    route = Teams.routeWithArgs,
                    arguments = listOf(
                        navArgument(Teams.teamsArgs) {
                            type = NavType.StringType
                            nullable = false
                        }
                    )
                ) {
                    TeamsScreen(
                        onBackPressed = {
                            navController.popBackStack()
                        },
                    )
                }
            }
        }
    }
}

fun NavHostController.navigateTo(route: String, argument: String) {
    val route = "${route}/$argument"
    this.navigate(route)
}

fun NavHostController.navigateToTeams(leagueName: String) {
    val route = "${Teams.route}/$leagueName"
    this.navigate(route)
}
