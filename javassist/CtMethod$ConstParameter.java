package javassist;

import javassist.bytecode.*;

public static class ConstParameter
{
    public static ConstParameter integer(final int a1) {
        return new IntConstParameter(a1);
    }
    
    public static ConstParameter integer(final long a1) {
        return new LongConstParameter(a1);
    }
    
    public static ConstParameter string(final String a1) {
        return new StringConstParameter(a1);
    }
    
    ConstParameter() {
        super();
    }
    
    int compile(final Bytecode a1) throws CannotCompileException {
        return 0;
    }
    
    String descriptor() {
        return defaultDescriptor();
    }
    
    static String defaultDescriptor() {
        return "([Ljava/lang/Object;)Ljava/lang/Object;";
    }
    
    String constDescriptor() {
        return defaultConstDescriptor();
    }
    
    static String defaultConstDescriptor() {
        return "([Ljava/lang/Object;)V";
    }
}
