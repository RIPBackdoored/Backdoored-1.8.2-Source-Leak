package javassist.tools.reflect;

public class CannotCreateException extends Exception
{
    public CannotCreateException(final String a1) {
        super(a1);
    }
    
    public CannotCreateException(final Exception a1) {
        super("by " + a1.toString());
    }
}
