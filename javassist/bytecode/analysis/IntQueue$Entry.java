package javassist.bytecode.analysis;

private static class Entry
{
    private Entry next;
    private int value;
    
    private Entry(final int a1) {
        super();
        this.value = a1;
    }
    
    Entry(final int a1, final IntQueue$1 a2) {
        this(a1);
    }
    
    static /* synthetic */ Entry access$102(final Entry a1, final Entry a2) {
        return a1.next = a2;
    }
    
    static /* synthetic */ int access$200(final Entry a1) {
        return a1.value;
    }
    
    static /* synthetic */ Entry access$100(final Entry a1) {
        return a1.next;
    }
}
