package nl.tudelft.jpacman.parser;

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
import java.util.*;
import static org.apache.commons.io.FileUtils.*;

public class ApplicationParser {
    public static void Astparser(ArrayList<File> files) throws IOException, BadLocationException, CoreException {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);
        String unitName = "test.java";
        parser.setUnitName(unitName);
        String[] sources = {"C:\\Users\\Ashwin PC\\Downloads\\jpacman-framework\\src\\main\\java\\nl\\tudelft\\jpacman"};
        String[] classpath = {"C:\\Program Files\\Java\\jdk1.8.0_144\\src.zip"};
        parser.setEnvironment(classpath, sources, null, true);
        for(File file : files) {
            parser.setSource(readFileToString(new File(file.getAbsolutePath())).toCharArray());
            final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
            System.out.println(" ----------------File Name:" + file.getName() + "--------------");
            VDS vds = new VDS();
            SN sn = new SN();
            Expression1 es = new Expression1();
            Assignment1 as = new Assignment1();
            Assert1 as1 = new Assert1();
            MethodInvoc mi = new MethodInvoc();
            IfStmt ifst = new IfStmt();
            WhileStmt whst = new WhileStmt();
            ForStmt fst = new ForStmt();
            SwitchStmt swst = new SwitchStmt();
            ReturnStmt rst = new ReturnStmt();
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
            List<Statement> st = new ArrayList<Statement>();
            for (ExpressionStatement e : es.getExpres()) {
                st.add(e);
            }
            //for (VariableDeclarationStatement v : vds.getVDS()) {
            //    st.add(v);
            //}
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

            //cu.accept(vds); // unit2 is the compilation unit
            //System.out.println("Variables :" + vds.getVDS().size());
            ASTRewrite rewriter = ASTRewrite.create(cu.getAST());
            Block block = cu.getAST().newBlock();
            for (Statement s_p : st) {
                int line = cu.getLineNumber(s_p.getStartPosition());
                int impcounter = 0;
                for (SimpleName sn_p : sn.getSN()) {
                    if (cu.getLineNumber(sn_p.getStartPosition()) == line) {
                        impcounter += 1;
                        for (MethodInvocation mi1 : mi.getMethod1()) {
                            if (cu.getLineNumber(mi1.getStartPosition()) == line && sn_p.getIdentifier().toString().equals(mi1.getName().toString())) {
                                impcounter -= 1;
                                break;
                            }
                        }
                    }
                }
                if (impcounter > 0) {


                    //System.out.println(es_p.toString());
                    String s = "TemplateClass.instrum(" + cu.getLineNumber(s_p.getStartPosition());
                    String cl = s_p.getClass().getName().toString();

                    if (cl == "org.eclipse.jdt.core.dom.VariableDeclarationStatement") {
                        s = s + ", " + "\"Declare\"";
                    } else if (cl == "org.eclipse.jdt.core.dom.ExpressionStatement") {
                        s = s + ", " + "\"Assign\"";
                    } else if (cl == "org.eclipse.jdt.core.dom.IfStatement") {
                        s = s + ", " + "\"IF Statement\"";
                    } else if (cl == "org.eclipse.jdt.core.dom.WhileStatement") {
                        s = s + ", " + "\"While Statement\"";
                    } else if (cl == "org.eclipse.jdt.core.dom.ForStatement") {
                        s = s + ", " + "\"For Statement\"";
                    } else if (cl == "org.eclipse.jdt.core.dom.SwitchStatement") {
                        s = s + ", " + "\"Switch Statement\"";
                    }
                    int checker = 0;
                    ASTNode s_p_1 = s_p.getParent();
                    String s_p_p = s_p_1.getClass().getName().toString();
                    while (s_p_p != "org.eclipse.jdt.core.dom.MethodDeclaration") {
                        if(s_p_p.equals("org.eclipse.jdt.core.dom.TypeDeclaration")) {
                            checker = 1;
                            break;
                        }
                        s_p_1 = s_p_1.getParent();
                        s_p_p = s_p_1.getClass().getName().toString();
                    }
                    if(checker == 0) {
                        MethodDeclaration m = (MethodDeclaration) s_p_1;
                        TypeDeclaration t = (TypeDeclaration) m.getParent();
                        for (SimpleName sn_p : sn.getSN()) {
                            if (cu.getLineNumber(sn_p.getStartPosition()) == line) {
                                int counter = 0;
                                for (MethodInvocation mi1 : mi.getMethod1()) {
                                    if (cu.getLineNumber(mi1.getStartPosition()) == line && sn_p.getIdentifier().toString().equals(mi1.getName().toString())) {
                                        counter = 1;
                                        break;
                                    }
                                }
                                //if(s_p.getClass() == ExpressionStatement)
                                if (counter == 0) {
                                    s = s + ", \"" + t.getName() + "." + m.getName() + "()." + sn_p.getIdentifier() + ": \"," + sn_p.getIdentifier() + ".toString()";
                                }
                            }
                        }
                    } else {
                        TypeDeclaration t = (TypeDeclaration) s_p_1;
                        for (SimpleName sn_p : sn.getSN()) {
                            if (cu.getLineNumber(sn_p.getStartPosition()) == line) {
                                int counter = 0;
                                for (MethodInvocation mi1 : mi.getMethod1()) {
                                    if (cu.getLineNumber(mi1.getStartPosition()) == line && sn_p.getIdentifier().toString().equals(mi1.getName().toString())) {
                                        counter = 1;
                                        break;
                                    }
                                }
                                //if(s_p.getClass() == ExpressionStatement)
                                if (counter == 0) {
                                    s = s + ", \"" + t.getName() + "." + sn_p.getIdentifier() + ": \"," + sn_p.getIdentifier() + ".toString()";
                                }
                            }
                        }
                    }
                    s = s + ");";
                    TextElement siso = cu.getAST().newTextElement();
                    siso.setText(s);

                    ListRewrite lrw = rewriter.getListRewrite(s_p.getParent(), Block.STATEMENTS_PROPERTY);
                    if (cl.equals("org.eclipse.jdt.core.dom.IfStatement") || cl.equals("org.eclipse.jdt.core.dom.ForStatement")
                        || cl.equals("org.eclipse.jdt.core.dom.WhileStatement")
                        || cl.equals("org.eclipse.jdt.core.dom.SwitchStatement")) {
                        lrw.insertBefore(siso, s_p, null);

                    } else {
                        lrw.insertAfter(siso, s_p, null);
                    }

                }
            }
            IDocument document2 = new Document(readFileToString(new File(file.getAbsolutePath())));
            TextEdit edits = rewriter.rewriteAST(document2, null);
            edits.apply(document2);
            String fileValue = document2.get();
            System.out.print(document2.get());
            writeStringToFile(new File("C:\\Users\\Ashwin PC\\Desktop\\MS Materials\\"+file.getName()), fileValue);
        }

    }
}


