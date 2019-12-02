package javassist.compiler;

import javassist.compiler.ast.*;

public class NoFieldException extends CompileError
{
    private String fieldName;
    private ASTree expr;
    
    public NoFieldException(final String a1, final ASTree a2) {
        super("no such field: " + a1);
        this.fieldName = a1;
        this.expr = a2;
    }
    
    public String getField() {
        return this.fieldName;
    }
    
    public ASTree getExpr() {
        return this.expr;
    }
}
