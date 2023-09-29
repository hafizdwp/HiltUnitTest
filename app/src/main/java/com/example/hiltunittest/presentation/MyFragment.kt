package com.example.hiltunittest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.hiltunittest.PermissionHelper
import com.example.hiltunittest.databinding.MyFragmentBinding
import com.example.hiltunittest.util.toJson
import com.example.hiltunittest.util.toast
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author hafizdwp
 * 22/09/2023
 **/
@AndroidEntryPoint
class MyFragment : Fragment() {

    private lateinit var binding: MyFragmentBinding
    private var openGalleryLauncher = PermissionHelper.multipleRequests(
            fragment = this,
            doOnGranted = {
                imagePickerLauncher.launch()
            },
            doOnDenied = {
                toast("eh ngentod acc dong")
            }
    )
    private val imagePickerLauncher = registerImagePicker { resultListImage ->
        try {
            toast(resultListImage.toJson())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MyFragmentBinding.inflate(LayoutInflater.from(requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPerm.setOnClickListener {
            openGalleryLauncher.launch(PermissionHelper.PERMISSION_PICK_PHOTO_GALLERY_WITH_CAMERA)
        }
    }
}