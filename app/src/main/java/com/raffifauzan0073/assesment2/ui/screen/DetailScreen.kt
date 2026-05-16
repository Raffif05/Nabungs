package com.raffifauzan0073.assesment2.ui.screen


import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.raffifauzan0073.assesment2.R
import com.raffifauzan0073.assesment2.ui.theme.Assesment2Theme
import com.raffifauzan0073.assesment2.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var nominal by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableIntStateOf(0) }

    val options = listOf("Pengeluaran", "Pemasukan")
    val jenis = options[selectedIndex]
    val isEdit = id != null

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getTransaksi(id) ?: return@LaunchedEffect
        nama = data.nama
        nominal = data.nominal.toString()
        kategori = data.kategori

        selectedIndex =
            if (data.jenis == "Pengeluaran") {
                0
            } else {
                1
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(
                        text = if (isEdit) {
                            stringResource(R.string.edit_transaksi)
                        } else {
                            stringResource(R.string.tambah_transaksi)
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama == "" || nominal == "" || (jenis == "Pengeluaran" && kategori == "")) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(nama, nominal.toInt(), kategori, jenis)
                        } else {
                            viewModel.update(id, nama, nominal.toInt(), kategori, jenis )
                        }

                        navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        FormTransaksi(
            nama = nama,
            onNamaChange = { nama = it },
            nominal = nominal,
            onNominalChange = { nominal = it },
            kategori = kategori,
            onKategoriChange = { kategori = it },
            selectedIndex = selectedIndex,
            onSelectedIndexChange = {
                selectedIndex = it
                kategori = ""
            },
            options = options,
            jenis = jenis,
            isEdit = isEdit,
            modifier = Modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTransaksi(
    nama: String, onNamaChange: (String) -> Unit,
    nominal: String, onNominalChange: (String) -> Unit,
    kategori: String, onKategoriChange: (String) -> Unit,
    selectedIndex: Int, onSelectedIndexChange: (Int) -> Unit,
    options: List<String>,
    jenis: String,
    isEdit: Boolean,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val kategoriOptions = listOf(
        "Makan",
        "Transportasi",
        "Hiburan",
        "Pakaian",
        "Tagihan"
    )

    val labelNama = if (jenis == "Pengeluaran") {
        stringResource(R.string.nama_pengeluaran)
    } else {
        stringResource(R.string.nama_pemasukan)
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!isEdit) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = {
                            onSelectedIndexChange(index)
                        },
                        selected = index == selectedIndex,
                        label = {
                            Text(text = label)
                        }
                    )
                }
            }
        } else {
            Text(
                text = jenis,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedTextField(
            value = nama,
            onValueChange = { onNamaChange(it) },
            label = { Text(text = labelNama) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nominal,
            onValueChange = { onNominalChange(it) },
            label = { Text(text = stringResource(R.string.nominal_transaksi)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (jenis == "Pengeluaran") {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = kategori,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(text = stringResource(R.string.kategori_pengeluaran))
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth())

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    kategoriOptions.forEach { pilihan ->
                        DropdownMenuItem(
                            text = {
                                Text(text = pilihan)
                            },
                            onClick = {
                                onKategoriChange(pilihan)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assesment2Theme {
        DetailScreen(rememberNavController())
    }
}