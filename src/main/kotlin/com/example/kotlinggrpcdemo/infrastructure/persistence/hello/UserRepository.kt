package com.example.kotlinggrpcdemo.infrastructure.persistence.hello

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByName(name: String): User?
}