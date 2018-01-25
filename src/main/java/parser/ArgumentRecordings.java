package parser;

import java.util.ArrayList;
import java.util.List;

public class ArgumentRecordings {
    String argumentName;
    List<ArgumentValues> argumentvalues = new ArrayList<ArgumentValues>();

   public List<ArgumentValues> getArgumentvalues() {
        return argumentvalues;
    }

    public void addArgumentvalues(ArgumentValues argumentvalues) {
        this.argumentvalues.add(argumentvalues);
    }

    public String getArgumentName() {
        return argumentName;
    }
    public void setArgumentName(String argumentName) {
        this.argumentName = argumentName;
    }

}
