package pstkm;

import java.util.ArrayList;

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
  
   public Demand(Integer startNode, Integer endNode, Integer demandVolume)
  {
      this.startNode=startNode;
      this.endNode=endNode;
      this.demandVolume=demandVolume;
      
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
  
  public void setListOfPaths(ArrayList<Path> listOfPaths) {
		this.listOfPaths=listOfPaths;
	}

	
     @Override
	public String toString() {
		String s = "DEMAND [startNode "+startNode+" endNode "+endNode+" demandVolume "+demandVolume+"]"+"\r\n";
                for(Path path : listOfPaths)
                {
                   s+=path.toString();
                   s+="\r\n";
                }
              
                return s;
	}
}
