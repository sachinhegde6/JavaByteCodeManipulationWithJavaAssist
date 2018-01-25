package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

import java.util.ArrayList;
import java.util.List;

public class MethodInvoc extends ASTVisitor {
    List<MethodInvocation> method1 = new ArrayList<MethodInvocation>();

    @Override
    public boolean visit(MethodInvocation node){
        method1.add(node);
        return super.visit(node);
    }

    public List<MethodInvocation> getMethod1() {
        return method1;
    }
}
