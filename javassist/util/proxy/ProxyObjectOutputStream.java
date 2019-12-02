package javassist.util.proxy;

import java.io.*;

public class ProxyObjectOutputStream extends ObjectOutputStream
{
    public ProxyObjectOutputStream(final OutputStream a1) throws IOException {
        super(a1);
    }
    
    @Override
    protected void writeClassDescriptor(final ObjectStreamClass v-5) throws IOException {
        final Class forClass = v-5.forClass();
        if (ProxyFactory.isProxyClass(forClass)) {
            this.writeBoolean(true);
            final Class superclass = forClass.getSuperclass();
            final Class[] interfaces = forClass.getInterfaces();
            final byte[] filterSignature = ProxyFactory.getFilterSignature(forClass);
            String v0 = superclass.getName();
            this.writeObject(v0);
            this.writeInt(interfaces.length - 1);
            for (int v2 = 0; v2 < interfaces.length; ++v2) {
                final Class a1 = interfaces[v2];
                if (a1 != ProxyObject.class && a1 != Proxy.class) {
                    v0 = interfaces[v2].getName();
                    this.writeObject(v0);
                }
            }
            this.writeInt(filterSignature.length);
            this.write(filterSignature);
        }
        else {
            this.writeBoolean(false);
            super.writeClassDescriptor(v-5);
        }
    }
}
