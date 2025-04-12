package com.ecommerceapp.payment.adapter.transport.grpc.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.ecommerceapp.generated.payment.MakePaymentRequest;
import com.ecommerceapp.generated.payment.MakePaymentResponse;
import com.ecommerceapp.payment.core.domain.enums.PaymentProvider;
import com.ecommerceapp.payment.core.port.inbound.commands.MakePaymentCommand;
import com.ecommerceapp.payment.core.port.inbound.results.MakePaymentResult;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface PaymentGrpcMapper {

    @Mapping(source = "paymentProvider", target = "paymentProvider", qualifiedByName = "mapPaymentProviderProto")
    MakePaymentCommand toMakePaymentCommand(MakePaymentRequest request);

    MakePaymentResponse toMakePaymentResponse(MakePaymentResult result);

    @Named("mapPaymentProviderProto")
    default PaymentProvider mapPaymentProviderProto(
            com.ecommerceapp.generated.payment.PaymentProvider paymentProvider) {
        if (paymentProvider == null) {
            return null;
        }
        return switch (paymentProvider) {
            case PAYMENT_PROVIDER_UNSPECIFIED -> null;
            case VNPAY -> PaymentProvider.VNPAY;
            default -> null;
        };
    }
}
