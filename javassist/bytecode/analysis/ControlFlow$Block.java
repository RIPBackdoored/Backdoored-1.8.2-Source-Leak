package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;
import javassist.bytecode.*;
import java.util.*;

public static class Block extends BasicBlock
{
    public Object clientData;
    int index;
    MethodInfo method;
    Block[] entrances;
    
    Block(final int a1, final MethodInfo a2) {
        super(a1);
        this.clientData = null;
        this.method = a2;
    }
    
    @Override
    protected void toString2(final StringBuffer v2) {
        super.toString2(v2);
        v2.append(", incoming{");
        for (int a1 = 0; a1 < this.entrances.length; ++a1) {
            v2.append(this.entrances[a1].position).append(", ");
        }
        v2.append("}");
    }
    
    BasicBlock[] getExit() {
        return this.exit;
    }
    
    public int index() {
        return this.index;
    }
    
    public int position() {
        return this.position;
    }
    
    public int length() {
        return this.length;
    }
    
    public int incomings() {
        return this.incoming;
    }
    
    public Block incoming(final int a1) {
        return this.entrances[a1];
    }
    
    public int exits() {
        return (this.exit == null) ? 0 : this.exit.length;
    }
    
    public Block exit(final int a1) {
        return (Block)this.exit[a1];
    }
    
    public Catcher[] catchers() {
        final ArrayList v1 = new ArrayList();
        for (Catch v2 = this.toCatch; v2 != null; v2 = v2.next) {
            v1.add(new Catcher(v2));
        }
        return v1.toArray(new Catcher[v1.size()]);
    }
}
