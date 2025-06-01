package com.geneticalgorith.final_implementation.sumFunction;
import java.util.*;

public interface SumInd {
    public List <SumInd> recombinar (SumInd ind);
    public SumInd mutar();
    public double getAvaliacao();    
}
