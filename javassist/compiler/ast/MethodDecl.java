package javassist.compiler.ast;

import javassist.compiler.*;

public class MethodDecl extends ASTList
{
    public static final String initName = "<init>";
    
    public MethodDecl(final ASTree a1, final ASTList a2) {
        super(a1, a2);
    }
    
    public boolean isConstructor() {
        final Symbol v1 = this.getReturn().getVariable();
        return v1 != null && "<init>".equals(v1.get());
    }
    
    public ASTList getModifiers() {
        return (ASTList)this.getLeft();
    }
    
    public Declarator getReturn() {
        return (Declarator)this.tail().head();
    }
    
    public ASTList getParams() {
        return (ASTList)this.sublist(2).head();
    }
    
    public ASTList getThrows() {
        return (ASTList)this.sublist(3).head();
    }
    
    public Stmnt getBody() {
        return (Stmnt)this.sublist(4).head();
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atMethodDecl(this);
    }
}
