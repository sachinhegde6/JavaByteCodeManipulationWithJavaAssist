package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;

import java.util.ArrayList;
import java.util.List;

public class Assert1 extends ASTVisitor {
    List<AssertStatement> assert1 = new ArrayList<AssertStatement>();

    @Override
    public boolean visit(AssertStatement node){
        assert1.add(node);
        return super.visit(node);
    }

    public List<AssertStatement> getAssert1() {
        return assert1;
    }
}
