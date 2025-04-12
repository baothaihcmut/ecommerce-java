package com.ecommerceapp.libs.grpc.interceptors;

import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

public class InjectUserAuthInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(
                next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                UserContext userContext = SecurityUtil.getUserContext();
                headers.put(Metadata.Key.of("user-id", Metadata.ASCII_STRING_MARSHALLER), userContext.userId());
                headers.put(Metadata.Key.of("is-shop-owner-active", Metadata.ASCII_STRING_MARSHALLER),
                        userContext.isShopOwnerActive().toString());
                super.start(responseListener, headers);
            }
        };
    }

}
