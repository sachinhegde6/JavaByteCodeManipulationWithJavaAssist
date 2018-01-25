package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.WhileStatement;

import java.util.ArrayList;
import java.util.List;

public class WhileStmt extends ASTVisitor {
    List<WhileStatement> whilestmt1 = new ArrayList<WhileStatement>();

    @Override
    public boolean visit(WhileStatement node){
        whilestmt1.add(node);
        return super.visit(node);
    }

    public List<WhileStatement> getWhilestmt1() {
        return whilestmt1;
    }
}
