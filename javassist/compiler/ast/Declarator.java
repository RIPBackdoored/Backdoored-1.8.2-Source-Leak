package javassist.compiler.ast;

import javassist.compiler.*;

public class Declarator extends ASTList implements TokenId
{
    protected int varType;
    protected int arrayDim;
    protected int localVar;
    protected String qualifiedClass;
    
    public Declarator(final int a1, final int a2) {
        super(null);
        this.varType = a1;
        this.arrayDim = a2;
        this.localVar = -1;
        this.qualifiedClass = null;
    }
    
    public Declarator(final ASTList a1, final int a2) {
        super(null);
        this.varType = 307;
        this.arrayDim = a2;
        this.localVar = -1;
        this.qualifiedClass = astToClassName(a1, '/');
    }
    
    public Declarator(final int a1, final String a2, final int a3, final int a4, final Symbol a5) {
        super(null);
        this.varType = a1;
        this.arrayDim = a3;
        this.localVar = a4;
        this.qualifiedClass = a2;
        this.setLeft(a5);
        ASTList.append(this, null);
    }
    
    public Declarator make(final Symbol a1, final int a2, final ASTree a3) {
        final Declarator v1 = new Declarator(this.varType, this.arrayDim + a2);
        v1.qualifiedClass = this.qualifiedClass;
        v1.setLeft(a1);
        ASTList.append(v1, a3);
        return v1;
    }
    
    public int getType() {
        return this.varType;
    }
    
    public int getArrayDim() {
        return this.arrayDim;
    }
    
    public void addArrayDim(final int a1) {
        this.arrayDim += a1;
    }
    
    public String getClassName() {
        return this.qualifiedClass;
    }
    
    public void setClassName(final String a1) {
        this.qualifiedClass = a1;
    }
    
    public Symbol getVariable() {
        return (Symbol)this.getLeft();
    }
    
    public void setVariable(final Symbol a1) {
        this.setLeft(a1);
    }
    
    public ASTree getInitializer() {
        final ASTList v1 = this.tail();
        if (v1 != null) {
            return v1.head();
        }
        return null;
    }
    
    public void setLocalVar(final int a1) {
        this.localVar = a1;
    }
    
    public int getLocalVar() {
        return this.localVar;
    }
    
    public String getTag() {
        return "decl";
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atDeclarator(this);
    }
    
    public static String astToClassName(final ASTList a1, final char a2) {
        if (a1 == null) {
            return null;
        }
        final StringBuffer v1 = new StringBuffer();
        astToClassName(v1, a1, a2);
        return v1.toString();
    }
    
    private static void astToClassName(final StringBuffer a2, ASTList a3, final char v1) {
        while (true) {
            final ASTree a4 = a3.head();
            if (a4 instanceof Symbol) {
                a2.append(((Symbol)a4).get());
            }
            else if (a4 instanceof ASTList) {
                astToClassName(a2, (ASTList)a4, v1);
            }
            a3 = a3.tail();
            if (a3 == null) {
                break;
            }
            a2.append(v1);
        }
    }
}
