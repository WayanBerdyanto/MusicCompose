package com.dicoding.mymusiccompose

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.mymusiccompose.ui.navigation.NavigationItem
import com.dicoding.mymusiccompose.ui.navigation.Screen
import com.dicoding.mymusiccompose.ui.screen.about.AboutScreen
import com.dicoding.mymusiccompose.ui.screen.detail.DetailScreen
import com.dicoding.mymusiccompose.ui.screen.home.HomeScreen
import com.dicoding.mymusiccompose.ui.theme.MyMusicComposeTheme

@Composable
fun MyMusicApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar ={
            if(currentRoute != Screen.DetailMusic.route){
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) {innerPadding->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { id ->
                        Log.d("MyMusicCompose" , "id route $id")
                        navController.navigate(Screen.DetailMusic.createRoute(id))
                    }
                )
            }
            composable(Screen.About.route){
                AboutScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = Screen.DetailMusic.route,
                arguments = listOf(navArgument("id"){type = NavType.LongType}),
            ){
                val id= it.arguments?.getLong("id") ?: -1L
                Log.d("MyMusicCompose" , "ID $id")
                DetailScreen(
                    id = id,
                    navigateBack ={
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MyMusicAppPreview(){
    MyMusicComposeTheme {
        MyMusicApp()
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        navigationItems.map {item->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}