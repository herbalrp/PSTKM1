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
public class Link {
    
    private Integer ID;
    private Integer startNode;
    private Integer endNode;
    private Integer numberOfFibres; // TODO: Czy na pewno tutaj jest to liczba fajberow?
    private Float costOfFibre;
    private Integer numberOfLambdas; //number of lambdas in a fiber
    
    public Link(Integer ID, Integer startNode, Integer endNode, Integer numberOfFibres, Float costOfFibre, Integer numberOfLambdas)
    {
        this.ID=ID;
        this.startNode=startNode;
        this.endNode=endNode;
        this.numberOfFibres=numberOfFibres; 
        this.costOfFibre=costOfFibre;
        this.numberOfLambdas=numberOfLambdas;
        
    }

    
    
    
	public Integer getID() {
		return ID;
	}




	public void setID(Integer iD) {
		ID = iD;
	}




	public Integer getStartNode() {
		return startNode;
	}




	public void setStartNode(Integer startNode) {
		this.startNode = startNode;
	}




	public Integer getEndNode() {
		return endNode;
	}




	public void setEndNode(Integer endNode) {
		this.endNode = endNode;
	}




	public Integer getNumberOfFibres() {
		return numberOfFibres;
	}




	public void setNumberOfFibres(Integer numberOfFibres) {
		this.numberOfFibres = numberOfFibres;
	}




	public Float getCostOfFibre() {
		return costOfFibre;
	}




	public void setCostOfFibre(Float costOfFibre) {
		this.costOfFibre = costOfFibre;
	}




	public Integer getNumberOfLambdas() {
		return numberOfLambdas;
	}




	public void setNumberOfLambdas(Integer numberOfLambdas) {
		this.numberOfLambdas = numberOfLambdas;
	}




	@Override
	public String toString() {
		return "Link [ID=" + ID + ", startNode=" + startNode + ", endNode=" + endNode + ", numberOfFibres="
				+ numberOfFibres + ", costOfFibre=" + costOfFibre + ", numberOfLambdas=" + numberOfLambdas + "]";
	}
    
    
            
    
}
