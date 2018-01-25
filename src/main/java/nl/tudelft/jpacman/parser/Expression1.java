package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;

import java.util.ArrayList;
import java.util.List;

public class Expression1 extends ASTVisitor {
    List<ExpressionStatement> expres = new ArrayList<ExpressionStatement>();

    @Override
    public boolean visit(ExpressionStatement node){
        expres.add(node);
        return super.visit(node);
    }

    public List<ExpressionStatement> getExpres() {
        return expres;
    }
}
