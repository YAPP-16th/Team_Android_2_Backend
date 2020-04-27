package com.teamplay.core.function;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Function<T, R> extends java.util.function.Function<T, R>, Function1<T, R> {
    @NotNull
    @Contract(pure = true)
    static <T> Function<T, T> identity() {
        return (t) -> t;
    }

    // Please Use When T Is Unit
    @Contract(pure = true)
    default R invoke() {
        return apply();
    }

    @Override
    @Contract(pure = true)
    default R invoke(T p1) {
        return apply(p1);
    }

    // Please Use When T Is Unit
    @Contract(pure = true)
    default R apply() {
        return apply((T) Unit.INSTANCE);
    }

    @NotNull
    @Contract(pure = true)
    default <V> Function<V, R> compose(@NotNull Function<? super V, ? extends T> before) {
        return (v) -> this.apply(before.apply(v));
    }

    @NotNull
    @Contract(pure = true)
    default <V> Function<T, V> andThen(@NotNull Function<? super R, ? extends V> after) {
        return (t) -> after.apply(this.apply(t));
    }
}

