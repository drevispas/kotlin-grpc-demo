package com.example.kotlinggrpcdemo.presentation.grpc

import io.grpc.Server
import io.grpc.ServerBuilder

class HelloServer(private val port: Int) {
    val server: Server = ServerBuilder.forPort(port).addService(HelloGrpcService()).build()

    fun start() {
        server.start()
        Runtime.getRuntime().addShutdownHook(Thread {
            this@HelloServer.stop()
        })
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    private class HelloGrpcService : GreeterGrpcKt.GreeterCoroutineImplBase() {
        override suspend fun sayHello(request: HelloRequest) =
            HelloReply.newBuilder().setMessage("Hello ${request.name}").build()
    }
}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50050
    val server = HelloServer(port)
    server.start()
    server.blockUntilShutdown()
}
