package javassist.tools.rmi;

public class RemoteException extends RuntimeException
{
    public RemoteException(final String a1) {
        super(a1);
    }
    
    public RemoteException(final Exception a1) {
        super("by " + a1.toString());
    }
}
