package parser;

public class ArgumentValues {

    int lineNumber;

    String typeofStatement;
    String values;

    public int getLineNumber() {
        return lineNumber;
    }

    public String getTypeofStatement() {
        return typeofStatement;
    }

    public String getValues() {
        return values;
    }
    ArgumentValues(int lineNumber, String typeofStatement, String values) {
        this.lineNumber = lineNumber;
        this.typeofStatement = typeofStatement;
        this.values = values;
    }
}
