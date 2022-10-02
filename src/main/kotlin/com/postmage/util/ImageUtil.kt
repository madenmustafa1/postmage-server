package com.postmage.util

//import com.postmage.extensions.toBase64
//import org.eclipse.jetty.util.Loader

class ImageUtil {
    private val photos: String = "photos"

    suspend fun imgPathToBase64(pathName: String, imgName: String): String{
        //val pathBytes = Loader.getResource("drawable/$photos/$pathName/$imgName.png")?.readBytes()
        //return pathBytes?.toBase64() ?: ""
        return ""
    }
}