package com.example.nycschools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycschools.components.displayNYCSchoolDetails
import com.example.nycschools.components.displayNYCSchoolItem
import com.example.nycschools.response.NYCSchoolItem
import com.example.nycschools.viewmodels.DisplayNYCSchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val displayNYCSchoolsViewModel by viewModels<DisplayNYCSchoolsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        return setContent {
            val isLoading by displayNYCSchoolsViewModel.isLoading
            val listOfNYCSchools by displayNYCSchoolsViewModel.uiState.observeAsState()
            displayNYCSchools(isLoading, listOfNYCSchools)
        }
    }
}

@Composable
fun displayNYCSchools(isLoading: Boolean, listOfNYCSchools: Result<List<NYCSchoolItem>>?) {
    val navController = rememberNavController()
    val openDialog = remember { mutableStateOf(true) }
    NavHost(navController = navController, startDestination = "schoolslist") {
        composable("schoolslist") {
            if (isLoading) {
                Row(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxSize()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.purple_200),
                    )
                }
            } else {
                listOfNYCSchools?.onSuccess {
                    LazyColumn {
                        itemsIndexed(
                            items = it
                        ) { index, NYCSchoolItem ->
                            displayNYCSchoolItem(nycSchoolItem = NYCSchoolItem, onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "NYCSchoolItem", NYCSchoolItem
                                )
                                navController.navigate("scoresdetails")
                            })
                        }
                    }
                }
                listOfNYCSchools?.onFailure {
                    if (openDialog.value) {
                        AlertDialog(onDismissRequest = {
                            openDialog.value = false
                        }, title = {
                            Text(text = "Error")
                        }, text = {
                            Text(text = "Something went wrong, please try again later")
                        }, buttons = {
                            Row(
                                modifier = Modifier.padding(all = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(modifier = Modifier.fillMaxWidth(),
                                    onClick = { openDialog.value = false }) {
                                    Text("Dismiss")
                                }
                            }
                        })
                    }
                }
            }
        }

        composable("scoresdetails") {
            val nycSchoolItem =
                navController.previousBackStackEntry?.savedStateHandle?.get<NYCSchoolItem>("NYCSchoolItem")
            displayNYCSchoolDetails(nycSchoolItem)
        }
    }
}