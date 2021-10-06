package com.example.kotlinggrpcdemo.infrastructure.persistence.hello

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "User")
data class UserTable(
    @Id @GeneratedValue val userId: Long = 0L,
    val id: String = "",
    val name: String = "",
    val country: String = "",
    val visitCount: Int = 0,
    val lastVisitedAt: LocalDateTime = LocalDateTime.now()
)