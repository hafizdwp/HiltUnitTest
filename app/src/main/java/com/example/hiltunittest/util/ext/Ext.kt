package com.example.hiltunittest.util.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * @author hafizdwp
 * 21/09/2023
 **/

fun getThreadName() = Thread.currentThread().name

fun log(msg: String,
        tag: String? = null) {
        Log.d(tag ?: "mytag", msg)
}

fun logError(msg: String?,
             tag: String? = null) {
        Log.e(tag ?: "mytag", msg ?: "")
}

private var toast: Toast? = null

fun Context.toast(msg: String?,
                  spammable: Boolean = true) {
    if (!msg.isNullOrBlank()) {
        if (toast != null && spammable) {
            toast?.cancel()
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast?.show()
    }
}

fun Fragment.toast(msg: String?,
                   spammable: Boolean = true) {
    requireContext()?.toast(msg, spammable)
}

val gson by lazy { Gson() }
val gsonPretty: Gson by lazy { GsonBuilder().setPrettyPrinting().create() }

inline fun <reified T> makeType() = object : TypeToken<T>() {}.type

fun <T> T.toJson(pretty: Boolean = false): String {
    return try {
        if (!pretty)
            gson.toJson(this)
        else
            gsonPretty.toJson(this)
    } catch (e: Exception) {
        logError("toJson error: $e")
        ""
    }
}

inline fun <reified T> String.fromJson(): T = gson.fromJson(this, makeType<T>())

fun <T : Drawable> RequestBuilder<T>.withLoadingPlaceholder(context: Context): RequestBuilder<T> {
    return this.apply(RequestOptions().placeholder(context.myCircularProgressDrawable()))
}

fun Context.myCircularProgressDrawable(): androidx.swiperefreshlayout.widget.CircularProgressDrawable =
        androidx.swiperefreshlayout.widget.CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
