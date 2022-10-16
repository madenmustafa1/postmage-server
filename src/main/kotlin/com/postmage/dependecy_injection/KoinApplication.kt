package com.postmage.dependecy_injection


import com.postmage.util.AppMessages
import com.postmage.service.login.LoginInterface
import com.postmage.vm.*
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Single
class KoinApplication: KoinComponent {

    val repository by inject<LoginInterface>()

    val appMessages by inject<AppMessages>()

    //Inject by VM
    val loginVM by inject<LoginVM>()
    val profileVM by inject<ProfileVM>()
    val usersPostsVM by inject<UserPostsVM>()
    val imageVM by inject<ImageVM>()
    val groupVM by inject<GroupVM>()

}
