package org.reflections.scanners;

import org.reflections.*;
import javassist.expr.*;
import javassist.bytecode.*;
import com.google.common.base.*;
import org.reflections.util.*;
import javassist.*;

public class MemberUsageScanner extends AbstractScanner
{
    private ClassPool classPool;
    
    public MemberUsageScanner() {
        super();
    }
    
    @Override
    public void scan(final Object v-4) {
        try {
            final CtClass value = this.getClassPool().get(this.getMetadataAdapter().getClassName(v-4));
            for (final CtBehavior a1 : value.getDeclaredConstructors()) {
                this.scanMember(a1);
            }
            for (final CtBehavior v1 : value.getDeclaredMethods()) {
                this.scanMember(v1);
            }
            value.detach();
        }
        catch (Exception a2) {
            throw new ReflectionsException("Could not scan method usage for " + this.getMetadataAdapter().getClassName(v-4), a2);
        }
    }
    
    void scanMember(final CtBehavior a1) throws CannotCompileException {
        final String v1 = a1.getDeclaringClass().getName() + "." + a1.getMethodInfo().getName() + "(" + this.parameterNames(a1.getMethodInfo()) + ")";
        a1.instrument(new ExprEditor() {
            final /* synthetic */ String val$key;
            final /* synthetic */ MemberUsageScanner this$0;
            
            MemberUsageScanner$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void edit(final NewExpr v2) throws CannotCompileException {
                try {
                    MemberUsageScanner.this.put(v2.getConstructor().getDeclaringClass().getName() + ".<init>(" + this.this$0.parameterNames(v2.getConstructor().getMethodInfo()) + ")", v2.getLineNumber(), v1);
                }
                catch (NotFoundException a1) {
                    throw new ReflectionsException("Could not find new instance usage in " + v1, a1);
                }
            }
            
            @Override
            public void edit(final MethodCall v2) throws CannotCompileException {
                try {
                    MemberUsageScanner.this.put(v2.getMethod().getDeclaringClass().getName() + "." + v2.getMethodName() + "(" + this.this$0.parameterNames(v2.getMethod().getMethodInfo()) + ")", v2.getLineNumber(), v1);
                }
                catch (NotFoundException a1) {
                    throw new ReflectionsException("Could not find member " + v2.getClassName() + " in " + v1, a1);
                }
            }
            
            @Override
            public void edit(final ConstructorCall v2) throws CannotCompileException {
                try {
                    MemberUsageScanner.this.put(v2.getConstructor().getDeclaringClass().getName() + ".<init>(" + this.this$0.parameterNames(v2.getConstructor().getMethodInfo()) + ")", v2.getLineNumber(), v1);
                }
                catch (NotFoundException a1) {
                    throw new ReflectionsException("Could not find member " + v2.getClassName() + " in " + v1, a1);
                }
            }
            
            @Override
            public void edit(final FieldAccess v2) throws CannotCompileException {
                try {
                    MemberUsageScanner.this.put(v2.getField().getDeclaringClass().getName() + "." + v2.getFieldName(), v2.getLineNumber(), v1);
                }
                catch (NotFoundException a1) {
                    throw new ReflectionsException("Could not find member " + v2.getFieldName() + " in " + v1, a1);
                }
            }
        });
    }
    
    private void put(final String a1, final int a2, final String a3) {
        if (this.acceptResult(a1)) {
            this.getStore().put((Object)a1, (Object)(a3 + " #" + a2));
        }
    }
    
    String parameterNames(final MethodInfo a1) {
        return Joiner.on(", ").join(this.getMetadataAdapter().getParameterNames(a1));
    }
    
    private ClassPool getClassPool() {
        if (this.classPool == null) {
            synchronized (this) {
                this.classPool = new ClassPool();
                ClassLoader[] array = this.getConfiguration().getClassLoaders();
                if (array == null) {
                    array = ClasspathHelper.classLoaders(new ClassLoader[0]);
                }
                for (final ClassLoader v1 : array) {
                    this.classPool.appendClassPath(new LoaderClassPath(v1));
                }
            }
        }
        return this.classPool;
    }
    
    static /* bridge */ void access$000(final MemberUsageScanner a1, final String a2, final int a3, final String a4) {
        a1.put(a2, a3, a4);
    }
}
