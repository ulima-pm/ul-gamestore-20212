package pe.edu.ulima.pm.ulgamestore.network

import android.os.Build
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class NetworkClient(var url : String) {

    // thread-safe
    fun download(callbackOK : (String) -> Unit, callbackError : (String) -> Unit) {

        Thread() {
            val urlConn = URL(url)
            val conn = urlConn.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            //conn.doOutput = true

            conn.connect() // cuidado!

            val statusCode = conn.responseCode

            if (statusCode != 200) {
                // Error
                callbackError("Error en la comunicacion: $statusCode")
            }else {
                val inputStream = conn.inputStream
                val resp = convertInputStreamtoString(inputStream)
                inputStream.close()
                callbackOK(resp)
            }
        }.start()
    }

    fun upload() {

    }

    private fun convertInputStreamtoString(inputStream : InputStream) : String {
        val br = BufferedReader(InputStreamReader(inputStream))
        val cadena = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            br.lines().collect(Collectors.joining("\n"))
        } else {
            val cadenaCompleta = StringBuilder()
            var line : String = ""
            line = br.readLine()
            while(line != "") {
                cadenaCompleta.append(line).append("\n")
                line = br.readLine()
            }
            cadenaCompleta.toString()
        }
        return cadena
    }
}