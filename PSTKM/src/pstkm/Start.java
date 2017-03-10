package pstkm;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Start {

    final Integer c_NumberOfRounds = 10000;
    Parser m_Parser = null;
    String m_PathToFile = "";

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
                // TODO: method needs to be renamed to have a name that corresponds strictly to its function
                _getFileWithData(orderParameter);
                break;
            default:
                System.out.println("Unrecognized parameters, application closed.");
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
        Net net = new Net();
        net.setListOfLinks(m_Parser.parseNetPart());
        net.setListOfDemands(m_Parser.parseDemandPart());

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
        ddap.StartBruteForce(c_NumberOfRounds);
    }

    private void _openFileWithNetwork(String path)
    {
        m_PathToFile = path;
        try
        {
            m_Parser = new Parser(m_PathToFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Parser Exception: " + e);
        }
    }

    private void _getFileWithData(String pathToFileWithData)
    {
        System.out.println("Test run of _getFileWithData. Passed value: " + pathToFileWithData);
    }
}
