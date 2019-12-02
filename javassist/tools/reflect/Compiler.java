package javassist.tools.reflect;

import javassist.*;
import java.io.*;

public class Compiler
{
    public Compiler() {
        super();
    }
    
    public static void main(final String[] a1) throws Exception {
        if (a1.length == 0) {
            help(System.err);
            return;
        }
        final CompiledClass[] v1 = new CompiledClass[a1.length];
        final int v2 = parse(a1, v1);
        if (v2 < 1) {
            System.err.println("bad parameter.");
            return;
        }
        processClasses(v1, v2);
    }
    
    private static void processClasses(final CompiledClass[] v-5, final int v-4) throws Exception {
        final Reflection reflection = new Reflection();
        final ClassPool default1 = ClassPool.getDefault();
        reflection.start(default1);
        for (int i = 0; i < v-4; ++i) {
            final CtClass v0 = default1.get(v-5[i].classname);
            if (v-5[i].metaobject != null || v-5[i].classobject != null) {
                String v2 = null;
                if (v-5[i].metaobject == null) {
                    final String a1 = "javassist.tools.reflect.Metaobject";
                }
                else {
                    v2 = v-5[i].metaobject;
                }
                String v3 = null;
                if (v-5[i].classobject == null) {
                    final String a2 = "javassist.tools.reflect.ClassMetaobject";
                }
                else {
                    v3 = v-5[i].classobject;
                }
                if (!reflection.makeReflective(v0, default1.get(v2), default1.get(v3))) {
                    System.err.println("Warning: " + v0.getName() + " is reflective.  It was not changed.");
                }
                System.err.println(v0.getName() + ": " + v2 + ", " + v3);
            }
            else {
                System.err.println(v0.getName() + ": not reflective");
            }
        }
        for (int i = 0; i < v-4; ++i) {
            reflection.onLoad(default1, v-5[i].classname);
            default1.get(v-5[i].classname).writeFile();
        }
    }
    
    private static int parse(final String[] v-2, final CompiledClass[] v-1) {
        int v0 = -1;
        for (int v2 = 0; v2 < v-2.length; ++v2) {
            final String a2 = v-2[v2];
            if (a2.equals("-m")) {
                if (v0 < 0 || v2 + 1 > v-2.length) {
                    return -1;
                }
                v-1[v0].metaobject = v-2[++v2];
            }
            else if (a2.equals("-c")) {
                if (v0 < 0 || v2 + 1 > v-2.length) {
                    return -1;
                }
                v-1[v0].classobject = v-2[++v2];
            }
            else {
                if (a2.charAt(0) == '-') {
                    return -1;
                }
                final CompiledClass a3 = new CompiledClass();
                a3.classname = a2;
                a3.metaobject = null;
                a3.classobject = null;
                v-1[++v0] = a3;
            }
        }
        return v0 + 1;
    }
    
    private static void help(final PrintStream a1) {
        a1.println("Usage: java javassist.tools.reflect.Compiler");
        a1.println("            (<class> [-m <metaobject>] [-c <class metaobject>])+");
    }
}
