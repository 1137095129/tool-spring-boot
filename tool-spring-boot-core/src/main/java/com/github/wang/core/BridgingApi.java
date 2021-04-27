package com.github.wang.core;

public interface BridgingApi<T, R> {
    R execute(T t);

    default <V> BridgingApi<V, R> brig(BridgingApi<? super V, ? extends T> bridgingApi) {
        return r -> execute(bridgingApi.execute(r));
    }

    default <V> BridgingApi<T, V> brig2(BridgingApi<? super R, ? extends V> bridgingApi) {
        return t -> bridgingApi.execute(execute(t));
    }
}
