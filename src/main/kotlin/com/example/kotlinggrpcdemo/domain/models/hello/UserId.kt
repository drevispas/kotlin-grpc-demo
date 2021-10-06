package com.example.kotlinggrpcdemo.domain.models.hello

import java.util.*

data class UserId(val id: String = UUID.randomUUID().toString())