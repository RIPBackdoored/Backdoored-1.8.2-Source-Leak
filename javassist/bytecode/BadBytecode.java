package javassist.bytecode;

public class BadBytecode extends Exception
{
    public BadBytecode(final int a1) {
        super("bytecode " + a1);
    }
    
    public BadBytecode(final String a1) {
        super(a1);
    }
    
    public BadBytecode(final String a1, final Throwable a2) {
        super(a1, a2);
    }
    
    public BadBytecode(final MethodInfo a1, final Throwable a2) {
        super(a1.toString() + " in " + a1.getConstPool().getClassName() + ": " + a2.getMessage(), a2);
    }
}
