package com.teamplay.core.function;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@FunctionalInterface
public interface Predicate<T> extends Function<T, Boolean>, java.util.function.Predicate<T> {
    @NotNull
    @Contract(pure = true)
    static <T> Predicate<T> from(@NotNull Function<T, Boolean> function) {
        return function::apply;
    }

    @NotNull
    @Contract(pure = true)
    static <T> Predicate<T> isEqual(Object targetRef) {
        return null == targetRef ? Objects::isNull : targetRef::equals;
    }

    @NotNull
    @Contract(pure = true)
    static <T> Predicate<T> not(@NotNull Predicate<T> target) {
        return target.negate();
    }

    // Please Use When T Is Unit
    @Contract(pure = true)
    default boolean test() {
        return apply();
    }

    @Override
    @Contract(pure = true)
    default boolean test(T var1) {
        return apply(var1);
    }

    @Override
    @NotNull
    @Contract(pure = true)
    Boolean apply(T var1);

    @NotNull
    @Contract(pure = true)
    default Predicate<T> and(@NotNull Predicate<? super T> other) {
        return (t) -> this.test(t) && other.test(t);
    }

    @NotNull
    @Contract(pure = true)
    default Predicate<T> negate() {
        return (t) -> !this.test(t);
    }

    @NotNull
    @Contract(pure = true)
    default Predicate<T> or(@NotNull Predicate<? super T> other) {
        return (t) -> this.test(t) || other.test(t);
    }
}
