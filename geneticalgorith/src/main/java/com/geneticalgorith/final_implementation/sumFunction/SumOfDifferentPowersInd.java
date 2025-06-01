package com.geneticalgorith.final_implementation.sumFunction;

import com.geneticalgorith.final_implementation.base.Ind;
import java.util.*;

public class SumOfDifferentPowersInd implements Ind {
    private double[] genes;
    private final int n;

    public SumOfDifferentPowersInd(int n, double[] genes) {
        this.n = n;
        this.genes = Arrays.copyOf(genes, n);
    }

    public SumOfDifferentPowersInd(int n) {
        this.n = n;
        this.genes = new double[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            this.genes[i] = -1 + rand.nextDouble() * 2;
        }
    }

    @Override
    public double getAvaliacao() {
        double sum = 0.0;
        for (int i = 1; i < n; i++) {
            sum += Math.pow(Math.abs(genes[i]), i + 2);
        }
        return sum;
    }

    @Override
    public List<Ind> recombinar(Ind parceiro) {
        if (!(parceiro instanceof SumOfDifferentPowersInd))
            return Collections.emptyList();
        SumOfDifferentPowersInd outro = (SumOfDifferentPowersInd) parceiro;
        Random rand = new Random();

        double[] filho1 = new double[n];
        double[] filho2 = new double[n];

        for (int i = 0; i < n; i++) {
            double alpha = rand.nextDouble();
            filho1[i] = alpha * this.genes[i] + (1 - alpha) * outro.genes[i];
            filho2[i] = (1 - alpha) * this.genes[i] + alpha * outro.genes[i];
        }

        return Arrays.asList(new SumOfDifferentPowersInd(n, filho1), new SumOfDifferentPowersInd(n, filho2));
    }

    @Override
    public Ind mutar() {
        Random rand = new Random();
        double[] genesMutados = Arrays.copyOf(genes, n);
        double sigma = 0.1;

        for (int i = 0; i < n; i++) {
            genesMutados[i] += rand.nextGaussian() * sigma;
        }

        return new SumOfDifferentPowersInd(n, genesMutados);
    }

    public double[] getGenes() {
        return genes;
    }
}
