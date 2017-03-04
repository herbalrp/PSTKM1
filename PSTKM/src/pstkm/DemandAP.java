/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pstkm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Lukasz
 */
public class DemandAP {
    
    public DemandAP(Net net) {
        for(int i = 0; i<net.getListOfLinks().size(); i++) {
            lambdasOnLink.add(0);
        }
    }
    
    List<List> solution = new ArrayList();
    List<Integer> lambdasOnLink = new ArrayList();
    
    public void startBruteForce(Net net) {
        //pętla po zapotrzebowaniach
        for(int i = 0; i<net.getListOfDemands().size(); i++) {
            List listaPolaczen = net.getListOfDemands().get(i).getListOfPaths();
            
            List<Integer> demandList = new ArrayList();

            int h = net.getListOfDemands().get(i).getdemandVolume();
            int max = h;
            Random rand = new Random();
            //wypełnienie losowo tabeli rozwiązań
            for(int j = 0; j<listaPolaczen.size(); j++) {
                int lambdasOnPath = rand.nextInt((max + 1));
                if(j+1 >= listaPolaczen.size()) {
                    lambdasOnPath = max;
                }
                
                demandList.add(lambdasOnPath);

                max = max - lambdasOnPath;
            }
            solution.add(demandList);
        }
        
        //wypisanie rozwiązań
        for(int k = 0; k <solution.size(); k++) {
            System.out.println("Demand " + (k+1));
            System.out.println("H: " +net.getListOfDemands().get(k).getdemandVolume());
            System.out.println("Solution: ");
            for(int l = 0; l<solution.get(k).size(); l++) {
                System.out.println(solution.get(k).get(l));
            }
        }
        //sprawdzenie rozwiązania, w przypadku niepowodzenia powtórzenie
        if(solutionChecked(net)) {
            return;
        } else {
            startBruteForce(net);
        }
    }

    //sprawdzenie rozwiązania
    private boolean solutionChecked(Net net) {
        //pętla po zapotrzebowaniach
        for(int i = 0; i< solution.size(); i++) {
            //pętla po lambdach zapotrzebowania
            for(int j = 0; j < solution.get(i).size(); j++) {
                int numberOfLambdas = (int) solution.get(i).get(j);
                List indexesOfLinks = net.getListOfDemands().get(i).getListOfPaths().get(j).getListOfLinks();
                for(int k = 0; k< indexesOfLinks.size(); k++) {
                    int index = (int)indexesOfLinks.get(k)-1;
                    //dodanie lambd do listy
                    lambdasOnLink.set(index, lambdasOnLink.get(index) + numberOfLambdas);
                }
            }
        }
        //wypisanie linku i przypisanej liczby lambd
        for(int l = 0; l<lambdasOnLink.size(); l++) {
            System.out.println("Link: " +(l+1));
            System.out.println("Number of lambdas: " +lambdasOnLink.get(l));
            System.out.println("Max number of lambdas on link: " +(net.getListOfLinks().get(l).getNumberOfLambdas() * net.getListOfLinks().get(l).getNumberOfFibres()));
            if(lambdasOnLink.get(l) > net.getListOfLinks().get(l).getNumberOfLambdas() * net.getListOfLinks().get(l).getNumberOfFibres()) {
                return false;
            }
        }
        System.out.println("DAP successfully completed.");
        return true;
    }

}


