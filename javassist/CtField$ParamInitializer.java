package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class ParamInitializer extends Initializer
{
    int nthParam;
    
    ParamInitializer() {
        super();
    }
    
    @Override
    int compile(final CtClass a4, final String a5, final Bytecode v1, final CtClass[] v2, final Javac v3) throws CannotCompileException {
        if (v2 != null && this.nthParam < v2.length) {
            v1.addAload(0);
            final int a6 = nthParamToLocal(this.nthParam, v2, false);
            final int a7 = v1.addLoad(a6, a4) + 1;
            v1.addPutfield(Bytecode.THIS, a5, Descriptor.of(a4));
            return a7;
        }
        return 0;
    }
    
    static int nthParamToLocal(final int v1, final CtClass[] v2, final boolean v3) {
        final CtClass v4 = CtClass.longType;
        final CtClass v5 = CtClass.doubleType;
        int v6 = 0;
        if (v3) {
            final int a1 = 0;
        }
        else {
            v6 = 1;
        }
        for (final CtClass a3 : v2) {
            if (a3 == v4 || a3 == v5) {
                v6 += 2;
            }
            else {
                ++v6;
            }
        }
        return v6;
    }
    
    @Override
    int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
        return 0;
    }
}
