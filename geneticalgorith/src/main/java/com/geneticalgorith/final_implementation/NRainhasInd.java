package com.geneticalgorith.final_implementation;

import java.util.*;

import com.geneticalgorith.final_implementation.base.Ind;

public class NRainhasInd  implements Ind{
    Random rand = new Random();
    private int n;
    protected int[] genes;

    public NRainhasInd(int n, int[] genes) {
        this.n = n;
        this.genes = Arrays.copyOf(genes, n);
    }

    /*public NRainhasInd(int n) {
        this.n = n;
        this.genes = new int[n];
        for (int i = 0; i < n; i++) {
            genes[i] = rand.nextInt(n);
        }
    }*/

    public List<Ind> recombinar(Ind ind) {
        List<Ind> filhos = new ArrayList<>();

        if (!(ind instanceof NRainhasInd)) {
            return filhos;
        }

        NRainhasInd outroIndividuo = (NRainhasInd) ind;

        if (this.n != outroIndividuo.n) {
            throw new IllegalArgumentException("Indivíduos com tamanhos diferentes");
        }

        int pontoCorte = rand.nextInt(n);

        int[] genesFilho1 = new int[n];
        int[] genesFilho2 = new int[n];

        for (int i = 0; i < n; i++) {
            if (i < pontoCorte) {
                genesFilho1[i] = this.genes[i];
                genesFilho2[i] = outroIndividuo.genes[i];
            } else {
                genesFilho1[i] = outroIndividuo.genes[i];
                genesFilho2[i] = this.genes[i];
            }
        }

        filhos.add(new NRainhasInd(n, genesFilho1));
        filhos.add(new NRainhasInd(n, genesFilho2));

        return filhos;
    }

    public Ind mutar() {
        int[] genesMutante = Arrays.copyOf(this.genes, this.n);
        Random rand = new Random();
        double TMG = 0.2;
        boolean mutacaoOcorreu = false;

        for (int i = 0; i < this.n; i++) {
            if (rand.nextDouble() < TMG) {
                int valorOriginal = genesMutante[i];
                int novoValor;

                do {
                    novoValor = rand.nextInt(this.n);
                } while (novoValor == valorOriginal);

                genesMutante[i] = novoValor;
                mutacaoOcorreu = true;
            }
        }

        if (!mutacaoOcorreu) {
            int posicao = rand.nextInt(this.n);
            int valorOriginal = genesMutante[posicao];
            int novoValor;

            do {
                novoValor = rand.nextInt(this.n);
            } while (novoValor == valorOriginal);

            genesMutante[posicao] = novoValor;

        }
        return new NRainhasInd(this.n, genesMutante);
    }

    public double getAvaliacao() {
        int colisoes = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (genes[i] == genes[j]) {
                    colisoes++;
                }

                if (Math.abs(i - j) == Math.abs(genes[i] - genes[j])) {
                    colisoes++;
                }
            }
        }
        return (double) colisoes;
    }

}
