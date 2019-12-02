package javassist;

import java.lang.ref.*;
import java.io.*;
import java.net.*;

public class LoaderClassPath implements ClassPath
{
    private WeakReference clref;
    
    public LoaderClassPath(final ClassLoader a1) {
        super();
        this.clref = new WeakReference((T)a1);
    }
    
    @Override
    public String toString() {
        Object v1 = null;
        if (this.clref != null) {
            v1 = this.clref.get();
        }
        return (v1 == null) ? "<null>" : v1.toString();
    }
    
    @Override
    public InputStream openClassfile(final String a1) {
        final String v1 = a1.replace('.', '/') + ".class";
        final ClassLoader v2 = (ClassLoader)this.clref.get();
        if (v2 == null) {
            return null;
        }
        return v2.getResourceAsStream(v1);
    }
    
    @Override
    public URL find(final String a1) {
        final String v1 = a1.replace('.', '/') + ".class";
        final ClassLoader v2 = (ClassLoader)this.clref.get();
        if (v2 == null) {
            return null;
        }
        return v2.getResource(v1);
    }
    
    @Override
    public void close() {
        this.clref = null;
    }
}
