package com.geneticalgorith.final_implementation.sumFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.geneticalgorith.final_implementation.base.Ind;
import com.geneticalgorith.final_implementation.base.IndFactory;

public class SumOfDifferentPowersIndFactory implements IndFactory {
    private final int n;
    private final Random rand = new Random();

    public SumOfDifferentPowersIndFactory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N deve ser maior que zero");
        }
        this.n = n;
    }

    @Override
    public Ind getIndividuoAleatorio() {
        double[] genes = new double[this.n];
        for (int i = 0; i < this.n; i++) {
            genes[i] = -1 + rand.nextDouble() * 2;
        }

        return new SumOfDifferentPowersInd(n, genes);
    }

}