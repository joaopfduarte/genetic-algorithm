package com.geneticalgorith.final_implementation.langermannFunction;

import com.geneticalgorith.final_implementation.base.Ind;
import java.util.*;

public class LangermannInd implements Ind {
    private double[] genes;
    private final int n;
    private static final double[][] c = {
            { 3, 5, 2, 1, 7 },
            { 5, 2, 1, 4, 9 }
    };
    private static final double[] A = { 1, 2, 5, 2, 3 };

    public LangermannInd(int n, double[] genes) {
        this.n = n;
        this.genes = Arrays.copyOf(genes, n);
    }

    public LangermannInd(int n) {
        this.n = n;
        this.genes = new double[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            this.genes[i] = rand.nextDouble() * 10;
        }
    }

    @Override
    public double getAvaliacao() {
        double result = 0.0;
        for (int j = 0; j < 5; j++) {
            double sum = 0.0;
            for (int i = 0; i < 2; i++) { 
                sum += Math.pow(genes[i] - c[i][j], 2);
            }
            result += A[j] * Math.exp(-sum / Math.PI) * Math.cos(Math.PI * sum);
        }
        return -result; 
    }

    @Override
    public List<Ind> recombinar(Ind parceiro) {
        if (!(parceiro instanceof LangermannInd))
            return Collections.emptyList();
        LangermannInd outro = (LangermannInd) parceiro;
        Random rand = new Random();

        double[] filho1 = new double[n];
        double[] filho2 = new double[n];

        for (int i = 0; i < n; i++) {
            double alpha = rand.nextDouble();
            filho1[i] = alpha * this.genes[i] + (1 - alpha) * outro.genes[i];
            filho2[i] = (1 - alpha) * this.genes[i] + alpha * outro.genes[i];
        }

        return Arrays.asList(new LangermannInd(n, filho1), new LangermannInd(n, filho2));
    }

    @Override
    public Ind mutar() {
        Random rand = new Random();
        double[] genesMutados = Arrays.copyOf(genes, n);
        double sigma = 0.1;

        for (int i = 0; i < n; i++) {
            genesMutados[i] += rand.nextGaussian() * sigma;
        }

        return new LangermannInd(n, genesMutados);
    }

    public double[] getGenes() {
        return genes;
    }
}
