package com.geneticalgorithm;

import com.geneticalgorithm.interfaces.Ind;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NRainhasInd implements Ind {
    private int[] genes;
    private int n;
    private Random rand = new Random();

    public NRainhasInd(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Tamanho inválido de N");
        }
        this.n = n;
        this.genes = new int[n];

        for (int i = 0; i < n; i++) {
            genes[i] = rand.nextInt(n); 
    }

    @Override
    public List<Ind> recombinar(Ind ind) {
        List<Ind> filhos = new ArrayList<>();

        if (!(ind instanceof NRainhasInd)) {
            return filhos;
        }

        NRainhasInd outroIndividuo = (NRainhasInd) ind;

        int pontoCorte = rand.nextInt(n);

        int[] filho1Genes = new int[n];
        int[] filho2Genes = new int[n];

        for (int i = 0; i < pontoCorte; i++) {
            filho1Genes[i] = this.genes[i];
            filho2Genes[i] = outroIndividuo.genes[i];
        }
        for (int i = pontoCorte; i < n; i++) {
            filho1Genes[i] = outroIndividuo.genes[i];
            filho2Genes[i] = this.genes[i];
        }

        NRainhasInd filho1 = new NRainhasInd(n, filho1Genes);
        NRainhasInd filho2 = new NRainhasInd(n, filho2Genes);

        filhos.add(filho1);
        filhos.add(filho2);

        return filhos;
    }

    @Override
    public Ind mutar() {
        int[] novosGenes = this.genes.clone();
        int posicaoMutacao = rand.nextInt(n);
        novosGenes[posicaoMutacao] = rand.nextInt(n);

        return new NRainhasInd(n, novosGenes);
    }

    private NRainhasInd(int n, int[] genes) {
        this.n = n;
        this.genes = genes.clone();
    }

    public double getAvaliacao() {
        double colisao = 0;
        
        return colisao;
    }
}