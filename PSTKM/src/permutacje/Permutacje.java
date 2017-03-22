package permutacje;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lukasz
 */
public class Permutacje {
  
    static int liczbaKomorek1 = 3;
    static int zadanaWartosc1 = 3;
    
    static int liczbaKomorek2 = 3;
    static int zadanaWartosc2 = 4
            ;
    static List rozwiazania = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //net4 na sztywno
//        List listaPermutacji = stworzPopulacjeZapotrzebowania(3, 3);
//        List listaPermutacji2 = stworzPopulacjeZapotrzebowania(3, 4);
//        List listaPermutacji3 = stworzPopulacjeZapotrzebowania(2, 5);
//        List listaPermutacji4 = stworzPopulacjeZapotrzebowania(3, 4);
//        List listaPermutacji5 = stworzPopulacjeZapotrzebowania(3, 3);
//        List listaPermutacji6 = stworzPopulacjeZapotrzebowania(3, 4);
//        List<List> Lists = new ArrayList();
//        Lists.add(listaPermutacji);
//        Lists.add(listaPermutacji2);
//        Lists.add(listaPermutacji3);
//        Lists.add(listaPermutacji4);
//        Lists.add(listaPermutacji5);
//        Lists.add(listaPermutacji6);
        //////////////////////////////////////
        //net12 na sztywno - sciezki/zapotrzebowanie
        List listaPermutacji = stworzPopulacjeZapotrzebowania(4, 18);
        List listaPermutacji2 = stworzPopulacjeZapotrzebowania(3, 17);
        List listaPermutacji3 = stworzPopulacjeZapotrzebowania(4, 16);
//        List listaPermutacji4 = stworzPopulacjeZapotrzebowania(4, 10);
//        List listaPermutacji5 = stworzPopulacjeZapotrzebowania(4, 17);
//        List listaPermutacji6 = stworzPopulacjeZapotrzebowania(4, 20);
        List<List> Lists = new ArrayList();
        Lists.add(listaPermutacji);
        Lists.add(listaPermutacji2);
        Lists.add(listaPermutacji3);
//        Lists.add(listaPermutacji4);
//        Lists.add(listaPermutacji5);
//        Lists.add(listaPermutacji6);
        
        List result = new ArrayList();
        String current = "";
        GeneratePermutations(Lists, result, 0, current);
        for(int i = 0; i<result.size(); i++) {
            System.out.println(result.get(i));
        }
        System.out.println(result.size());
    }

    
    public static List stworzPopulacjeZapotrzebowania(int liczbaSiezek, int zadanaWartosc) {
        List<List> listaListSum = new ArrayList<>();
        List<String> str = getPossibleInt(zadanaWartosc, liczbaSiezek);
        for(int i = 0; i<str.size(); i++) {
            List <String> listaSum = new ArrayList<String>();
            String doPerm = (String)str.get(i).replaceAll("\\s+",",");
            
            String[] doPermArray = doPerm.split(",");
            for(int l = 1; l<=doPermArray.length - 1; l++) {
                listaSum.add(doPermArray[l]);
            }
            if(listaSum.size() < liczbaSiezek) {
                int ilePowtorzen = liczbaSiezek - listaSum.size();

                for(int k = 0; k<ilePowtorzen; k++) {
                    listaSum.add("0");
                }
            }
            if(listaSum.size() <= liczbaSiezek)
            listaListSum.add(listaSum);
        }
        List listaPermutacji = new ArrayList();
        for(int j = 0; j<listaListSum.size(); j++) {

            int[] inputArray = new int[liczbaSiezek];
            
            for(int k = 0; k< listaListSum.get(j).size(); k++) {
                if(listaListSum.get(j).size() <= liczbaSiezek) {
                    inputArray[k] = Integer.valueOf((String) listaListSum.get(j).get(k));
                }    
            }
            permute(0, inputArray, listaPermutacji);
        }
        
        return listaPermutacji;
    }
    
    public static void GeneratePermutations(List<List> Lists, List result, int depth, String current)
{
    if(depth == Lists.size())
    {
       result.add(current);
       return;
     }

    for(int i = 0; i < Lists.get(depth).size(); ++i)
    {
        GeneratePermutations(Lists, result, depth + 1, current + Lists.get(depth).get(i));
    }
}
    
public  static void permute(int start, int[] input, List listaPermutacji ) {
        if (start == input.length) {
            String toList = "";
            for(int x: input){
                toList = toList + x + ",";           
        }
            toList = toList + ";";
            if(!listaPermutacji.contains(toList)) {
                    listaPermutacji.add(toList);
                }
        return;
    }
    for (int i = start; i < input.length; i++) {
        int temp = input[i];
        input[i] = input[start];
        input[start] = temp;

        permute(start + 1, input, listaPermutacji);

        int temp2 = input[i];
        input[i] = input[start];
        input[start] = temp2;
    }
}
    
    private static List getPossibleInt(int zadanaWartosc, int liczbaSciezek) {
        List strList = new ArrayList();
        partition(zadanaWartosc, strList, liczbaSciezek);
        
        return strList;
    }
    
     public static void partition(int n, List strList, int liczbaSciezek) {
        partition(n, n, "", strList, liczbaSciezek);
    }
    public static void partition(int n, int max, String prefix, List strList, int liczbaSciezek) {
        if (n == 0) {
            if(whiteSpaces(prefix) <= liczbaSciezek)           
            strList.add(prefix);
            return;
        }

        for (int i = Math.min(max, n); i >= 1; i--) {
            partition(n-i, i, prefix + " " + i, strList, liczbaSciezek);
        }
    }
    
    public static int whiteSpaces(String str) {
        int spaceCount = 0;
        for (char c : str.toCharArray()) {
            if (c == ' ') {
                spaceCount++;
            }
        }
        return spaceCount;
    }
}