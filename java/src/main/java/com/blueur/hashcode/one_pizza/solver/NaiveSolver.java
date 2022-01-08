package com.blueur.hashcode.one_pizza.solver;

import com.blueur.hashcode.common.Solver;
import com.blueur.hashcode.one_pizza.dto.Client;
import com.blueur.hashcode.one_pizza.dto.Pizza;
import com.blueur.hashcode.one_pizza.dto.Preference;

public class NaiveSolver<T> extends Solver<Preference, Pizza> {
    @Override
    public Pizza solve(Preference input) {
        return Pizza.builder()
                .ingredients(input
                        .getClients()
                        .flatMap(Client::getLikedIngredients)
                        .toSet()
                        .toList()
                        .asJava())
                .build();
    }
}
