package com.blueur.hashcode.one_pizza.dto;

import io.vavr.collection.Set;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Client {
    private final int id;
    private int likedIngredientsCount;
    private Set<String> likedIngredients;
    private int dislikedIngredientsCount;
    private Set<String> dislikedIngredients;
}
