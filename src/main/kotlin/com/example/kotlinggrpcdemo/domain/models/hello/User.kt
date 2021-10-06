package com.example.kotlinggrpcdemo.domain.models.hello

import com.example.kotlinggrpcdemo.domain.models.BaseEntity
import java.util.*

class User(val name: String, val country: String) : BaseEntity<String>(UUID.randomUUID().toString())