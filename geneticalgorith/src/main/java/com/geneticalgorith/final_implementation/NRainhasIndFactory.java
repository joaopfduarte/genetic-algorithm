package com.geneticalgorith.final_implementation;

import java.util.Random;

public class NRainhasIndFactory implements IndFactory{
    private final int n;
    private final Random rand = new Random();

    public NRainhasIndFactory(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException("N deve ser maior que zero");
        }
        this.n = n;
    }

    public Ind getInstance() {
        int[] genes = new int[n];
        for(int i = 0; i < n; i++) {
            genes[i] = rand.nextInt(n); 
        }
        return new NRainhasInd(n, genes);
    }
}