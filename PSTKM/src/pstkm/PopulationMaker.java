package pstkm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopulationMaker {
    List<Chromosome> m_Population;
    Net m_Network;

    public PopulationMaker() {  }

    public PopulationMaker(Net net)
    {
        this.m_Network = net;
    }

    public List<Chromosome> getPopulation(int m_NumberOfChromosomes)
    {
        m_Population = new ArrayList<Chromosome>();
        _generatePopulation(m_NumberOfChromosomes);
        return m_Population;
    }

    private void _generatePopulation(int numberOfChromosomes)
    {
        List<Chromosome> population = new ArrayList<Chromosome>();

        for (int i = 0; i < numberOfChromosomes; i++)
        {
            _generateChromosome();
        }
    }

    private void _generateChromosome()
    {
        List<List> solution = new ArrayList();

        for(int i = 0; i < m_Network.getListOfDemands().size(); i++)
        {
            int h = m_Network.getListOfDemands().get(i).getdemandVolume();
            int max = h;
            Random rand = new Random();

            List listOfConnections = m_Network.getListOfDemands().get(i).getListOfPaths();
            List<Integer> demandList = new ArrayList();

            for (int j = 0; j < listOfConnections.size(); j++)
            {
                int lambdasOnPath = rand.nextInt((max + 1));
                if(j+1 >= listOfConnections.size())
                {
                    lambdasOnPath = max;
                }

                demandList.add(lambdasOnPath);

                max = max - lambdasOnPath;
            }
            solution.add(demandList);
        }

        if (_checkSolution(solution))
        {
            Chromosome newChromosome = new Chromosome(solution);
            m_Population.add(newChromosome);
        }
        else
        {
            _generateChromosome();
        }
    }

    private boolean _checkSolution(List<List> solution)
    {
        List<Integer> lambdasOnLink = new ArrayList();

        for(int i = 0; i < m_Network.getListOfLinks().size(); i++)
        {
            lambdasOnLink.add(0);
        }

        // Loop over demands
        for(int i = 0; i< solution.size(); i++)
        {
            // Loop over lambdas of demand
            for(int j = 0; j < solution.get(i).size(); j++)
            {
                int numberOfLambdas = (int) solution.get(i).get(j);
                List indexesOfLinks = m_Network.getListOfDemands().get(i).getListOfPaths().get(j).getListOfLinks();
                for(int k = 0; k< indexesOfLinks.size(); k++)
                {
                    int index = (int)indexesOfLinks.get(k)-1;
                    //Adding lambdas to list
                    lambdasOnLink.set(index, lambdasOnLink.get(index) + numberOfLambdas);
                }
            }
        }

        for (int l = 0; l < lambdasOnLink.size(); l++)
        {
            if(lambdasOnLink.get(l) > m_Network.getListOfLinks().get(l).getNumberOfLambdas() * m_Network.getListOfLinks().get(l).getNumberOfFibres())
            {
                return false;
            }
        }
        return true;
    }

    public boolean serializePopulation(String pathToFile)
    {
        boolean isGenerated = false;

        try
        {
            FileOutputStream fileOut = new FileOutputStream(pathToFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(m_Population);
            out.close();
            fileOut.close();
            System.out.println("Data serialized correctly");
            isGenerated = true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return isGenerated;
    }

    public void deserializePopulation(String pathToFile)
    {
        m_Population = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(pathToFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            m_Population = (List<Chromosome>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Data deserialized correctly");
            System.out.println(m_Population.get(0).cost);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException c)
        {
            c.printStackTrace();
        }
    }
}
