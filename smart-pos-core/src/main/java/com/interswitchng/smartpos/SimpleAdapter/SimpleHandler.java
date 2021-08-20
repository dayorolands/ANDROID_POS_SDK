package com.interswitchng.smartpos.SimpleAdapter;

public interface SimpleHandler<T> {
    void accept(T response, Throwable throwable);
}