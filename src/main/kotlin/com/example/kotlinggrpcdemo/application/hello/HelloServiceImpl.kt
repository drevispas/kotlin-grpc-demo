package com.example.kotlinggrpcdemo.application.hello

import com.example.kotlinggrpcdemo.domain.models.hello.Greeting
import com.example.kotlinggrpcdemo.infrastructure.persistence.hello.UserRepository
import com.example.kotlinggrpcdemo.infrastructure.persistence.hello.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class HelloServiceImpl(private val repository: UserRepository) : HelloService<UserCommand, UserDto> {
    override fun register(command: UserCommand): UserDto {
        val user = repository.findByName(command.name)
            ?: Greeting(command.name, command.country, 0).let {
                User(it.number, it.name,it.country, it.visitCount, LocalDateTime.now())
            }
        user.apply { visitCount += 1 }
        println(user)
        val savedRecord = repository.save(user)
        println("savedRecord:")
        println(savedRecord)
        return UserDto(user.number, user.visitCount, user.lastVisitedAt)
    }

    override fun list(): List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun unregister(command: UserCommand) {
        TODO("Not yet implemented")
    }
}

data class UserCommand(val name: String, val country: String)

data class UserDto(val number: String, val visitCount: Int, val lastVisitedAt: LocalDateTime)