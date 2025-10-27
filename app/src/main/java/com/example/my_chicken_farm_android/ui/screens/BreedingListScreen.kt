package com.example.my_chicken_farm_android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.my_chicken_farm_android.data.model.Breeding
import com.example.my_chicken_farm_android.ui.navigation.Screen
import com.example.my_chicken_farm_android.viewmodel.BreedingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedingListScreen(
    navController: NavController,
    viewModel: BreedingViewModel = viewModel()
) {
    val breedingList by viewModel.breedingList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Breeding") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadBreeding() }) {
                        Icon(Icons.Default.Refresh, "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.BreedingForm.createRoute()) }
            ) {
                Icon(Icons.Default.Add, "Tambah")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                error != null -> {
                    Column(modifier = Modifier.align(Alignment.Center).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $error", color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.loadBreeding() }) { Text("Retry") }
                    }
                }
                breedingList.isEmpty() -> {
                    Column(modifier = Modifier.align(Alignment.Center).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Favorite, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Belum ada data breeding")
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(breedingList) { breeding ->
                            BreedingCard(
                                breeding = breeding,
                                onEdit = { navController.navigate(Screen.BreedingForm.createRoute(breeding.id)) },
                                onDelete = { viewModel.deleteBreeding(breeding.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BreedingCard(breeding: Breeding, onEdit: () -> Unit, onDelete: () -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Pejantan ID: ${breeding.pejantanId}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Betina ID: ${breeding.betinaId}")
                    Text("Tanggal Kawin: ${breeding.tanggalKawin}")
                    Text("Tanggal Menetas: ${breeding.tanggalMenetas}")
                    Text("Jumlah Anakan: ${breeding.jumlahAnakan}")
                }
                Row {
                    IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, "Edit") }
                    IconButton(onClick = { showDeleteDialog = true }) { Icon(Icons.Default.Delete, "Delete") }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Data") },
            text = { Text("Yakin ingin menghapus data breeding ini?") },
            confirmButton = { TextButton(onClick = { onDelete(); showDeleteDialog = false }) { Text("Hapus") } },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") } }
        )
    }
}
