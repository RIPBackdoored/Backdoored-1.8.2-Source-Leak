package javassist.util.proxy;

import java.security.*;

class SerializedProxy$1 implements PrivilegedExceptionAction {
    final /* synthetic */ String val$className;
    final /* synthetic */ SerializedProxy this$0;
    
    SerializedProxy$1(final SerializedProxy a1, final String val$className) {
        this.this$0 = a1;
        this.val$className = val$className;
        super();
    }
    
    @Override
    public Object run() throws Exception {
        final ClassLoader v1 = Thread.currentThread().getContextClassLoader();
        return Class.forName(this.val$className, true, v1);
    }
}