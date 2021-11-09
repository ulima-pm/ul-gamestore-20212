package pe.edu.ulima.pm.ulgamestore

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import pe.edu.ulima.pm.ulgamestore.model.LoginManager

class SignupActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        createNotificationChannel("1")

        findViewById<Button>(R.id.butSignup).setOnClickListener { v : View ->
            // Guardar los datos en algun servicio de persistencia

            LoginManager.instance.saveUser(
                findViewById<EditText>(R.id.eteName).text.toString(),
                findViewById<EditText>(R.id.eteUsername).text.toString(),
                findViewById<EditText>(R.id.etePassword).text.toString(),
                {


                    sendNotification(createNotification("1"))

                },
                {
                    Log.e("SignupActivity", it)
                    Toast.makeText(this, "Error guardando usuario", Toast.LENGTH_SHORT).show()
                }
            )


        }

        findViewById<Button>(R.id.butCancel).setOnClickListener { v: View ->
            setResult(RESULT_CANCELED)
            finish()
        }

    }

    private fun sendNotification(notification : Notification) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        Log.d("SignupActivity", "Notification")
        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Canal 1",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(channelId : String) : Notification {
        val bundle = Bundle()
        bundle.putString("username", findViewById<EditText>(R.id.eteUsername).text.toString())
        bundle.putString("password", findViewById<EditText>(R.id.etePassword).text.toString())

        // 1010 | 0001  -> 1011
        val intent = Intent(this, LoginActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.putExtra("signup_data", bundle)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setContentTitle("Signup")
            .setContentText("Se registro un nuevo usuario")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH).build()
    }
}