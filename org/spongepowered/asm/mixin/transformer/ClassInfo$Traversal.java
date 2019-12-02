package org.spongepowered.asm.mixin.transformer;

public enum Traversal
{
    NONE((Traversal)null, false, SearchType.SUPER_CLASSES_ONLY), 
    ALL((Traversal)null, true, SearchType.ALL_CLASSES), 
    IMMEDIATE(Traversal.NONE, true, SearchType.SUPER_CLASSES_ONLY), 
    SUPER(Traversal.ALL, false, SearchType.SUPER_CLASSES_ONLY);
    
    private final Traversal next;
    private final boolean traverse;
    private final SearchType searchType;
    private static final /* synthetic */ Traversal[] $VALUES;
    
    public static Traversal[] values() {
        return Traversal.$VALUES.clone();
    }
    
    public static Traversal valueOf(final String a1) {
        return Enum.valueOf(Traversal.class, a1);
    }
    
    private Traversal(final Traversal a1, final boolean a2, final SearchType a3) {
        this.next = ((a1 != null) ? a1 : this);
        this.traverse = a2;
        this.searchType = a3;
    }
    
    public Traversal next() {
        return this.next;
    }
    
    public boolean canTraverse() {
        return this.traverse;
    }
    
    public SearchType getSearchType() {
        return this.searchType;
    }
    
    static {
        $VALUES = new Traversal[] { Traversal.NONE, Traversal.ALL, Traversal.IMMEDIATE, Traversal.SUPER };
    }
}
