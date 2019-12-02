package org.spongepowered.asm.util;

class KeyValue implements IVariableWidthEntry
{
    private final String key;
    private final Object value;
    final /* synthetic */ PrettyPrinter this$0;
    
    public KeyValue(final PrettyPrinter a1, final String a2, final Object a3) {
        this.this$0 = a1;
        super();
        this.key = a2;
        this.value = a3;
    }
    
    @Override
    public String toString() {
        return String.format(this.this$0.kvFormat, this.key, this.value);
    }
    
    @Override
    public int getWidth() {
        return this.toString().length();
    }
}
