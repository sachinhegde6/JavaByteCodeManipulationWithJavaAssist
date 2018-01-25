
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class csvReader {

    public static List<Configuration> readcsv()    {

        String csvFile = "./src/main/resources/input.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<Configuration> config = new ArrayList<Configuration>();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                Configuration c = new Configuration();
                c.setClassfile(values[0]);
                c.setJavafile(values[1]);
                config.add(c);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}