package com.postmage.service.login

import com.postmage.model.password.ChangePasswordModel
import com.postmage.model.sign_in.SignInRequestModel
import com.postmage.model.sign_in.SignInResponseModel
import com.postmage.model.sign_up.SignUpDtoModel
import com.postmage.model.sign_up.SignUpRequestModel
import com.postmage.model.sign_up.SignUpResponseModel
import com.postmage.service.ResponseData

interface LoginInterface {

    suspend fun singIn(signInRequestModel: SignInRequestModel): ResponseData<SignInResponseModel?>

    suspend fun singUp(signUpRequestModel: SignUpRequestModel): ResponseData<SignUpResponseModel?>
    //suspend fun singUp(signUpRequestModel: SignUpDtoModel): ResponseData<SignUpResponseModel?>

    suspend fun changePassword(changePasswordModel: ChangePasswordModel): Boolean

}