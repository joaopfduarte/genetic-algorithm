package com.geneticalgorith.final_implementation.langermannFunction;

import java.util.Random;

import com.geneticalgorith.final_implementation.base.Ind;
import com.geneticalgorith.final_implementation.base.IndFactory;

public class LangermannFunctionIndFactory implements IndFactory {
    private final int n;
    private final Random rand = new Random();

    public LangermannFunctionIndFactory(int n) {
        if (n != 2) {
            throw new IllegalArgumentException("N deve ser igual à 02");
        }
        this.n = n;
    }

    @Override
    public Ind getIndividuoAleatorio() {
        double[] genes = new double[this.n];
        for (int i = 0; i < this.n; i++) {
            genes[i] = -10 + rand.nextDouble() * 20;
        }

        return new LangermannInd(n, genes);
    }

}