package com.ecommerceapp.orders.adapter.transport.grpc.clients;

import org.springframework.stereotype.Service;

import com.ecommerceapp.generated.payment.MakePaymentRequest;
import com.ecommerceapp.generated.payment.MakePaymentResponse;
import com.ecommerceapp.generated.payment.PaymentProvider;
import com.ecommerceapp.generated.payment.PaymentServiceGrpc.PaymentServiceBlockingStub;
import com.ecommerceapp.libs.grpc.interceptors.InjectUserAuthInterceptor;
import com.ecommerceapp.orders.core.port.outbound.clients.PaymentClient;

import jakarta.annotation.PostConstruct;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class PaymentGrpcClient implements PaymentClient {

    @GrpcClient("payment-service")
    private PaymentServiceBlockingStub paymentServiceBlockingStub;

    @PostConstruct
    public void applyInterceptor() {
        paymentServiceBlockingStub = paymentServiceBlockingStub.withInterceptors(new InjectUserAuthInterceptor());
    }

    @Override
    public String makePayment(MakePaymentArg arg) {
        MakePaymentResponse response = paymentServiceBlockingStub
                .makePayment(
                        MakePaymentRequest.newBuilder()
                                .setOrderId(arg.orderId())
                                .setAmount(arg.amount())
                                .setIpAddr(arg.ipAddr())
                                .setPaymentProvider(
                                        switch (arg.paymentProvider()) {
                                            case VNPAY -> PaymentProvider.VNPAY;
                                        })
                                .build());
        return response.getPaymentUrl();
    }

}
