package org.spongepowered.asm.mixin.injection.invoke;

class Meta
{
    public static final String KEY = "redirector";
    final int priority;
    final boolean isFinal;
    final String name;
    final String desc;
    final /* synthetic */ RedirectInjector this$0;
    
    public Meta(final RedirectInjector a1, final int a2, final boolean a3, final String a4, final String a5) {
        this.this$0 = a1;
        super();
        this.priority = a2;
        this.isFinal = a3;
        this.name = a4;
        this.desc = a5;
    }
    
    RedirectInjector getOwner() {
        return this.this$0;
    }
}
