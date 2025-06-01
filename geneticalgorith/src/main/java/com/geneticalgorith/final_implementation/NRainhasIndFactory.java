package com.geneticalgorith.final_implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.geneticalgorith.final_implementation.base.Ind;
import com.geneticalgorith.final_implementation.base.IndFactory;

public class NRainhasIndFactory implements IndFactory {
    private final int n;
    private final Random rand = new Random();

    public NRainhasIndFactory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N deve ser maior que zero");
        }
        this.n = n;
    }

    @Override
    public Ind getIndividuoAleatorio() {
        List<Integer> posicoes = new ArrayList<>();
        for (int i = 0; i < this.n; i++) {
            posicoes.add(i);
        }
        Collections.shuffle(posicoes);

        int[] genes = new int[this.n];
        for (int i = 0; i < this.n; i++) {
            genes[i] = posicoes.get(i);
        }

        return new NRainhasInd(n, genes);
    }

}