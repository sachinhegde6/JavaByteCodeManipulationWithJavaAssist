package nl.tudelft.jpacman;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

public class JavaAssistEditor {
    public static void main(String args[]) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("C:\\Users\\Ashwin PC\\Desktop\\new-1\\jpacman-framework\\out\\production\\classes\\nl\\tudelft\\jpacman");
        CtClass cc = pool.get("nl.tudelft.jpacman.GetFileNames");
        System.out.println(cc.toString());
        /*cc.setSuperclass(pool.get("test.Point"));
        try {
              cc.writeFile();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }*/

    }
}
