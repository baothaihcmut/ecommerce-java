package com.ecommerceapp.libs.saga;

public interface SagaAction<T> {

    T execute();

    void abort();

}
