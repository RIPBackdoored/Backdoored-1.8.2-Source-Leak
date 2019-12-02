package javassist.util.proxy;

import java.io.*;
import java.lang.reflect.*;

static class DefaultMethodHandler implements MethodHandler, Serializable
{
    DefaultMethodHandler() {
        super();
    }
    
    @Override
    public Object invoke(final Object a1, final Method a2, final Method a3, final Object[] a4) throws Exception {
        return a3.invoke(a1, a4);
    }
}
