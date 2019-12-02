package javassist.runtime;

public class DotClass
{
    public DotClass() {
        super();
    }
    
    public static NoClassDefFoundError fail(final ClassNotFoundException a1) {
        return new NoClassDefFoundError(a1.getMessage());
    }
}
