package com.blueur.hashcode.one_pizza;

import com.blueur.hashcode.common.Parser;
import com.blueur.hashcode.one_pizza.dto.Client;
import com.blueur.hashcode.one_pizza.dto.Preference;

import java.nio.file.Path;
import java.util.Iterator;

public class PreferenceParser extends Parser<Preference> {

    public PreferenceParser(Path path) {
        super(path);
    }

    @Override
    public Preference parseIterator(Iterator<String> fileIterator) {
        return object(Preference::new,
                line(
                        integer(Preference::setClientsCount)
                ),
                idList(Preference::getClientsCount, Preference::setClients, preference -> idObject(Client::new,
                        line(
                                integer(Client::setLikedIngredientsCount),
                                set(Client::getLikedIngredientsCount, Client::setLikedIngredients, client -> string)
                        ),
                        line(
                                integer(Client::setDislikedIngredientsCount),
                                set(Client::getDislikedIngredientsCount, Client::setDislikedIngredients, client -> string)
                        )
                ))
        ).apply(fileIterator);
    }
}
