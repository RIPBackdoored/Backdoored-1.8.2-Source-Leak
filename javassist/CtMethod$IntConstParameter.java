package javassist;

import javassist.bytecode.*;

static class IntConstParameter extends ConstParameter
{
    int param;
    
    IntConstParameter(final int a1) {
        super();
        this.param = a1;
    }
    
    @Override
    int compile(final Bytecode a1) throws CannotCompileException {
        a1.addIconst(this.param);
        return 1;
    }
    
    @Override
    String descriptor() {
        return "([Ljava/lang/Object;I)Ljava/lang/Object;";
    }
    
    @Override
    String constDescriptor() {
        return "([Ljava/lang/Object;I)V";
    }
}
