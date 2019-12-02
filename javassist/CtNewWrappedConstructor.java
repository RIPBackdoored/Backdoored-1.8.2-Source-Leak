package javassist;

import javassist.bytecode.*;

class CtNewWrappedConstructor extends CtNewWrappedMethod
{
    private static final int PASS_NONE = 0;
    private static final int PASS_PARAMS = 2;
    
    CtNewWrappedConstructor() {
        super();
    }
    
    public static CtConstructor wrapped(final CtClass[] a4, final CtClass[] a5, final int a6, final CtMethod v1, final CtMethod.ConstParameter v2, final CtClass v3) throws CannotCompileException {
        try {
            final CtConstructor a7 = new CtConstructor(a4, v3);
            a7.setExceptionTypes(a5);
            final Bytecode a8 = makeBody(v3, v3.getClassFile2(), a6, v1, a4, v2);
            a7.getMethodInfo2().setCodeAttribute(a8.toCodeAttribute());
            return a7;
        }
        catch (NotFoundException a9) {
            throw new CannotCompileException(a9);
        }
    }
    
    protected static Bytecode makeBody(final CtClass v-6, final ClassFile v-5, final int v-4, final CtMethod v-3, final CtClass[] v-2, final CtMethod.ConstParameter v-1) throws CannotCompileException {
        final int v2 = v-5.getSuperclassId();
        final Bytecode v3 = new Bytecode(v-5.getConstPool(), 0, 0);
        v3.setMaxLocals(false, v-2, 0);
        v3.addAload(0);
        int v4 = 0;
        if (v-4 == 0) {
            final int a1 = 1;
            v3.addInvokespecial(v2, "<init>", "()V");
        }
        else if (v-4 == 2) {
            final int a2 = v3.addLoadParameters(v-2, 1) + 1;
            v3.addInvokespecial(v2, "<init>", Descriptor.ofConstructor(v-2));
        }
        else {
            v4 = CtNewWrappedMethod.compileParameterList(v3, v-2, 1);
            int a5 = 0;
            String a6 = null;
            if (v-1 == null) {
                final int a3 = 2;
                final String a4 = CtMethod.ConstParameter.defaultConstDescriptor();
            }
            else {
                a5 = v-1.compile(v3) + 2;
                a6 = v-1.constDescriptor();
            }
            if (v4 < a5) {
                v4 = a5;
            }
            v3.addInvokespecial(v2, "<init>", a6);
        }
        if (v-3 == null) {
            v3.add(177);
        }
        else {
            final int v5 = CtNewWrappedMethod.makeBody0(v-6, v-5, v-3, false, v-2, CtClass.voidType, v-1, v3);
            if (v4 < v5) {
                v4 = v5;
            }
        }
        v3.setMaxStack(v4);
        return v3;
    }
}
