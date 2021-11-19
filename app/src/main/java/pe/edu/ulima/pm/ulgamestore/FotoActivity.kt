package pe.edu.ulima.pm.ulgamestore

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class FotoActivity : AppCompatActivity() {
    private lateinit var iviFoto : ImageView
    private var photoPath : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)

        iviFoto = findViewById(R.id.iviFoto)

        findViewById<Button>(R.id.butTomarFoto).setOnClickListener {
            takePhoto()
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

    fun takePhoto() {
        var imageFile : File? = null
        try {
            imageFile = createImageFile()
        }catch (ioe : IOException) {
            Log.e("FotoActivity", "No se pudo crear archivo de imagen")
            return
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoURI = FileProvider.getUriForFile(
            this,
            "pe.edu.ulima.ulgamestore.fileprovider",
            imageFile
        )

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, 200)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imagen : Bitmap = data!!.extras!!.get("data") as Bitmap
            iviFoto.setImageBitmap(imagen)
        }else if(requestCode == 200 && resultCode == RESULT_OK) {
            showPhoto()
        }
    }

    private fun showPhoto() {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        BitmapFactory.decodeFile(photoPath, options)

        val iviHeight = iviFoto.height
        val iviWidth = iviFoto.width

        val scaleFactor = Math.min(iviWidth / options.outWidth, iviHeight / options.outHeight)

        options.inJustDecodeBounds = false
        options.inSampleSize = scaleFactor
        val bitmap = BitmapFactory.decodeFile(photoPath, options)
        iviFoto.setImageBitmap(bitmap)

    }

    @Throws(IOException::class)
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