package pstkm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

	private String netPart = null;
	private String demandPart=null;

	Parser(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			StringBuilder netPart = new StringBuilder();
			StringBuilder demandPart = new StringBuilder();
			boolean firstPart = true;
			String line = br.readLine();

			while (line != null) {
				if (firstPart) {
					if (!line.equals("-1")) {

						netPart.append(line.trim());
						netPart.append(System.lineSeparator());
					}else
					firstPart = false;
				} else {
					demandPart.append(line.trim());
					demandPart.append(System.lineSeparator());
				}
				line = br.readLine();
			}

			//	System.out.println(netPart.toString().trim());
			this.setNetPart(netPart.toString().trim());
			//	System.out.println(demandPart.toString().trim());
			this.setDemandPart(demandPart.toString().trim());
		} catch (IOException e) {
			System.out.println("Error durring parsing file in Parser constructor: " + e);
			e.printStackTrace();
		} finally {
			br.close();
		}		
	}

	/**
	 * Method parses the net part of configuration file
	 * @return List of links
	 */
	public ArrayList<Link> parseNetPart(){
		ArrayList<Link> links= new ArrayList<Link>();
		if(!netPart.isEmpty() && netPart != null){
		//System.out.println(netPart);
		String lines[] = netPart.split("\\r?\\n");
		
		for(int i = 1; i<=Integer.parseInt(lines[0].trim()); i++){
			//System.out.println(lines[i]);
			String linkParts[] = lines[i].split(" ");
			Link link = new Link(i, Integer.parseInt(linkParts[0]), Integer.parseInt(linkParts[1]),Integer.parseInt(linkParts[2]),Float.parseFloat(linkParts[3]),Integer.parseInt(linkParts[4]));
			links.add(link);
		}
		
				return links;
		}
		return null;
	}
	
	/**
	 * Method parses the demand part of configuration file
	 * @return List of demands
	 */
	public ArrayList<Demand> parseDemandPart(){
		ArrayList<Demand> demands= new ArrayList<Demand>();
		//System.out.println(demandPart);        
	
		if(!demandPart.isEmpty() && demandPart != null){
		String lines[] = demandPart.split("\\r?\\n");
		
                Integer numberOfDemand=Integer.parseInt(lines[0]);
               
                Integer LineIndex=2;
                Demand demand;
                ArrayList<Path> paths;
                while(numberOfDemand>0)
                {
                    String linkParts[] = lines[LineIndex].split(" ");
                    demand=new Demand(Integer.parseInt(linkParts[0]),Integer.parseInt(linkParts[1]),Integer.parseInt(linkParts[2]));
                    LineIndex+=1;
                    linkParts = lines[LineIndex].split(" ");
                    paths=new ArrayList<Path>();
                    Integer NumberOfPaths = Integer.parseInt(linkParts[0]);
                    for(int i=1; i<=NumberOfPaths;i++)
                    {
                        LineIndex+=1;
                        linkParts = lines[LineIndex].split(" ");
                        ArrayList<Integer> links =new ArrayList<Integer>();
                        for(int j=1; j<linkParts.length;j++)
                        {
                            links.add(Integer.parseInt(linkParts[j]));
                        }
                       
                        paths.add(new Path(Integer.parseInt(linkParts[0]),links));
                       
                    }
                    demand.setListOfPaths(paths);
                    demands.add(demand);
                //    System.out.println(demand.toString());
                    LineIndex+=2;
                    numberOfDemand-=1;
                }
                
                
	return demands;
		}
		
			
	
	
		return null;
	}

	public String getNetPart() {
		return netPart;
	}

	public void setNetPart(String netPart) {
		this.netPart = netPart;
	}

	public String getDemandPart() {
		return demandPart;
	}

	public void setDemandPart(String demandPart) {
		this.demandPart = demandPart;
	}

}
