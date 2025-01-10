package com.example.userauth.module.login.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Constraints
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userauth.Constants
import com.example.userauth.module.login.viewModels.LoginViewModel
import com.example.userauth.network.NetworkState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val loginResponse by loginViewModel.mLoginResponse.collectAsState()

    LaunchedEffect(loginResponse) {
        when(loginResponse){
            is NetworkState.Loading -> {}
            is NetworkState.Success -> {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                // TODO: save token to shared preference
                navController.navigate(Constants.homeScreen)
            }
            is NetworkState.Failure -> {
                Toast.makeText(context, (loginResponse as NetworkState.Failure).errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Login") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                TextField(value = email, onValueChange =  { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    placeholder = { Text("Enter email") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = password, onValueChange =  { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    placeholder = { Text("Enter password") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Don't have an account?")
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(modifier = Modifier.clickable {
                        navController.navigate(Constants.signupScreen)
                    }) {
                        Text("Signup", style = TextStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp
                        ))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    if(email.isNotEmpty() && password.isNotEmpty()){
                        loginViewModel.loginUser(email, password)
                    } else{
                        Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Login", style = TextStyle(
                        fontSize = 15.sp
                    ))
                }
            }
        }
    }
}