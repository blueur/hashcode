package com.blueur.hashcode.one_pizza.dto;

import io.vavr.collection.List;
import lombok.Data;

@Data
public class Preference {
    private int clientsCount;
    private List<Client> clients;
}
