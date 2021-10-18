package com.example.kotlinggrpcdemo.presentation.grpc

import com.example.kotlinggrpcdemo.application.hello.HelloService
import com.example.kotlinggrpcdemo.application.hello.UserCommand
import com.example.kotlinggrpcdemo.application.hello.UserDto
import finance.chai.pg.pgintegration.v2.PreprocessResponse
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
//import net.devh.boot.grpc.server.service.GrpcService
import org.lognet.springboot.grpc.GRpcService

//@GrpcService
//class HelloServer : GreeterGrpcKt.GreeterCoroutineImplBase() {
//    override suspend fun sayHello(request: HelloRequest): HelloReply {
//        println("Starting sayHello(): ${request.name}")
//        return HelloReply.newBuilder().setMessage("Hello, ${request.name}").build()
//    }
//}

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
//    override fun sayHello(request: HelloRequest?, responseObserver: StreamObserver<HelloReply>?) {
//        println("Starting sayHello(): ${request?.name}, ${request?.country}")
//        val reply = HelloReply.newBuilder().setId("ID1").setMessage("Hello, ${request?.name}").build()
//        responseObserver?.onNext(reply)
//        responseObserver?.onCompleted()
//    }
//}

@GRpcService
class HelloServer(private val service: HelloService<UserCommand,UserDto>) : GreeterGrpcKt.GreeterCoroutineImplBase() {
    override suspend fun sayHello(request: HelloRequest): HelloReply {
        println("\n*** Starting sayHello(): ${request.name}, ${request.country} ***")
        val userDto = service.register(UserCommand(request.name, request.country))
        return HelloReply.newBuilder()
            .setId(userDto.number)
            .setMessage("Hello, ${request.name} from ${request.country}! You've visited ${userDto.visitCount} times.")
            .build()
    }
}
