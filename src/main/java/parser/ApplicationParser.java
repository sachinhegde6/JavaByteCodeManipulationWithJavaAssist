package parser;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.FileUtils.*;

public class ApplicationParser {
    public static List<AllStatements> Astparser(String fileName, String file1) throws IOException, BadLocationException, CoreException {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);
        String unitName = "test.java";
        parser.setUnitName(unitName);
        String[] sources = {".\\src"};
        String[] classpath = {"C:\\Program Files\\Java\\jdk1.8.0_144\\src.zip"};
        parser.setEnvironment(classpath, sources, null, true);
        File file = new File(fileName);
        parser.setSource(readFileToString(new File(file.getAbsolutePath())).toCharArray());
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        cu.recordModifications();
        System.out.println(" ----------------File Name:" + file.getName() + "--------------");
        VDS vds = new VDS();
        SN sn = new SN();
        Expression1 es = new Expression1();
        Assignment1 as = new Assignment1();
        Assert1 as1 = new Assert1();
        MethodInvoc mi = new MethodInvoc();
        IfStmt ifst = new IfStmt();
        ForStmt fst = new ForStmt();
        WhileStmt whst = new WhileStmt();
        SwitchStmt swst = new SwitchStmt();
        ReturnStmt rst = new ReturnStmt();
        InfixOperation ino = new InfixOperation();
        cu.accept(ino);
        cu.accept(es);
        cu.accept(vds);
        cu.accept(sn);
        cu.accept(as);
        cu.accept(as1);
        cu.accept(mi);
        cu.accept(ifst);
        cu.accept(fst);
        cu.accept(whst);
        cu.accept(swst);
        cu.accept(rst);
        List<Integer> lineNumbers = new ArrayList<Integer>();
        for (Assignment node : as.getAssign()) {
            if (node.getOperator().equals(Assignment.Operator.toOperator("+="))) {
                node.setOperator(Assignment.Operator.toOperator("-="));
            } else if (node.getOperator().equals(Assignment.Operator.toOperator("-="))) {
                node.setOperator(Assignment.Operator.toOperator("+="));
            } else if (node.getOperator().equals(Assignment.Operator.toOperator("-"))) {
                node.setOperator(Assignment.Operator.toOperator("+"));
            } else if (node.getOperator().equals(Assignment.Operator.toOperator("+"))) {
                node.setOperator(Assignment.Operator.toOperator("-"));
            } else if (node.getOperator().equals(Assignment.Operator.toOperator("/"))) {
                node.setOperator(Assignment.Operator.toOperator("*"));
            } else if (node.getOperator().equals(Assignment.Operator.toOperator("*"))) {
                node.setOperator(Assignment.Operator.toOperator("/"));

            }
        }
        for (InfixExpression node : ino.getInfix1()) {
            if (node.getOperator().equals(InfixExpression.Operator.toOperator(">="))) {
                node.setOperator(InfixExpression.Operator.toOperator("<="));
            } else if (node.getOperator().equals(InfixExpression.Operator.toOperator("<="))) {
                node.setOperator(InfixExpression.Operator.toOperator(">="));
            } else if (node.getOperator().equals(InfixExpression.Operator.toOperator("=="))) {
                node.setOperator(InfixExpression.Operator.toOperator("!="));
            } else if (node.getOperator().equals(InfixExpression.Operator.toOperator("<"))) {
                node.setOperator(InfixExpression.Operator.toOperator(">"));
            } else if (node.getOperator().equals(InfixExpression.Operator.toOperator(">"))) {
                node.setOperator(InfixExpression.Operator.toOperator("<"));
            } else if (node.getOperator().equals(InfixExpression.Operator.toOperator("&&"))) {
                node.setOperator(InfixExpression.Operator.toOperator("||"));
            } else if (node.getOperator().equals(InfixExpression.Operator.toOperator("||"))) {
                node.setOperator(InfixExpression.Operator.toOperator("&&"));
            }

            if (lineNumbers.contains(cu.getLineNumber(node.getStartPosition()))) {
            } else {
                lineNumbers.add(cu.getLineNumber(node.getStartPosition()));
            }
        }

        List<Statement> st = new ArrayList<Statement>();
        List<AllStatements> ast = new ArrayList<AllStatements>();
        for (ExpressionStatement e : es.getExpres()) {
            st.add(e);
        }
        for (VariableDeclarationStatement v : vds.getVDS()) {
            st.add(v);
        }
        for (IfStatement v : ifst.getIfstmt1()) {
            st.add(v);
        }
        for (ForStatement v : fst.getForst1()) {
            st.add(v);
        }
        for (WhileStatement v : whst.getWhilestmt1()) {
            st.add(v);
        }
        for (SwitchStatement v : swst.getSwitchstmt1()) {
            st.add(v);
        }
        for (ReturnStatement v : rst.getReturnstmt1()) {
            st.add(v);
        }

        for (Statement s_p : st) {
            int line = cu.getLineNumber(s_p.getStartPosition());
            if (lineNumbers.contains(line)) {
                int checker = 0;
                ASTNode s_p_1 = s_p.getParent();
                ASTNode s_p_2;
                String s_p_p = s_p_1.getClass().getName().toString();
                while (s_p_p != "org.eclipse.jdt.core.dom.MethodDeclaration") {
                    if (s_p_p.equals("org.eclipse.jdt.core.dom.TypeDeclaration")) {
                        checker = 1;
                        break;
                    }
                    s_p_2 = s_p_1;
                    s_p_1 = s_p_1.getParent();
                    s_p_p = s_p_1.getClass().getName().toString();
                }
                if (checker == 0) {
                    MethodDeclaration m = (MethodDeclaration) s_p_1;
                    AllStatements check = new AllStatements(s_p.toString(), m.getName().toString(), line);
                    ast.add(check);
                }
            }
        }

        for (AllStatements check : ast) {
            System.out.println(check.getLineNumber() + "  " + check.getMethodName() + " " + check.getStatement());
        }
        IDocument document2 = new Document(readFileToString(new File(file.getAbsolutePath())));
        TextEdit edits = cu.rewrite(document2, null);
        edits.apply(document2);
        String fileValue = document2.get();
        String newSource = document2.get();

        System.out.print(document2.get());

        writeStringToFile(new File(".\\src\\main\\java\\" + file1), fileValue);
        return ast;
    }
}




