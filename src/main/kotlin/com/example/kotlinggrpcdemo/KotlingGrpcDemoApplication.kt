package com.example.kotlinggrpcdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlingGrpcDemoApplication

fun main(args: Array<String>) {
    runApplication<KotlingGrpcDemoApplication>(*args)
}
