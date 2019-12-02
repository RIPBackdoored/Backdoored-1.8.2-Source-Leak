package javassist.util.proxy;

import java.io.*;

public class ProxyObjectInputStream extends ObjectInputStream
{
    private ClassLoader loader;
    
    public ProxyObjectInputStream(final InputStream a1) throws IOException {
        super(a1);
        this.loader = Thread.currentThread().getContextClassLoader();
        if (this.loader == null) {
            this.loader = ClassLoader.getSystemClassLoader();
        }
    }
    
    public void setClassLoader(ClassLoader a1) {
        if (a1 != null) {
            this.loader = a1;
        }
        else {
            a1 = ClassLoader.getSystemClassLoader();
        }
    }
    
    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        final boolean boolean1 = this.readBoolean();
        if (boolean1) {
            String s = (String)this.readObject();
            final Class loadClass = this.loader.loadClass(s);
            int n = this.readInt();
            final Class[] v0 = new Class[n];
            for (int v2 = 0; v2 < n; ++v2) {
                s = (String)this.readObject();
                v0[v2] = this.loader.loadClass(s);
            }
            n = this.readInt();
            final byte[] v3 = new byte[n];
            this.read(v3);
            final ProxyFactory v4 = new ProxyFactory();
            v4.setUseCache(true);
            v4.setUseWriteReplace(false);
            v4.setSuperclass(loadClass);
            v4.setInterfaces(v0);
            final Class v5 = v4.createClass(v3);
            return ObjectStreamClass.lookup(v5);
        }
        return super.readClassDescriptor();
    }
}
