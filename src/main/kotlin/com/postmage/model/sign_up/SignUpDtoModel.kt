package com.postmage.model.sign_up


import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class SignUpDtoModel(
    val id: String? = null,
    val nameSurname: String,
    val mail: String,
    val password: String,
    val phoneNumber: String?,
    val gender: Int?,
    val profilePhotoUrl: String?,
    val userId: String?,
    val userRole: Int?
)

data class SignUpModel(
    @BsonId
    val id: Id<SignUpModel>? = null,
    val nameSurname: String,
    val mail: String,
    val password: String,
    val phoneNumber: String?,
    val gender: Int?,
    val profilePhotoUrl: String?,
    val userId: String?,
    val userRole: Int?
)


fun SignUpDtoModel.toSignUpModel(): SignUpModel =
    SignUpModel(
        nameSurname = this.nameSurname,
        mail = this.mail,
        password = this.password,
        phoneNumber = this.phoneNumber,
        gender = this.gender,
        profilePhotoUrl = this.profilePhotoUrl,
        userId = this.userId,
        userRole = this.userRole,
    )