package com.micudasoftware.planwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.micudasoftware.presentation.agenda.screen.Agenda
import com.micudasoftware.presentation.agenda.screen.AgendaScreen
import com.micudasoftware.presentation.agenda.viewmodel.AgendaViewModel
import com.micudasoftware.presentation.categories.Categories
import com.micudasoftware.presentation.categories.CategoriesScreen
import com.micudasoftware.presentation.categories.CategoriesViewModel
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.taskdetail.TaskDetail
import com.micudasoftware.presentation.taskdetail.TaskDetailScreen
import com.micudasoftware.presentation.taskdetail.TaskDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanWiseTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = Agenda
                ) {
                    composable<Agenda> {
                        val viewmodel = hiltViewModel<AgendaViewModel>()
                        AgendaScreen(
                            viewModel = viewmodel,
                            onNavigateToTaskDetail = { navController.navigate(TaskDetail()) }
                        )
                    }
                    composable<TaskDetail> { backStackEntry ->
                        val taskId = backStackEntry.toRoute<TaskDetail>().id
                        val viewModel = hiltViewModel<TaskDetailViewModel, TaskDetailViewModel.Factory> { factory ->
                            factory.create(taskId)
                        }
                        TaskDetailScreen(
                            viewModel = viewModel,
                            onNavigateBack = { navController.navigateUp() }
                        )
                    }
                    composable<Categories> {
                        val viewModel = hiltViewModel<CategoriesViewModel>()
                        CategoriesScreen(
                            viewModel = viewModel,
                            onNavigateBack = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}