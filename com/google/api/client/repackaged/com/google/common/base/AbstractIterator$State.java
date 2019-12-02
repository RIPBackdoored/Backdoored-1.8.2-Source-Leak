package com.google.api.client.repackaged.com.google.common.base;

private enum State
{
    READY, 
    NOT_READY, 
    DONE, 
    FAILED;
    
    private static final /* synthetic */ State[] $VALUES;
    
    public static State[] values() {
        return State.$VALUES.clone();
    }
    
    public static State valueOf(final String a1) {
        return Enum.valueOf(State.class, a1);
    }
    
    static {
        $VALUES = new State[] { State.READY, State.NOT_READY, State.DONE, State.FAILED };
    }
}
