package com.teamplay.core.function;

import com.teamplay.core.function.error.ValidateError;
import kotlin.Unit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Validator<T> extends Predicate<T> {
    @NotNull
    @Contract(pure = true)
    static <T> Validator<T> from(@NotNull Function<T, Boolean> function) {
        return function::apply;
    }

    // Please Use When T Is Unit
    @Contract(pure = true)
    default void verify() {
        verify(new ValidateError());
    }

    // Please Use When T Is Unit
    @Contract(pure = true)
    default void verify(Error error) {
        if (!test()) throw error;
    }

    @Contract(pure = true)
    default void verify(T var1) {
        verify(var1, new ValidateError());
    }

    @Contract(pure = true)
    default void verify(T var1, Error error) {
        if (!test(var1)) throw error;
    }

    @NotNull
    @Contract(pure = true)
    default <U> Function<T, U> andThen(Function<Unit, U> afterSuccess, Function<Throwable, U> afterError) {
        return (t) -> {
            try {
                verify(t);
                if (afterSuccess == null) return null;
                return afterSuccess.apply();
            } catch (Throwable error) {
                if (afterError == null) return null;
                return afterError.apply(error);
            }
        };
    }

    @NotNull
    @Contract(pure = true)
    default <U, R> BiFunction<T, U, R> onSuccess(@NotNull Function<U, R> function) {
        return (t, u) -> {
            verify(t);
            return function.apply(u);
        };
    }

    @NotNull
    @Contract(pure = true)
    default <U, R> BiFunction<T, U, R> onError(@NotNull BiFunction<Throwable, U, R> function) {
        return (t, u) -> {
            try {
                verify(t);
                return null;
            } catch (Throwable error) {
                return function.apply(error, u);
            }
        };
    }

    @NotNull
    @Contract(pure = true)
    default <U> Function<T, U> doOnSuccess(@NotNull Function<Unit, U> function) {
        return (t) -> {
            verify(t);
            return function.apply();
        };
    }

    @NotNull
    @Contract(pure = true)
    default <U> Function<T, U> doOnError(@NotNull Function<Throwable, U> function) {
        return (t) -> {
            try {
                verify(t);
                return null;
            } catch (Throwable error) {
                return function.apply(error);
            }
        };
    }
}
