package javassist;

import javassist.bytecode.*;

static class StringConstParameter extends ConstParameter
{
    String param;
    
    StringConstParameter(final String a1) {
        super();
        this.param = a1;
    }
    
    @Override
    int compile(final Bytecode a1) throws CannotCompileException {
        a1.addLdc(this.param);
        return 1;
    }
    
    @Override
    String descriptor() {
        return "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;";
    }
    
    @Override
    String constDescriptor() {
        return "([Ljava/lang/Object;Ljava/lang/String;)V";
    }
}
