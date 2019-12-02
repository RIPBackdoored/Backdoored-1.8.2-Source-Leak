package javassist.tools.rmi;

public class ObjectNotFoundException extends Exception
{
    public ObjectNotFoundException(final String a1) {
        super(a1 + " is not exported");
    }
    
    public ObjectNotFoundException(final String a1, final Exception a2) {
        super(a1 + " because of " + a2.toString());
    }
}
