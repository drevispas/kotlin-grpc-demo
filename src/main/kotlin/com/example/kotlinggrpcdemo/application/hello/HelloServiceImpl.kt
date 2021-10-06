package com.example.kotlinggrpcdemo.application.hello

import java.time.LocalDateTime

class HelloServiceImpl : HelloService<UserCommand, UserDto> {
    override fun register(command: UserCommand): UserDto {
        TODO("Not yet implemented")
    }

    override fun list(): List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun unregister(command: UserCommand) {
        TODO("Not yet implemented")
    }

}

data class UserCommand(val name: String, val country:String)

data class UserDto(val id:String, val visitCount:Int, val lastVisitedAt: LocalDateTime)