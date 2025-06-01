package com.geneticalgorith.final_implementation.dixonpriceFunction;

import com.geneticalgorith.final_implementation.base.Ind;
import java.util.*;

public class DixonPriceInd implements Ind {
    private double[] genes;
    private final int n;
    private double sigmaAdaptativo = 0.5;

    public void setSigmaAdaptativo(double sigma) {
        this.sigmaAdaptativo = sigma;
    }

    public DixonPriceInd(int n, double[] genes) {
        this.n = n;
        this.genes = Arrays.copyOf(genes, n);
    }

    @Override
    public double getAvaliacao() {
        double sum = Math.pow(genes[0] - 1, 2);
        for (int i = 1; i < n; i++) {
            sum += (i + 1) * Math.pow((2 * Math.pow(genes[i], 2) - genes[i - 1]), 2);
        }
        return sum;
    }

    @Override
    public List<Ind> recombinar(Ind parceiro) {
        DixonPriceInd outro = (DixonPriceInd) parceiro;
        Random rand = new Random();
        double[] filho1 = new double[n];
        double[] filho2 = new double[n];

        for (int i = 0; i < n; i++) {
            double beta;
            if (rand.nextDouble() <= 0.5) {
                beta = Math.pow(2 * rand.nextDouble(), 1.0 / 20.0);
            } else {
                beta = Math.pow(2 * rand.nextDouble(), -1.0 / 20.0);
            }

            filho1[i] = 0.5 * ((1 + beta) * genes[i] + (1 - beta) * outro.genes[i]);
            filho2[i] = 0.5 * ((1 - beta) * genes[i] + (1 + beta) * outro.genes[i]);
        }

        return Arrays.asList(new DixonPriceInd(n, filho1), new DixonPriceInd(n, filho2));
    }

    @Override
    public Ind mutar() {
        Random rand = new Random();
        double[] genesMutados = Arrays.copyOf(genes, n);

        for (int i = 0; i < n; i++) {
            genesMutados[i] += rand.nextGaussian() * sigmaAdaptativo;
        }

        return new DixonPriceInd(n, genesMutados);
    }

    public double[] getGenes() {
        return genes;
    }
}
