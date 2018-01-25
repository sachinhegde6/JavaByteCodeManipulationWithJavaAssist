package nl.tudelft.jpacman.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SwitchStatement;

import java.util.ArrayList;
import java.util.List;

public class SwitchStmt extends ASTVisitor {
    List<SwitchStatement> switchstmt1 = new ArrayList<SwitchStatement>();

    @Override
    public boolean visit(SwitchStatement node){
        switchstmt1.add(node);
        return super.visit(node);
    }

    public List<SwitchStatement> getSwitchstmt1() {
        return switchstmt1;
    }
}
