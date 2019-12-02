package javassist.compiler;

import javassist.bytecode.*;

protected abstract static class ReturnHook
{
    ReturnHook next;
    
    protected abstract boolean doit(final Bytecode p0, final int p1);
    
    protected ReturnHook(final CodeGen a1) {
        super();
        this.next = a1.returnHooks;
        a1.returnHooks = this;
    }
    
    protected void remove(final CodeGen a1) {
        a1.returnHooks = this.next;
    }
}
