package parser;

public class AllStatements {
    int lineNumber;
    String statement;
    String methodName;
    AllStatements(String Statement, String methodName, int lineNumber) {
        this.statement = Statement;
        this.lineNumber = lineNumber;
        this.methodName = methodName;
    }
    public int getLineNumber() {
        return lineNumber;
    }

    public String getStatement() {
        return statement;
    }

    public String getMethodName() {
        return methodName;
    }

}
