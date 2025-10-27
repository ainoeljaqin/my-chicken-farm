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
import com.example.my_chicken_farm_android.data.model.AyamAnakan
import com.example.my_chicken_farm_android.viewmodel.AyamAnakanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyamAnakanFormScreen(
    navController: NavController,
    id: String,
    viewModel: AyamAnakanViewModel = viewModel()
) {
    val ayamList by viewModel.ayamList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val isEdit = id != "new"
    val existingAyam = if (isEdit) ayamList.find { it.id == id } else null

    var breedingId by remember { mutableStateOf(existingAyam?.breedingId ?: "") }
    var kode by remember { mutableStateOf(existingAyam?.kode ?: "") }
    var jenisKelamin by remember { mutableStateOf(existingAyam?.jenisKelamin ?: "jantan") }
    var warna by remember { mutableStateOf(existingAyam?.warna ?: "") }
    var status by remember { mutableStateOf(existingAyam?.status ?: "hidup") }

    var expandedJenisKelamin by remember { mutableStateOf(false) }
    var expandedStatus by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Edit Ayam Anakan" else "Tambah Ayam Anakan") },
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
            OutlinedTextField(value = breedingId, onValueChange = { breedingId = it }, label = { Text("Breeding ID") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)
            OutlinedTextField(value = kode, onValueChange = { kode = it }, label = { Text("Kode Ayam") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)

            ExposedDropdownMenuBox(
                expanded = expandedJenisKelamin,
                onExpandedChange = { expandedJenisKelamin = !expandedJenisKelamin }
            ) {
                OutlinedTextField(
                    value = jenisKelamin,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Jenis Kelamin") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedJenisKelamin) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    enabled = !isLoading
                )
                ExposedDropdownMenu(expanded = expandedJenisKelamin, onDismissRequest = { expandedJenisKelamin = false }) {
                    DropdownMenuItem(text = { Text("jantan") }, onClick = { jenisKelamin = "jantan"; expandedJenisKelamin = false })
                    DropdownMenuItem(text = { Text("betina") }, onClick = { jenisKelamin = "betina"; expandedJenisKelamin = false })
                }
            }

            OutlinedTextField(value = warna, onValueChange = { warna = it }, label = { Text("Warna") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)

            ExposedDropdownMenuBox(
                expanded = expandedStatus,
                onExpandedChange = { expandedStatus = !expandedStatus }
            ) {
                OutlinedTextField(
                    value = status,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Status") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    enabled = !isLoading
                )
                ExposedDropdownMenu(expanded = expandedStatus, onDismissRequest = { expandedStatus = false }) {
                    DropdownMenuItem(text = { Text("hidup") }, onClick = { status = "hidup"; expandedStatus = false })
                    DropdownMenuItem(text = { Text("mati") }, onClick = { status = "mati"; expandedStatus = false })
                    DropdownMenuItem(text = { Text("dijual") }, onClick = { status = "dijual"; expandedStatus = false })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val ayam = AyamAnakan(
                        id = if (isEdit) id else "",
                        breedingId = breedingId,
                        kode = kode,
                        jenisKelamin = jenisKelamin,
                        warna = warna,
                        status = status
                    )

                    if (isEdit) {
                        viewModel.updateAyam(ayam) { navController.navigateUp() }
                    } else {
                        viewModel.addAyam(ayam) { navController.navigateUp() }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && kode.isNotBlank() && breedingId.isNotBlank()
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
