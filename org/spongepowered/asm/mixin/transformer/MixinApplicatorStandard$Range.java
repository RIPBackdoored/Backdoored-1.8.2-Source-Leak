package org.spongepowered.asm.mixin.transformer;

class Range
{
    final int start;
    final int end;
    final int marker;
    final /* synthetic */ MixinApplicatorStandard this$0;
    
    Range(final MixinApplicatorStandard a1, final int a2, final int a3, final int a4) {
        this.this$0 = a1;
        super();
        this.start = a2;
        this.end = a3;
        this.marker = a4;
    }
    
    boolean isValid() {
        return this.start != 0 && this.end != 0 && this.end >= this.start;
    }
    
    boolean contains(final int a1) {
        return a1 >= this.start && a1 <= this.end;
    }
    
    boolean excludes(final int a1) {
        return a1 < this.start || a1 > this.end;
    }
    
    @Override
    public String toString() {
        return String.format("Range[%d-%d,%d,valid=%s)", this.start, this.end, this.marker, this.isValid());
    }
}
