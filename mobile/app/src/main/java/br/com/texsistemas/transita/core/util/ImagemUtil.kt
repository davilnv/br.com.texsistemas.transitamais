package br.com.texsistemas.transita.core.util

import android.content.Context
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.File
import java.io.FileOutputStream

object ImagemUtil {
    suspend fun baixarEArmazenarImagem(context: Context, url: String, nomeArquivo: String): String? {
        return try {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()
            val result = (loader.execute(request) as? SuccessResult)?.drawable
            if (result != null) {
                val file = File(context.filesDir, nomeArquivo)
                val outputStream = FileOutputStream(file)
                val bitmap = (result as android.graphics.drawable.BitmapDrawable).bitmap
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
                file.absolutePath
            } else null
        } catch (_: Exception) {
            null
        }
    }
}

