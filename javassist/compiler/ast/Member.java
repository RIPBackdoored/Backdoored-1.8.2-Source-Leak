package javassist.compiler.ast;

import javassist.*;
import javassist.compiler.*;

public class Member extends Symbol
{
    private CtField field;
    
    public Member(final String a1) {
        super(a1);
        this.field = null;
    }
    
    public void setField(final CtField a1) {
        this.field = a1;
    }
    
    public CtField getField() {
        return this.field;
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atMember(this);
    }
}
