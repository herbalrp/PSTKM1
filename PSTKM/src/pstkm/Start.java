package pstkm;

import java.io.IOException;
import java.io.File;
import java.io.InterruptedIOException;
import java.util.List;
import java.util.Scanner;

public class Start {

    final Integer c_NumberOfRounds = 10000;
    Parser m_Parser = null;
    String m_PathToFile = "";
    Net m_Network = null;
    List<Chromosome> m_Population;

    public Start(String[] args)
    {
        if (args.length > 0)
        {
            int numberOfOrders = args.length;
            for (int i = 0; i < numberOfOrders; i = i + 2)
            {
                _executeRequestFromInputParameters(args[i], args[i+1]);
            }
        }
        else
        {
            _getPathToFileFromCommandLine();
        }
    }

    private void _executeRequestFromInputParameters(String orderName, String orderParameter)
    {
        switch (orderName)
        {
            case "-p":
                _openFileWithNetwork(orderParameter);
                break;
            case "-v":
                _readPopulationFromFile(orderParameter);
                break;
            case "-g":
                System.out.println("Population will be generated in file: " + orderParameter);
                _generatePopulationToFile(orderParameter);
                break;
            case "-e":
            case "-evolution":
                _runEvolutionAlgorithm(orderParameter);
                break;
            default:
                System.out.println("Unrecognized parameters: " + orderName + ": " + orderParameter + "  Application closed.");
                System.exit(0);
        }
    }

    private void _getPathToFileFromCommandLine()
    {
        Scanner scanner = new Scanner(System.in);
        while(!(new File(m_PathToFile).isFile()))
        {
            System.out.println("Please enter path to file with network description. To terminate press [N]:");
            m_PathToFile = scanner.next();
            if (m_PathToFile.equals("N"))
            {
                System.exit(0);
            }
        }
    }

    public void start()
    {
        m_Network = new Net();
        m_Network.setListOfLinks(m_Parser.parseNetPart());
        m_Network.setListOfDemands(m_Parser.parseDemandPart());

//        // temporary printing links
//        for(Link lk : net.getListOfLinks()){
//            System.out.println(lk);
//        }
//        for(Demand lk : net.getListOfDemands()){
//            System.out.println(lk);
//        }

        //start of DAP brute force
        //DemandAP dap = new DemandAP(net);
        //dap.startBruteForce(net);
        DDAP ddap = new DDAP(m_Network);
        ddap.StartBruteForce(c_NumberOfRounds);
    }

    private void _openFileWithNetwork(String path)
    {
        m_PathToFile = path;
        try
        {
            m_Parser = new Parser(m_PathToFile);
            m_Network = new Net();
            m_Network.setListOfLinks(m_Parser.parseNetPart());
            m_Network.setListOfDemands(m_Parser.parseDemandPart());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Parser Exception: " + e);
        }
    }

    private void _readPopulationFromFile(String pathToFileWithData)
    {
        PopulationMaker populationMaker = new PopulationMaker();
        populationMaker.deserializePopulation(pathToFileWithData);
        this.m_Population = populationMaker.m_Population;
    }

    private boolean _generatePopulationToFile(String pathToFile)
    {
        boolean isGenerated = false;

        if (m_Parser == null || m_Network == null)
        {
            System.out.println("Network not initialized.");
            System.exit(0);
        }

        int sizeOfPopulation = 0;
        Scanner scanner = new Scanner(System.in);
        while (sizeOfPopulation == 0)
        {
            System.out.println("Please enter size of population to create:");
            // TODO: It is worth to consider adding some checker, I am not fluent in Java
            sizeOfPopulation = scanner.nextInt();
        }

        PopulationMaker populationMaker = new PopulationMaker(m_Network);
        populationMaker.getPopulation(sizeOfPopulation);
        if (populationMaker.serializePopulation(pathToFile))
        {
            isGenerated = true;
        }

        return isGenerated;
    }

    private void _runEvolutionAlgorithm(String inputParameters)
    {
        int numberOfPopulations = 10;
        float probabilityOfMutation = (float) 0.5;

        if (!inputParameters.equals("default"))
        {
            String[] parts = inputParameters.split("p");
            numberOfPopulations = Integer.parseInt(parts[0]);
            probabilityOfMutation = Float.parseFloat(parts[1]);
        }

        Evolution evolution = new Evolution(m_Population, m_Population.size(), numberOfPopulations, probabilityOfMutation, m_Network);
        evolution.start();
    }
}
