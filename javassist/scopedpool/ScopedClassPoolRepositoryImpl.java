package javassist.scopedpool;

import javassist.*;
import java.util.*;

public class ScopedClassPoolRepositoryImpl implements ScopedClassPoolRepository
{
    private static final ScopedClassPoolRepositoryImpl instance;
    private boolean prune;
    boolean pruneWhenCached;
    protected Map registeredCLs;
    protected ClassPool classpool;
    protected ScopedClassPoolFactory factory;
    
    public static ScopedClassPoolRepository getInstance() {
        return ScopedClassPoolRepositoryImpl.instance;
    }
    
    private ScopedClassPoolRepositoryImpl() {
        super();
        this.prune = true;
        this.registeredCLs = Collections.synchronizedMap(new WeakHashMap<Object, Object>());
        this.factory = new ScopedClassPoolFactoryImpl();
        this.classpool = ClassPool.getDefault();
        final ClassLoader v1 = Thread.currentThread().getContextClassLoader();
        this.classpool.insertClassPath(new LoaderClassPath(v1));
    }
    
    @Override
    public boolean isPrune() {
        return this.prune;
    }
    
    @Override
    public void setPrune(final boolean a1) {
        this.prune = a1;
    }
    
    @Override
    public ScopedClassPool createScopedClassPool(final ClassLoader a1, final ClassPool a2) {
        return this.factory.create(a1, a2, this);
    }
    
    @Override
    public ClassPool findClassPool(final ClassLoader a1) {
        if (a1 == null) {
            return this.registerClassLoader(ClassLoader.getSystemClassLoader());
        }
        return this.registerClassLoader(a1);
    }
    
    @Override
    public ClassPool registerClassLoader(final ClassLoader v2) {
        synchronized (this.registeredCLs) {
            if (this.registeredCLs.containsKey(v2)) {
                return this.registeredCLs.get(v2);
            }
            final ScopedClassPool a1 = this.createScopedClassPool(v2, this.classpool);
            this.registeredCLs.put(v2, a1);
            return a1;
        }
    }
    
    @Override
    public Map getRegisteredCLs() {
        this.clearUnregisteredClassLoaders();
        return this.registeredCLs;
    }
    
    @Override
    public void clearUnregisteredClassLoaders() {
        ArrayList list = null;
        synchronized (this.registeredCLs) {
            final Iterator iterator = this.registeredCLs.values().iterator();
            while (iterator.hasNext()) {
                final ScopedClassPool v0 = iterator.next();
                if (v0.isUnloadedClassLoader()) {
                    iterator.remove();
                    final ClassLoader v2 = v0.getClassLoader();
                    if (v2 == null) {
                        continue;
                    }
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(v2);
                }
            }
            if (list != null) {
                for (int v3 = 0; v3 < list.size(); ++v3) {
                    this.unregisterClassLoader(list.get(v3));
                }
            }
        }
    }
    
    @Override
    public void unregisterClassLoader(final ClassLoader v2) {
        synchronized (this.registeredCLs) {
            final ScopedClassPool a1 = this.registeredCLs.remove(v2);
            if (a1 != null) {
                a1.close();
            }
        }
    }
    
    public void insertDelegate(final ScopedClassPoolRepository a1) {
    }
    
    @Override
    public void setClassPoolFactory(final ScopedClassPoolFactory a1) {
        this.factory = a1;
    }
    
    @Override
    public ScopedClassPoolFactory getClassPoolFactory() {
        return this.factory;
    }
    
    static {
        instance = new ScopedClassPoolRepositoryImpl();
    }
}
