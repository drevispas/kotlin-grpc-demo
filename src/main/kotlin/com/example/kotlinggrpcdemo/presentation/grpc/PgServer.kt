package com.example.kotlinggrpcdemo.presentation.grpc

import finance.chai.pg.pgintegration.v2.PgintegrationServiceGrpcKt
import finance.chai.pg.pgintegration.v2.PreprocessRequest
import finance.chai.pg.pgintegration.v2.PreprocessResponse
import io.grpc.Server
import io.grpc.ServerBuilder

class PgServer(private val port: Int) {
    val server: Server = ServerBuilder.forPort(port).addService(PgGrpcService()).build()

    fun start() {
        server.start()
        Runtime.getRuntime().addShutdownHook(Thread {
            this@PgServer.stop()
        })
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    private class PgGrpcService : PgintegrationServiceGrpcKt.PgintegrationServiceCoroutineImplBase() {
        override suspend fun preprocess(request: PreprocessRequest): PreprocessResponse {
            return PreprocessResponse.newBuilder().setCommon(
                PreprocessResponse.CommonInfoResponse.newBuilder().setCode(1004).setMessage("OK!!!").build()
            ).build()
        }
    }
}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = PgServer(port)
    server.start()
    server.blockUntilShutdown()
}
