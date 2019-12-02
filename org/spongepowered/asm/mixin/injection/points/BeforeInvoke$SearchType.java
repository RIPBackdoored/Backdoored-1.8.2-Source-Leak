package org.spongepowered.asm.mixin.injection.points;

public enum SearchType
{
    STRICT, 
    PERMISSIVE;
    
    private static final /* synthetic */ SearchType[] $VALUES;
    
    public static SearchType[] values() {
        return SearchType.$VALUES.clone();
    }
    
    public static SearchType valueOf(final String a1) {
        return Enum.valueOf(SearchType.class, a1);
    }
    
    static {
        $VALUES = new SearchType[] { SearchType.STRICT, SearchType.PERMISSIVE };
    }
}
