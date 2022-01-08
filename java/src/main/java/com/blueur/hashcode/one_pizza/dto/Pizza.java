package com.blueur.hashcode.one_pizza.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class Pizza {
    @Singular
    private final List<String> ingredients;
}
