package javassist;

import java.util.*;
import java.security.*;
import java.lang.reflect.*;
import java.net.*;
import java.io.*;

public class Loader extends ClassLoader
{
    private Hashtable notDefinedHere;
    private Vector notDefinedPackages;
    private ClassPool source;
    private Translator translator;
    private ProtectionDomain domain;
    public boolean doDelegation;
    
    public Loader() {
        this((ClassPool)null);
    }
    
    public Loader(final ClassPool a1) {
        super();
        this.doDelegation = true;
        this.init(a1);
    }
    
    public Loader(final ClassLoader a1, final ClassPool a2) {
        super(a1);
        this.doDelegation = true;
        this.init(a2);
    }
    
    private void init(final ClassPool a1) {
        this.notDefinedHere = new Hashtable();
        this.notDefinedPackages = new Vector();
        this.source = a1;
        this.translator = null;
        this.domain = null;
        this.delegateLoadingOf("javassist.Loader");
    }
    
    public void delegateLoadingOf(final String a1) {
        if (a1.endsWith(".")) {
            this.notDefinedPackages.addElement(a1);
        }
        else {
            this.notDefinedHere.put(a1, this);
        }
    }
    
    public void setDomain(final ProtectionDomain a1) {
        this.domain = a1;
    }
    
    public void setClassPool(final ClassPool a1) {
        this.source = a1;
    }
    
    public void addTranslator(final ClassPool a1, final Translator a2) throws NotFoundException, CannotCompileException {
        this.source = a1;
        (this.translator = a2).start(a1);
    }
    
    public static void main(final String[] a1) throws Throwable {
        final Loader v1 = new Loader();
        v1.run(a1);
    }
    
    public void run(final String[] v-1) throws Throwable {
        final int v0 = v-1.length - 1;
        if (v0 >= 0) {
            final String[] v2 = new String[v0];
            for (int a1 = 0; a1 < v0; ++a1) {
                v2[a1] = v-1[a1 + 1];
            }
            this.run(v-1[0], v2);
        }
    }
    
    public void run(final String v1, final String[] v2) throws Throwable {
        final Class v3 = this.loadClass(v1);
        try {
            v3.getDeclaredMethod("main", String[].class).invoke(null, v2);
        }
        catch (InvocationTargetException a1) {
            throw a1.getTargetException();
        }
    }
    
    @Override
    protected Class loadClass(String v1, final boolean v2) throws ClassFormatError, ClassNotFoundException {
        v1 = v1.intern();
        synchronized (v1) {
            Class a1 = this.findLoadedClass(v1);
            if (a1 == null) {
                a1 = this.loadClassByDelegation(v1);
            }
            if (a1 == null) {
                a1 = this.findClass(v1);
            }
            if (a1 == null) {
                a1 = this.delegateToParent(v1);
            }
            if (v2) {
                this.resolveClass(a1);
            }
            return a1;
        }
    }
    
    @Override
    protected Class findClass(final String v-1) throws ClassNotFoundException {
        byte[] v3 = null;
        try {
            Label_0101: {
                if (this.source != null) {
                    if (this.translator != null) {
                        this.translator.onLoad(this.source, v-1);
                    }
                    try {
                        final byte[] a1 = this.source.get(v-1).toBytecode();
                        break Label_0101;
                    }
                    catch (NotFoundException v7) {
                        return null;
                    }
                }
                final String v1 = "/" + v-1.replace('.', '/') + ".class";
                final InputStream v2 = this.getClass().getResourceAsStream(v1);
                if (v2 == null) {
                    return null;
                }
                v3 = ClassPoolTail.readStream(v2);
            }
        }
        catch (Exception v4) {
            throw new ClassNotFoundException("caught an exception while obtaining a class file for " + v-1, v4);
        }
        final int v5 = v-1.lastIndexOf(46);
        if (v5 != -1) {
            final String v6 = v-1.substring(0, v5);
            if (this.getPackage(v6) == null) {
                try {
                    this.definePackage(v6, null, null, null, null, null, null, null);
                }
                catch (IllegalArgumentException ex) {}
            }
        }
        if (this.domain == null) {
            return this.defineClass(v-1, v3, 0, v3.length);
        }
        return this.defineClass(v-1, v3, 0, v3.length, this.domain);
    }
    
    protected Class loadClassByDelegation(final String a1) throws ClassNotFoundException {
        Class v1 = null;
        if (this.doDelegation && (a1.startsWith("java.") || a1.startsWith("javax.") || a1.startsWith("sun.") || a1.startsWith("com.sun.") || a1.startsWith("org.w3c.") || a1.startsWith("org.xml.") || this.notDelegated(a1))) {
            v1 = this.delegateToParent(a1);
        }
        return v1;
    }
    
    private boolean notDelegated(final String v2) {
        if (this.notDefinedHere.get(v2) != null) {
            return true;
        }
        for (int v3 = this.notDefinedPackages.size(), a1 = 0; a1 < v3; ++a1) {
            if (v2.startsWith(this.notDefinedPackages.elementAt(a1))) {
                return true;
            }
        }
        return false;
    }
    
    protected Class delegateToParent(final String a1) throws ClassNotFoundException {
        final ClassLoader v1 = this.getParent();
        if (v1 != null) {
            return v1.loadClass(a1);
        }
        return this.findSystemClass(a1);
    }
}
