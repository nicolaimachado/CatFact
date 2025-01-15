package com.nicolaischirmer.catfact

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.nicolaischirmer.catfact.models.CatFacts
import com.nicolaischirmer.catfact.ui.theme.CatFactTheme
import com.nicolaischirmer.catfact.utils.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatFactTheme {
                CatFactApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CatFactTheme {
        CatFactApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CatFactApp() {
    val viewModel: CatViewModel = viewModel()
    var catFacts by mutableStateOf(viewModel.catFacts)
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Update catFacts when the list in the ViewModel changes
    LaunchedEffect(viewModel.catFacts) {
        catFacts = viewModel.catFacts
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("10 Factos de Gatito Aleatorios") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(text = "Error: $errorMessage")
            } else {
                CatFactList(catFacts = catFacts)
            }
        }
    }
}

@Composable
fun CatFactList(catFacts: List<CatFacts>) {
    LazyColumn {
        items(catFacts) { fact ->
            CatFactItem(fact = fact)
        }
    }
}

@Composable
fun CatFactItem(fact: CatFacts) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = fact.fact, style = MaterialTheme.typography.bodyLarge)
    }
}




