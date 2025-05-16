package com.geneticalgorith.first_implementation;

import java.util.Random;

import java.util.*;

public class NRainhasInd {
    private int[] genes;
    private int fitness;

    public NRainhasInd(int[] genes) {
        this.genes = genes.clone();
        calcFitness();
    }

    public int[] getGenes() {
        return genes.clone();
    }

    public int getFitness() {
        return fitness;
    }

    private void calcFitness() {
        fitness = 0;
        for (int i = 0; i < genes.length; i++) {
            for (int j = i + 1; j < genes.length; j++) {
                if (Math.abs(genes[i] - genes[j]) == Math.abs(i - j)) {
                    fitness++;
                }
            }
        }
    }

    public void mutate() {
        Random rand = new Random();
        int i = rand.nextInt(genes.length);
        int j = rand.nextInt(genes.length);
        int temp = genes[i];
        genes[i] = genes[j];
        genes[j] = temp;
        calcFitness();
    }

    public NRainhasInd crossover(NRainhasInd other) {
        Random rand = new Random();
        int n = genes.length;
        int[] childGenes = new int[n];

        int start = rand.nextInt(n);
        int end = rand.nextInt(n - start) + start;

        Set<Integer> used = new HashSet<>();

        for (int i = start; i <= end; i++) {
            childGenes[i] = this.genes[i];
            used.add(childGenes[i]);
        }

        int index = 0;
        for (int i = 0; i < n; i++) {
            if (i >= start && i <= end)
                continue;
            while (used.contains(other.genes[index])) {
                index++;
            }
            childGenes[i] = other.genes[index++];
        }

        return new NRainhasInd(childGenes);
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
