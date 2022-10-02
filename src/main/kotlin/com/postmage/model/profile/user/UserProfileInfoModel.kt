package model.profile.user

data class UserProfileInfoModel(
    val nameSurname: String?,
    val mail: String?,
    val phoneNumber: String?,
    val gender: Int?,
    val profilePhotoUrl: String?,
    val userId: String?,
    val group: ArrayList<String?>?,
    val followersSize: Int? = null,
    val followingSize: Int? = null
)
