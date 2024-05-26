package com.test.fdj.ui

interface Destination {
    val route: String
}

object Leagues: Destination{
    override val route: String = "leagues"
}
object Teams: Destination{
    override val route: String = "teams"
    const val teamsArgs = "leagueName"
    val routeWithArgs: String = "${route}/{${teamsArgs}}"
}
