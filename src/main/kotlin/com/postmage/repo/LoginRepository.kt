package com.postmage.repo

import com.postmage.extensions.genderControl
import com.postmage.extensions.isValidEmail
import com.postmage.model.password.ChangePasswordModel
import com.postmage.model.sign_in.SignInRequestModel
import com.postmage.model.sign_in.SignInResponseModel
import com.postmage.model.sign_up.SignUpRequestModel
import com.postmage.model.sign_up.SignUpResponseModel
import com.postmage.service.ResponseData
import com.postmage.service.login.LoginInterface
import com.postmage.util.AppMessages
import repo.sendErrorData
import service.login.LoginService

class LoginRepository(
    private val longinService: LoginService,
    private val appMessages: AppMessages
) : LoginInterface {

    override suspend fun singIn(signInRequestModel: SignInRequestModel): ResponseData<SignInResponseModel?> {
        if (signInRequestModel.mail == null || signInRequestModel.password == null) return sendErrorData(appMessages.NOT_VALID_EMAIL)
        if (!signInRequestModel.mail.isValidEmail()) return sendErrorData(appMessages.NOT_VALID_EMAIL)
        if (signInRequestModel.password.length < 4) return sendErrorData(appMessages.PASSWORD_NOT_BE_SHORT)
        return longinService.singIn(signInRequestModel)
    }

    override suspend fun singUp(signUpRequestModel: SignUpRequestModel): ResponseData<SignUpResponseModel?> {
        if (!signUpRequestModel.mail.isValidEmail()) return sendErrorData(appMessages.NOT_VALID_EMAIL)
        if (signUpRequestModel.password.length < 4) return sendErrorData(appMessages.PASSWORD_NOT_BE_SHORT)
        if (signUpRequestModel.nameSurname.trim() == "") return sendErrorData(appMessages.NAME_SURNAME_NOT_BE_EMPTY)
        if (signUpRequestModel.gender != null && !signUpRequestModel.gender.genderControl()) return sendErrorData(
            appMessages.GENDER_INVALIDATE
        )
        return longinService.singUp(signUpRequestModel)
    }

    override suspend fun changePassword(changePasswordModel: ChangePasswordModel): Boolean {
        return longinService.changePassword(changePasswordModel)
    }
}