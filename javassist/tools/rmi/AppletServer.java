package javassist.tools.rmi;

import java.util.*;
import javassist.*;
import javassist.tools.web.*;
import java.io.*;

public class AppletServer extends Webserver
{
    private StubGenerator stubGen;
    private Hashtable exportedNames;
    private Vector exportedObjects;
    private static final byte[] okHeader;
    
    public AppletServer(final String a1) throws IOException, NotFoundException, CannotCompileException {
        this(Integer.parseInt(a1));
    }
    
    public AppletServer(final int a1) throws IOException, NotFoundException, CannotCompileException {
        this(ClassPool.getDefault(), new StubGenerator(), a1);
    }
    
    public AppletServer(final int a1, final ClassPool a2) throws IOException, NotFoundException, CannotCompileException {
        this(new ClassPool(a2), new StubGenerator(), a1);
    }
    
    private AppletServer(final ClassPool a1, final StubGenerator a2, final int a3) throws IOException, NotFoundException, CannotCompileException {
        super(a3);
        this.exportedNames = new Hashtable();
        this.exportedObjects = new Vector();
        this.addTranslator(a1, this.stubGen = a2);
    }
    
    @Override
    public void run() {
        super.run();
    }
    
    public synchronized int exportObject(final String v1, final Object v2) throws CannotCompileException {
        final Class v3 = v2.getClass();
        final ExportedObject v4 = new ExportedObject();
        v4.object = v2;
        v4.methods = v3.getMethods();
        this.exportedObjects.addElement(v4);
        v4.identifier = this.exportedObjects.size() - 1;
        if (v1 != null) {
            this.exportedNames.put(v1, v4);
        }
        try {
            this.stubGen.makeProxyClass(v3);
        }
        catch (NotFoundException a1) {
            throw new CannotCompileException(a1);
        }
        return v4.identifier;
    }
    
    @Override
    public void doReply(final InputStream a1, final OutputStream a2, final String a3) throws IOException, BadHttpRequest {
        if (a3.startsWith("POST /rmi ")) {
            this.processRMI(a1, a2);
        }
        else if (a3.startsWith("POST /lookup ")) {
            this.lookupName(a3, a1, a2);
        }
        else {
            super.doReply(a1, a2, a3);
        }
    }
    
    private void processRMI(final InputStream v-6, final OutputStream v-5) throws IOException {
        final ObjectInputStream v-7 = new ObjectInputStream(v-6);
        final int int1 = v-7.readInt();
        final int int2 = v-7.readInt();
        Exception ex = null;
        Object v0 = null;
        try {
            final ExportedObject a1 = this.exportedObjects.elementAt(int1);
            final Object[] a2 = this.readParameters(v-7);
            v0 = this.convertRvalue(a1.methods[int2].invoke(a1.object, a2));
        }
        catch (Exception v2) {
            ex = v2;
            this.logging2(v2.toString());
        }
        v-5.write(AppletServer.okHeader);
        final ObjectOutputStream v3 = new ObjectOutputStream(v-5);
        if (ex != null) {
            v3.writeBoolean(false);
            v3.writeUTF(ex.toString());
        }
        else {
            try {
                v3.writeBoolean(true);
                v3.writeObject(v0);
            }
            catch (NotSerializableException v4) {
                this.logging2(v4.toString());
            }
            catch (InvalidClassException v5) {
                this.logging2(v5.toString());
            }
        }
        v3.flush();
        v3.close();
        v-7.close();
    }
    
    private Object[] readParameters(final ObjectInputStream v-5) throws IOException, ClassNotFoundException {
        final int int1 = v-5.readInt();
        final Object[] array = new Object[int1];
        for (int i = 0; i < int1; ++i) {
            Object o = v-5.readObject();
            if (o instanceof RemoteRef) {
                final RemoteRef a1 = (RemoteRef)o;
                final ExportedObject v1 = this.exportedObjects.elementAt(a1.oid);
                o = v1.object;
            }
            array[i] = o;
        }
        return array;
    }
    
    private Object convertRvalue(final Object a1) throws CannotCompileException {
        if (a1 == null) {
            return null;
        }
        final String v1 = a1.getClass().getName();
        if (this.stubGen.isProxyClass(v1)) {
            return new RemoteRef(this.exportObject(null, a1), v1);
        }
        return a1;
    }
    
    private void lookupName(final String a1, final InputStream a2, final OutputStream a3) throws IOException {
        final ObjectInputStream v1 = new ObjectInputStream(a2);
        final String v2 = DataInputStream.readUTF(v1);
        final ExportedObject v3 = this.exportedNames.get(v2);
        a3.write(AppletServer.okHeader);
        final ObjectOutputStream v4 = new ObjectOutputStream(a3);
        if (v3 == null) {
            this.logging2(v2 + "not found.");
            v4.writeInt(-1);
            v4.writeUTF("error");
        }
        else {
            this.logging2(v2);
            v4.writeInt(v3.identifier);
            v4.writeUTF(v3.object.getClass().getName());
        }
        v4.flush();
        v4.close();
        v1.close();
    }
    
    static {
        okHeader = "HTTP/1.0 200 OK\r\n\r\n".getBytes();
    }
}
