package pstkm;

import java.util.ArrayList;

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
    
    @Override
	public String toString() {
		String s = "PATH ID "+ID+" [ ";
                for(Integer Link : listOfLinks)
                {
                   s+=Link;
                   s+=" ";
                }
                s+="]";
                
                return s;
	}
    
}
