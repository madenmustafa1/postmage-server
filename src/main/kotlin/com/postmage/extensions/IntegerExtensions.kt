package com.postmage.extensions

fun Int.genderControl(): Boolean {
    if (this != 0 && this != 1) return false
    return true
}