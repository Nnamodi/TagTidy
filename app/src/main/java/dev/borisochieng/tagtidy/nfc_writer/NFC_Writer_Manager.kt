package dev.borisochieng.tagtidy.nfc_writer

import android.content.Context
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import com.google.gson.Gson

class NfcWriter(private val context: Context,private val tag: Tag) {

    fun writeLaundryInfoToNfcTag(tag: Tag, info: LaundryInfo): NfcWriteState<LaundryInfo> {
        // Initialize NFC adapter and check if NFC is available
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            // NFC not available on this device
            return NfcWriteState.error("NFC not available on this device")
        }
        val json = Gson().toJson(info)
        val ndefRecord = NdefRecord.createMime("application/json", json.toByteArray())
        val ndefMessage = NdefMessage(arrayOf(ndefRecord))

        try {
            val ndef = Ndef.get(tag)
            ndef.connect()
            ndef.writeNdefMessage(ndefMessage)
            ndef.close()
            // Writing success
            return NfcWriteState.success(info)
        } catch (e: Exception) {
            // Writing error
            return NfcWriteState.error("Error writing LaundryInfo to NFC tag: ${e.message}")
        }
    }

}