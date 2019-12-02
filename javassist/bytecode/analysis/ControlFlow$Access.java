package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

abstract static class Access
{
    Node[] all;
    
    Access(final Node[] a1) {
        super();
        this.all = a1;
    }
    
    Node node(final BasicBlock a1) {
        return this.all[((Block)a1).index];
    }
    
    abstract BasicBlock[] exits(final Node p0);
    
    abstract BasicBlock[] entrances(final Node p0);
}
