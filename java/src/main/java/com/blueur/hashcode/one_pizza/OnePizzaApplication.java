package com.blueur.hashcode.one_pizza;

import com.blueur.hashcode.common.Executor;
import com.blueur.hashcode.one_pizza.dto.Pizza;
import com.blueur.hashcode.one_pizza.dto.Preference;
import com.blueur.hashcode.one_pizza.solver.NaiveSolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OnePizzaApplication {
    public static void main(String[] args) throws Throwable {
        final Executor<Preference, Pizza> executor = Executor.<Preference, Pizza>builder()
                .application(OnePizzaApplication.class)
                .problemFolder("../problem/2022-one_pizza")
                .parser(PreferenceParser::new)
                .solver(NaiveSolver::new)
                .writer(PizzaWriter::new)
                .build();
//        executor.execute("a.txt");
        executor.executeAll();
    }
}
