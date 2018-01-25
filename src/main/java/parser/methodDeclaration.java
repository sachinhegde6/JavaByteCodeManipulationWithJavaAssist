package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class methodDeclaration extends ASTVisitor {
    List<MethodDeclaration> method1 = new ArrayList<MethodDeclaration>();

    @Override
    public boolean visit(MethodDeclaration node){
        method1.add(node);
        return super.visit(node);
    }

    public List<MethodDeclaration> getMethod1() {
        return method1;
    }
}
