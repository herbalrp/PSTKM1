/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pstkm;

import java.util.ArrayList;

/**
 *
 * @author Mateusz
 */
public class Demand {
    
  private Integer startNode;
  private Integer endNode;
  private Integer demandVolume;
  private ArrayList<Path> listOfPaths;
  
  public Demand(Integer startNode, Integer endNode, Integer demandVolume, ArrayList<Path> listOfPaths)
  {
      this.startNode=startNode;
      this.endNode=endNode;
      this.demandVolume=demandVolume;
      this.listOfPaths=listOfPaths;
  }
  
  
  public Integer getstartNode() {
		return this.startNode;
	}
  public Integer getendNode() {
		return this.endNode;
	}
  public Integer getdemandVolume() {
		return this.demandVolume;
	}
	
  public ArrayList<Path> getListOfPaths() {
		return this.listOfPaths;
	}

	
    
}
