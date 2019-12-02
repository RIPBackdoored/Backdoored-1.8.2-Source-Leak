package javassist.tools.rmi;

public class Sample
{
    private ObjectImporter importer;
    private int objectId;
    
    public Sample() {
        super();
    }
    
    public Object forward(final Object[] a1, final int a2) {
        return this.importer.call(this.objectId, a2, a1);
    }
    
    public static Object forwardStatic(final Object[] a1, final int a2) throws RemoteException {
        throw new RemoteException("cannot call a static method.");
    }
}
