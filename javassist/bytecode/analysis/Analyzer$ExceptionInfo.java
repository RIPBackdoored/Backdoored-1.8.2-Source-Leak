package javassist.bytecode.analysis;

private static class ExceptionInfo
{
    private int end;
    private int handler;
    private int start;
    private Type type;
    
    private ExceptionInfo(final int a1, final int a2, final int a3, final Type a4) {
        super();
        this.start = a1;
        this.end = a2;
        this.handler = a3;
        this.type = a4;
    }
    
    ExceptionInfo(final int a1, final int a2, final int a3, final Type a4, final Analyzer$1 a5) {
        this(a1, a2, a3, a4);
    }
    
    static /* synthetic */ int access$100(final ExceptionInfo a1) {
        return a1.start;
    }
    
    static /* synthetic */ int access$200(final ExceptionInfo a1) {
        return a1.end;
    }
    
    static /* synthetic */ Type access$300(final ExceptionInfo a1) {
        return a1.type;
    }
    
    static /* synthetic */ int access$400(final ExceptionInfo a1) {
        return a1.handler;
    }
}
