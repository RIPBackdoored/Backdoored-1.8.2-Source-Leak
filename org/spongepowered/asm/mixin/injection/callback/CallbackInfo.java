package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.*;

public class CallbackInfo implements Cancellable
{
    private final String name;
    private final boolean cancellable;
    private boolean cancelled;
    
    public CallbackInfo(final String a1, final boolean a2) {
        super();
        this.name = a1;
        this.cancellable = a2;
    }
    
    public String getId() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", this.getClass().getSimpleName(), this.name, this.cancellable);
    }
    
    @Override
    public final boolean isCancellable() {
        return this.cancellable;
    }
    
    @Override
    public final boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void cancel() throws CancellationException {
        if (!this.cancellable) {
            throw new CancellationException(String.format("The call %s is not cancellable.", this.name));
        }
        this.cancelled = true;
    }
    
    static String getCallInfoClassName() {
        return CallbackInfo.class.getName();
    }
    
    public static String getCallInfoClassName(final Type a1) {
        return (a1.equals(Type.VOID_TYPE) ? CallbackInfo.class.getName() : CallbackInfoReturnable.class.getName()).replace('.', '/');
    }
    
    static String getConstructorDescriptor(final Type a1) {
        if (a1.equals(Type.VOID_TYPE)) {
            return getConstructorDescriptor();
        }
        if (a1.getSort() == 10 || a1.getSort() == 9) {
            return String.format("(%sZ%s)V", "Ljava/lang/String;", "Ljava/lang/Object;");
        }
        return String.format("(%sZ%s)V", "Ljava/lang/String;", a1.getDescriptor());
    }
    
    static String getConstructorDescriptor() {
        return String.format("(%sZ)V", "Ljava/lang/String;");
    }
    
    static String getIsCancelledMethodName() {
        return "isCancelled";
    }
    
    static String getIsCancelledMethodSig() {
        return "()Z";
    }
}
