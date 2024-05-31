package com.test.fdj.ui.screens.leagues

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.fdj.R
import com.test.fdj.ui.screens.error.ErrorScreen
import com.test.fdj.ui.screens.leagues.model.LeagueUiModel
import com.test.fdj.ui.screens.leagues.model.LeaguesErrorUiModelType.NETWORK
import com.test.fdj.ui.screens.leagues.model.LeaguesErrorUiModelType.UNKNOWN
import com.test.fdj.ui.screens.leagues.model.LeaguesNavigation
import com.test.fdj.ui.screens.leagues.model.LeaguesUiModel
import com.test.fdj.utils.theme.FdjTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaguesScreen(
    modifier: Modifier = Modifier,
    viewModel: LeaguesViewModel = hiltViewModel(),
    onShowTeams: (leagueName: String) -> Unit,
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiModelFlow.collectAsStateWithLifecycle()
    val searchedLeague = remember { mutableStateOf("") }

    BackHandler { onBackPressed() }

    when (val navigation = uiState.navigation) {
        is LeaguesNavigation.NONE -> {}
        is LeaguesNavigation.ShowTeams -> {
            onShowTeams(navigation.leagueName)
            viewModel.resetNavigation()
        }
    }
    FdjTheme {
        Scaffold(
            modifier = Modifier,
            topBar = {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = {
                        Text(text = stringResource(id = R.string.search_leagues))
                    },
                    onQueryChange = {
                        searchedLeague.value = it
                        viewModel.onSearchTextChange(it)
                    },
                    onSearch = viewModel::onSearchTextChange,
                    active = uiState.isSearching,
                    query = searchedLeague.value,
                    onActiveChange = {
                        viewModel.onToggleSearch(it)
                    },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                ) {
                    LeaguesContent(
                        modifier = modifier.padding(),
                        leagueUiModels = uiState,
                        onClick = viewModel::showTeams
                    )
                }
            },
            content = {
                if (uiState.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error?.let {
                    ErrorScreen(
                        error = it.label,
                        iconId = when (it.type) {
                            NETWORK -> R.drawable.cloud
                            UNKNOWN -> R.drawable.football
                        }
                    )
                }
            }
        )
    }
}
@Composable
private fun LeaguesContent(
    modifier: Modifier,
    leagueUiModels: LeaguesUiModel,
    onClick: (leagueName: String) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(20.dp)
    ) {
        leagueUiModels.leagues?.map {
            item(it) {
                Text(text = it.name,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clickable {
                            onClick(it.name)
                        }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED,
    showBackground = true,
)
@Composable
private fun LeaguesContentPreview() {
    FdjTheme {
        LeaguesContent(
            leagueUiModels = LeaguesUiModel(
                leagues = listOf(
                    LeagueUiModel("English"),
                    LeagueUiModel("Spanish"),
                    LeagueUiModel("Frensh")
                )
            ),
            onClick = {},
            modifier = Modifier
        )
    }
}
