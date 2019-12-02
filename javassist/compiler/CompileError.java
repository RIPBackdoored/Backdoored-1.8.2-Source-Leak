package javassist.compiler;

import javassist.*;

public class CompileError extends Exception
{
    private Lex lex;
    private String reason;
    
    public CompileError(final String a1, final Lex a2) {
        super();
        this.reason = a1;
        this.lex = a2;
    }
    
    public CompileError(final String a1) {
        super();
        this.reason = a1;
        this.lex = null;
    }
    
    public CompileError(final CannotCompileException a1) {
        this(a1.getReason());
    }
    
    public CompileError(final NotFoundException a1) {
        this("cannot find " + a1.getMessage());
    }
    
    public Lex getLex() {
        return this.lex;
    }
    
    @Override
    public String getMessage() {
        return this.reason;
    }
    
    @Override
    public String toString() {
        return "compile error: " + this.reason;
    }
}
