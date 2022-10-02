package com.postmage.extensions


import com.google.gson.Gson
import com.postmage.enums.AppUserRole
import com.postmage.util.PatternUtil
import com.postmage.util.TokenUtil
import com.postmage.model.token.TokenDataModel


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
    val gson = Gson()
    return gson.fromJson(TokenUtil.decodeToken(this), TokenDataModel::class.java)
}





