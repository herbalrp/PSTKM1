package pstkm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Evolution {
    
    List<Chromosome> ListOfChromosome;
    Integer PopulationSize;
    Integer NumberOfPopulations;
    float ProbabilityOfMutation;
    Net net;
    
    public Evolution(List<Chromosome> ListOfChromosome, Integer PopulationSize, Integer NumberOfPopulations, float ProbabilityOfMutation, Net net)
    {
        this.ListOfChromosome=ListOfChromosome;        
        this.PopulationSize=PopulationSize;
        this.NumberOfPopulations=NumberOfPopulations;
        this.ProbabilityOfMutation=ProbabilityOfMutation;
        this.net=net;
    }
    
    public void start()
    {
        for(int i=0; i<NumberOfPopulations; i++)
        {
            cross();
            mutate();
            select();
        }
        
        System.out.println("The best solution:");
       System.out.println("Total cost: "+ListOfChromosome.get(0).cost);
       for(int k = 0; k <ListOfChromosome.get(0).ListOfTablesS.size(); k++) {
         // System.out.println("Demand " + (k+1));
         //   System.out.println("H: " +net.getListOfDemands().get(k).getdemandVolume());
         //   System.out.println("Solution: ");
            for(int l = 0; l<ListOfChromosome.get(0).ListOfTablesS.get(k).size(); l++) {
             //   System.out.println(ListOfChromosome.get(0).ListOfTablesS.get(k).get(l));
            }
        }
    }

    private void cross() {
       
        Integer size = ListOfChromosome.size();
        Integer NumberOfPair=size/2;
        Random rand = new Random();
        
        for(int i=0; i<NumberOfPair; i++)
        {
            Integer Index1 = rand.nextInt(NumberOfPair-1);
            Integer Index2 = NumberOfPair+rand.nextInt(NumberOfPair-1);
            
            List<Chromosome> Children = new ArrayList();
            Children=crossThisPair(ListOfChromosome.get(Index1).ListOfTablesS,ListOfChromosome.get(Index2).ListOfTablesS);
            ListOfChromosome.addAll(Children);
        }
        
        
    }

    private void mutate() {
        
        Random rand = new Random();
        
        for (int i=0; i<ListOfChromosome.size();i++)
        {
            for(int j=0; j<ListOfChromosome.get(i).ListOfTablesS.size(); j++)
            {
                float x = rand.nextFloat();
                if(x>ProbabilityOfMutation)
                {
                    continue;
                }
                
                List<Integer> Gene = newGene(ListOfChromosome.get(i).ListOfTablesS.get(j));
                ListOfChromosome.get(i).ListOfTablesS.set(j, Gene);
                
            }
        }
        
    }

    private void select() {
        
        for(int i=0; i<ListOfChromosome.size();i++)
        {
            float cost = checkAndCountSolution(ListOfChromosome.get(i).ListOfTablesS);
            if(cost==-1)
            {
                ListOfChromosome.remove(i);
                i--;
            } else
            {
                ListOfChromosome.get(i).cost=cost;
            }
        }
        
        Collections.sort(ListOfChromosome);
        
        for(int i=PopulationSize; i<ListOfChromosome.size(); i++)
        {
            ListOfChromosome.remove(i);
            i--;
        }
    }
    
      private float checkAndCountSolution(List<List> solution) {
       float cost=0;
       List<Integer> lambdasOnLink = new ArrayList();
       for(int i = 0; i<net.getListOfLinks().size(); i++) 
       {
             lambdasOnLink.add(0);
       }
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
          //  System.out.println("Link: " +(l+1));
          //  System.out.println("Number of lambdas: " +lambdasOnLink.get(l));
            Float costOfLink = count(lambdasOnLink.get(l),net.getListOfLinks().get(l).getNumberOfLambdas(),net.getListOfLinks().get(l).getCostOfFibre());
            cost+=costOfLink;
          //  System.out.println("Cost: " +costOfLink);
           // System.out.println("Max number of lambdas on link: " +(net.getListOfLinks().get(l).getNumberOfLambdas() * net.getListOfLinks().get(l).getNumberOfFibres()));
            if(lambdasOnLink.get(l) > net.getListOfLinks().get(l).getNumberOfLambdas() * net.getListOfLinks().get(l).getNumberOfFibres()) {
                return (float) -1;
            }
        }
       
        return cost;
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

    private List<Chromosome> crossThisPair(List<List> Chromosome1, List<List> Chromosome2) {
        
        Random rand = new Random();
        Integer NumberOfChanges=rand.nextInt(Chromosome1.size()-1);
         
        for(int i=0; i<NumberOfChanges; i++)
        {
            Integer IndexOfGene=rand.nextInt(Chromosome1.size()-1);
            List<Integer> Gene1 = Chromosome1.get(IndexOfGene);
            Chromosome1.set(IndexOfGene, Chromosome2.get(IndexOfGene));
            Chromosome2.set(IndexOfGene, Gene1);
        }
        
        List<Chromosome> Children = new ArrayList();
        Children.add(new Chromosome (Chromosome1));
        Children.add(new Chromosome(Chromosome2));
        return Children;
    }

    private List<Integer> newGene(List Gene) {
        
        Random rand = new Random();
        Integer sum=0;
        for(int i=0;i<Gene.size();i++)
        {
            sum+=(Integer)Gene.get(i);
        }
        
        
        for(int i=0; i<Gene.size()-1;i++)
        {
            Integer x = rand.nextInt(sum);
            Gene.set(i, x);
            sum-=x;
            if(sum==0)
            {
                for(int j=i+1; j<Gene.size()-1; j++)
                {
                    Gene.set(j, 0);
                }
                break;
            }
        }
        Gene.set(Gene.size()-1, sum);
        
        return Gene;
    }
}
