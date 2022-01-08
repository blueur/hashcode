package com.blueur.hashcode.one_pizza.dto;

import io.vavr.collection.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Client {
    private final int id;
    private int likedIngredientsCount;
    private List<String> likedIngredients;
    private int dislikedIngredientsCount;
    private List<String> dislikedIngredients;
}
