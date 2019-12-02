package javassist.util.proxy;

import java.lang.ref.*;

static class ProxyDetails
{
    byte[] signature;
    WeakReference proxyClass;
    boolean isUseWriteReplace;
    
    ProxyDetails(final byte[] a1, final Class a2, final boolean a3) {
        super();
        this.signature = a1;
        this.proxyClass = new WeakReference((T)a2);
        this.isUseWriteReplace = a3;
    }
}
