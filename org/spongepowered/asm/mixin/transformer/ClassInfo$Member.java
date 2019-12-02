package org.spongepowered.asm.mixin.transformer;

abstract static class Member
{
    private final Type type;
    private final String memberName;
    private final String memberDesc;
    private final boolean isInjected;
    private final int modifiers;
    private String currentName;
    private String currentDesc;
    private boolean decoratedFinal;
    private boolean decoratedMutable;
    private boolean unique;
    
    protected Member(final Member a1) {
        this(a1.type, a1.memberName, a1.memberDesc, a1.modifiers, a1.isInjected);
        this.currentName = a1.currentName;
        this.currentDesc = a1.currentDesc;
        this.unique = a1.unique;
    }
    
    protected Member(final Type a1, final String a2, final String a3, final int a4) {
        this(a1, a2, a3, a4, false);
    }
    
    protected Member(final Type a1, final String a2, final String a3, final int a4, final boolean a5) {
        super();
        this.type = a1;
        this.memberName = a2;
        this.memberDesc = a3;
        this.isInjected = a5;
        this.currentName = a2;
        this.currentDesc = a3;
        this.modifiers = a4;
    }
    
    public String getOriginalName() {
        return this.memberName;
    }
    
    public String getName() {
        return this.currentName;
    }
    
    public String getOriginalDesc() {
        return this.memberDesc;
    }
    
    public String getDesc() {
        return this.currentDesc;
    }
    
    public boolean isInjected() {
        return this.isInjected;
    }
    
    public boolean isRenamed() {
        return !this.currentName.equals(this.memberName);
    }
    
    public boolean isRemapped() {
        return !this.currentDesc.equals(this.memberDesc);
    }
    
    public boolean isPrivate() {
        return (this.modifiers & 0x2) != 0x0;
    }
    
    public boolean isStatic() {
        return (this.modifiers & 0x8) != 0x0;
    }
    
    public boolean isAbstract() {
        return (this.modifiers & 0x400) != 0x0;
    }
    
    public boolean isFinal() {
        return (this.modifiers & 0x10) != 0x0;
    }
    
    public boolean isSynthetic() {
        return (this.modifiers & 0x1000) != 0x0;
    }
    
    public boolean isUnique() {
        return this.unique;
    }
    
    public void setUnique(final boolean a1) {
        this.unique = a1;
    }
    
    public boolean isDecoratedFinal() {
        return this.decoratedFinal;
    }
    
    public boolean isDecoratedMutable() {
        return this.decoratedMutable;
    }
    
    public void setDecoratedFinal(final boolean a1, final boolean a2) {
        this.decoratedFinal = a1;
        this.decoratedMutable = a2;
    }
    
    public boolean matchesFlags(final int a1) {
        return ((~this.modifiers | (a1 & 0x2)) & 0x2) != 0x0 && ((~this.modifiers | (a1 & 0x8)) & 0x8) != 0x0;
    }
    
    public abstract ClassInfo getOwner();
    
    public ClassInfo getImplementor() {
        return this.getOwner();
    }
    
    public int getAccess() {
        return this.modifiers;
    }
    
    public String renameTo(final String a1) {
        return this.currentName = a1;
    }
    
    public String remapTo(final String a1) {
        return this.currentDesc = a1;
    }
    
    public boolean equals(final String a1, final String a2) {
        return (this.memberName.equals(a1) || this.currentName.equals(a1)) && (this.memberDesc.equals(a2) || this.currentDesc.equals(a2));
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (!(a1 instanceof Member)) {
            return false;
        }
        final Member v1 = (Member)a1;
        return (v1.memberName.equals(this.memberName) || v1.currentName.equals(this.currentName)) && (v1.memberDesc.equals(this.memberDesc) || v1.currentDesc.equals(this.currentDesc));
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public String toString() {
        return String.format(this.getDisplayFormat(), this.memberName, this.memberDesc);
    }
    
    protected String getDisplayFormat() {
        return "%s%s";
    }
    
    enum Type
    {
        METHOD, 
        FIELD;
        
        private static final /* synthetic */ Type[] $VALUES;
        
        public static Type[] values() {
            return Type.$VALUES.clone();
        }
        
        public static Type valueOf(final String a1) {
            return Enum.valueOf(Type.class, a1);
        }
        
        static {
            $VALUES = new Type[] { Type.METHOD, Type.FIELD };
        }
    }
}
