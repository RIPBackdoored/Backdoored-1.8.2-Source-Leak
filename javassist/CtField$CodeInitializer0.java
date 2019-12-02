package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;

abstract static class CodeInitializer0 extends Initializer
{
    CodeInitializer0() {
        super();
    }
    
    abstract void compileExpr(final Javac p0) throws CompileError;
    
    @Override
    int compile(final CtClass a3, final String a4, final Bytecode a5, final CtClass[] v1, final Javac v2) throws CannotCompileException {
        try {
            a5.addAload(0);
            this.compileExpr(v2);
            a5.addPutfield(Bytecode.THIS, a4, Descriptor.of(a3));
            return a5.getMaxStack();
        }
        catch (CompileError a6) {
            throw new CannotCompileException(a6);
        }
    }
    
    @Override
    int compileIfStatic(final CtClass a3, final String a4, final Bytecode v1, final Javac v2) throws CannotCompileException {
        try {
            this.compileExpr(v2);
            v1.addPutstatic(Bytecode.THIS, a4, Descriptor.of(a3));
            return v1.getMaxStack();
        }
        catch (CompileError a5) {
            throw new CannotCompileException(a5);
        }
    }
    
    int getConstantValue2(final ConstPool v1, final CtClass v2, final ASTree v3) {
        if (v2.isPrimitive()) {
            if (v3 instanceof IntConst) {
                final long a1 = ((IntConst)v3).get();
                if (v2 == CtClass.doubleType) {
                    return v1.addDoubleInfo((double)a1);
                }
                if (v2 == CtClass.floatType) {
                    return v1.addFloatInfo((float)a1);
                }
                if (v2 == CtClass.longType) {
                    return v1.addLongInfo(a1);
                }
                if (v2 != CtClass.voidType) {
                    return v1.addIntegerInfo((int)a1);
                }
            }
            else if (v3 instanceof DoubleConst) {
                final double a2 = ((DoubleConst)v3).get();
                if (v2 == CtClass.floatType) {
                    return v1.addFloatInfo((float)a2);
                }
                if (v2 == CtClass.doubleType) {
                    return v1.addDoubleInfo(a2);
                }
            }
        }
        else if (v3 instanceof StringL && v2.getName().equals("java.lang.String")) {
            return v1.addStringInfo(((StringL)v3).get());
        }
        return 0;
    }
}
