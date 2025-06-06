package com.geneticalgorith.final_implementation.dixonpriceFunction;

import java.util.Random;

import com.geneticalgorith.final_implementation.base.Ind;
import com.geneticalgorith.final_implementation.base.IndFactory;

public class DixonPriceIndFactory implements IndFactory {
    private final int n;
    private final Random rand = new Random();

    public DixonPriceIndFactory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N deve ser maior que zero");
        }
        this.n = n;
    }

    @Override
    public Ind getIndividuoAleatorio() {
        double[] genes = new double[this.n];
        for (int i = 0; i < this.n; i++) {
            genes[i] = -2 + rand.nextDouble() * 4;
        }

        return new DixonPriceInd(n, genes);
    }

}