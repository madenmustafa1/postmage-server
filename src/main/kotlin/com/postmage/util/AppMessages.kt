package com.postmage.util

class AppMessages {
    //GENERAL ERROR
    val SERVER_ERROR by lazy { "Something went wrong." }
    val MODEL_IS_NOT_VALID by lazy { "Model is not valid." }
    val ACCESS_DENIED by lazy { "Access denied" }
    val UNAUTHORIZED by lazy { "Unauthorized." }

    val NOT_VALID_EMAIL by lazy { "E-mail not valid." }
    val PASSWORD_NOT_BE_SHORT by lazy { "Password cannot be shorter than 5 characters" }
    val NAME_SURNAME_NOT_BE_EMPTY by lazy { "Name Surname cannot be empty" }
    val GENDER_INVALIDATE by lazy { "Gender invalidate. INT: 0 MALE, 1 FEMALE" }
    val EMAIL_NOT_UNIQUE by lazy { "E-mail already exits" }
    val EMAIL_OR_PASSWORD_INCORRECT by lazy { "E-mail or password incorrect." }


    val USER_ID_NOT_BE_NULL by lazy { "userId cannot be null." }

    //Photo
    val PHOTO_CANNOT_BE_EMPTY by lazy { "Photo cannot be empty" }
    val PHOTO_NAME_CANNOT_BE_EMPTY by lazy { "Photo name cannot be empty" }
    val PHOTO_NAME_NOT_UNIQUE by lazy { "Photo name already exits" }
    val PHOTO_NOT_FOUND by lazy { "Photo not found" }

}