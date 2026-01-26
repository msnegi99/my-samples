package gaur.himanshu.preferencesdatastoreyt.ui_layer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val userName = viewModel.userName.collectAsState()

    val text = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(text = "Your Saved Name", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = userName.value, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Enter Your name", style = MaterialTheme.typography.h6)

        OutlinedTextField(value = text.value, onValueChange = { text.value = it })

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { viewModel.saveData(text.value) }) {
            Text(text = "Save Name")
        }

    }

}