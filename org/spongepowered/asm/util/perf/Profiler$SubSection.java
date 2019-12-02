package org.spongepowered.asm.util.perf;

class SubSection extends LiveSection
{
    private final String baseName;
    private final Section root;
    final /* synthetic */ Profiler this$0;
    
    SubSection(final Profiler a1, final String a2, final int a3, final String a4, final Section a5) {
        this.this$0 = a1;
        a1.super(a2, a3);
        this.baseName = a4;
        this.root = a5;
    }
    
    @Override
    Section invalidate() {
        this.root.invalidate();
        return super.invalidate();
    }
    
    @Override
    public String getBaseName() {
        return this.baseName;
    }
    
    @Override
    public void setInfo(final String a1) {
        this.root.setInfo(a1);
        super.setInfo(a1);
    }
    
    @Override
    Section getDelegate() {
        return this.root;
    }
    
    @Override
    Section start() {
        this.root.start();
        return super.start();
    }
    
    @Override
    public Section end() {
        this.root.stop();
        return super.end();
    }
    
    @Override
    public Section next(final String a1) {
        super.stop();
        return this.root.next(a1);
    }
}
