/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pstkm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mateusz
 */
public class DDAP {
    
    Net net;
    
     public DDAP(Net net) {
      
        this.net=net;
    }
    
     
    List<List<List>> ListOfSolutions = new ArrayList();
    List<List> solution = new ArrayList();
     List<List> TheBestSolution = new ArrayList();
      float TheBestCost = 0;
       List<Integer> TheBestlambdasOnLink = new ArrayList();
    List<Integer> lambdasOnLink = new ArrayList();
    List<Float> ListOfCosts = new ArrayList();
    float cost; //zmienna używana przez metodę checkAndCountSolution
    
    public void StartBruteForce(Integer NumberOfRound)
    {
       ListOfSolutions = new ArrayList();       
       ListOfCosts = new ArrayList();
       TheBestCost = 0;
       TheBestSolution = new ArrayList();
       TheBestlambdasOnLink = new ArrayList();
       for(int k=0; k<NumberOfRound; k++)
       {
       solution = new ArrayList();
     lambdasOnLink = new ArrayList();
      for(int i = 0; i<net.getListOfLinks().size(); i++) {
            lambdasOnLink.add(0);
        }
       BruteForce(net);
       }
       System.out.println("The best solution:");
           System.out.println("Total cost: "+TheBestCost);
       for(int k = 0; k <TheBestSolution.size(); k++) {
            System.out.println("Demand " + (k+1));
            System.out.println("H: " +net.getListOfDemands().get(k).getdemandVolume());
            System.out.println("Solution: ");
            for(int l = 0; l<TheBestSolution.get(k).size(); l++) {
                System.out.println(TheBestSolution.get(k).get(l));
            }
        }
       for(int l = 0; l<TheBestlambdasOnLink.size(); l++) {
            System.out.println("Link: " +(l+1));
            System.out.println("Number of lambdas: " +TheBestlambdasOnLink.get(l));
            Float costOfLink = count(TheBestlambdasOnLink.get(l),net.getListOfLinks().get(l).getNumberOfLambdas(),net.getListOfLinks().get(l).getCostOfFibre());
            System.out.println("Cost: " +costOfLink);
            System.out.println("Max number of lambdas on link: " +(net.getListOfLinks().get(l).getNumberOfLambdas() * net.getListOfLinks().get(l).getNumberOfFibres()));
            
        }
    
    }
    
    private void BruteForce(Net net) {
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
        if(checkAndCountSolution(net)) {
            ListOfSolutions.add(solution);
           ListOfCosts.add(cost);
           if(TheBestCost==0 || cost<TheBestCost)
           {
               TheBestCost=cost;
               TheBestSolution=solution;
               TheBestlambdasOnLink=lambdasOnLink;
           } ;
                
            return;
        } else {
            BruteForce(net);
        }
    }

    //sprawdzenie i wyliczenie rozwiązania
    private boolean checkAndCountSolution(Net net) {
       cost=0;
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
        //wyliczenie kosztu, wypisanie linku i przypisanej liczby lambd
        for(int l = 0; l<lambdasOnLink.size(); l++) {
            System.out.println("Link: " +(l+1));
            System.out.println("Number of lambdas: " +lambdasOnLink.get(l));
            Float costOfLink = count(lambdasOnLink.get(l),net.getListOfLinks().get(l).getNumberOfLambdas(),net.getListOfLinks().get(l).getCostOfFibre());
            cost+=costOfLink;
            System.out.println("Cost: " +costOfLink);
            System.out.println("Max number of lambdas on link: " +(net.getListOfLinks().get(l).getNumberOfLambdas() * net.getListOfLinks().get(l).getNumberOfFibres()));
            if(lambdasOnLink.get(l) > net.getListOfLinks().get(l).getNumberOfLambdas() * net.getListOfLinks().get(l).getNumberOfFibres()) {
                return false;
            }
        }
         System.out.println("Total cost: "+cost);
        System.out.println("DAP successfully completed.");
        return true;
    }

    private Float count(Integer get, Integer numberOfLambdas, Float costOfFibre) {
        
        Integer x = get/numberOfLambdas;
        Integer y = get%numberOfLambdas;
        if(y!=0)
        {
            x+=1;
        }
         return (x*costOfFibre);
        
        
    }

   

    
}
