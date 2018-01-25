package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ForStatement;

import java.util.ArrayList;
import java.util.List;

public class ForStmt extends ASTVisitor {
    List<ForStatement> forst1 = new ArrayList<ForStatement>();

    @Override
    public boolean visit(ForStatement node){
        forst1.add(node);
        return super.visit(node);
    }

    public List<ForStatement> getForst1() {
        return forst1;
    }
}
