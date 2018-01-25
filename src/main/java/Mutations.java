import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LineNumberAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import parser.AllStatements;
import parser.ApplicationParser;
import parser.ApplicationParser2;

import java.io.IOException;
import java.util.List;

import static java.lang.Boolean.TRUE;

public class Mutations {
    public synchronized void changeModifier(CtClass cc) {
        CtMethod[] methods = cc.getDeclaredMethods();
        for (CtMethod ctm : methods) {
            ctm.setModifiers(Modifier.PUBLIC);

        }
    }

    public synchronized void removesuper(CtClass cc) {

        CtConstructor[] constructors = cc.getDeclaredConstructors();
        final int[] line = new int[1];
        for (CtConstructor ctc : constructors) {
            try {
                ctc.instrument(new ExprEditor() {
                    public void edit(ConstructorCall c) throws CannotCompileException {
                        if (c.isSuper()) {
                            line[0] = c.getLineNumber();
                            c.replace("{}");
                        }
                    }
                });
                CodeAttribute codeAttribute = ctc.getMethodInfo().getCodeAttribute();
                LineNumberAttribute lineNumberAttribute = (LineNumberAttribute)codeAttribute.getAttribute(LineNumberAttribute.tag);
                int startPc = lineNumberAttribute.toStartPc(line[0]);
                int endPc = lineNumberAttribute.toStartPc(line[0]+1);
                byte[] code = codeAttribute.getCode();
                for (int k = startPc; k < endPc; k++) {
                    code[k] = CodeAttribute.NOP;
                }

            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized void arithmeticOperation(CtClass cc, CtClass c11) throws CoreException, IOException, BadLocationException, CannotCompileException, NotFoundException, InterruptedException {


        CtMethod[] methods = cc.getDeclaredMethods();
        final Object wait = new Object();
        for (CtMethod ctm : methods) {
            //CtMethod ctm = cc.getDeclaredMethod("invariant");
            String methodName = ctm.getName();
            CtMethod ctm1 = c11.getDeclaredMethod(methodName);
            System.out.println(ctm.toString());
            System.out.println(ctm1.toString());
            while(cc.isFrozen()== TRUE) {
                synchronized (wait) {
                    wait.wait(2);
                }
            }
            ctm.setBody(ctm1, null);
        }
    }

    public synchronized void changevariableasparent(CtClass cc) throws CannotCompileException {
        CtMethod[] methods = cc.getDeclaredMethods();
        for (CtMethod ctm : methods) {
            System.out.println("Method name" + ctm.getName());

            MethodInfo methodInfo = ctm.getMethodInfo();
            System.out.println(methodInfo.getCodeAttribute());
        }
    }

}

