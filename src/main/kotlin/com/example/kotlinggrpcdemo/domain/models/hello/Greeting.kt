package com.example.kotlinggrpcdemo.domain.models.hello

import com.example.kotlinggrpcdemo.domain.models.BaseEntity
import java.util.*

class Greeting(val name: String, val country: String, var visitCount: Int) :
    BaseEntity<String>(UUID.randomUUID().toString())
