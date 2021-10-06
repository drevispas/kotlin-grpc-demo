package com.example.kotlinggrpcdemo

import com.example.kotlinggrpcdemo.presentation.grpc.HelloServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class KotlingGrpcDemoApplication

fun main(args: Array<String>) {
    runApplication<KotlingGrpcDemoApplication>(*args)
}
