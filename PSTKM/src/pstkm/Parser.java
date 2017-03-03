package pstkm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that loads configuration file and splits into two blocks
 * @author robert
 *
 */
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

						netPart.append(line);
						netPart.append(System.lineSeparator());
					}else
					firstPart = false;
				} else {
					demandPart.append(line);
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
		
		for(int i = 1; i<=Integer.parseInt(lines[0]); i++){
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
		
		String dems[] = demandPart.split("\\n\\n");
		
		Integer startNode;
		Integer endNode;
		Integer demandVolume;
		Integer numberOfPaths;
		ArrayList<Path> listOfPaths;
		
		for(int i = 1; i<=Integer.parseInt(dems[0]); i++){
			String demLines[] = dems[i].split("\\r?\\n");
			
			//first line of demand
			String firstLineParts[] = demLines[0].split(" ");
			startNode=Integer.parseInt(firstLineParts[0]);
			endNode=Integer.parseInt(firstLineParts[1]);
			demandVolume=Integer.parseInt(firstLineParts[2]);
			
			//second line of demand
			numberOfPaths=Integer.parseInt(demLines[1]);
			
			//other lines - paths
			
			
			for(int j=2; j<demLines.length; j++){
				
				
				//TODO: dorobic parsowanko na Pathy - musiałem już isc na siłke xD
				
			}
			
			
				
		
			
			
		}
		// TODO: Parsowanie bloku zapotrzebowan
		return demands;
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
