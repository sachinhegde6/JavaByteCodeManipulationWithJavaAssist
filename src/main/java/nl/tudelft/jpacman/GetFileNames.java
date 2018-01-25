package nl.tudelft.jpacman;

import nl.tudelft.jpacman.parser.ApplicationParser;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GetFileNames {
    public static ArrayList<File> listf(String directoryName) {

        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        ArrayList<File> files1;
        ArrayList<File> files = new ArrayList<File>();
        for (File file : fList) {
            if (file.isFile()) {
                if(file.getName().endsWith(".java")){
                    if(file.getName().equals("GetFileNames.java")){

                    } else {
                        files.add(file);
                    }
                }
            } else if (file.isDirectory()) {
                if(file.getName().equals("parser")) {
                } else {
                    files1 = listf(file.getAbsolutePath());
                    files.addAll(files1);
                }
            }
        }
        return files;
    }
    public static void launchParser() throws CoreException, IOException, BadLocationException {
        ArrayList<File> files;
        files = listf(".");
        System.out.println(files.size());
        ApplicationParser.Astparser(files);
    }
}
