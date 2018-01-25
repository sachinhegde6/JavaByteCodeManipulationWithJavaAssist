package parser;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jface.text.BadLocationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class ApplicationParser2 {
    public static String Astparser(String fileName,  String method) throws IOException, BadLocationException, CoreException {
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
        File file = new File(fileName);
//        for(File file : files) {
        parser.setSource(readFileToString(new File(file.getAbsolutePath())).toCharArray());
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        cu.recordModifications();
        System.out.println(" ----------------File Name:" + file.getName() + "--------------");
        methodDeclaration md = new methodDeclaration();
        cu.accept(md);
        String methodBody = "";
        for(MethodDeclaration m :md.getMethod1()) {
            if(m.getName().equals(method)) {
                methodBody = m.getBody().toString();
            }
        }
/*            for (VariableDeclarationStatement v : vds.getVDS()) {
                System.out.println("variable declaration statement" + v.toString());
                List<VariableDeclarationFragment> vdflist = v.fragments();
                for (VariableDeclarationFragment vdf : vdflist) {
                    System.out.println(vdf.getName());

                    System.out.println(vdf.getClass());
                }    //st.add(v);
            }*/

        return methodBody;
    }
}
