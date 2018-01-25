package parser;

public class StringList {
    String statement;
    String methodName;
    StringList(String Statement, String methodName) {
        this.statement = Statement;
        this.methodName = methodName;
    }

    public String getStatement() {
        return statement;
    }

    public String getMethodName() {
        return methodName;
    }

}
