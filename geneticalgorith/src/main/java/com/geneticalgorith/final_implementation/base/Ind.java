package com.geneticalgorith.final_implementation.base;
import java.util.*;

public interface Ind {
    public List <Ind> recombinar (Ind ind);
    public Ind mutar();
    public double getAvaliacao();    
}
