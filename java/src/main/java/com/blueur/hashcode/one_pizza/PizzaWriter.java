package com.blueur.hashcode.one_pizza;

import com.blueur.hashcode.common.Writer;
import com.blueur.hashcode.one_pizza.dto.Pizza;

import java.io.PrintWriter;
import java.nio.file.Path;

public class PizzaWriter extends Writer<Pizza> {
    public PizzaWriter(Path path) {
        super(path);
    }

    @Override
    protected void write(PrintWriter writer, Pizza output) {
        writer.print(output.getIngredients().size() + " ");
        output.getIngredients().forEach(ingredient -> writer.print(ingredient + " "));
    }
}
