import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.writeStringToFile;

public class Comparator
{
    public static void main(String[] args) throws IOException
    {

        BufferedReader reader1 = new BufferedReader(new FileReader(".\\.trace.txt"));
        BufferedReader reader2 = new BufferedReader(new FileReader(".\\MutatedTrace.txt"));
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        line1 = reader1.readLine();
        line2 = reader2.readLine();
        boolean areEqual = true;
        int lineNum = 2;
        while (line1 != null || line2 != null)
        {
            if(line1 == null || line2 == null)
            {
                areEqual = false;

                break;
            }
            else if(! line1.equalsIgnoreCase(line2))
            {
                areEqual = false;

                break;
            }

            line1 = reader1.readLine();

            line2 = reader2.readLine();

            lineNum+=1;
        }

        if(areEqual)
        {
            File tracewithVariables = new File(".\\" + "traceComparison.txt");
            writeStringToFile(tracewithVariables, "Two files have same content.");
            System.out.println("Two files have same content.");
        }
        else
        {
            String msg = "Two files have different content. They differ at line "+lineNum+"\n";
            msg+= "File1 has "+line1+"\nFile2 has "+line2+" at line "+lineNum;
            File tracewithVariables = new File(".\\" + "traceComparison.txt");
            writeStringToFile(tracewithVariables, msg);
            System.out.println(msg);
        }
        reader1.close();
        reader2.close();
    }
}
