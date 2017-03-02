/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pstkm;

import static java.util.Collections.list;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateusz
 */
public class Net {
    
    
    private ArrayList<Link> listOfLinks;
    private ArrayList<Demand> listOfDemands;
     
    public Net()
    {
        
    }

	public ArrayList<Link> getListOfLinks() {
		return listOfLinks;
	}

	public void setListOfLinks(ArrayList<Link> listOfLinks) {
		this.listOfLinks = listOfLinks;
	}

	public ArrayList<Demand> getListOfDemands() {
		return listOfDemands;
	}

	public void setListOfDemands(ArrayList<Demand> arrayList) {
		this.listOfDemands = arrayList;
	}
    
   
    
}
