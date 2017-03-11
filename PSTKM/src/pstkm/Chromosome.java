/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pstkm;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Mateusz
 */
public class Chromosome implements Comparable<Chromosome>, Serializable{
    
    public List<List> ListOfTablesS;
    public float cost;
    
    public Chromosome(List<List> ListOfTablesS, float cost)
    {
       this.ListOfTablesS=ListOfTablesS;
       this.cost=cost;
    }

     public Chromosome(List<List> ListOfTablesS)
    {
       this.ListOfTablesS=ListOfTablesS;
       cost=-1;
    }
    @Override
    public int compareTo(Chromosome o) {
        if(cost<o.cost)
        {
            return -1;
        } else if(cost==o.cost)
        {
            return 0;
        } else
        {
            return 1;
        }
    }
    
}
