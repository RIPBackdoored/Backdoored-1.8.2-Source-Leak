package javassist;

import javassist.bytecode.*;

static class LongConstParameter extends ConstParameter
{
    long param;
    
    LongConstParameter(final long a1) {
        super();
        this.param = a1;
    }
    
    @Override
    int compile(final Bytecode a1) throws CannotCompileException {
        a1.addLconst(this.param);
        return 2;
    }
    
    @Override
    String descriptor() {
        return "([Ljava/lang/Object;J)Ljava/lang/Object;";
    }
    
    @Override
    String constDescriptor() {
        return "([Ljava/lang/Object;J)V";
    }
}
