package com.example.userauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.userauth.ui.theme.UserAuthTheme
import com.example.userauth.module.home.views.HomeScreen
import com.example.userauth.module.login.views.LoginScreen
import com.example.userauth.module.signup.views.SignupScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserAuthTheme {
                AppNav()
            }
        }
    }
}

@Composable
fun AppNav() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Constants.loginScreen) {
        composable(Constants.loginScreen) {
            LoginScreen(navController)
        }
        composable(Constants.signupScreen) {
            SignupScreen(navController = navController)
        }
        composable(Constants.homeScreen){
            HomeScreen(navController = navController)
        }
    }
}
