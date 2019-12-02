package javassist.bytecode.annotation;

import javassist.*;
import java.lang.reflect.*;
import javassist.bytecode.*;
import java.io.*;

public abstract class MemberValue
{
    ConstPool cp;
    char tag;
    
    MemberValue(final char a1, final ConstPool a2) {
        super();
        this.cp = a2;
        this.tag = a1;
    }
    
    abstract Object getValue(final ClassLoader p0, final ClassPool p1, final Method p2) throws ClassNotFoundException;
    
    abstract Class getType(final ClassLoader p0) throws ClassNotFoundException;
    
    static Class loadClass(final ClassLoader a2, final String v1) throws ClassNotFoundException, NoSuchClassError {
        try {
            return Class.forName(convertFromArray(v1), true, a2);
        }
        catch (LinkageError a3) {
            throw new NoSuchClassError(v1, a3);
        }
    }
    
    private static String convertFromArray(final String v-2) {
        int i = v-2.indexOf("[]");
        if (i != -1) {
            final String a1 = v-2.substring(0, i);
            final StringBuffer v1 = new StringBuffer(Descriptor.of(a1));
            while (i != -1) {
                v1.insert(0, "[");
                i = v-2.indexOf("[]", i + 1);
            }
            return v1.toString().replace('/', '.');
        }
        return v-2;
    }
    
    public abstract void accept(final MemberValueVisitor p0);
    
    public abstract void write(final AnnotationsWriter p0) throws IOException;
}
