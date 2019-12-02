package javassist.compiler.ast;

import javassist.compiler.*;

public class FieldDecl extends ASTList
{
    public FieldDecl(final ASTree a1, final ASTList a2) {
        super(a1, a2);
    }
    
    public ASTList getModifiers() {
        return (ASTList)this.getLeft();
    }
    
    public Declarator getDeclarator() {
        return (Declarator)this.tail().head();
    }
    
    public ASTree getInit() {
        return this.sublist(2).head();
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atFieldDecl(this);
    }
}
