package javassist.tools.rmi;

import java.util.*;
import javassist.*;
import java.lang.reflect.*;

public class StubGenerator implements Translator
{
    private static final String fieldImporter = "importer";
    private static final String fieldObjectId = "objectId";
    private static final String accessorObjectId = "_getObjectId";
    private static final String sampleClass = "javassist.tools.rmi.Sample";
    private ClassPool classPool;
    private Hashtable proxyClasses;
    private CtMethod forwardMethod;
    private CtMethod forwardStaticMethod;
    private CtClass[] proxyConstructorParamTypes;
    private CtClass[] interfacesForProxy;
    private CtClass[] exceptionForProxy;
    
    public StubGenerator() {
        super();
        this.proxyClasses = new Hashtable();
    }
    
    @Override
    public void start(final ClassPool a1) throws NotFoundException {
        this.classPool = a1;
        final CtClass v1 = a1.get("javassist.tools.rmi.Sample");
        this.forwardMethod = v1.getDeclaredMethod("forward");
        this.forwardStaticMethod = v1.getDeclaredMethod("forwardStatic");
        this.proxyConstructorParamTypes = a1.get(new String[] { "javassist.tools.rmi.ObjectImporter", "int" });
        this.interfacesForProxy = a1.get(new String[] { "java.io.Serializable", "javassist.tools.rmi.Proxy" });
        this.exceptionForProxy = new CtClass[] { a1.get("javassist.tools.rmi.RemoteException") };
    }
    
    @Override
    public void onLoad(final ClassPool a1, final String a2) {
    }
    
    public boolean isProxyClass(final String a1) {
        return this.proxyClasses.get(a1) != null;
    }
    
    public synchronized boolean makeProxyClass(final Class v2) throws CannotCompileException, NotFoundException {
        final String v3 = v2.getName();
        if (this.proxyClasses.get(v3) != null) {
            return false;
        }
        final CtClass a1 = this.produceProxyClass(this.classPool.get(v3), v2);
        this.proxyClasses.put(v3, a1);
        this.modifySuperclass(a1);
        return true;
    }
    
    private CtClass produceProxyClass(final CtClass v1, final Class v2) throws CannotCompileException, NotFoundException {
        final int v3 = v1.getModifiers();
        if (Modifier.isAbstract(v3) || Modifier.isNative(v3) || !Modifier.isPublic(v3)) {
            throw new CannotCompileException(v1.getName() + " must be public, non-native, and non-abstract.");
        }
        final CtClass v4 = this.classPool.makeClass(v1.getName(), v1.getSuperclass());
        v4.setInterfaces(this.interfacesForProxy);
        CtField v5 = new CtField(this.classPool.get("javassist.tools.rmi.ObjectImporter"), "importer", v4);
        v5.setModifiers(2);
        v4.addField(v5, CtField.Initializer.byParameter(0));
        v5 = new CtField(CtClass.intType, "objectId", v4);
        v5.setModifiers(2);
        v4.addField(v5, CtField.Initializer.byParameter(1));
        v4.addMethod(CtNewMethod.getter("_getObjectId", v5));
        v4.addConstructor(CtNewConstructor.defaultConstructor(v4));
        final CtConstructor v6 = CtNewConstructor.skeleton(this.proxyConstructorParamTypes, null, v4);
        v4.addConstructor(v6);
        try {
            this.addMethods(v4, v2.getMethods());
            return v4;
        }
        catch (SecurityException a1) {
            throw new CannotCompileException(a1);
        }
    }
    
    private CtClass toCtClass(Class v-1) throws NotFoundException {
        String v2 = null;
        if (!v-1.isArray()) {
            final String a1 = v-1.getName();
        }
        else {
            final StringBuffer v1 = new StringBuffer();
            do {
                v1.append("[]");
                v-1 = v-1.getComponentType();
            } while (v-1.isArray());
            v1.insert(0, v-1.getName());
            v2 = v1.toString();
        }
        return this.classPool.get(v2);
    }
    
    private CtClass[] toCtClass(final Class[] v2) throws NotFoundException {
        final int v3 = v2.length;
        final CtClass[] v4 = new CtClass[v3];
        for (int a1 = 0; a1 < v3; ++a1) {
            v4[a1] = this.toCtClass(v2[a1]);
        }
        return v4;
    }
    
    private void addMethods(final CtClass v-1, final Method[] v0) throws CannotCompileException, NotFoundException {
        for (int v = 0; v < v0.length; ++v) {
            final Method v2 = v0[v];
            final int v3 = v2.getModifiers();
            if (v2.getDeclaringClass() != Object.class && !Modifier.isFinal(v3)) {
                if (Modifier.isPublic(v3)) {
                    CtMethod a2 = null;
                    if (Modifier.isStatic(v3)) {
                        final CtMethod a1 = this.forwardStaticMethod;
                    }
                    else {
                        a2 = this.forwardMethod;
                    }
                    final CtMethod v4 = CtNewMethod.wrapped(this.toCtClass(v2.getReturnType()), v2.getName(), this.toCtClass((Class[])v2.getParameterTypes()), this.exceptionForProxy, a2, CtMethod.ConstParameter.integer(v), v-1);
                    v4.setModifiers(v3);
                    v-1.addMethod(v4);
                }
                else if (!Modifier.isProtected(v3) && !Modifier.isPrivate(v3)) {
                    throw new CannotCompileException("the methods must be public, protected, or private.");
                }
            }
        }
    }
    
    private void modifySuperclass(CtClass a1) throws CannotCompileException, NotFoundException {
        while (true) {
            final CtClass v1 = a1.getSuperclass();
            if (v1 == null) {
                break;
            }
            try {
                v1.getDeclaredConstructor(null);
            }
            catch (NotFoundException ex) {
                v1.addConstructor(CtNewConstructor.defaultConstructor(v1));
                a1 = v1;
                continue;
            }
            break;
        }
    }
}
