package javassist.tools.rmi;

import java.applet.*;
import java.net.*;
import java.lang.reflect.*;
import java.io.*;

public class ObjectImporter implements Serializable
{
    private final byte[] endofline;
    private String servername;
    private String orgServername;
    private int port;
    private int orgPort;
    protected byte[] lookupCommand;
    protected byte[] rmiCommand;
    private static final Class[] proxyConstructorParamTypes;
    
    public ObjectImporter(final Applet a1) {
        super();
        this.endofline = new byte[] { 13, 10 };
        this.lookupCommand = "POST /lookup HTTP/1.0".getBytes();
        this.rmiCommand = "POST /rmi HTTP/1.0".getBytes();
        final URL v1 = a1.getCodeBase();
        final String host = v1.getHost();
        this.servername = host;
        this.orgServername = host;
        final int port = v1.getPort();
        this.port = port;
        this.orgPort = port;
    }
    
    public ObjectImporter(final String a1, final int a2) {
        super();
        this.endofline = new byte[] { 13, 10 };
        this.lookupCommand = "POST /lookup HTTP/1.0".getBytes();
        this.rmiCommand = "POST /rmi HTTP/1.0".getBytes();
        this.servername = a1;
        this.orgServername = a1;
        this.port = a2;
        this.orgPort = a2;
    }
    
    public Object getObject(final String v2) {
        try {
            return this.lookupObject(v2);
        }
        catch (ObjectNotFoundException a1) {
            return null;
        }
    }
    
    public void setHttpProxy(final String a1, final int a2) {
        final String v1 = "POST http://" + this.orgServername + ":" + this.orgPort;
        String v2 = v1 + "/lookup HTTP/1.0";
        this.lookupCommand = v2.getBytes();
        v2 = v1 + "/rmi HTTP/1.0";
        this.rmiCommand = v2.getBytes();
        this.servername = a1;
        this.port = a2;
    }
    
    public Object lookupObject(final String v-1) throws ObjectNotFoundException {
        try {
            final Socket a1 = new Socket(this.servername, this.port);
            final OutputStream v1 = a1.getOutputStream();
            v1.write(this.lookupCommand);
            v1.write(this.endofline);
            v1.write(this.endofline);
            final ObjectOutputStream v2 = new ObjectOutputStream(v1);
            v2.writeUTF(v-1);
            v2.flush();
            final InputStream v3 = new BufferedInputStream(a1.getInputStream());
            this.skipHeader(v3);
            final ObjectInputStream v4 = new ObjectInputStream(v3);
            final int v5 = v4.readInt();
            final String v6 = v4.readUTF();
            v4.close();
            v2.close();
            a1.close();
            if (v5 >= 0) {
                return this.createProxy(v5, v6);
            }
        }
        catch (Exception v7) {
            v7.printStackTrace();
            throw new ObjectNotFoundException(v-1, v7);
        }
        throw new ObjectNotFoundException(v-1);
    }
    
    private Object createProxy(final int a1, final String a2) throws Exception {
        final Class v1 = Class.forName(a2);
        final Constructor v2 = v1.getConstructor((Class[])ObjectImporter.proxyConstructorParamTypes);
        return v2.newInstance(this, new Integer(a1));
    }
    
    public Object call(final int v-7, final int v-6, final Object[] v-5) throws RemoteException {
        boolean boolean1;
        Object o;
        String utf;
        try {
            final Socket a2 = new Socket(this.servername, this.port);
            final OutputStream a3 = new BufferedOutputStream(a2.getOutputStream());
            a3.write(this.rmiCommand);
            a3.write(this.endofline);
            a3.write(this.endofline);
            final ObjectOutputStream v1 = new ObjectOutputStream(a3);
            v1.writeInt(v-7);
            v1.writeInt(v-6);
            this.writeParameters(v1, v-5);
            v1.flush();
            final InputStream v2 = new BufferedInputStream(a2.getInputStream());
            this.skipHeader(v2);
            final ObjectInputStream v3 = new ObjectInputStream(v2);
            boolean1 = v3.readBoolean();
            o = null;
            utf = null;
            if (boolean1) {
                o = v3.readObject();
            }
            else {
                utf = v3.readUTF();
            }
            v3.close();
            v1.close();
            a2.close();
            if (o instanceof RemoteRef) {
                final RemoteRef a4 = (RemoteRef)o;
                o = this.createProxy(a4.oid, a4.classname);
            }
        }
        catch (ClassNotFoundException a5) {
            throw new RemoteException(a5);
        }
        catch (IOException a6) {
            throw new RemoteException(a6);
        }
        catch (Exception a7) {
            throw new RemoteException(a7);
        }
        if (boolean1) {
            return o;
        }
        throw new RemoteException(utf);
    }
    
    private void skipHeader(final InputStream v2) throws IOException {
        int v3;
        do {
            v3 = 0;
            int a1;
            while ((a1 = v2.read()) >= 0 && a1 != 13) {
                ++v3;
            }
            v2.read();
        } while (v3 > 0);
    }
    
    private void writeParameters(final ObjectOutputStream v2, final Object[] v3) throws IOException {
        final int v4 = v3.length;
        v2.writeInt(v4);
        for (int a2 = 0; a2 < v4; ++a2) {
            if (v3[a2] instanceof Proxy) {
                final Proxy a3 = (Proxy)v3[a2];
                v2.writeObject(new RemoteRef(a3._getObjectId()));
            }
            else {
                v2.writeObject(v3[a2]);
            }
        }
    }
    
    static {
        proxyConstructorParamTypes = new Class[] { ObjectImporter.class, Integer.TYPE };
    }
}
