package javassist;

import javassist.compiler.ast.*;
import javassist.compiler.*;
import javassist.bytecode.*;

public abstract static class Initializer
{
    public Initializer() {
        super();
    }
    
    public static Initializer constant(final int a1) {
        return new IntInitializer(a1);
    }
    
    public static Initializer constant(final boolean a1) {
        return new IntInitializer(a1 ? 1 : 0);
    }
    
    public static Initializer constant(final long a1) {
        return new LongInitializer(a1);
    }
    
    public static Initializer constant(final float a1) {
        return new FloatInitializer(a1);
    }
    
    public static Initializer constant(final double a1) {
        return new DoubleInitializer(a1);
    }
    
    public static Initializer constant(final String a1) {
        return new StringInitializer(a1);
    }
    
    public static Initializer byParameter(final int a1) {
        final ParamInitializer v1 = new ParamInitializer();
        v1.nthParam = a1;
        return v1;
    }
    
    public static Initializer byNew(final CtClass a1) {
        final NewInitializer v1 = new NewInitializer();
        v1.objectType = a1;
        v1.stringParams = null;
        v1.withConstructorParams = false;
        return v1;
    }
    
    public static Initializer byNew(final CtClass a1, final String[] a2) {
        final NewInitializer v1 = new NewInitializer();
        v1.objectType = a1;
        v1.stringParams = a2;
        v1.withConstructorParams = false;
        return v1;
    }
    
    public static Initializer byNewWithParams(final CtClass a1) {
        final NewInitializer v1 = new NewInitializer();
        v1.objectType = a1;
        v1.stringParams = null;
        v1.withConstructorParams = true;
        return v1;
    }
    
    public static Initializer byNewWithParams(final CtClass a1, final String[] a2) {
        final NewInitializer v1 = new NewInitializer();
        v1.objectType = a1;
        v1.stringParams = a2;
        v1.withConstructorParams = true;
        return v1;
    }
    
    public static Initializer byCall(final CtClass a1, final String a2) {
        final MethodInitializer v1 = new MethodInitializer();
        v1.objectType = a1;
        v1.methodName = a2;
        v1.stringParams = null;
        v1.withConstructorParams = false;
        return v1;
    }
    
    public static Initializer byCall(final CtClass a1, final String a2, final String[] a3) {
        final MethodInitializer v1 = new MethodInitializer();
        v1.objectType = a1;
        v1.methodName = a2;
        v1.stringParams = a3;
        v1.withConstructorParams = false;
        return v1;
    }
    
    public static Initializer byCallWithParams(final CtClass a1, final String a2) {
        final MethodInitializer v1 = new MethodInitializer();
        v1.objectType = a1;
        v1.methodName = a2;
        v1.stringParams = null;
        v1.withConstructorParams = true;
        return v1;
    }
    
    public static Initializer byCallWithParams(final CtClass a1, final String a2, final String[] a3) {
        final MethodInitializer v1 = new MethodInitializer();
        v1.objectType = a1;
        v1.methodName = a2;
        v1.stringParams = a3;
        v1.withConstructorParams = true;
        return v1;
    }
    
    public static Initializer byNewArray(final CtClass a1, final int a2) throws NotFoundException {
        return new ArrayInitializer(a1.getComponentType(), a2);
    }
    
    public static Initializer byNewArray(final CtClass a1, final int[] a2) {
        return new MultiArrayInitializer(a1, a2);
    }
    
    public static Initializer byExpr(final String a1) {
        return new CodeInitializer(a1);
    }
    
    static Initializer byExpr(final ASTree a1) {
        return new PtreeInitializer(a1);
    }
    
    void check(final String a1) throws CannotCompileException {
    }
    
    abstract int compile(final CtClass p0, final String p1, final Bytecode p2, final CtClass[] p3, final Javac p4) throws CannotCompileException;
    
    abstract int compileIfStatic(final CtClass p0, final String p1, final Bytecode p2, final Javac p3) throws CannotCompileException;
    
    int getConstantValue(final ConstPool a1, final CtClass a2) {
        return 0;
    }
}
