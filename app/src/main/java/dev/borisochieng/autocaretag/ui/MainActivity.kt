package dev.borisochieng.autocaretag.ui

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.borisochieng.autocaretag.nfc_reader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.ui.screens.Client
import dev.borisochieng.autocaretag.ui.screens.HomeScreen
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val nfcReaderViewModel: NFCReaderViewModel by inject()
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        enableEdgeToEdge()
        setContent {
            AutoCareTagTheme {
                val fakeClients = listOf(
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "Mark Joe",
                        vehicleName = "Ford Ranger"
                    ),
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )

                )
                HomeScreen(
                    onNavigateToScan = { /*TODO*/ },
                    onNavigateToNotifications = { /*TODO*/ },
                    onNavigateToClient = {},
                    clients = fakeClients
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action != NfcAdapter.ACTION_NDEF_DISCOVERED) return
        nfcReaderViewModel.readNFCTag(intent)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent
            .getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val intentFilters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        )
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }
}