package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.*;

public class CallbackInfoReturnable<R> extends CallbackInfo
{
    private R returnValue;
    
    public CallbackInfoReturnable(final String a1, final boolean a2) {
        super(a1, a2);
        this.returnValue = null;
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final R a3) {
        super(a1, a2);
        this.returnValue = a3;
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final byte a3) {
        super(a1, a2);
        this.returnValue = (R)Byte.valueOf(a3);
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final char a3) {
        super(a1, a2);
        this.returnValue = (R)Character.valueOf(a3);
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final double a3) {
        super(a1, a2);
        this.returnValue = (R)Double.valueOf(a3);
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final float a3) {
        super(a1, a2);
        this.returnValue = (R)Float.valueOf(a3);
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final int a3) {
        super(a1, a2);
        this.returnValue = (R)Integer.valueOf(a3);
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final long a3) {
        super(a1, a2);
        this.returnValue = (R)Long.valueOf(a3);
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final short a3) {
        super(a1, a2);
        this.returnValue = (R)Short.valueOf(a3);
    }
    
    public CallbackInfoReturnable(final String a1, final boolean a2, final boolean a3) {
        super(a1, a2);
        this.returnValue = (R)Boolean.valueOf(a3);
    }
    
    public void setReturnValue(final R a1) throws CancellationException {
        super.cancel();
        this.returnValue = a1;
    }
    
    public R getReturnValue() {
        return this.returnValue;
    }
    
    public byte getReturnValueB() {
        if (this.returnValue == null) {
            return 0;
        }
        return (byte)this.returnValue;
    }
    
    public char getReturnValueC() {
        if (this.returnValue == null) {
            return '\0';
        }
        return (char)this.returnValue;
    }
    
    public double getReturnValueD() {
        if (this.returnValue == null) {
            return 0.0;
        }
        return (double)this.returnValue;
    }
    
    public float getReturnValueF() {
        if (this.returnValue == null) {
            return 0.0f;
        }
        return (float)this.returnValue;
    }
    
    public int getReturnValueI() {
        if (this.returnValue == null) {
            return 0;
        }
        return (int)this.returnValue;
    }
    
    public long getReturnValueJ() {
        if (this.returnValue == null) {
            return 0L;
        }
        return (long)this.returnValue;
    }
    
    public short getReturnValueS() {
        if (this.returnValue == null) {
            return 0;
        }
        return (short)this.returnValue;
    }
    
    public boolean getReturnValueZ() {
        return this.returnValue != null && (boolean)this.returnValue;
    }
    
    static String getReturnAccessor(final Type a1) {
        if (a1.getSort() == 10 || a1.getSort() == 9) {
            return "getReturnValue";
        }
        return String.format("getReturnValue%s", a1.getDescriptor());
    }
    
    static String getReturnDescriptor(final Type a1) {
        if (a1.getSort() == 10 || a1.getSort() == 9) {
            return String.format("()%s", "Ljava/lang/Object;");
        }
        return String.format("()%s", a1.getDescriptor());
    }
}
