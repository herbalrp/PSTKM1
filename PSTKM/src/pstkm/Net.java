package pstkm;

import java.util.ArrayList;

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
