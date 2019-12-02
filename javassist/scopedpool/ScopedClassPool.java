package javassist.scopedpool;

import java.lang.ref.*;
import java.util.*;
import java.security.*;
import javassist.*;

public class ScopedClassPool extends ClassPool
{
    protected ScopedClassPoolRepository repository;
    protected WeakReference classLoader;
    protected LoaderClassPath classPath;
    protected SoftValueHashMap softcache;
    boolean isBootstrapCl;
    
    @Deprecated
    protected ScopedClassPool(final ClassLoader a1, final ClassPool a2, final ScopedClassPoolRepository a3) {
        this(a1, a2, a3, false);
    }
    
    protected ScopedClassPool(final ClassLoader a1, final ClassPool a2, final ScopedClassPoolRepository a3, final boolean a4) {
        super(a2);
        this.softcache = new SoftValueHashMap();
        this.isBootstrapCl = true;
        this.repository = a3;
        this.classLoader = new WeakReference((T)a1);
        if (a1 != null) {
            this.insertClassPath(this.classPath = new LoaderClassPath(a1));
        }
        this.childFirstLookup = true;
        if (!a4 && a1 == null) {
            this.isBootstrapCl = true;
        }
    }
    
    @Override
    public ClassLoader getClassLoader() {
        final ClassLoader v1 = this.getClassLoader0();
        if (v1 == null && !this.isBootstrapCl) {
            throw new IllegalStateException("ClassLoader has been garbage collected");
        }
        return v1;
    }
    
    protected ClassLoader getClassLoader0() {
        return (ClassLoader)this.classLoader.get();
    }
    
    public void close() {
        this.removeClassPath(this.classPath);
        this.classPath.close();
        this.classes.clear();
        this.softcache.clear();
    }
    
    public synchronized void flushClass(final String a1) {
        this.classes.remove(a1);
        this.softcache.remove(a1);
    }
    
    public synchronized void soften(final CtClass a1) {
        if (this.repository.isPrune()) {
            a1.prune();
        }
        this.classes.remove(a1.getName());
        this.softcache.put(a1.getName(), a1);
    }
    
    public boolean isUnloadedClassLoader() {
        return false;
    }
    
    @Override
    protected CtClass getCached(final String v-4) {
        CtClass ctClass = this.getCachedLocally(v-4);
        if (ctClass == null) {
            boolean b = false;
            final ClassLoader classLoader0 = this.getClassLoader0();
            if (classLoader0 != null) {
                final int a1 = v-4.lastIndexOf(36);
                String v1 = null;
                if (a1 < 0) {
                    v1 = v-4.replaceAll("[\\.]", "/") + ".class";
                }
                else {
                    v1 = v-4.substring(0, a1).replaceAll("[\\.]", "/") + v-4.substring(a1) + ".class";
                }
                b = (classLoader0.getResource(v1) != null);
            }
            if (!b) {
                final Map v2 = this.repository.getRegisteredCLs();
                synchronized (v2) {
                    for (final ScopedClassPool v4 : v2.values()) {
                        if (v4.isUnloadedClassLoader()) {
                            this.repository.unregisterClassLoader(v4.getClassLoader());
                        }
                        else {
                            ctClass = v4.getCachedLocally(v-4);
                            if (ctClass != null) {
                                return ctClass;
                            }
                            continue;
                        }
                    }
                }
            }
        }
        return ctClass;
    }
    
    @Override
    protected void cacheCtClass(final String a1, final CtClass a2, final boolean a3) {
        if (a3) {
            super.cacheCtClass(a1, a2, a3);
        }
        else {
            if (this.repository.isPrune()) {
                a2.prune();
            }
            this.softcache.put(a1, a2);
        }
    }
    
    public void lockInCache(final CtClass a1) {
        super.cacheCtClass(a1.getName(), a1, false);
    }
    
    protected CtClass getCachedLocally(final String a1) {
        final CtClass v1 = this.classes.get(a1);
        if (v1 != null) {
            return v1;
        }
        synchronized (this.softcache) {
            return (CtClass)this.softcache.get(a1);
        }
    }
    
    public synchronized CtClass getLocally(final String a1) throws NotFoundException {
        this.softcache.remove(a1);
        CtClass v1 = this.classes.get(a1);
        if (v1 == null) {
            v1 = this.createCtClass(a1, true);
            if (v1 == null) {
                throw new NotFoundException(a1);
            }
            super.cacheCtClass(a1, v1, false);
        }
        return v1;
    }
    
    @Override
    public Class toClass(final CtClass a1, final ClassLoader a2, final ProtectionDomain a3) throws CannotCompileException {
        this.lockInCache(a1);
        return super.toClass(a1, this.getClassLoader0(), a3);
    }
    
    static {
        ClassPool.doPruning = false;
        ClassPool.releaseUnmodifiedClassFile = false;
    }
}
