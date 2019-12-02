package javassist.compiler.ast;

import javassist.compiler.*;

public class StringL extends ASTree
{
    protected String text;
    
    public StringL(final String a1) {
        super();
        this.text = a1;
    }
    
    public String get() {
        return this.text;
    }
    
    @Override
    public String toString() {
        return "\"" + this.text + "\"";
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atStringL(this);
    }
}
