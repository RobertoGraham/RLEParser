package io.github.robertograham.rleparser;

import java.util.function.Predicate;

class Predicates {

    static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
}
