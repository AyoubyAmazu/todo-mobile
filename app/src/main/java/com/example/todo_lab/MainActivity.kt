package com.example.todo_lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_lab.api.RetrofitClient
import com.example.todo_lab.data.Todo
import com.example.todo_lab.ui.theme.TodolabTheme

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

@Composable
fun TodoApp() {
    var titre by remember { mutableStateOf("Chargement...") }

    // ⚠️ On appelle Retrofit ici avec enqueue (asynchrone mais sans coroutine)
    LaunchedEffect(Unit) {
        val call = RetrofitClient.api.getTodo()
        call.enqueue(object : Callback<Todo> {
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                if (response.isSuccessful) {
                    val todo = response.body()
                    if (todo != null) {
                        titre = todo.title
                    } else {
                        titre = "Réponse vide"
                    }
                } else {
                    titre = "Erreur HTTP ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                titre = "Erreur réseau : ${t.message}"
            }
        })
    }

    // UI simple
    Surface (modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titre, style = MaterialTheme.typography.bodyLarge)
        }
    }
}