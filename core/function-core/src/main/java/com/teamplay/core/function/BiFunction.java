package com.teamplay.core.function;

import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface BiFunction<T, U, R> extends java.util.function.BiFunction<T, U, R>, Function2<T, U, R> {
    @Override
    @Contract(pure = true)
    default R invoke(T p1, U p2) {
        return apply(p1, p2);
    }

    @NotNull
    @Contract(pure = true)
    default <V> BiFunction<T, U, V> andThen(@NotNull Function<? super R, ? extends V> after) {
        return (t, u) -> after.apply(this.apply(t, u));
    }
}
