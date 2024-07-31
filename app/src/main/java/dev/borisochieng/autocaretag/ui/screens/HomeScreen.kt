package dev.borisochieng.autocaretag.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.nfc_reader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.ui.components.ReadDialog
import dev.borisochieng.autocaretag.ui.components.ClientCard
<<<<<<< HEAD
import dev.borisochieng.autocaretag.ui.components.ScreenTitle
=======
import dev.borisochieng.autocaretag.ui.navigation.Screens
>>>>>>> main
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography
import dev.borisochieng.autocaretag.utils.Dummies.fakeClients
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    clients: List<Client>,
    viewModel: NFCReaderViewModel,
<<<<<<< HEAD
    scanNFC: () -> Unit
=======
    scanForNfcTag: () -> Unit,
    navigate: (Screens) -> Unit
>>>>>>> main
) {
    var isReadDialogVisible by remember {
        mutableStateOf(false)
    }
    if (isReadDialogVisible) {
<<<<<<< HEAD
        ReadDialog(viewModel = viewModel, onCancel = { isReadDialogVisible = false }, scanNFC )
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ScreenTitle()
                NavigationDrawerItem(
                    modifier = Modifier.padding(4.dp),
                    label = { Text(text = "Help", style = typography.bodyLarge) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Rounded.Info, contentDescription = "Help") }
                )
                NavigationDrawerItem(
                    modifier = Modifier.padding(4.dp),
                    label = { Text(text = "About Us", style = typography.bodyLarge) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Rounded.Info, contentDescription = "Help") }
                )
                NavigationDrawerItem(
                    modifier = Modifier.padding(4.dp),
                    label = {
=======
        ReadDialog(
            viewModel = viewModel,
            navigate = navigate,
            onCancel = { isReadDialogVisible = false }
        )
    }
    Scaffold(
        modifier = Modifier.background(colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        style = typography.title,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .border(
                            width = 1.dp,
                            color = colorScheme.primary,
                            shape = CircleShape
                        )
                        .background(
                            color = colorScheme.background,
                            shape = CircleShape
                        )
                        .clickable {
                            scanForNfcTag()
                            isReadDialogVisible = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp),
                        text = stringResource(R.string.tap_to_scan_nfc_tag),
                        style = typography.title,
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.nfc_status),
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .align(Alignment.CenterVertically),
                        style = typography.body,
                        fontWeight = FontWeight.SemiBold
                    )

                    val statusColor = if (viewModel.nfcIsEnabled) Color.Green else colorScheme.onBackgroundVariant
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .size(6.dp)
                            .background(statusColor, shape = CircleShape)
                    )
                }
            }

            item {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = { navigate(Screens.AddScreen) },
                    shape = shape.button,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = Color.White
                    ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(
                                id = R.string.scan_nfc
                            )
                        )
>>>>>>> main
                        Text(
                            text = "Rate this app",
                            style = typography.bodyLarge
                        )
                    },
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Rounded.Info, contentDescription = "Help") }
                )
            }
        }) {
        val icon = if (drawerState.isOpen) Icons.Rounded.Close else Icons.Rounded.Menu
        Scaffold(
            modifier = Modifier.background(colorScheme.background),
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(colorScheme.background),
                    title = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ScreenTitle()
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (drawerState.isOpen) {
                                    drawerState.close()
                                } else {
                                    drawerState.open()
                                }
                            }

                        }) {

                            Icon(icon, contentDescription = if (drawerState.isOpen) "Close" else "Menu" )


                        }
                    }
                )
            }
<<<<<<< HEAD
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
=======

            item {
                Row(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 24.dp,
                        bottom = 16.dp,
                        end = 24.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.recent_activity),
                        style = typography.title,
                        color = colorScheme.primary
                    )

                    Text(
                        text = stringResource(R.string.view_all),
                        style = typography.body,
                        color = colorScheme.primary,
                        modifier = Modifier
                            .clickable { navigate(Screens.ManageScreen) }
                    )
                }
            }
            if (clients.isNotEmpty()) {
                items(items = clients) { client ->
                    ClientCard(client = client, onNavigateToClient = {})
                }
            } else {
>>>>>>> main
                item {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .border(
                                width = 1.dp,
                                color = colorScheme.primary,
                                shape = CircleShape
                            )
                            .background(
                                color = Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                isReadDialogVisible = true
                                scanNFC()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(4.dp),
                            text = stringResource(R.string.tap_to_scan_nfc_tag),
                            style = typography.title,
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {

                        Text(
                            text = stringResource(R.string.nfc_status),
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .align(Alignment.CenterVertically),
                            style = typography.body,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .size(6.dp)
                                .background(colorScheme.onBackgroundVariant, shape = CircleShape)
                        )
                    }
                }

                item {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = onNavigateToAddClient,
                        shape = shape.button,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary,
                            contentColor = Color.White
                        ),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                                contentDescription = stringResource(
                                    id = R.string.scan_nfc
                                )
                            )
                            Text(
                                text = stringResource(R.string.add_client),
                                style = typography.bodyLarge
                            )

                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 24.dp,
                            bottom = 16.dp,
                            end = 24.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.recent_activity),
                            style = typography.title,
                            color = colorScheme.primary
                        )

                        Text(
                            text = stringResource(R.string.view_all),
                            style = typography.body,
                            color = colorScheme.primary,
                            modifier = Modifier
                                .clickable(
                                    onClick =
                                    onNavigateToManage
                                )
                        )
                    }
                }
                if (clients.isNotEmpty()) {
                    items(items = clients) { client ->
                        ClientCard(client = client, onNavigateToClient = {})
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_activity),
                                style = typography.bodyLarge,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }

                }

            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
<<<<<<< HEAD
    HomeScreen(
        onNavigateToAddClient = {},
        onNavigateToManage = {},
        clients = fakeClients,
        viewModel(),
        {}
    )
=======
    HomeScreen(fakeClients, viewModel(), {}) {}
>>>>>>> main
}