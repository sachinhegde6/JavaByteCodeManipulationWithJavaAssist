package nl.tudelft.jpacman.parser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.io.FileUtils.writeStringToFile;

public class TemplateClass {
    public static String totalTrace;
    public static List<ArgumentRecordings> arg = null;

    public static void instrum(int lineNumber, String typeOfStatement, String... args) {
        if(totalTrace == null ){
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            totalTrace =  "File last written on" + df.format(dateobj);
        }
        totalTrace += "\nLine Number: "+lineNumber+" TypeofStatement: "+typeOfStatement+" Parameters: ";
        int arg_len = args.length;
        int i = 0;
        while(i < arg_len) {
            totalTrace += args[i] + ":";
            totalTrace += args[i+1] + ", ";
            traceTable(lineNumber, typeOfStatement, args[i], args[i+1]);
            i = i + 2;
        }
    }
    public static void printTrace() throws IOException {
        File trace = new File("." + "trace.txt");
        writeStringToFile(trace, totalTrace);
        printelementTable();
        //System.out.println("Printing Trace" + totalTrace);
    }
    public static void printelementTable() throws IOException {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String printelementTrace = "File last written on" + df.format(dateobj);
        for(ArgumentRecordings args:arg ) {
            printelementTrace += "\n-------------------------" + args.getArgumentName() + "---------------------------------";
            for(ArgumentValues argvalues :args.getArgumentvalues()) {
                printelementTrace += "\nUsed at LineNumber:" + argvalues.getLineNumber();
                printelementTrace += "\nStatement Type:" + argvalues.getTypeofStatement();
                printelementTrace += "\nValue:" + argvalues.getValues();
            }
        }
        File tracewithVariables = new File("." + "traceWithVariables_bonus.txt");
        writeStringToFile(tracewithVariables, printelementTrace);
    }
    public static void traceTable(int lineNumber, String typeOfStatement, String argument, String argumentValue) {
        ArgumentValues argumentValues = new ArgumentValues(lineNumber,typeOfStatement,argumentValue);
        if(arg == null) {
            ArgumentRecordings args = new ArgumentRecordings();
            args.setArgumentName(argument);
            args.addArgumentvalues(argumentValues);
            arg = new ArrayList<ArgumentRecordings>();
            arg.add(args);
        } else {
            int counter = 0;
            for(ArgumentRecordings allarg : arg) {
                if(allarg.getArgumentName() == argument) {
                    allarg.addArgumentvalues(argumentValues);
                    counter +=1;
                    break;
                }
            }
            if(counter == 0) {
                ArgumentRecordings args = new ArgumentRecordings();
                args.setArgumentName(argument);
                args.addArgumentvalues(argumentValues);
                arg.add(args);
            }
        }
    }
}
