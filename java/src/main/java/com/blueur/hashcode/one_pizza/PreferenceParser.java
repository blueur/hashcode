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
                        field(integer(Preference::setClientsCount))
                ),
                list(Preference::getClientsCount, Preference::setClients, preference -> idObject(Client::new,
                        line(
                                field(integer(Client::setLikedIngredientsCount)),
                                set(Client::getLikedIngredientsCount, Client::setLikedIngredients, client -> string)
                        ),
                        line(
                                field(integer(Client::setDislikedIngredientsCount)),
                                set(Client::getDislikedIngredientsCount, Client::setDislikedIngredients, client -> string)
                        )
                ))
        ).apply(fileIterator);
    }
}
