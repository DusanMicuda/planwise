package com.micudasoftware.planwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.micudasoftware.presentation.agenda.screen.AgendaScreen
import com.micudasoftware.presentation.agenda.viewmodel.AgendaViewModel
import com.micudasoftware.presentation.categories.CategoriesScreen
import com.micudasoftware.presentation.categories.CategoriesViewModel
import com.micudasoftware.presentation.common.utils.collectAsEvent
import com.micudasoftware.presentation.common.navigation.Destination
import com.micudasoftware.presentation.common.navigation.NavEvent
import com.micudasoftware.presentation.common.navigation.Navigator
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.taskdetail.TaskDetailScreen
import com.micudasoftware.presentation.taskdetail.TaskDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanWiseTheme {
                val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f).toArgb()
                val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
                LaunchedEffect(Unit) {
                    this@MainActivity.window.statusBarColor = primaryColor
                    this@MainActivity.window.navigationBarColor = backgroundColor
                }

                val navController = rememberNavController()
                navigator.navEvent.collectAsEvent { navEvent ->
                    when (navEvent) {
                        is NavEvent.To -> navController.navigate(navEvent.destination, navEvent.navOptions)
                        NavEvent.Back -> navController.navigateUp()
                    }
                }

                NavHost(
                    navController = navController, startDestination = Destination.Agenda
                ) {
                    composable<Destination.Agenda> {
                        val viewmodel = hiltViewModel<AgendaViewModel>()
                        AgendaScreen(
                            viewModel = viewmodel,
                            navigator = navigator
                        )
                    }
                    composable<Destination.TaskDetail> { backStackEntry ->
                        val data = backStackEntry.toRoute<Destination.TaskDetail>()
                        val viewModel = hiltViewModel<TaskDetailViewModel, TaskDetailViewModel.Factory> { factory ->
                            factory.create(data.id, data.edit)
                        }
                        TaskDetailScreen(
                            viewModel = viewModel,
                            navigator = navigator,
                        )
                    }
                    composable<Destination.Categories> {
                        val viewModel = hiltViewModel<CategoriesViewModel>()
                        CategoriesScreen(
                            viewModel = viewModel,
                            navigator = navigator
                        )
                    }
                }
            }
        }
    }
}