package com.geneticalgorith.final_implementation;

import java.util.*;

import com.geneticalgorith.final_implementation.base.Ind;

public class MainAg {
    public static void main(String[] args) {
        int nRainhas = 14;
        int tamanhoPopulacao = 60;
        int maxGeracoes = 10000;
        double taxaMutacao = 0.2;
        double taxaCrossover = 0.8;
        int elitismo = 8;

        NRainhasIndFactory factory = new NRainhasIndFactory(nRainhas);
        List<Ind> populacao = new ArrayList<>();

        for (int i = 0; i < tamanhoPopulacao; i++) {
            populacao.add(factory.getIndividuoAleatorio());
        }

        for (int geracao = 0; geracao < maxGeracoes; geracao++) {
            List<Ind> novaPopulacao = new ArrayList<>();

            Collections.sort(populacao, Comparator.comparingDouble(Ind::getAvaliacao));
            novaPopulacao.addAll(populacao.subList(0, elitismo));

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
                    if (novaPopulacao.size() < tamanhoPopulacao) {
                        novaPopulacao.add(pai1);
                    }
                    if (novaPopulacao.size() < tamanhoPopulacao) {
                        novaPopulacao.add(pai2);
                    }
                }
            }

            List<Ind> mutados = new ArrayList<>();
            for (Ind ind : novaPopulacao) {
                if (Math.random() < taxaMutacao) {
                    mutados.add(ind.mutar());
                } else {
                    mutados.add(ind);
                }
            }
            populacao = mutados;

            Ind melhor = Collections.min(populacao, Comparator.comparingDouble(Ind::getAvaliacao));
            System.out.println("Geração " + geracao + ": Melhor = " + melhor.getAvaliacao());

            if (melhor.getAvaliacao() == 0) {
                System.out.println("Solução encontrada!");
                System.out.println("Posições das rainhas: " + Arrays.toString(((NRainhasInd) melhor).genes));

                System.out.println("Tabuleiro:");
                for (int linha = 0; linha < nRainhas; linha++) {
                    for (int coluna = 0; coluna < nRainhas; coluna++) {
                        if (((NRainhasInd) melhor).genes[coluna] == linha) {
                            System.out.print("Q ");
                        } else {
                            System.out.print(". ");
                        }
                    }
                    System.out.println();
                }
                break;
            }
        }
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
}