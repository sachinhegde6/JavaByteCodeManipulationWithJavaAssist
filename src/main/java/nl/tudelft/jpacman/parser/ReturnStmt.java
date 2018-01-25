package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ReturnStatement;

import java.util.ArrayList;
import java.util.List;

public class ReturnStmt extends ASTVisitor {
    List<ReturnStatement> returnstmt1 = new ArrayList<ReturnStatement>();

    @Override
    public boolean visit(ReturnStatement node){
        returnstmt1.add(node);
        return super.visit(node);
    }

    public List<ReturnStatement> getReturnstmt1() {
        return returnstmt1;
    }
}
