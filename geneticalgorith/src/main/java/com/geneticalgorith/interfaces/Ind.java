package com.geneticalgorith.interfaces;

import java.util.List;

public interface Ind {
    public List<Ind> Recombinar(int id);

    public <Ind> void mutar();

    public double getAvaliacao = 0;
}
