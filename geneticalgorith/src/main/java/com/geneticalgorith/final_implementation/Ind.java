package com.geneticalgorith.final_implementation;
import java.util.*;

public interface Ind {
    public List <Ind> recombinar (Ind ind);
    public Ind mutar();
    public double getAvaliacao();    
}
