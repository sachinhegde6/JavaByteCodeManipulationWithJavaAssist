package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;

import java.util.ArrayList;
import java.util.List;

public class SN extends ASTVisitor {
    List<SimpleName> sn = new ArrayList<SimpleName>();

    @Override
    public boolean visit(SimpleName node) {
        sn.add(node);
        return super.visit(node);
    }

    public List<SimpleName> getSN() {
        return sn;
    }
}
