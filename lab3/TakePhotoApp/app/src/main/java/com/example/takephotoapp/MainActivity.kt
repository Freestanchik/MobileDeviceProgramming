package com.example.takephotoapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.example.takephotoapp.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListeners()
    }

    private val takeImageLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    binding.imagePreview.setImageURI(uri)
                }
            }
        }

    private val takeImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            latestTmpUri = uri
            uri?.let { binding.imagePreview.setImageURI(uri) }
        }

    private var latestTmpUri: Uri? = null

    private fun setClickListeners() {
        binding.takeImageButton.setOnClickListener { takeImage() }
        binding.selectImageButton.setOnClickListener { selectImageFromGallery() }
        binding.sendImageButton.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "КПП НАИ-196 БигунН")
            i.putExtra(Intent.EXTRA_STREAM, latestTmpUri)
            startActivity(i)
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageLauncher.launch(uri)
            }
        }
    }

    private fun selectImageFromGallery() = takeImageFromGalleryLauncher.launch("image/*")

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_img_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile)
    }
}