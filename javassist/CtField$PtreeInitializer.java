package javassist;

import javassist.compiler.ast.*;
import javassist.compiler.*;
import javassist.bytecode.*;

static class PtreeInitializer extends CodeInitializer0
{
    private ASTree expression;
    
    PtreeInitializer(final ASTree a1) {
        super();
        this.expression = a1;
    }
    
    @Override
    void compileExpr(final Javac a1) throws CompileError {
        a1.compileExpr(this.expression);
    }
    
    @Override
    int getConstantValue(final ConstPool a1, final CtClass a2) {
        return this.getConstantValue2(a1, a2, this.expression);
    }
}
