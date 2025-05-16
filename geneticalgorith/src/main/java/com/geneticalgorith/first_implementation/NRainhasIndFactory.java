package com.geneticalgorith.first_implementation;

import java.util.*;

public class NRainhasIndFactory {
    private final int n;
    private final Random rand = new Random();

    public NRainhasIndFactory(int n) {
        this.n = n;
    }

    public NRainhasInd create() {
        int[] genes = new int[n];

        for (int i = 0; i < n; i++) {
            genes[i] = i;
        }

        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = genes[i];
            genes[i] = genes[j];
            genes[j] = temp;
        }

        return new NRainhasInd(genes);
    }
}
