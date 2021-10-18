package com.example.kotlinggrpcdemo.infrastructure.persistence.hello

import com.example.kotlinggrpcdemo.domain.models.BaseEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "User")
data class User(
    @Id val number: String = "",
    @Column(unique=true) val name: String = "",
    val country: String = "",
    var visitCount: Int = 0,
    val lastVisitedAt: LocalDateTime = LocalDateTime.now()
)