package com.geneticalgorith;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int N = 20;
        final int POP_SIZE = 1000;
        final int MAX_GEN = 1000;
        final double CROSSOVER_RATE = 0.8;
        final double MUTATION_RATE = 0.2;

        NRainhasIndFactory factory = new NRainhasIndFactory(N);
        List<NRainhasInd> population = new ArrayList<>();

        for (int i = 0; i < POP_SIZE; i++) {
            population.add(factory.create());
        }

        int generation = 0;
        NRainhasInd best = null;

        while (generation < MAX_GEN) {
            population.sort(Comparator.comparingInt(NRainhasInd::getFitness));
            best = population.get(0);

            System.out.println("Geração " + generation + " | Melhor fitness: " + best.getFitness());

            if (best.getFitness() == 0) {
                break;
            }
            List<NRainhasInd> newPopulation = new ArrayList<>();

            Random rand = new Random();

            while (newPopulation.size() < POP_SIZE) {
                NRainhasInd parent1 = tournamentSelection(population);
                NRainhasInd parent2 = tournamentSelection(population);

                NRainhasInd child;

                if (rand.nextDouble() < CROSSOVER_RATE) {
                    child = parent1.crossover(parent2);
                } else {
                    child = new NRainhasInd(parent1.getGenes());
                }

                if (rand.nextDouble() < MUTATION_RATE) {
                    child.mutate();
                }

                newPopulation.add(child);
            }

            population = newPopulation;
            generation++;
        }

        System.out.println("\nMelhor solução encontrada:");
        System.out.println(best);
        System.out.println("Fitness: " + best.getFitness());
    }

    private static NRainhasInd tournamentSelection(List<NRainhasInd> population) {
        Random rand = new Random();
        NRainhasInd ind1 = population.get(rand.nextInt(population.size()));
        NRainhasInd ind2 = population.get(rand.nextInt(population.size()));
        return ind1.getFitness() < ind2.getFitness() ? ind1 : ind2;
    }
}
