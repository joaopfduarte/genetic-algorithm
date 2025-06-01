package com.geneticalgorith.final_implementation;

import java.util.*;
import com.geneticalgorith.final_implementation.base.Ind;
import com.geneticalgorith.final_implementation.base.IndFactory;
import com.geneticalgorith.final_implementation.dixonpriceFunction.DixonPriceInd;
import com.geneticalgorith.final_implementation.dixonpriceFunction.DixonPriceIndFactory;
import com.geneticalgorith.final_implementation.langermannFunction.LangermannFunctionIndFactory;
import com.geneticalgorith.final_implementation.sumFunction.SumOfDifferentPowersIndFactory;

public class MainAg {

    public static void main(String[] args) {
        int tamanhoPopulacao = 200;
        int maxGeracoes = 50000;
        double taxaMutacao = 0.3;
        double taxaCrossover = 0.8;
        int elitismo = 8;

        executarAG(new LangermannFunctionIndFactory(2), "Langermann (2D)", tamanhoPopulacao, maxGeracoes, taxaMutacao,
                taxaCrossover,
                elitismo);
        executarAG(new DixonPriceIndFactory(30), "Dixon-Price (30D)", tamanhoPopulacao, maxGeracoes, taxaMutacao,
                taxaCrossover, elitismo);
        executarAG(new SumOfDifferentPowersIndFactory(30), "Sum of Different Powers (30D)", tamanhoPopulacao,
                maxGeracoes,
                taxaMutacao, taxaCrossover, elitismo);
    }

    private static void executarAG(IndFactory factory, String nomeFuncao,
            int tamanhoPopulacao, int maxGeracoes,
            double taxaMutacao, double taxaCrossover, int elitismo) {

        System.out.println("\n================================================");
        System.out.println("  INICIANDO OTIMIZAÇÃO PARA: " + nomeFuncao);
        System.out.println("================================================\n");

        List<Ind> populacao = new ArrayList<>();
        for (int i = 0; i < tamanhoPopulacao; i++) {
            populacao.add(factory.getIndividuoAleatorio());
        }

        Ind melhorGlobal = null;
        boolean encontrouOtimo = false;
        double melhorAvaliacaoAnterior = Double.POSITIVE_INFINITY;
        int estagnacoes = 0;
        final int estagnacaoMax = 500;
        double sigmaBase = 0.5;
        final double decayRate = 0.001;

        for (int geracao = 0; geracao < maxGeracoes; geracao++) {
            List<Ind> novaPopulacao = new ArrayList<>();

            Collections.sort(populacao, Comparator.comparingDouble(Ind::getAvaliacao));
            novaPopulacao.addAll(populacao.subList(0, elitismo));

            if (geracao % 100 == 0) {
                for (int i = elitismo; i < tamanhoPopulacao; i++) {
                    if (i < novaPopulacao.size() && Math.random() < 0.1) {
                        novaPopulacao.set(i, factory.getIndividuoAleatorio());
                    }
                }
            }

            while (novaPopulacao.size() < tamanhoPopulacao) {
                Ind pai1 = selecaoTorneio(populacao);
                Ind pai2 = selecaoTorneio(populacao);

                if (Math.random() < taxaCrossover) {
                    List<Ind> filhos = pai1.recombinar(pai2);
                    for (Ind filho : filhos) {
                        if (novaPopulacao.size() < tamanhoPopulacao) {
                            novaPopulacao.add(filho);
                        }
                    }
                } else {
                    if (novaPopulacao.size() < tamanhoPopulacao)
                        novaPopulacao.add(pai1);
                    if (novaPopulacao.size() < tamanhoPopulacao)
                        novaPopulacao.add(pai2);
                }
            }

            // Calcula o sigma adaptativo para esta geração
            double sigmaAtual = sigmaBase * Math.exp(-decayRate * geracao);

            List<Ind> mutados = new ArrayList<>();
            for (Ind ind : novaPopulacao) {
                // Aplica mutação adaptativa para DixonPriceInd
                if (ind instanceof DixonPriceInd) {
                    ((DixonPriceInd) ind).setSigmaAdaptativo(sigmaAtual);
                }

                if (Math.random() < taxaMutacao) {
                    mutados.add(ind.mutar());
                } else {
                    mutados.add(ind);
                }
            }

            populacao = mutados;
            Ind melhorGeracao = Collections.min(populacao, Comparator.comparingDouble(Ind::getAvaliacao));

            // Atualiza o melhor global
            if (melhorGlobal == null || melhorGeracao.getAvaliacao() < melhorGlobal.getAvaliacao()) {
                melhorGlobal = melhorGeracao;
            }

            // Verifica estagnação
            if (Math.abs(melhorAvaliacaoAnterior - melhorGeracao.getAvaliacao()) < 1e-10) {
                estagnacoes++;
            } else {
                estagnacoes = 0;
            }
            melhorAvaliacaoAnterior = melhorGeracao.getAvaliacao();

            // Log reduzido (apenas a cada 500 gerações)
            if (geracao % 500 == 0) {
                System.out.printf("[%s] Geração %6d: Melhor = %.8f (Estagnações: %d)\n",
                        nomeFuncao, geracao, melhorGeracao.getAvaliacao(), estagnacoes);
            }

            // Critérios de parada
            if (melhorGeracao.getAvaliacao() <= 1e-6) {
                System.out.printf("\nSolução ótima encontrada na geração %d!\n", geracao);
                encontrouOtimo = true;
                break;
            } else if (estagnacoes >= estagnacaoMax) {
                System.out.printf("\nParada por estagnação na geração %d. Melhor avaliação: %.8f\n",
                        geracao, melhorGeracao.getAvaliacao());
                break;
            }
        }

        System.out.println("\n==========================================");
        System.out.println("  RESULTADO FINAL: " + nomeFuncao);
        System.out.println("==========================================");
        System.out.printf("Melhor avaliação: %.10f\n", melhorGlobal.getAvaliacao());
        System.out.println("Melhor solução:");

        double[] genes = getGenesSafe(melhorGlobal);
        printGenes(genes);

        if (!encontrouOtimo) {
            System.out.println("\n(Máximo de gerações ou estagnação atingido)");
        }
        System.out.println("==========================================\n");
    }

    private static void printGenes(double[] genes) {
        int genesPerLine = 10;
        for (int i = 0; i < genes.length; i++) {
            if (i % genesPerLine == 0) {
                if (i > 0)
                    System.out.println();
                System.out.print("   ");
            }
            System.out.printf("%.4f", genes[i]);
            if (i < genes.length - 1)
                System.out.print(", ");
        }
        System.out.println();
    }

    private static Ind selecaoTorneio(List<Ind> populacao) {
        Random rand = new Random();
        List<Ind> torneio = new ArrayList<>();
        int tamanhoTorneio = 5;

        for (int i = 0; i < tamanhoTorneio; i++) {
            torneio.add(populacao.get(rand.nextInt(populacao.size())));
        }

        return Collections.min(torneio, Comparator.comparingDouble(Ind::getAvaliacao));
    }

    private static double[] getGenesSafe(Ind individuo) {
        try {
            return (double[]) individuo.getClass().getMethod("getGenes").invoke(individuo);
        } catch (Exception e) {
            return new double[0];
        }
    }
}