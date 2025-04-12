package com.ecommerceapp.payment.adapter.transport.grpc.servers;

import com.ecommerceapp.generated.payment.MakePaymentRequest;
import com.ecommerceapp.generated.payment.MakePaymentResponse;
import com.ecommerceapp.generated.payment.PaymentServiceGrpc.PaymentServiceImplBase;
import com.ecommerceapp.payment.adapter.transport.grpc.mappers.PaymentGrpcMapper;
import com.ecommerceapp.payment.core.port.inbound.handlers.PaymentHandler;
import com.ecommerceapp.payment.core.port.inbound.results.MakePaymentResult;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class PaymentGrpcServer extends PaymentServiceImplBase {
    private final PaymentHandler paymentHandler;
    private final PaymentGrpcMapper paymentGrpcMapper;

    @Override
    public void makePayment(MakePaymentRequest request, StreamObserver<MakePaymentResponse> rObserver) {
        MakePaymentResult result = paymentHandler.makePayment(
                paymentGrpcMapper.toMakePaymentCommand(request));
        rObserver.onNext(
                paymentGrpcMapper.toMakePaymentResponse(result));
        rObserver.onCompleted();
    }

}
