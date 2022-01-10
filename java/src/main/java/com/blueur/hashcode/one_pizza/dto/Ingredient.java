package com.blueur.hashcode.one_pizza.dto;

import io.vavr.collection.Set;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Ingredient {
    final String id;
    final Set<Integer> likedClientIds;
    final Set<Integer> dislikedClientIds;
}
