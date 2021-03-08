package com.blueur.hashcode.dummy;

import com.blueur.hashcode.common.Solver;

public class IdentitySolver<T> extends Solver<T, T> {
    @Override
    public T solve(T input) {
        return input;
    }
}
