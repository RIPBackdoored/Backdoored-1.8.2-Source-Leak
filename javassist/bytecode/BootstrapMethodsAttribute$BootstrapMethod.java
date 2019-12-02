package javassist.bytecode;

public static class BootstrapMethod
{
    public int methodRef;
    public int[] arguments;
    
    public BootstrapMethod(final int a1, final int[] a2) {
        super();
        this.methodRef = a1;
        this.arguments = a2;
    }
}
