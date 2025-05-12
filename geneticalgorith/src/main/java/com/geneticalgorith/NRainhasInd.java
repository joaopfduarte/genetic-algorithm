package com.geneticalgorith;

import com.geneticalgorith.interfaces.Ind;

import java.util.Random;
import java.util.random.*;
import java.util.List;
import java.util.ArrayList;

public class NRainhasInd implements Ind {
    private int[] genes;
    private int n;
    Random rand = new Random();

    public NRainhasInd(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Tamanho inválido de N");
        }
        this.n = n;
        this.genes = new int[n];

        for (int i = 0; i < n; i++) {
            genes[i] = rand.nextInt(n - 1);
        }

    }

    public List recombinar(Ind ind) {

    }

    public Ind mutar() {
        List<Integer> genesList = new ArrayList<>();
        for (int gene : this.genes) {
            genesList.add(gene);
        }

        int posicaoMutacao = rand.nextInt(this.n);
        genesList.set(posicaoMutacao, rand.nextInt(this.n));

        NRainhasInd mutante = new NRainhasInd(this.n);
        for (int i = 0; i < this.n; i++) {
            mutante.genes[i] = genesList.get(i);
        }

        return mutante;
    }
}
