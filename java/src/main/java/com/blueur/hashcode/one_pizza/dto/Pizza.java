package com.blueur.hashcode.one_pizza.dto;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import lombok.Builder;
import lombok.Data;

import static java.util.function.Predicate.not;


@Data
@Builder
public class Pizza {
    private final Set<String> ingredients;

    public int getScore(List<Client> clients) {
        return clients
                .count(client -> ingredients.containsAll(client.getLikedIngredients()) && client.getDislikedIngredients().forAll(not(ingredients::contains)));
    }
}
