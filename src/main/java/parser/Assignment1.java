package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;

import java.util.ArrayList;
import java.util.List;

public class Assignment1 extends ASTVisitor {
    List<Assignment> assign = new ArrayList<Assignment>();

    @Override
    public boolean visit(Assignment node){
        assign.add(node);
        return super.visit(node);
    }

    public List<Assignment> getAssign() {
        return assign;
    }
}
