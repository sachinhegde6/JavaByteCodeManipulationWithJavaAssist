package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.InfixExpression;

import java.util.ArrayList;
import java.util.List;

public class InfixOperation extends ASTVisitor {
    List<InfixExpression> infix1 = new ArrayList<InfixExpression>();

    @Override
    public boolean visit(InfixExpression node){
        infix1.add(node);
        return super.visit(node);
    }

    public List<InfixExpression> getInfix1() {
        return infix1;
    }
}
