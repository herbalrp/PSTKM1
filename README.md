# PSTKM1

**Command line guide**:

**-p**: path to file with network

**-v**: read generated population from file for evolution algorithm

**-g**: generate population to file for evolution algorithm

**-e**: (or -evolution) run evolution algorithm

# Use cases:
1. Run without any arguments
You will be asked for path to file with network. Then start() method of Start class will be executed (currently it is just a bruteforce of DDAP algorithm).

2. Run with only -p pathToFileWithNetwork
Given path will be used to parse network. Then start() method of Start class will be executed (currently it is just a bruteforce of DDAP algorithm).

3. Run with -p pathToFileWithNetwork and -g pathWherePopulationShouldBeSaved
With given network, population will be generated to pathWherePopulationShouldBeSaved. pathWherePopulationShouldBeSaved should have *.ser extention.

4. Run with -p pathToFileWithNetwork and -v pathWherePopulationIsSaved -e (or -evolution) inputParametersForEvolutionAlgorithm
With given network and population, evolution algorithm will be executed. inputParametersForEvolutionAlgorithm should be either "default" - then number of population will be set to 10 and probability of mutation will be 0.5 - or intpfloat, where int is number of population, p is divider and float is probability of mutation.

