package com.fasterxml.jackson.core.format;

public enum MatchStrength
{
    NO_MATCH, 
    INCONCLUSIVE, 
    WEAK_MATCH, 
    SOLID_MATCH, 
    FULL_MATCH;
    
    private static final /* synthetic */ MatchStrength[] $VALUES;
    
    public static MatchStrength[] values() {
        return MatchStrength.$VALUES.clone();
    }
    
    public static MatchStrength valueOf(final String a1) {
        return Enum.valueOf(MatchStrength.class, a1);
    }
    
    static {
        $VALUES = new MatchStrength[] { MatchStrength.NO_MATCH, MatchStrength.INCONCLUSIVE, MatchStrength.WEAK_MATCH, MatchStrength.SOLID_MATCH, MatchStrength.FULL_MATCH };
    }
}
