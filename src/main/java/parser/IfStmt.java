package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IfStatement;

import java.util.ArrayList;
import java.util.List;

public class IfStmt extends ASTVisitor {
    List<IfStatement> ifstmt1 = new ArrayList<IfStatement>();

    @Override
    public boolean visit(IfStatement node){
        ifstmt1.add(node);
        return super.visit(node);
    }

    public List<IfStatement> getIfstmt1() {
        return ifstmt1;
    }
}
