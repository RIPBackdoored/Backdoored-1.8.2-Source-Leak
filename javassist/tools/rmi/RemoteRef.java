package javassist.tools.rmi;

import java.io.*;

public class RemoteRef implements Serializable
{
    public int oid;
    public String classname;
    
    public RemoteRef(final int a1) {
        super();
        this.oid = a1;
        this.classname = null;
    }
    
    public RemoteRef(final int a1, final String a2) {
        super();
        this.oid = a1;
        this.classname = a2;
    }
}
