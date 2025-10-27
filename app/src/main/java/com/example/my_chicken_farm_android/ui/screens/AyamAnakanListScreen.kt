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
import com.example.my_chicken_farm_android.data.model.AyamAnakan
import com.example.my_chicken_farm_android.ui.navigation.Screen
import com.example.my_chicken_farm_android.viewmodel.AyamAnakanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyamAnakanListScreen(
    navController: NavController,
    viewModel: AyamAnakanViewModel = viewModel()
) {
    val ayamList by viewModel.ayamList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ayam Anakan") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadAyamAnakan() }) {
                        Icon(Icons.Default.Refresh, "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AyamAnakanForm.createRoute()) }
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
                        Button(onClick = { viewModel.loadAyamAnakan() }) { Text("Retry") }
                    }
                }
                ayamList.isEmpty() -> {
                    Column(modifier = Modifier.align(Alignment.Center).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.ChildCare, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Belum ada data ayam anakan")
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ayamList) { ayam ->
                            AyamAnakanCard(
                                ayam = ayam,
                                onEdit = { navController.navigate(Screen.AyamAnakanForm.createRoute(ayam.id)) },
                                onDelete = { viewModel.deleteAyam(ayam.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AyamAnakanCard(ayam: AyamAnakan, onEdit: () -> Unit, onDelete: () -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(ayam.kode, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Breeding ID: ${ayam.breedingId}")
                    Text("Jenis: ${ayam.jenisKelamin}")
                    Text("Warna: ${ayam.warna}")
                    Text("Status: ${ayam.status}")
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
            text = { Text("Yakin ingin menghapus ${ayam.kode}?") },
            confirmButton = { TextButton(onClick = { onDelete(); showDeleteDialog = false }) { Text("Hapus") } },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") } }
        )
    }
}
