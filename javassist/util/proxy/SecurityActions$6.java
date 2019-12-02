package javassist.util.proxy;

import java.security.*;
import java.lang.reflect.*;

static final class SecurityActions$6 implements PrivilegedExceptionAction {
    final /* synthetic */ Field val$fld;
    final /* synthetic */ Object val$target;
    final /* synthetic */ Object val$value;
    
    SecurityActions$6(final Field val$fld, final Object val$target, final Object val$value) {
        this.val$fld = val$fld;
        this.val$target = val$target;
        this.val$value = val$value;
        super();
    }
    
    @Override
    public Object run() throws Exception {
        this.val$fld.set(this.val$target, this.val$value);
        return null;
    }
}