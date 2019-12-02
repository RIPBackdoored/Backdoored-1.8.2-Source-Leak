package org.spongepowered.asm.mixin.injection;

public enum Condition
{
    LESS_THAN_ZERO(new int[] { 155, 156 }), 
    LESS_THAN_OR_EQUAL_TO_ZERO(new int[] { 158, 157 }), 
    GREATER_THAN_OR_EQUAL_TO_ZERO(Condition.LESS_THAN_ZERO), 
    GREATER_THAN_ZERO(Condition.LESS_THAN_OR_EQUAL_TO_ZERO);
    
    private final int[] opcodes;
    private final Condition equivalence;
    private static final /* synthetic */ Condition[] $VALUES;
    
    public static Condition[] values() {
        return Condition.$VALUES.clone();
    }
    
    public static Condition valueOf(final String a1) {
        return Enum.valueOf(Condition.class, a1);
    }
    
    private Condition(final int[] a1) {
        this(null, a1);
    }
    
    private Condition(final Condition a1) {
        this(a1, a1.opcodes);
    }
    
    private Condition(final Condition a1, final int[] a2) {
        this.equivalence = ((a1 != null) ? a1 : this);
        this.opcodes = a2;
    }
    
    public Condition getEquivalentCondition() {
        return this.equivalence;
    }
    
    public int[] getOpcodes() {
        return this.opcodes;
    }
    
    static {
        $VALUES = new Condition[] { Condition.LESS_THAN_ZERO, Condition.LESS_THAN_OR_EQUAL_TO_ZERO, Condition.GREATER_THAN_OR_EQUAL_TO_ZERO, Condition.GREATER_THAN_ZERO };
    }
}
