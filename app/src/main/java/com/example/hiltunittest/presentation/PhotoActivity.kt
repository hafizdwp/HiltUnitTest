package com.example.hiltunittest.presentation

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.hiltunittest.databinding.ActivityBinding
import com.example.hiltunittest.util.ext.withLoadingPlaceholder
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author hafizdwp
 * 21/09/2023
 **/
@AndroidEntryPoint
class PhotoActivity : AppCompatActivity() {

    private val viewModel by viewModels<PhotoViewModel>()
    private lateinit var binding: ActivityBinding
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnGetPhoto.setOnClickListener {
            viewModel.getPhoto()
        }

        setupObserver()
    }

    fun setupObserver() = viewModel.apply {
        eventPhoto.observe(this@PhotoActivity) {
            Glide.with(this@PhotoActivity)
                    .load(it?.get(index)?.url)
                    .override(getDeviceWindowWidth(), Target.SIZE_ORIGINAL)
                    .withLoadingPlaceholder(this@PhotoActivity)
                    .into(binding.imgView)
            index++
        }
    }

    private fun getDeviceWindowWidth(): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        return displayMetrics.widthPixels
    }
}