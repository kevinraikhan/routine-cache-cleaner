package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

// CREDIT : https://stackoverflow.com/a/8826357
class StringUtils {
    companion object {
        private fun floatForm(d: Double): String {
            return DecimalFormat("#.##").format(d)
        }


        fun bytesToHuman(size: Long): String? {
            val Kb = 1 * 1024.toLong()
            val Mb = Kb * 1024
            val Gb = Mb * 1024
            val Tb = Gb * 1024
            val Pb = Tb * 1024
            val Eb = Pb * 1024
            if (size < Kb) return floatForm(
                size.toDouble()
            ) + " byte"
            if (size >= Kb && size < Mb) return floatForm(
                size.toDouble() / Kb
            ) + " Kb"
            if (size >= Mb && size < Gb) return floatForm(
                size.toDouble() / Mb
            ) + " Mb"
            if (size >= Gb && size < Tb) return floatForm(
                size.toDouble() / Gb
            ) + " Gb"
            if (size >= Tb && size < Pb) return floatForm(
                size.toDouble() / Tb
            ) + " Tb"
            if (size >= Pb && size < Eb) return floatForm(
                size.toDouble() / Pb
            ) + " Pb"
            return if (size >= Eb) floatForm(
                size.toDouble() / Eb
            ) + " Eb" else "???"
        }

        fun milisToDateString(milisLong: Long): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm")
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = milisLong
            return formatter.format(calendar.time)
        }
    }
}