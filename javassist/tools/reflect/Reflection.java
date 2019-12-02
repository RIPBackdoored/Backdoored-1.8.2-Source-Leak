package javassist.tools.reflect;

import javassist.*;
import javassist.bytecode.*;
import java.util.*;

public class Reflection implements Translator
{
    static final String classobjectField = "_classobject";
    static final String classobjectAccessor = "_getClass";
    static final String metaobjectField = "_metaobject";
    static final String metaobjectGetter = "_getMetaobject";
    static final String metaobjectSetter = "_setMetaobject";
    static final String readPrefix = "_r_";
    static final String writePrefix = "_w_";
    static final String metaobjectClassName = "javassist.tools.reflect.Metaobject";
    static final String classMetaobjectClassName = "javassist.tools.reflect.ClassMetaobject";
    protected CtMethod trapMethod;
    protected CtMethod trapStaticMethod;
    protected CtMethod trapRead;
    protected CtMethod trapWrite;
    protected CtClass[] readParam;
    protected ClassPool classPool;
    protected CodeConverter converter;
    
    private boolean isExcluded(final String a1) {
        return a1.startsWith("_m_") || a1.equals("_getClass") || a1.equals("_setMetaobject") || a1.equals("_getMetaobject") || a1.startsWith("_r_") || a1.startsWith("_w_");
    }
    
    public Reflection() {
        super();
        this.classPool = null;
        this.converter = new CodeConverter();
    }
    
    @Override
    public void start(final ClassPool v-1) throws NotFoundException {
        this.classPool = v-1;
        final String v0 = "javassist.tools.reflect.Sample is not found or broken.";
        try {
            final CtClass a1 = this.classPool.get("javassist.tools.reflect.Sample");
            this.rebuildClassFile(a1.getClassFile());
            this.trapMethod = a1.getDeclaredMethod("trap");
            this.trapStaticMethod = a1.getDeclaredMethod("trapStatic");
            this.trapRead = a1.getDeclaredMethod("trapRead");
            this.trapWrite = a1.getDeclaredMethod("trapWrite");
            this.readParam = new CtClass[] { this.classPool.get("java.lang.Object") };
        }
        catch (NotFoundException v2) {
            throw new RuntimeException("javassist.tools.reflect.Sample is not found or broken.");
        }
        catch (BadBytecode v3) {
            throw new RuntimeException("javassist.tools.reflect.Sample is not found or broken.");
        }
    }
    
    @Override
    public void onLoad(final ClassPool a1, final String a2) throws CannotCompileException, NotFoundException {
        final CtClass v1 = a1.get(a2);
        v1.instrument(this.converter);
    }
    
    public boolean makeReflective(final String a1, final String a2, final String a3) throws CannotCompileException, NotFoundException {
        return this.makeReflective(this.classPool.get(a1), this.classPool.get(a2), this.classPool.get(a3));
    }
    
    public boolean makeReflective(final Class a1, final Class a2, final Class a3) throws CannotCompileException, NotFoundException {
        return this.makeReflective(a1.getName(), a2.getName(), a3.getName());
    }
    
    public boolean makeReflective(final CtClass a1, final CtClass a2, final CtClass a3) throws CannotCompileException, CannotReflectException, NotFoundException {
        if (a1.isInterface()) {
            throw new CannotReflectException("Cannot reflect an interface: " + a1.getName());
        }
        if (a1.subclassOf(this.classPool.get("javassist.tools.reflect.ClassMetaobject"))) {
            throw new CannotReflectException("Cannot reflect a subclass of ClassMetaobject: " + a1.getName());
        }
        if (a1.subclassOf(this.classPool.get("javassist.tools.reflect.Metaobject"))) {
            throw new CannotReflectException("Cannot reflect a subclass of Metaobject: " + a1.getName());
        }
        this.registerReflectiveClass(a1);
        return this.modifyClassfile(a1, a2, a3);
    }
    
    private void registerReflectiveClass(final CtClass v-2) {
        final CtField[] declaredFields = v-2.getDeclaredFields();
        for (int v0 = 0; v0 < declaredFields.length; ++v0) {
            final CtField v2 = declaredFields[v0];
            final int v3 = v2.getModifiers();
            if ((v3 & 0x1) != 0x0 && (v3 & 0x10) == 0x0) {
                final String a1 = v2.getName();
                this.converter.replaceFieldRead(v2, v-2, "_r_" + a1);
                this.converter.replaceFieldWrite(v2, v-2, "_w_" + a1);
            }
        }
    }
    
    private boolean modifyClassfile(final CtClass a3, final CtClass v1, final CtClass v2) throws CannotCompileException, NotFoundException {
        if (a3.getAttribute("Reflective") != null) {
            return false;
        }
        a3.setAttribute("Reflective", new byte[0]);
        final CtClass v3 = this.classPool.get("javassist.tools.reflect.Metalevel");
        final boolean v4 = !a3.subtypeOf(v3);
        if (v4) {
            a3.addInterface(v3);
        }
        this.processMethods(a3, v4);
        this.processFields(a3);
        if (v4) {
            final CtField a4 = new CtField(this.classPool.get("javassist.tools.reflect.Metaobject"), "_metaobject", a3);
            a4.setModifiers(4);
            a3.addField(a4, CtField.Initializer.byNewWithParams(v1));
            a3.addMethod(CtNewMethod.getter("_getMetaobject", a4));
            a3.addMethod(CtNewMethod.setter("_setMetaobject", a4));
        }
        final CtField v5 = new CtField(this.classPool.get("javassist.tools.reflect.ClassMetaobject"), "_classobject", a3);
        v5.setModifiers(10);
        a3.addField(v5, CtField.Initializer.byNew(v2, new String[] { a3.getName() }));
        a3.addMethod(CtNewMethod.getter("_getClass", v5));
        return true;
    }
    
    private void processMethods(final CtClass v-2, final boolean v-1) throws CannotCompileException, NotFoundException {
        final CtMethod[] v0 = v-2.getMethods();
        for (int v2 = 0; v2 < v0.length; ++v2) {
            final CtMethod a1 = v0[v2];
            final int a2 = a1.getModifiers();
            if (Modifier.isPublic(a2) && !Modifier.isAbstract(a2)) {
                this.processMethods0(a2, v-2, a1, v2, v-1);
            }
        }
    }
    
    private void processMethods0(int a4, final CtClass a5, final CtMethod v1, final int v2, final boolean v3) throws CannotCompileException, NotFoundException {
        final String v4 = v1.getName();
        if (this.isExcluded(v4)) {
            return;
        }
        CtMethod v5 = null;
        if (v1.getDeclaringClass() == a5) {
            if (Modifier.isNative(a4)) {
                return;
            }
            final CtMethod a6 = v1;
            if (Modifier.isFinal(a4)) {
                a4 &= 0xFFFFFFEF;
                a6.setModifiers(a4);
            }
        }
        else {
            if (Modifier.isFinal(a4)) {
                return;
            }
            a4 &= 0xFFFFFEFF;
            v5 = CtNewMethod.delegator(this.findOriginal(v1, v3), a5);
            v5.setModifiers(a4);
            a5.addMethod(v5);
        }
        v5.setName("_m_" + v2 + "_" + v4);
        CtMethod v6 = null;
        if (Modifier.isStatic(a4)) {
            final CtMethod a7 = this.trapStaticMethod;
        }
        else {
            v6 = this.trapMethod;
        }
        final CtMethod v7 = CtNewMethod.wrapped(v1.getReturnType(), v4, v1.getParameterTypes(), v1.getExceptionTypes(), v6, CtMethod.ConstParameter.integer(v2), a5);
        v7.setModifiers(a4);
        a5.addMethod(v7);
    }
    
    private CtMethod findOriginal(final CtMethod v2, final boolean v3) throws NotFoundException {
        if (v3) {
            return v2;
        }
        final String v4 = v2.getName();
        final CtMethod[] v5 = v2.getDeclaringClass().getDeclaredMethods();
        for (int a2 = 0; a2 < v5.length; ++a2) {
            final String a3 = v5[a2].getName();
            if (a3.endsWith(v4) && a3.startsWith("_m_") && v5[a2].getSignature().equals(v2.getSignature())) {
                return v5[a2];
            }
        }
        return v2;
    }
    
    private void processFields(final CtClass v-5) throws CannotCompileException, NotFoundException {
        final CtField[] declaredFields = v-5.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; ++i) {
            final CtField ctField = declaredFields[i];
            int modifiers = ctField.getModifiers();
            if ((modifiers & 0x1) != 0x0 && (modifiers & 0x10) == 0x0) {
                modifiers |= 0x8;
                final String a1 = ctField.getName();
                final CtClass v1 = ctField.getType();
                CtMethod v2 = CtNewMethod.wrapped(v1, "_r_" + a1, this.readParam, null, this.trapRead, CtMethod.ConstParameter.string(a1), v-5);
                v2.setModifiers(modifiers);
                v-5.addMethod(v2);
                final CtClass[] v3 = { this.classPool.get("java.lang.Object"), v1 };
                v2 = CtNewMethod.wrapped(CtClass.voidType, "_w_" + a1, v3, null, this.trapWrite, CtMethod.ConstParameter.string(a1), v-5);
                v2.setModifiers(modifiers);
                v-5.addMethod(v2);
            }
        }
    }
    
    public void rebuildClassFile(final ClassFile v2) throws BadBytecode {
        if (ClassFile.MAJOR_VERSION < 50) {
            return;
        }
        for (final MethodInfo a1 : v2.getMethods()) {
            a1.rebuildStackMap(this.classPool);
        }
    }
}
