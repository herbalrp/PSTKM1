package pstkm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Evolution {
    
    List<Chromosome> ListOfChromosome;
    List<Chromosome> ListOfTheBestChromosome = new ArrayList<Chromosome>();
    Integer PopulationSize;
    Integer NumberOfPopulations;
    float ProbabilityOfMutation;
    float ProbabilityOfCrossing;
    Net net;
    long seed;
    int NumberOfMutations=0;
    // cost=0;
    int N=0;
    String file;
    Long time;
    Random rand;
    
    public Evolution(List<Chromosome> ListOfChromosome, Integer PopulationSize,  float ProbabilityOfMutation, float ProbabilityOfCrossing, long seed, Net net, String file)
    {
        this.ListOfChromosome=ListOfChromosome;        
        this.PopulationSize=PopulationSize;
        this.ProbabilityOfMutation=ProbabilityOfMutation;
        this.ProbabilityOfCrossing=ProbabilityOfCrossing;
        this.net=net;
        this.seed=seed;
        this.file=file;
       rand = new Random(seed);
    }
    
    public String startMaxTime(float numberOfMinutes)
    {
        long finish = System.currentTimeMillis()+(long)numberOfMinutes*60000; 
        time=System.currentTimeMillis();
        
        while(System.currentTimeMillis() < finish)
        {
            cross();
            mutate();
            select();
            
        }

        write();      
        String s = "Zakończyłem pracę. Kolejne najlepsze wyniki zapisane zostały w pliku. \r\n";
        s+=ListOfTheBestChromosome.get(ListOfTheBestChromosome.size()-1).toString();
        return s;
    }
    
      public String startNumberofRounds(int NumberOfPopulations)
    {
        time=System.currentTimeMillis();
        for(int i=0; i<NumberOfPopulations; i++)
        {
            cross();
            mutate();
            select();
        
        }

        write();      
        String s = "Zakończyłem pracę. Kolejne najlepsze wyniki zapisane zostały w pliku. \r\n";
        s+=ListOfTheBestChromosome.get(ListOfTheBestChromosome.size()-1).toString();
        return s;
        
    }
        public String startNumberOfMutations(int NumberOfMutations)
    {
        time=System.currentTimeMillis();
        boolean continuation = true;
        while(continuation)
        {
            cross();
            continuation=mutateMax(NumberOfMutations);
            select();
        
        }

        write();      
         String s = "Zakończyłem pracę. Kolejne najlepsze wyniki zapisane zostały w pliku. \r\n";
        s+=ListOfTheBestChromosome.get(ListOfTheBestChromosome.size()-1).toString();
        return s;
        
    }
        
          public String startLackOfDifference(int N)
    {
         time=System.currentTimeMillis();
        boolean continuation = true;
        while(continuation)
        {
            cross();
            mutate();
            continuation=select(N);
          
        }

        write();    
         String s = "Zakończyłem pracę. Kolejne najlepsze wyniki zapisane zostały w pliku. \r\n";
        s+=ListOfTheBestChromosome.get(ListOfTheBestChromosome.size()-1).toString();
        return s;
        
    }
    

    private void _writeBestSolutionToConsole(Chromosome chromosome) {
        int x=1;
        for (List<Integer> genes : chromosome.ListOfTablesS)
        {
            
            System.out.println(x);
            x++;
            genes.stream().forEach(g -> System.out.print("|" +g));
            System.out.println("|");
        }
    }
    
     private void _writeBestSolutionTofile(String file) {
       
          long time2=System.currentTimeMillis()-time;
         File plik = new File(file);
        try {
            PrintWriter zapis = new PrintWriter(plik);
            zapis.println("Długość trwania algorytmu: "+(double)(time2/60000)+" minut, a dokładnie: "+time2 + " ms");  
            zapis.println("Najlepsze chromosomy w rundach:");           
            int i=1;
            for(Chromosome chromosome: ListOfTheBestChromosome)
            {
                zapis.println("Runda "+i);
                zapis.println(chromosome.cost);
                i++;
            }
            zapis.println("Ostateczny najlepszy chromosom:");    
            zapis.println(ListOfTheBestChromosome.get(ListOfTheBestChromosome.size()-1).toString());    
            zapis.println("Liczba lambd na linkach:");
            zapis.println(CountLambdas((ListOfTheBestChromosome.get(ListOfTheBestChromosome.size()-1).ListOfTablesS)));
            zapis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Evolution.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    private void cross() {
       
        Integer size = ListOfChromosome.size();
        Integer NumberOfPair=size/2;
      
        if(NumberOfPair>0)
        {
          for(int i=0; i<NumberOfPair; i++)
        {
            float x = rand.nextFloat();
            if(x>ProbabilityOfCrossing)
            {
                continue;
            }
            int Index1 = rand.nextInt(ListOfChromosome.size()-1);
            int Index2 = Index1;
            while (Index2==Index1)
            {
                 Index2 = rand.nextInt(ListOfChromosome.size()-1);
            }           
            
            List<Chromosome> Children = new ArrayList();
            Children=crossThisPair(ListOfChromosome.get(Index1).ListOfTablesS,ListOfChromosome.get(Index2).ListOfTablesS);
            ListOfChromosome.addAll(Children);
        }
        }
       
        
        
    }

    private void mutate() {
        
    
        
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
    
     private boolean mutateMax(int max) {
        
       
        
        for (int i=0; i<ListOfChromosome.size();i++)
        {
            for(int j=0; j<ListOfChromosome.get(i).ListOfTablesS.size(); j++)
            {
                float x = rand.nextFloat();
                if(x>ProbabilityOfMutation)
                {
                    continue;
                }
                if(NumberOfMutations>max)
                {
                    return false;
                }
                List<Integer> Gene = newGene(ListOfChromosome.get(i).ListOfTablesS.get(j));
                ListOfChromosome.get(i).ListOfTablesS.set(j, Gene);
                this.NumberOfMutations++;
                return true;
            }
        }
        return true;
        
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
        
        if(ListOfChromosome.size()>0)
        {
          System.out.println(ListOfChromosome.get(0).cost);
          Chromosome ch = ListOfChromosome.get(0);
          ListOfTheBestChromosome.add(new Chromosome(ch.ListOfTablesS, ch.cost));  
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
      
       private String CountLambdas(List<List> solution) {
       float cost=0;
       String s="";
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
            s+="Link: " +(l+1)+"\r\n";
            s+="    liczba lambd: " +lambdasOnLink.get(l)+"\r\n";
            Float costOfLink = count(lambdasOnLink.get(l),net.getListOfLinks().get(l).getNumberOfLambdas(),net.getListOfLinks().get(l).getCostOfFibre());
            cost+=costOfLink;
            s+="    Koszt: " +costOfLink+"\r\n";
           // System.out.println("Max number of lambdas on link: " +(net.getListOfLinks().get(l).getNumberOfLambdas() * net.getListOfLinks().get(l).getNumberOfFibres()));
           
        }
       
        return s;
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
        
       
        List<List> Chromosome1k = copy(Chromosome1);
        List<List> Chromosome2k = copy(Chromosome2);
        Integer NumberOfChanges=rand.nextInt(Chromosome1k.size()-1)+1;
                 
        for(int i=0; i<NumberOfChanges; i++)
        {
            Integer IndexOfGene=rand.nextInt(Chromosome1k.size()-1);
            List<Integer> Gene1 = Chromosome1k.get(IndexOfGene);
            Chromosome1k.set(IndexOfGene, Chromosome2k.get(IndexOfGene));
            Chromosome2k.set(IndexOfGene, Gene1);
        }
        
        List<Chromosome> Children = new ArrayList();
        Children.add(new Chromosome (Chromosome1k));
        Children.add(new Chromosome(Chromosome2k));
        return Children;
    }

    private List<Integer> newGene(List Gene) {
        
       
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

    private void write() {
         System.out.println("The best solution:");
        _writeBestSolutionToConsole(ListOfChromosome.get(0));
        _writeBestSolutionTofile(file);
        System.out.println("Total cost: "+ListOfChromosome.get(0).cost);
    }

    private boolean select(int Nx) {
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
        
        if(ListOfTheBestChromosome.size()>0)
        {
           float costT = ListOfTheBestChromosome.get(ListOfTheBestChromosome.size()-1).cost;  
            if(ListOfChromosome.get(0).cost< costT)
        {
            N=0;
        } else
        {
            N++;
        }
        if(N>Nx)
        {
            return false;
        }
        }
       
        
        if(ListOfChromosome.size()>0)
        {
            System.out.println(ListOfChromosome.get(0).cost);
           Chromosome ch = ListOfChromosome.get(0);
          ListOfTheBestChromosome.add(new Chromosome(ch.ListOfTablesS, ch.cost));  
        }
        
       
        return true;
    }

    private List<List> copy(List<List> Chromosome1) {
        List<List> list = new ArrayList();
        
        for(int i=0; i<Chromosome1.size(); i++)
        {
            List list2 = new ArrayList();
            
            for(int j=0; j<Chromosome1.get(i).size(); j++)
            {
                list2.add(Chromosome1.get(i).get(j));
            }
            
            list.add(list2);
            
        }
        return list;
    }
}
