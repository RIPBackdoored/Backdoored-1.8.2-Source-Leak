package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class MethodInitializer extends NewInitializer
{
    String methodName;
    
    MethodInitializer() {
        super();
    }
    
    @Override
    int compile(final CtClass a3, final String a4, final Bytecode a5, final CtClass[] v1, final Javac v2) throws CannotCompileException {
        a5.addAload(0);
        a5.addAload(0);
        int v3 = 0;
        if (this.stringParams == null) {
            final int a6 = 2;
        }
        else {
            v3 = this.compileStringParameter(a5) + 2;
        }
        if (this.withConstructorParams) {
            v3 += CtNewWrappedMethod.compileParameterList(a5, v1, 1);
        }
        final String v4 = Descriptor.of(a3);
        final String v5 = this.getDescriptor() + v4;
        a5.addInvokestatic(this.objectType, this.methodName, v5);
        a5.addPutfield(Bytecode.THIS, a4, v4);
        return v3;
    }
    
    private String getDescriptor() {
        final String v1 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
        if (this.stringParams == null) {
            if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/Object;)";
            }
            return "(Ljava/lang/Object;)";
        }
        else {
            if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
            }
            return "(Ljava/lang/Object;[Ljava/lang/String;)";
        }
    }
    
    @Override
    int compileIfStatic(final CtClass a3, final String a4, final Bytecode v1, final Javac v2) throws CannotCompileException {
        int v3 = 1;
        String v4 = null;
        if (this.stringParams == null) {
            final String a5 = "()";
        }
        else {
            v4 = "([Ljava/lang/String;)";
            v3 += this.compileStringParameter(v1);
        }
        final String v5 = Descriptor.of(a3);
        v1.addInvokestatic(this.objectType, this.methodName, v4 + v5);
        v1.addPutstatic(Bytecode.THIS, a4, v5);
        return v3;
    }
}
