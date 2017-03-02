package pstkm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

	String path = null;

	Parser(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			StringBuilder netPart = new StringBuilder();
			StringBuilder demandPart = new StringBuilder();
			boolean firstPart = true;
			String line = br.readLine();

			while (line != null) {
				if (firstPart) {
					if (!line.equals("-1")) {

						netPart.append(line);
						netPart.append(System.lineSeparator());
					}else
					firstPart = false;
				} else {
					demandPart.append(line);
					demandPart.append(System.lineSeparator());
				}
				line = br.readLine();
			}

			System.out.println(netPart.toString().trim());
			System.out.println(demandPart.toString().trim());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			br.close();
		}

	}

}
