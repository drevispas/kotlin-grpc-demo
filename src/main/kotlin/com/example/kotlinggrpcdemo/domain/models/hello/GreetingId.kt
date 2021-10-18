package com.example.kotlinggrpcdemo.domain.models.hello

import java.util.*

data class GreetingId(val number: String = UUID.randomUUID().toString())