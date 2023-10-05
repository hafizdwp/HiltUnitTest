package com.example.hiltunittest.util

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

/**
 * @author hafizdwp
 * 22/09/2023
 **/
object PermissionHelper {
    val PERMISSION_CAMERA =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(Manifest.permission.CAMERA)
            } else {
                arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
            }

    val PERMISSION_PICK_PHOTO_GALLERY_WITH_CAMERA =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.CAMERA)
            } else {
                arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
            }

    fun singleRequest(fragment: Fragment,
                      doOnGranted: () -> Unit,
                      doOnDenied: () -> Unit) = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) doOnGranted()
        else doOnDenied()
    }

    fun multipleRequests(fragment: Fragment,
                         doOnGranted: () -> Unit,
                         doOnDenied: () -> Unit) = fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        var allGranted = false
        it.forEach { mapIterate ->
            allGranted = mapIterate.value
        }

        if (allGranted) doOnGranted()
        else doOnDenied()
    }
}