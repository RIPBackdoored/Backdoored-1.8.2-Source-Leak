package org.spongepowered.asm.mixin.injection.callback;

public enum LocalCapture
{
    NO_CAPTURE(false, false), 
    PRINT(false, true), 
    CAPTURE_FAILSOFT, 
    CAPTURE_FAILHARD, 
    CAPTURE_FAILEXCEPTION;
    
    private final boolean captureLocals;
    private final boolean printLocals;
    private static final /* synthetic */ LocalCapture[] $VALUES;
    
    public static LocalCapture[] values() {
        return LocalCapture.$VALUES.clone();
    }
    
    public static LocalCapture valueOf(final String a1) {
        return Enum.valueOf(LocalCapture.class, a1);
    }
    
    private LocalCapture() {
        this(true, false);
    }
    
    private LocalCapture(final boolean a1, final boolean a2) {
        this.captureLocals = a1;
        this.printLocals = a2;
    }
    
    boolean isCaptureLocals() {
        return this.captureLocals;
    }
    
    boolean isPrintLocals() {
        return this.printLocals;
    }
    
    static {
        $VALUES = new LocalCapture[] { LocalCapture.NO_CAPTURE, LocalCapture.PRINT, LocalCapture.CAPTURE_FAILSOFT, LocalCapture.CAPTURE_FAILHARD, LocalCapture.CAPTURE_FAILEXCEPTION };
    }
}
