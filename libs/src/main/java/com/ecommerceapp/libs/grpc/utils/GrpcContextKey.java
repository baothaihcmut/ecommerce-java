package com.ecommerceapp.libs.grpc.utils;

import com.ecommerceapp.libs.security.SecurityUtil.UserContext;

import io.grpc.Context;

public class GrpcContextKey {
    public static final Context.Key<UserContext> USER_CONTEXT = Context.key("user-id");
}
