package javassist.compiler;

import java.util.*;
import javassist.compiler.ast.*;

public final class SymbolTable extends HashMap
{
    private SymbolTable parent;
    
    public SymbolTable() {
        this(null);
    }
    
    public SymbolTable(final SymbolTable a1) {
        super();
        this.parent = a1;
    }
    
    public SymbolTable getParent() {
        return this.parent;
    }
    
    public Declarator lookup(final String a1) {
        final Declarator v1 = this.get(a1);
        if (v1 == null && this.parent != null) {
            return this.parent.lookup(a1);
        }
        return v1;
    }
    
    public void append(final String a1, final Declarator a2) {
        this.put(a1, a2);
    }
}
