package javassist.tools.reflect;

import java.lang.reflect.*;

public class CannotInvokeException extends RuntimeException
{
    private Throwable err;
    
    public Throwable getReason() {
        return this.err;
    }
    
    public CannotInvokeException(final String a1) {
        super(a1);
        this.err = null;
    }
    
    public CannotInvokeException(final InvocationTargetException a1) {
        super("by " + a1.getTargetException().toString());
        this.err = null;
        this.err = a1.getTargetException();
    }
    
    public CannotInvokeException(final IllegalAccessException a1) {
        super("by " + a1.toString());
        this.err = null;
        this.err = a1;
    }
    
    public CannotInvokeException(final ClassNotFoundException a1) {
        super("by " + a1.toString());
        this.err = null;
        this.err = a1;
    }
}
