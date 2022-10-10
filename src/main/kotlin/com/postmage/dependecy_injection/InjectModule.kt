package com.postmage.dependecy_injection

import com.postmage.mongo_client.MongoInitialize
import com.postmage.repo.ImageRepository
import com.postmage.util.AppMessages
import com.postmage.service.login.LoginInterface
import org.koin.core.module.Module
import org.koin.dsl.module
import com.postmage.repo.LoginRepository
import com.postmage.repo.ProfileRepository
import com.postmage.repo.UserPostRepository
import com.postmage.service.image.ImageInterface
import com.postmage.service.image.ImageService
import com.postmage.service.login.LoginService
import com.postmage.service.profile.ProfileInterface
import com.postmage.service.profile.ProfileService
import com.postmage.service.user_posts.UserPostsInterface
import com.postmage.service.user_posts.UserPostsService
import com.postmage.vm.ImageVM
import com.postmage.vm.LoginVM
import com.postmage.vm.ProfileVM
import com.postmage.vm.UserPostsVM

fun injectModule(): Module {
    return module {

        single { AppMessages() }
        single { MongoInitialize() }

        //Login
        single { LoginService(mongoDB = get(), appMessages = get()) }
        single { LoginRepository(longinService = get(), appMessages = get()) as LoginInterface }

        //Profile
        single { ProfileService(mongoDB = get(), appMessages = get()) }
        single { ProfileRepository(profileService = get(), appMessages = get()) as ProfileInterface }

        //AddPosts
        single { UserPostsService(mongoDB = get(), appMessages = get()) }
        single { UserPostRepository(userPostsService = get(), appMessages = get()) as UserPostsInterface }

        //Image
        single { ImageService(mongoDB = get(), appMessages = get()) }
        single { ImageRepository(imageService = get(), appMessages = get()) as ImageInterface }

        //By VM
        single<LoginVM> { LoginVM(LoginRepository(longinService = get(), appMessages = get())) }
        single<ProfileVM> { ProfileVM(ProfileRepository(profileService = get(), appMessages = get()), get()) }
        single<UserPostsVM> { UserPostsVM(UserPostRepository(userPostsService = get(), appMessages = get()), get()) }
        single<ImageVM> { ImageVM(ImageRepository(imageService = get(), appMessages = get()), get()) }
    }
}