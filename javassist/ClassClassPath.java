package javassist;

import java.io.*;
import java.net.*;

public class ClassClassPath implements ClassPath
{
    private Class thisClass;
    
    public ClassClassPath(final Class a1) {
        super();
        this.thisClass = a1;
    }
    
    ClassClassPath() {
        this(Object.class);
    }
    
    @Override
    public InputStream openClassfile(final String a1) {
        final String v1 = "/" + a1.replace('.', '/') + ".class";
        return this.thisClass.getResourceAsStream(v1);
    }
    
    @Override
    public URL find(final String a1) {
        final String v1 = "/" + a1.replace('.', '/') + ".class";
        return this.thisClass.getResource(v1);
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public String toString() {
        return this.thisClass.getName() + ".class";
    }
}
