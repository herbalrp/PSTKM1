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
class Path {
    
    private Integer ID;
    private ArrayList<Integer> listOfLinks;
    
    public Path(Integer ID, ArrayList<Integer> listOfLinks)
    {
        this.ID=ID;
        this.listOfLinks=listOfLinks;
    }
    
    public Integer getID() {
		return this.ID;
	}
    public ArrayList<Integer> getListOfLinks() {
		return this.listOfLinks;
	}
    
}
