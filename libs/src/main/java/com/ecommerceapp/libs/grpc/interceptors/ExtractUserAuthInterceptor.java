package com.ecommerceapp.libs.grpc.interceptors;

import org.springframework.stereotype.Component;

import com.ecommerceapp.libs.grpc.utils.GrpcContextKey;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

@Component
@GrpcGlobalServerInterceptor
public class ExtractUserAuthInterceptor implements ServerInterceptor {

        @Override
        public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                        ServerCallHandler<ReqT, RespT> next) {
                String userId = headers.get(Metadata.Key.of("user-id", Metadata.ASCII_STRING_MARSHALLER));
                String isShopOwnerActive = headers
                                .get(Metadata.Key.of("is-shop-owner-active", Metadata.ASCII_STRING_MARSHALLER));
                UserContext userContext = new UserContext(userId, Boolean.valueOf(isShopOwnerActive));
                Context context = Context.current()
                                .withValue(GrpcContextKey.USER_CONTEXT, userContext);
                return Contexts.interceptCall(context, call, headers, next);
        }

}
