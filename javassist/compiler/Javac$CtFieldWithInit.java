package javassist.compiler;

import javassist.compiler.ast.*;
import javassist.*;

public static class CtFieldWithInit extends CtField
{
    private ASTree init;
    
    CtFieldWithInit(final CtClass a1, final String a2, final CtClass a3) throws CannotCompileException {
        super(a1, a2, a3);
        this.init = null;
    }
    
    protected void setInit(final ASTree a1) {
        this.init = a1;
    }
    
    @Override
    protected ASTree getInitAST() {
        return this.init;
    }
}
