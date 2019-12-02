package javassist;

import javassist.bytecode.*;
import javassist.compiler.*;
import javassist.compiler.ast.*;

static class CodeInitializer extends CodeInitializer0
{
    private String expression;
    
    CodeInitializer(final String a1) {
        super();
        this.expression = a1;
    }
    
    @Override
    void compileExpr(final Javac a1) throws CompileError {
        a1.compileExpr(this.expression);
    }
    
    @Override
    int getConstantValue(final ConstPool v2, final CtClass v3) {
        try {
            final ASTree a1 = Javac.parseExpr(this.expression, new SymbolTable());
            return this.getConstantValue2(v2, v3, a1);
        }
        catch (CompileError a2) {
            return 0;
        }
    }
}
