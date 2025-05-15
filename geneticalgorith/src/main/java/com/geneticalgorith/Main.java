package com.geneticalgorith;

public class Main {
    public static void main(String[] args) {
        int nPos = 20; int nElite = 4;; int nGer = 1000;
        Main Ag = new Main();
        IndFactory indFac = new NRainhasIndFactory(4);
        Ind melhor = Ag.executar(Npos, nElite, indFac, nGer);    
    }

    private void executar() {}

}
