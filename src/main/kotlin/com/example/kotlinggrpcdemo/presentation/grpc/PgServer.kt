package com.example.kotlinggrpcdemo.presentation.grpc

import finance.chai.pg.pgintegration.v2.*
import io.grpc.Server
import io.grpc.ServerBuilder
import org.lognet.springboot.grpc.GRpcService

//class PgServer(private val port: Int) {
//    val server: Server = ServerBuilder.forPort(port).addService(PgGrpcService()).build()
//
//    fun start() {
//        server.start()
//        Runtime.getRuntime().addShutdownHook(Thread {
//            this@PgServer.stop()
//        })
//    }
//
//    private fun stop() {
//        server.shutdown()
//    }
//
//    fun blockUntilShutdown() {
//        server.awaitTermination()
//    }
//
//    private class PgGrpcService : PgintegrationServiceGrpcKt.PgintegrationServiceCoroutineImplBase() {
//        override suspend fun preprocess(request: PreprocessRequest): PreprocessResponse {
//            return PreprocessResponse.newBuilder().setCommon(
//                PreprocessResponse.CommonInfoResponse.newBuilder().setCode(1004).setMessage("OK!!!").build()
//            ).build()
//        }
//    }
//}
//
//fun main() {
//    val port = System.getenv("PORT")?.toInt() ?: 50051
//    val server = PgServer(port)
//    server.start()
//    server.blockUntilShutdown()
//}

@GRpcService
class PgServer : PgintegrationServiceGrpcKt.PgintegrationServiceCoroutineImplBase() {
    override suspend fun preprocess(request: PreprocessRequest): PreprocessResponse {
        return PreprocessResponse.newBuilder().setCommon(
            PreprocessResponse.CommonInfoResponse.newBuilder()
                .setCode(200).setMessage("OK").setData(
                    PreprocessResponse.CommonInfoResponse.DetailData.newBuilder()
                        .setPgTxId("pg_tx_1").setPaidAt(13800000)
                )
        ).build()
    }

    override suspend fun confirm(request: ConfirmRequest): ConfirmResponse {
        println(request)
        // Call application service

        return ConfirmResponse.getDefaultInstance()
    }
}
