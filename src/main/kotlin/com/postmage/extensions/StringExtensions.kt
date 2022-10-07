package com.postmage.extensions


import com.google.gson.Gson
import com.postmage.enums.AppUserRole
import com.postmage.util.PatternUtil
import com.postmage.util.TokenUtil
import com.postmage.model.token.TokenDataModel
import com.postmage.util.Directory
import java.nio.file.Paths


fun String.isValidEmail() = PatternUtil.EMAIL_ADDRESS.matcher(this).matches()
fun String.createToken(userID: String, userRole: AppUserRole) =
    TokenUtil.createToken(
        tToken = this,
        userID = userID,
        userRole = userRole
    )

fun String.verifyToken(vararg userRole: AppUserRole) = TokenUtil.verifyToken(
    token = this,
    userRole = userRole
)

fun String.authToDataClass(): TokenDataModel? {
    return try {
        val gson = Gson()
        gson.fromJson(TokenUtil.decodeToken(this), TokenDataModel::class.java)
    } catch (e: Exception) {
        null
    }
}

fun String.makeFolder(folderName: String?): Boolean {
    val homeFolder = System.getProperty("user.home")
    Directory.userDesktopDir = Paths.get(homeFolder, "Desktop", folderName).toFile()
    return Directory.userDesktopDir?.mkdir() ?: false
}




