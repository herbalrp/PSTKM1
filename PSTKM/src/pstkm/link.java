/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pstkm;

/**
 *
 * @author Mateusz
 */
public class link {
    
    Integer ID;
    Integer start_node;
    Integer end_node;
    Integer n_of_fibres;
    Float cost_of_fibre;
    Integer n_of_lambdas; //number of lambdas in a fiber
    
    public link(Integer ID, Integer start_node, Integer end_node, Integer n_of_fibres, Float cost_of_fibre, Integer n_of_lambdas)
    {
        this.ID=ID;
        this.start_node=start_node;
        this.end_node=end_node;
        this.n_of_fibres=n_of_fibres;
        this.cost_of_fibre=cost_of_fibre;
        this.n_of_lambdas=n_of_lambdas;
        
    }
    
    
            
    
}
