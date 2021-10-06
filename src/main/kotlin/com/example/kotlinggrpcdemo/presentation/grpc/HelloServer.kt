package com.example.kotlinggrpcdemo.presentation.grpc

import finance.chai.pg.pgintegration.v2.PreprocessResponse
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
//import org.lognet.springboot.grpc.GRpcService

@GrpcService
class HelloServer : GreeterGrpcKt.GreeterCoroutineImplBase() {
    override suspend fun sayHello(request: HelloRequest): HelloReply {
        println("Starting sayHello(): ${request.name}")
        return HelloReply.newBuilder().setMessage("Hello, ${request.name}").build()
    }
}

//@GrpcService
//class HelloServer : GreeterGrpc.GreeterImplBase() {
//    override fun sayHello(request: HelloRequest, responseObserver: StreamObserver<HelloReply>) {
//        val reply: HelloReply = HelloReply.newBuilder().setMessage("Hello, ${request.name}").build()
//        responseObserver.onNext(reply)
//        responseObserver.onCompleted()
//    }
//}

//@GRpcService
//class HelloServer : GreeterGrpc.GreeterImplBase() {
//    override fun sayHello(request: HelloRequest, responseObserver: StreamObserver<HelloReply>) {
//        println("Starting sayHello(): ${request.name}")
//        val reply = HelloReply.newBuilder().setMessage("Hello, ${request.name}").build()
//        responseObserver.onNext(reply)
//        responseObserver.onCompleted()
//    }
//}

//@GRpcService
//class HelloServer : GreeterGrpcKt.GreeterCoroutineImplBase() {
//    override suspend fun sayHello(request: HelloRequest): HelloReply {
//        return HelloReply.newBuilder().setMessage("Hello, ${request.name}").build()
//    }
//}
