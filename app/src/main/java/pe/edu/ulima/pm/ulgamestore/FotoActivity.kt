package pe.edu.ulima.pm.ulgamestore

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FotoActivity : AppCompatActivity() {
    private lateinit var iviFoto : ImageView
    private var photoPath : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)

        iviFoto = findViewById(R.id.iviFoto)

        findViewById<Button>(R.id.butTomarFoto).setOnClickListener {
            showCamera()
        }
    }

    fun showCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, 100)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this,
                "No hay aplicativo de camara",
                Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imagen : Bitmap = data!!.extras!!.get("data") as Bitmap
            iviFoto.setImageBitmap(imagen)
        }
    }

    fun createImageFile() : File {
        val timestamp = SimpleDateFormat("yyyyMMddd_HHmmss").format(Date())
        val imageFile = File.createTempFile(
            "${timestamp}_",
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        photoPath = imageFile.absolutePath
        return imageFile
    }
}