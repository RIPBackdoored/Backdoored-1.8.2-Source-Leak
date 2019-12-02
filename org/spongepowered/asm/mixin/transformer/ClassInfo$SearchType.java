package org.spongepowered.asm.mixin.transformer;

public enum SearchType
{
    ALL_CLASSES, 
    SUPER_CLASSES_ONLY;
    
    private static final /* synthetic */ SearchType[] $VALUES;
    
    public static SearchType[] values() {
        return SearchType.$VALUES.clone();
    }
    
    public static SearchType valueOf(final String a1) {
        return Enum.valueOf(SearchType.class, a1);
    }
    
    static {
        $VALUES = new SearchType[] { SearchType.ALL_CLASSES, SearchType.SUPER_CLASSES_ONLY };
    }
}
