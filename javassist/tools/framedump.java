package javassist.tools;

import javassist.bytecode.analysis.*;
import javassist.*;

public class framedump
{
    private framedump() {
        super();
    }
    
    public static void main(final String[] a1) throws Exception {
        if (a1.length != 1) {
            System.err.println("Usage: java javassist.tools.framedump <fully-qualified class name>");
            return;
        }
        final ClassPool v1 = ClassPool.getDefault();
        final CtClass v2 = v1.get(a1[0]);
        System.out.println("Frame Dump of " + v2.getName() + ":");
        FramePrinter.print(v2, System.out);
    }
}
