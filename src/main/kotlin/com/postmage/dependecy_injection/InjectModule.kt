package com.postmage.dependecy_injection

import com.postmage.mongo_client.MongoInitialize
import com.postmage.util.AppMessages
import com.postmage.service.login.LoginInterface
import org.koin.core.module.Module
import org.koin.dsl.module
import com.postmage.repo.LoginRepository
import com.postmage.repo.ProfileRepository
import com.postmage.service.login.LoginService
import com.postmage.service.profile.ProfileInterface
import com.postmage.service.profile.ProfileService
import com.postmage.vm.LoginVM
import com.postmage.vm.ProfileVM
import org.koin.core.scope.get

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

        //By VM
        single<LoginVM> { LoginVM(LoginRepository(longinService = get(), appMessages = get())) }
        single<ProfileVM> { ProfileVM(ProfileRepository(profileService = get(), appMessages = get()), get()) }
    }
}