package javassist.util.proxy;

static class Find2MethodsArgs
{
    String methodName;
    String delegatorName;
    String descriptor;
    int origIndex;
    
    Find2MethodsArgs(final String a1, final String a2, final String a3, final int a4) {
        super();
        this.methodName = a1;
        this.delegatorName = a2;
        this.descriptor = a3;
        this.origIndex = a4;
    }
}
