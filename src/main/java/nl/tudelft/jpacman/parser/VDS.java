package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import java.util.ArrayList;
import java.util.List;

public class VDS extends ASTVisitor {
    List<VariableDeclarationStatement> vds = new ArrayList<VariableDeclarationStatement>();

    @Override
    public boolean visit(VariableDeclarationStatement node) {
        vds.add(node);
        return super.visit(node);
    }

    public List<VariableDeclarationStatement> getVDS() {
        return vds;
    }
}
