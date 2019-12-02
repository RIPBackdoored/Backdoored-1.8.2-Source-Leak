package org.spongepowered.asm.lib;

public final class Handle
{
    final int tag;
    final String owner;
    final String name;
    final String desc;
    final boolean itf;
    
    @Deprecated
    public Handle(final int a1, final String a2, final String a3, final String a4) {
        this(a1, a2, a3, a4, a1 == 9);
    }
    
    public Handle(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        super();
        this.tag = a1;
        this.owner = a2;
        this.name = a3;
        this.desc = a4;
        this.itf = a5;
    }
    
    public int getTag() {
        return this.tag;
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public boolean isInterface() {
        return this.itf;
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (!(a1 instanceof Handle)) {
            return false;
        }
        final Handle v1 = (Handle)a1;
        return this.tag == v1.tag && this.itf == v1.itf && this.owner.equals(v1.owner) && this.name.equals(v1.name) && this.desc.equals(v1.desc);
    }
    
    @Override
    public int hashCode() {
        return this.tag + (this.itf ? 64 : 0) + this.owner.hashCode() * this.name.hashCode() * this.desc.hashCode();
    }
    
    @Override
    public String toString() {
        return this.owner + '.' + this.name + this.desc + " (" + this.tag + (this.itf ? " itf" : "") + ')';
    }
}
