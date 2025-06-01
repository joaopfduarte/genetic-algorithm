package com.geneticalgorith.final_implementation.dixonpriceFunction;
import java.util.*;

public interface DixonPriceInd {
    public List <DixonPriceInd> recombinar (DixonPriceInd ind);
    public DixonPriceInd mutar();
    public double getAvaliacao();    
}
