package com.ecommerceapp.libs.grpc.interceptors;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ecommerceapp.libs.exception.AppException;

import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

@Component
@GRpcGlobalInterceptor
public class ErrorHandlerInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(next.startCall(call, headers)) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (RuntimeException e) {
                    handleException(call, e);
                    throw e;
                }
            }
        };

    }

    private <RespT> void handleException(ServerCall<?, RespT> call, RuntimeException e) {
        Status status = Status.UNKNOWN;
        if (e instanceof AppException) {
            status = mapHttpStatusToGrpcStatus(((AppException) e).getStatus());
        }
        call.close(status, new Metadata());
    }

    private Status mapHttpStatusToGrpcStatus(HttpStatus httpStatus) {
        return switch (httpStatus) {
            case BAD_REQUEST -> Status.INVALID_ARGUMENT;
            case UNAUTHORIZED -> Status.UNAUTHENTICATED;
            case FORBIDDEN -> Status.PERMISSION_DENIED;
            case NOT_FOUND -> Status.NOT_FOUND;
            case CONFLICT -> Status.ALREADY_EXISTS;
            case TOO_MANY_REQUESTS -> Status.RESOURCE_EXHAUSTED;
            case INTERNAL_SERVER_ERROR -> Status.INTERNAL;
            case SERVICE_UNAVAILABLE -> Status.UNAVAILABLE;
            default -> Status.UNKNOWN;
        };
    }

}
