package com.postmage.service.login

import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.gson.Gson
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoCollection
import com.postmage.enums.AppUserRole
import com.postmage.extensions.createToken
import com.postmage.model.password.ChangePasswordModel
import com.postmage.model.sign_in.SignInRequestModel
import com.postmage.model.sign_in.SignInResponseModel
import com.postmage.model.sign_up.SignUpRequestModel
import com.postmage.model.sign_up.SignUpResponseModel
import com.postmage.mongo_client.MongoInitialize
import com.postmage.mongo_client.db_router.DBRouter
import org.bson.Document
import org.bson.types.ObjectId
import com.postmage.service.ErrorMessage
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages

class LoginService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
) : LoginInterface {

    override suspend fun singIn(signInRequestModel: SignInRequestModel): ResponseData<SignInResponseModel?> {
        try {
            val collection: MongoCollection<Document> = mongoDB.getDB().getCollection(DBRouter.USERS)
            val query = BasicDBObject("mail", signInRequestModel.mail!!)

            var result: ResponseData<SignInResponseModel?>? = null

            collection.find(query).limit(1).forEach {
                val gson = Gson()
                val model = gson.fromJson(it.toJson(), SignUpRequestModel::class.java)

                //Verify
                val bcryptResult = BCrypt.verifyer().verify(signInRequestModel.password!!.toCharArray(), model.password)
                if (bcryptResult.verified) {
                    val token = signInRequestModel.mail.createToken(
                        model.userId.toString(),
                        userRole = AppUserRole.values()[model.userRole ?: AppUserRole.USER.ordinal]
                    )
                    result = ResponseData.success(
                        SignInResponseModel(
                            token = token,
                            isSuccess = true,
                            userId = model.userId.toString()
                        )
                    )
                }
            }
            result?.let { return it }

            return ResponseData.error(ErrorMessage(appMessages.EMAIL_OR_PASSWORD_INCORRECT, statusCode = 404), null)
        } catch (e: Exception) {
            println(e.message)
            return ResponseData.error(ErrorMessage(appMessages.SERVER_ERROR, statusCode = 500), null)
        }
    }


    override suspend fun singUp(signUpRequestModel: SignUpRequestModel): ResponseData<SignUpResponseModel?> {
        return try {
            val collection = mongoDB.signUpUserCollection

            val query = BasicDBObject("mail", signUpRequestModel.mail)
            repeat(collection.find(query).limit(1).count()) {
                return ResponseData.error(ErrorMessage(appMessages.EMAIL_NOT_UNIQUE), null)
            }

            val password = signUpRequestModel.password
            val bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray())
            val userId = ObjectId.get()
            signUpRequestModel.userId = userId.toString()
            signUpRequestModel.password = bcryptHashString

            collection.insertOne(signUpRequestModel)

            ResponseData.success(
                SignUpResponseModel(
                    token = signUpRequestModel.mail.createToken(userId.toString(), userRole = AppUserRole.USER),
                    isSuccess = true,
                    userId = userId.toString()
                )
            )
        } catch (e: Exception) {
            ResponseData.error(ErrorMessage(appMessages.SERVER_ERROR), null)
        }
    }

    override suspend fun changePassword(changePasswordModel: ChangePasswordModel): Boolean {
        println("LoginService " + (false))
        return false
    }
}


/*
val document = Document("docName", signUpRequestModel.mail)
    .append("nameSurname", signUpRequestModel.nameSurname)
    .append("mail", signUpRequestModel.mail)
    .append("password", bcryptHashString)
    .append("phoneNumber", signUpRequestModel.phoneNumber)
    .append("gender", signUpRequestModel.gender)
    .append("profilePhotoUrl", signUpRequestModel.profilePhotoUrl)
    .append("userId", userId.toString())
    .append("userRole", AppUserRole.USER.ordinal)
    .append("followersSize", 0)
    .append("followingSize", 0)
    .append("following", arrayListOf<SingleFollowerDataModel>())
    .append("followers", arrayListOf<SingleFollowerDataModel>())
    .append("group", arrayListOf<String>())
 */