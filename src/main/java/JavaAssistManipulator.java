import javassist.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import parser.AllStatements;
import parser.ApplicationParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Boolean.TRUE;

public class JavaAssistManipulator {

    public static void main(String args[]) throws BadLocationException, CoreException, CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        try {
            List<Configuration> config = csvReader.readcsv();
            List<CtClass> cclist = new ArrayList<CtClass>();
            final Object wait = new Object();
            pool.insertClassPath(".\\src\\main\\resources\\original\\jpacman-framework-7.0.0-all.jar");

            for ( Configuration c : config) {
                ApplicationParser.Astparser(".\\src\\main\\java\\" + c.getJavafile(), c.getJavafile());
            }
            // ---------------------
                // ----- 1. Encapsulation
                //AMC – Access modifier change:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            // Implementing Access Modifier Mutation for each file
                            for (Configuration c : config) {
                                try {
                                    CtClass cc = null;
                                    cc = pool.get(c.getClassfile());
                                    // If file is frozen then wait till file is available
                                    while(cc.isFrozen()== TRUE) {
                                        wait(2);
                                    }
                                    CtClass cc1 = pool.get(c.getClassfile());
                                    cc.getClassPool().importPackage("nl.tudelft.jpacman.parser.TemplateClass");
                                    // Creating instance of mutation class and calling function change modifier
                                    Mutations m = new Mutations();
                                    m.changeModifier(cc);
                                    // Writing back the mutated byte code to the class file
                                    cc.writeFile("./random");
                                    // Defrost the class for other mutations to use
                                    cc.defrost();
                                } catch (NotFoundException e1) {
                                    e1.printStackTrace();
                                } catch (CannotCompileException e1) {
                                    e1.printStackTrace();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                }).start();
                // ----- 2.Polymorphism
                // IPC – Explicit call of a parent’s constructor deletion:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Implementing explicit call of parent's constructor deletion
                        for (Configuration c : config) {
                            try {
                                CtClass cc = null;
                                cc = pool.get(c.getClassfile());
                                // If file is frozen then wait till file is available
                                while(cc.isFrozen()== TRUE) {
                                    wait(2);
                                }
                                CtClass cc1 = pool.get(c.getClassfile());
                                cc.getClassPool().importPackage("nl.tudelft.jpacman.parser.TemplateClass");
                                Mutations m = new Mutations();
                                m.removesuper(cc);
                                // Write mutated byte code to the class file
                                cc.writeFile("./random");
                                // Defrost the class for other mutations to use
                                cc.defrost();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (CannotCompileException e) {
                                e.printStackTrace();
                            } catch (NotFoundException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                // ----- Implementing
                //  3. Arithmetic Operator Mutation
                //  4. Relational Operator Mutation
                //  5. Logical Operator Mutation
                //  6. Conditional Operator Mutation
                //  7. Shift Operator Mutation
                //  8. Assignment Operator Mutation
                // using AST Parser and Java Assist
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Implementing explicit call of parent's constructor deletion
                        for (Configuration c : config) {
                            try {
                                CtClass cc = null;
                                cc = pool.get(c.getClassfile());
                                // If file is frozen then wait till file is available
                                while(cc.isFrozen()== TRUE) {
                                    synchronized (wait) {
                                        wait.wait(2);
                                    }
                                }
                                CtClass cc1 = pool.get(c.getClassfile());
                                cc.getClassPool().importPackage("nl.tudelft.jpacman.parser.TemplateClass");
                                Mutations m = new Mutations();
                                m.arithmeticOperation(cc, cc1);
                                // Write mutated byte code to the class file
                                cc.writeFile("./random");
                                // Defrost the class for other mutations to use
                                cc.defrost();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            } catch (CoreException e) {
                                e.printStackTrace();
                            } catch (CannotCompileException e) {
                                e.printStackTrace();
                            } catch (NotFoundException e) {
                                e.printStackTrace();
                            } //catch (InterruptedException e) {
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // e.printStackTrace();
                            //}
                        }
                    }
                }).start();
            } catch(NotFoundException e){
                e.printStackTrace();
            } /*catch(CannotCompileException e){
                e.printStackTrace();
            }*/ catch (IOException e) {
            e.printStackTrace();
        }
    }
}
