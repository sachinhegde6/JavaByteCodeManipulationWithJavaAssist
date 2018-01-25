package parser;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class Statement1 extends ASTVisitor {
    List<Statement> st = new ArrayList<Statement>();

    public boolean visit(Statement node) {
        st.add(node);
        super.visit((AssertStatement) node);
        super.visit((ForStatement) node);
        super.visit((VariableDeclarationStatement) node);
        return true;
    }

    public List<Statement> getSt() {
        return st;
    }
}
