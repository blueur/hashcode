package com.blueur.hashcode.one_pizza.solver;

import com.blueur.hashcode.common.Solver;
import com.blueur.hashcode.one_pizza.dto.Client;
import com.blueur.hashcode.one_pizza.dto.Ingredient;
import com.blueur.hashcode.one_pizza.dto.Pizza;
import com.blueur.hashcode.one_pizza.dto.Preference;
import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NaiveSolver<T> extends Solver<Preference, Pizza> {
    @Override
    public Pizza solve(Preference input) {
        final List<Client> clients = input.getClients();
        final List<Ingredient> ingredients = clients
                .flatMap(Client::getLikedIngredients)
                .distinct()
                .map(id -> Ingredient.builder()
                        .id(id)
                        .likedClientIds(clients
                                .filter(client -> client.getLikedIngredients().contains(id))
                                .map(Client::getId).toSet())
                        .dislikedClientIds(clients
                                .filter(client -> client.getDislikedIngredients().contains(id))
                                .map(Client::getId).toSet())
                        .build());
        final Pizza pizza = Pizza.builder()
                .ingredients(ingredients
                        .filter(ingredient -> ingredient.getLikedClientIds().size() > ingredient.getDislikedClientIds().size())
                        .map(Ingredient::getId)
                        .toSet())
                .build();
        log.info("score: {}", pizza.getScore(clients));
        return pizza;
    }
}
