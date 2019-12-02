package javassist.bytecode.annotation;

public class NoSuchClassError extends Error
{
    private String className;
    
    public NoSuchClassError(final String a1, final Error a2) {
        super(a2.toString(), a2);
        this.className = a1;
    }
    
    public String getClassName() {
        return this.className;
    }
}
