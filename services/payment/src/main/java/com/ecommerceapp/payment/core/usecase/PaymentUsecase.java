package com.ecommerceapp.payment.core.usecase;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.grpc.utils.GrpcContextKey;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.payment.core.domain.entities.PaymentHistory;
import com.ecommerceapp.payment.core.domain.enums.PaymentStatus;
import com.ecommerceapp.payment.core.domain.events.PaymentResultEvent;
import com.ecommerceapp.payment.core.exception.ErrorCode;
import com.ecommerceapp.payment.core.models.PaymentInfo;
import com.ecommerceapp.payment.core.port.inbound.commands.ConfirmPaymentCommand;
import com.ecommerceapp.payment.core.port.inbound.commands.MakePaymentCommand;
import com.ecommerceapp.payment.core.port.inbound.handlers.PaymentHandler;
import com.ecommerceapp.payment.core.port.inbound.results.MakePaymentResult;
import com.ecommerceapp.payment.core.port.outbound.publishers.PaymentEventPublisher;
import com.ecommerceapp.payment.core.port.outbound.repositories.PaymentHistoryRepository;
import com.ecommerceapp.payment.core.port.outbound.services.PaymentProviderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentUsecase implements PaymentHandler {
    private final List<PaymentProviderService> paymentProviderServices;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    @Transactional
    public MakePaymentResult makePayment(MakePaymentCommand makePaymentCommand) {
        // create new payment
        UserContext userContext = GrpcContextKey.USER_CONTEXT.get();

        PaymentHistory paymentHistory = new PaymentHistory(
                userContext.userId(),
                makePaymentCommand.orderId(),
                makePaymentCommand.amount(),
                makePaymentCommand.paymentProvider());
        // save to db
        paymentHistoryRepository.save(paymentHistory);
        PaymentInfo paymentInfo = new PaymentInfo(
                makePaymentCommand.amount(),
                makePaymentCommand.ipAddr(),
                "Nap tien cho thue bao 0123456789. So tien 100,000 VND",
                "100000",
                paymentHistory.getId().toString());
        String paymentUrl = paymentProviderServices
                .stream()
                .filter(svc -> svc.getPaymentProvider().equals(makePaymentCommand.paymentProvider()))
                .findFirst().orElseThrow(() -> new AppException(ErrorCode.PAYMENT_PROVIDER_NOT_SUPPORT))
                .generatePaymentUrl(paymentInfo);

        return new MakePaymentResult(makePaymentCommand.paymentProvider(), paymentUrl);
    }

    @Override
    @Transactional
    public void handlePaymentResult(ConfirmPaymentCommand paymentResult) {
        UUID paymentId = UUID.fromString(paymentResult.paymentId());
        PaymentHistory paymentHistory = paymentHistoryRepository.findPaymentHistoryById(paymentId).orElse(null);
        if (paymentHistory == null) {
            System.out.println("payment not found");
            return;
        }
        if (paymentResult.status().equals(PaymentStatus.SUCCESS)) {
            paymentHistory.paymentSuccess();
        } else {
            paymentHistory.paymentFailure();
        }
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executorService.submit(() -> {
                paymentHistoryRepository.save(paymentHistory);
            });
            executorService.submit(() -> {
                paymentEventPublisher.publishPaymentResultEvent(new PaymentResultEvent(paymentHistory));
            });
            executorService.wait();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

    }

}
