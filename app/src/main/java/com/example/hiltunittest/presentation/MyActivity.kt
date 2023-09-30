package com.example.hiltunittest.presentation

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.hiltunittest.databinding.ActivityBinding
import com.example.hiltunittest.util.log
import com.example.hiltunittest.util.toJson
import com.example.hiltunittest.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.measureTimeMillis


/**
 * @author hafizdwp
 * 21/09/2023
 **/
@AndroidEntryPoint
class MyActivity : AppCompatActivity() {

    private val viewModel by viewModels<MyViewModel>()
    private lateinit var binding: ActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnGetPhoto.setOnClickListener {
            val time = measureTimeMillis {
                viewModel.getPhoto()
            }
            binding.textTime.text = time.toString()
        }
        binding.btnPermission.setOnClickListener {
//            openGalleryLauncher.launch(PermissionHelper.PERMISSION_PICK_PHOTO_GALLERY_WITH_CAMERA)
        }
        binding.btnPlaystore.setOnClickListener {
            openPlaystore()
        }

        observe()

        viewModel.heavyOperation()
        supportFragmentManager.commit {
            replace(binding.frameLayout.id, MyFragment())
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun openPlaystore() {
        val packageName = "id.gits.bidanconnect.sandbox"
        val marketUri = "market://details?id=$packageName"
        val urlUri = "https://play.google.com/store/apps/details?id=$packageName"

        // try market uri
        try {
            log("opening market")

            val firstUri = Uri.parse(marketUri)
            val firstIntent = Intent(Intent.ACTION_VIEW, firstUri)

            if (firstIntent.resolveActivity(this.packageManager) != null) {
                startActivity(firstIntent)
            } else {

                // try url uri
                try {
                    log("opening url")

                    val secondUri = Uri.parse(urlUri)
                    val secondIntent = Intent(Intent.ACTION_VIEW, secondUri)

                    if (secondIntent.resolveActivity(this.packageManager) != null) {
                        startActivity(secondIntent)
                    } else {
                        toast("Gagal membuka play store")
                    }

                } catch (e: Exception) {
                    toast("Gagal membuka play store")
                }

            }
        } catch (e: Exception) {
            toast("Gagal membuka play store")
        }
    }

    private val imagePickerLauncher = registerImagePicker { resultListImage ->
        try {
            toast(resultListImage.toJson())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var index = 0
    fun observe() = viewModel.apply {
        eventPhoto.observe(this@MyActivity) {
            log("ui screen thread: ${Thread.currentThread().name}")
            Glide.with(this@MyActivity)
                    .load(it?.get(index)?.url)
                    .override(getDeviceWindowWidth(), Target.SIZE_ORIGINAL)
                    .centerCrop()
                    .into(binding.imgView)
            index++
        }

        eventCountdown.observe(this@MyActivity) {
            binding.countdown.text = it.toString()
        }
    }

    fun getDeviceWindowWidth(): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        toast(displayMetrics.widthPixels.toString())
        return displayMetrics.widthPixels
    }

}