package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;

import java.util.ArrayList;
import java.util.List;

public class FieldAccess1 extends ASTVisitor {
    List<FieldAccess> fa = new ArrayList<FieldAccess>();

    @Override
    public boolean visit(FieldAccess node) {
        fa.add(node);
        return super.visit(node);
    }

    public List<FieldAccess> getFa() {
        return fa;
    }
}
