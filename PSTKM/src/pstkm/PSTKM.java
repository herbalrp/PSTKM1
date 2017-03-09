/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pstkm;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mateusz
 */
public class PSTKM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	
    	/*
    	 * 	args[0] - to parametr podawany po nazwie jara przy uruchamianiu w consoli "java PSTKM1.jar PARAMETR"
    	 * 	zeby to zrobic z IDE to dodajemy to w konfiguracji Run As.. i jest to sciezka
    	 * 	ja wpisalem tam: /Users/robert/Documents/PSTKM_cw/pstkm_cw1.txt
    	 */
         final Integer NUMBEROFROUNDS=100;
         final Integer POPULATIONSIZE=100; //Liczebność populacji
         final Integer NUMBEROFPOPULATION=20;//Liczba kolejnych populacji
         final float PROBABILITYOFMUTATION=(float)0.5; //prawdopodobieństwo mutacji chromosomu <0-1>
    	Parser parser = null;
        String pathToFile = "";
       
    	if (args.length > 0) {
            pathToFile = args[0];
		} else {
            Scanner scanner = new Scanner(System.in);
            while(!(new File(pathToFile).isFile())) {
                System.out.println("Please enter path to file with network description. To terminate press [N]:");
                pathToFile = scanner.next();
                if (pathToFile.equals("N")) {
                    System.exit(0);
                }
            }
		}

        try {
            parser = new Parser(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Parser Exception: " + e);
        }

    	Net net = new Net();
    	net.setListOfLinks(parser.parseNetPart());
    	net.setListOfDemands(parser.parseDemandPart());


    	// temporary printing links
    	for(Link lk : net.getListOfLinks()){
			System.out.println(lk);
		}
        for(Demand lk : net.getListOfDemands()){
			System.out.println(lk);
		}

    	//start of DAP brute force
        //DemandAP dap = new DemandAP(net);
        //dap.startBruteForce(net);
         DDAP ddap = new DDAP(net);
    //   List<List<List>> list =  ddap.StartBruteForce(NUMBEROFROUNDS);
    //   List<Chromosome> list2 = new ArrayList();
     //  for(int i=0; i<list.size();i++)
     //  {
     //      list2.add(new Chromosome(list.get(i)));
      // }
     //  Evolution ev = new Evolution(list2, POPULATIONSIZE, NUMBEROFPOPULATION, PROBABILITYOFMUTATION, net);
     //  ev.start();
      // ddap.StartBruteForce(100000);
    }
}
