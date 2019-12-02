package javassist;

public class NotFoundException extends Exception
{
    public NotFoundException(final String a1) {
        super(a1);
    }
    
    public NotFoundException(final String a1, final Exception a2) {
        super(a1 + " because of " + a2.toString());
    }
}
