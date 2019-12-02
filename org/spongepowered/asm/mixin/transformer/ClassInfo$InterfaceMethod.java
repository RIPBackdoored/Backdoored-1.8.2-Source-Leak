package org.spongepowered.asm.mixin.transformer;

public class InterfaceMethod extends Method
{
    private final ClassInfo owner;
    final /* synthetic */ ClassInfo this$0;
    
    public InterfaceMethod(final ClassInfo a1, final Member a2) {
        this.this$0 = a1;
        a1.super(a2);
        this.owner = a2.getOwner();
    }
    
    @Override
    public ClassInfo getOwner() {
        return this.owner;
    }
    
    @Override
    public ClassInfo getImplementor() {
        return this.this$0;
    }
}
