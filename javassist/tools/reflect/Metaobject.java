package javassist.tools.reflect;

import java.io.*;
import java.lang.reflect.*;

public class Metaobject implements Serializable
{
    protected ClassMetaobject classmetaobject;
    protected Metalevel baseobject;
    protected Method[] methods;
    
    public Metaobject(final Object a1, final Object[] a2) {
        super();
        this.baseobject = (Metalevel)a1;
        this.classmetaobject = this.baseobject._getClass();
        this.methods = this.classmetaobject.getReflectiveMethods();
    }
    
    protected Metaobject() {
        super();
        this.baseobject = null;
        this.classmetaobject = null;
        this.methods = null;
    }
    
    private void writeObject(final ObjectOutputStream a1) throws IOException {
        a1.writeObject(this.baseobject);
    }
    
    private void readObject(final ObjectInputStream a1) throws IOException, ClassNotFoundException {
        this.baseobject = (Metalevel)a1.readObject();
        this.classmetaobject = this.baseobject._getClass();
        this.methods = this.classmetaobject.getReflectiveMethods();
    }
    
    public final ClassMetaobject getClassMetaobject() {
        return this.classmetaobject;
    }
    
    public final Object getObject() {
        return this.baseobject;
    }
    
    public final void setObject(final Object a1) {
        this.baseobject = (Metalevel)a1;
        this.classmetaobject = this.baseobject._getClass();
        this.methods = this.classmetaobject.getReflectiveMethods();
        this.baseobject._setMetaobject(this);
    }
    
    public final String getMethodName(final int v2) {
        final String v3 = this.methods[v2].getName();
        int v4 = 3;
        char a1;
        do {
            a1 = v3.charAt(v4++);
        } while (a1 >= '0' && '9' >= a1);
        return v3.substring(v4);
    }
    
    public final Class[] getParameterTypes(final int a1) {
        return this.methods[a1].getParameterTypes();
    }
    
    public final Class getReturnType(final int a1) {
        return this.methods[a1].getReturnType();
    }
    
    public Object trapFieldRead(final String v-1) {
        final Class v0 = this.getClassMetaobject().getJavaClass();
        try {
            return v0.getField(v-1).get(this.getObject());
        }
        catch (NoSuchFieldException a1) {
            throw new RuntimeException(a1.toString());
        }
        catch (IllegalAccessException v2) {
            throw new RuntimeException(v2.toString());
        }
    }
    
    public void trapFieldWrite(final String v2, final Object v3) {
        final Class v4 = this.getClassMetaobject().getJavaClass();
        try {
            v4.getField(v2).set(this.getObject(), v3);
        }
        catch (NoSuchFieldException a1) {
            throw new RuntimeException(a1.toString());
        }
        catch (IllegalAccessException a2) {
            throw new RuntimeException(a2.toString());
        }
    }
    
    public Object trapMethodcall(final int v2, final Object[] v3) throws Throwable {
        try {
            return this.methods[v2].invoke(this.getObject(), v3);
        }
        catch (InvocationTargetException a1) {
            throw a1.getTargetException();
        }
        catch (IllegalAccessException a2) {
            throw new CannotInvokeException(a2);
        }
    }
}
