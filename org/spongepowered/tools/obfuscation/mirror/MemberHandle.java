package org.spongepowered.tools.obfuscation.mirror;

public abstract class MemberHandle<T extends java.lang.Object>
{
    private final String owner;
    private final String name;
    private final String desc;
    
    protected MemberHandle(final String a1, final String a2, final String a3) {
        super();
        this.owner = a1;
        this.name = a2;
        this.desc = a3;
    }
    
    public final String getOwner() {
        return this.owner;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final String getDesc() {
        return this.desc;
    }
    
    public abstract Visibility getVisibility();
    
    public abstract T asMapping(final boolean p0);
}
