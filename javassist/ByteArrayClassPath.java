package javassist;

import java.io.*;
import java.net.*;

public class ByteArrayClassPath implements ClassPath
{
    protected String classname;
    protected byte[] classfile;
    
    public ByteArrayClassPath(final String a1, final byte[] a2) {
        super();
        this.classname = a1;
        this.classfile = a2;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public String toString() {
        return "byte[]:" + this.classname;
    }
    
    @Override
    public InputStream openClassfile(final String a1) {
        if (this.classname.equals(a1)) {
            return new ByteArrayInputStream(this.classfile);
        }
        return null;
    }
    
    @Override
    public URL find(final String v2) {
        if (this.classname.equals(v2)) {
            final String a1 = v2.replace('.', '/') + ".class";
            try {
                return new URL("file:/ByteArrayClassPath/" + a1);
            }
            catch (MalformedURLException ex) {}
        }
        return null;
    }
}
