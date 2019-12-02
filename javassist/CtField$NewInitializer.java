package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class NewInitializer extends Initializer
{
    CtClass objectType;
    String[] stringParams;
    boolean withConstructorParams;
    
    NewInitializer() {
        super();
    }
    
    @Override
    int compile(final CtClass a3, final String a4, final Bytecode a5, final CtClass[] v1, final Javac v2) throws CannotCompileException {
        a5.addAload(0);
        a5.addNew(this.objectType);
        a5.add(89);
        a5.addAload(0);
        int v3 = 0;
        if (this.stringParams == null) {
            final int a6 = 4;
        }
        else {
            v3 = this.compileStringParameter(a5) + 4;
        }
        if (this.withConstructorParams) {
            v3 += CtNewWrappedMethod.compileParameterList(a5, v1, 1);
        }
        a5.addInvokespecial(this.objectType, "<init>", this.getDescriptor());
        a5.addPutfield(Bytecode.THIS, a4, Descriptor.of(a3));
        return v3;
    }
    
    private String getDescriptor() {
        final String v1 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
        if (this.stringParams == null) {
            if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/Object;)V";
            }
            return "(Ljava/lang/Object;)V";
        }
        else {
            if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
            }
            return "(Ljava/lang/Object;[Ljava/lang/String;)V";
        }
    }
    
    @Override
    int compileIfStatic(final CtClass a3, final String a4, final Bytecode v1, final Javac v2) throws CannotCompileException {
        v1.addNew(this.objectType);
        v1.add(89);
        int v3 = 2;
        String v4 = null;
        if (this.stringParams == null) {
            final String a5 = "()V";
        }
        else {
            v4 = "([Ljava/lang/String;)V";
            v3 += this.compileStringParameter(v1);
        }
        v1.addInvokespecial(this.objectType, "<init>", v4);
        v1.addPutstatic(Bytecode.THIS, a4, Descriptor.of(a3));
        return v3;
    }
    
    protected final int compileStringParameter(final Bytecode v2) throws CannotCompileException {
        final int v3 = this.stringParams.length;
        v2.addIconst(v3);
        v2.addAnewarray("java.lang.String");
        for (int a1 = 0; a1 < v3; ++a1) {
            v2.add(89);
            v2.addIconst(a1);
            v2.addLdc(this.stringParams[a1]);
            v2.add(83);
        }
        return 4;
    }
}
