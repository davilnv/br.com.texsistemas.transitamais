package br.com.texsistemas.transita.presentation.ui.common

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor

fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes res: Int,
    width: Int? = null,
    height: Int? = null
): BitmapDescriptor? {
    return try {
        MapsInitializer.initialize(context)

        val vectorDrawable = ContextCompat.getDrawable(context, res) ?: return null

        val finalWidth = width ?: vectorDrawable.intrinsicWidth
        val finalHeight = height ?: vectorDrawable.intrinsicHeight

        vectorDrawable.setBounds(0, 0, finalWidth, finalHeight)

        val bitmap = createBitmap(finalWidth, finalHeight)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)

        BitmapDescriptorFactory.fromBitmap(bitmap)
    } catch (e: Exception) {
        Log.e("MapMarker", "Erro ao gerar BitmapDescriptor", e)
        null
    }
}