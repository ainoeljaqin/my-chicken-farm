package com.example.my_chicken_farm_android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.my_chicken_farm_android.data.model.Breeding
import com.example.my_chicken_farm_android.viewmodel.BreedingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedingFormScreen(
    navController: NavController,
    id: String,
    viewModel: BreedingViewModel = viewModel()
) {
    val breedingList by viewModel.breedingList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val isEdit = id != "new"
    val existingBreeding = if (isEdit) breedingList.find { it.id == id } else null

    var pejantanId by remember { mutableStateOf(existingBreeding?.pejantanId ?: "") }
    var betinaId by remember { mutableStateOf(existingBreeding?.betinaId ?: "") }
    var tanggalKawin by remember { mutableStateOf(existingBreeding?.tanggalKawin ?: "") }
    var tanggalMenetas by remember { mutableStateOf(existingBreeding?.tanggalMenetas ?: "") }
    var jumlahAnakan by remember { mutableStateOf(existingBreeding?.jumlahAnakan?.toString() ?: "0") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Edit Breeding" else "Tambah Breeding") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(value = pejantanId, onValueChange = { pejantanId = it }, label = { Text("ID Pejantan") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)
            OutlinedTextField(value = betinaId, onValueChange = { betinaId = it }, label = { Text("ID Betina") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)
            OutlinedTextField(value = tanggalKawin, onValueChange = { tanggalKawin = it }, label = { Text("Tanggal Kawin (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)
            OutlinedTextField(value = tanggalMenetas, onValueChange = { tanggalMenetas = it }, label = { Text("Tanggal Menetas (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)
            OutlinedTextField(value = jumlahAnakan, onValueChange = { jumlahAnakan = it }, label = { Text("Jumlah Anakan") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val breeding = Breeding(
                        id = if (isEdit) id else "",
                        pejantanId = pejantanId,
                        betinaId = betinaId,
                        tanggalKawin = tanggalKawin,
                        tanggalMenetas = tanggalMenetas,
                        jumlahAnakan = jumlahAnakan.toIntOrNull() ?: 0
                    )

                    if (isEdit) {
                        viewModel.updateBreeding(breeding) { navController.navigateUp() }
                    } else {
                        viewModel.addBreeding(breeding) { navController.navigateUp() }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && pejantanId.isNotBlank() && betinaId.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(if (isEdit) "Update" else "Simpan")
                }
            }
        }
    }
}
