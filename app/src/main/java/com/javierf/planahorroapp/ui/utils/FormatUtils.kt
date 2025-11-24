package com.javierf.planahorroapp.ui.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun formatMoneyNumber(raw: String): String {
    if (raw.isEmpty()) return ""

    // Quitar TODO excepto números
    val clean = raw.filter { it.isDigit() }

    if (clean.isEmpty()) return ""

    val number = clean.toLong()

    val formatter = NumberFormat.getNumberInstance(Locale("es", "CO"))
    return formatter.format(number)
}

// Convierte "25.000" → 25000.0
fun parseFormattedNumberToDouble(text: String): Double {
    val clean = text.replace(".", "").trim()
    return clean.toDoubleOrNull() ?: 0.0
}
// ---------------------------------------------------
// FORMATO DE DINERO
// ---------------------------------------------------
fun formatMoney(amount: Double): String {
    val formatter = NumberFormat.getNumberInstance(Locale("es", "CO"))
    formatter.maximumFractionDigits = 0
    formatter.minimumFractionDigits = 0
    return "$" + formatter.format(amount)
}

// ---------------------------------------------------
// FORMATO DE FECHA (ISO 8601 → “23 nov 2025 — 8:33 PM”)
// ---------------------------------------------------
fun formatDate(isoDate: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        parser.timeZone = TimeZone.getTimeZone("UTC")

        val date = parser.parse(isoDate)

        val formatter = SimpleDateFormat("d MMM yyyy — h:mm a", Locale("es", "CO"))
        formatter.format(date!!)
    } catch (e: Exception) {
        isoDate // si falla, deja la fecha original
    }


}

