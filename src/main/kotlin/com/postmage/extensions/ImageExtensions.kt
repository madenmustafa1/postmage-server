package com.postmage.extensions

import java.util.*

fun ByteArray.toBase64(): String =
    String(Base64.getEncoder().encode(this))