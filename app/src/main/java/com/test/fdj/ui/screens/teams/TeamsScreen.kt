package com.test.fdj.ui.screens.teams

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.test.fdj.R
import com.test.fdj.ui.screens.error.ErrorScreen
import com.test.fdj.ui.screens.teams.model.TeamsErrorUiModelType
import com.test.fdj.ui.screens.teams.model.TeamsUiModel
import com.test.fdj.utils.theme.FdjTheme

@Composable
fun TeamsScreen(
    modifier: Modifier = Modifier,
    viewModel: TeamsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiModelFlow.collectAsStateWithLifecycle()
    BackHandler {
        onBackPressed()
    }
    FdjTheme {
        Scaffold(
            modifier = Modifier,
            topBar = {},
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize(),
                ) {
                    if (uiState.isLoading) {
                        Column(
                            Modifier.fillMaxSize(),
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
                                TeamsErrorUiModelType.NETWORK -> R.drawable.cloud
                                else -> R.drawable.football
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = uiState.teams.isNotEmpty(),
                        enter = fadeIn(animationSpec = tween(durationMillis = 400)),
                        exit = fadeOut(animationSpec = tween(durationMillis = 200)),
                    ) {
                        TeamsContent(
                            modifier = modifier.padding(padding),
                            uiModel = uiState
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun TeamsContent(
    modifier: Modifier = Modifier,
    uiModel: TeamsUiModel,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(uiModel.teams.size) { item ->
            AsyncImage(
                modifier = Modifier,
                model = uiModel.teams[item].imageUrl,
                contentDescription = uiModel.teams[item].imageAccessibilityLabel,
            )

        }
    }
}
