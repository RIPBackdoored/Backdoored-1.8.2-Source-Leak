package org.spongepowered.asm.util;

class CentredText
{
    private final Object centred;
    final /* synthetic */ PrettyPrinter this$0;
    
    public CentredText(final PrettyPrinter a1, final Object a2) {
        this.this$0 = a1;
        super();
        this.centred = a2;
    }
    
    @Override
    public String toString() {
        final String v1 = this.centred.toString();
        return String.format("%" + ((this.this$0.width - v1.length()) / 2 + v1.length()) + "s", v1);
    }
}
