package com.example.kotlinggrpcdemo.application.hello

interface HelloService<T,R> {
    fun register(command: T): R
    fun list(): List<R>
    fun unregister(command: T)
}