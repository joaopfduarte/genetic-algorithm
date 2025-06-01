package com.geneticalgorith.final_implementation.langermannFunction;
import java.util.*;

public interface LangermannInd {
    public List <LangermannInd> recombinar (LangermannInd ind);
    public LangermannInd mutar();
    public double getAvaliacao();    
}
