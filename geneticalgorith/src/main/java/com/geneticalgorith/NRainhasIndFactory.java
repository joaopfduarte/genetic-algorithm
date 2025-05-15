package com.geneticalgorith;
import com.geneticalgorith.interfaces.*;

public class NRainhasIndFactory implements IndFactory {
    private int n = 0;

    public NRainhasIndFactory(int n) {
        this.n = n;
    }

    public Ind getInstance() {
        return new NRainhasIndFactory(n);
    }
}
