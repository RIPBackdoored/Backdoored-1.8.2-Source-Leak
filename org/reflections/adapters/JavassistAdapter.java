package org.reflections.adapters;

import com.google.common.collect.*;
import javassist.bytecode.annotation.*;
import org.reflections.vfs.*;
import org.reflections.*;
import java.io.*;
import org.reflections.util.*;
import com.google.common.base.*;
import java.util.*;
import javassist.bytecode.*;

public class JavassistAdapter implements MetadataAdapter<ClassFile, FieldInfo, MethodInfo>
{
    public static boolean includeInvisibleTag;
    
    public JavassistAdapter() {
        super();
    }
    
    @Override
    public List<FieldInfo> getFields(final ClassFile a1) {
        return (List<FieldInfo>)a1.getFields();
    }
    
    @Override
    public List<MethodInfo> getMethods(final ClassFile a1) {
        return (List<MethodInfo>)a1.getMethods();
    }
    
    @Override
    public String getMethodName(final MethodInfo a1) {
        return a1.getName();
    }
    
    @Override
    public List<String> getParameterNames(final MethodInfo a1) {
        String v1 = a1.getDescriptor();
        v1 = v1.substring(v1.indexOf("(") + 1, v1.lastIndexOf(")"));
        return this.splitDescriptorToTypeNames(v1);
    }
    
    @Override
    public List<String> getClassAnnotationNames(final ClassFile a1) {
        return this.getAnnotationNames((AnnotationsAttribute)a1.getAttribute("RuntimeVisibleAnnotations"), JavassistAdapter.includeInvisibleTag ? ((AnnotationsAttribute)a1.getAttribute("RuntimeInvisibleAnnotations")) : null);
    }
    
    @Override
    public List<String> getFieldAnnotationNames(final FieldInfo a1) {
        return this.getAnnotationNames((AnnotationsAttribute)a1.getAttribute("RuntimeVisibleAnnotations"), JavassistAdapter.includeInvisibleTag ? ((AnnotationsAttribute)a1.getAttribute("RuntimeInvisibleAnnotations")) : null);
    }
    
    @Override
    public List<String> getMethodAnnotationNames(final MethodInfo a1) {
        return this.getAnnotationNames((AnnotationsAttribute)a1.getAttribute("RuntimeVisibleAnnotations"), JavassistAdapter.includeInvisibleTag ? ((AnnotationsAttribute)a1.getAttribute("RuntimeInvisibleAnnotations")) : null);
    }
    
    @Override
    public List<String> getParameterAnnotationNames(final MethodInfo v-4, final int v-3) {
        final List<String> arrayList = (List<String>)Lists.newArrayList();
        final List<ParameterAnnotationsAttribute> arrayList2 = Lists.newArrayList((ParameterAnnotationsAttribute)v-4.getAttribute("RuntimeVisibleParameterAnnotations"), (ParameterAnnotationsAttribute)v-4.getAttribute("RuntimeInvisibleParameterAnnotations"));
        if (arrayList2 != null) {
            for (final ParameterAnnotationsAttribute v1 : arrayList2) {
                if (v1 != null) {
                    final Annotation[][] a2 = v1.getAnnotations();
                    if (v-3 >= a2.length) {
                        continue;
                    }
                    final Annotation[] a3 = a2[v-3];
                    arrayList.addAll(this.getAnnotationNames(a3));
                }
            }
        }
        return arrayList;
    }
    
    @Override
    public String getReturnTypeName(final MethodInfo a1) {
        String v1 = a1.getDescriptor();
        v1 = v1.substring(v1.lastIndexOf(")") + 1);
        return this.splitDescriptorToTypeNames(v1).get(0);
    }
    
    @Override
    public String getFieldName(final FieldInfo a1) {
        return a1.getName();
    }
    
    @Override
    public ClassFile getOfCreateClassObject(final Vfs.File v-1) {
        InputStream v0 = null;
        try {
            v0 = v-1.openInputStream();
            final DataInputStream a1 = new DataInputStream(new BufferedInputStream(v0));
            return new ClassFile(a1);
        }
        catch (IOException v2) {
            throw new ReflectionsException("could not create class file from " + v-1.getName(), v2);
        }
        finally {
            Utils.close(v0);
        }
    }
    
    @Override
    public String getMethodModifier(final MethodInfo a1) {
        final int v1 = a1.getAccessFlags();
        return AccessFlag.isPrivate(v1) ? "private" : (AccessFlag.isProtected(v1) ? "protected" : (this.isPublic(v1) ? "public" : ""));
    }
    
    @Override
    public String getMethodKey(final ClassFile a1, final MethodInfo a2) {
        return this.getMethodName(a2) + "(" + Joiner.on(", ").join(this.getParameterNames(a2)) + ")";
    }
    
    @Override
    public String getMethodFullKey(final ClassFile a1, final MethodInfo a2) {
        return this.getClassName(a1) + "." + this.getMethodKey(a1, a2);
    }
    
    @Override
    public boolean isPublic(final Object a1) {
        final Integer v1 = (a1 instanceof ClassFile) ? ((ClassFile)a1).getAccessFlags() : ((a1 instanceof FieldInfo) ? ((FieldInfo)a1).getAccessFlags() : ((a1 instanceof MethodInfo) ? Integer.valueOf(((MethodInfo)a1).getAccessFlags()) : null));
        return v1 != null && AccessFlag.isPublic(v1);
    }
    
    @Override
    public String getClassName(final ClassFile a1) {
        return a1.getName();
    }
    
    @Override
    public String getSuperclassName(final ClassFile a1) {
        return a1.getSuperclass();
    }
    
    @Override
    public List<String> getInterfacesNames(final ClassFile a1) {
        return Arrays.asList(a1.getInterfaces());
    }
    
    @Override
    public boolean acceptsInput(final String a1) {
        return a1.endsWith(".class");
    }
    
    private List<String> getAnnotationNames(final AnnotationsAttribute... v-4) {
        final List<String> arrayList = (List<String>)Lists.newArrayList();
        if (v-4 != null) {
            for (final AnnotationsAttribute v1 : v-4) {
                if (v1 != null) {
                    for (final Annotation a1 : v1.getAnnotations()) {
                        arrayList.add(a1.getTypeName());
                    }
                }
            }
        }
        return arrayList;
    }
    
    private List<String> getAnnotationNames(final Annotation[] v2) {
        final List<String> v3 = (List<String>)Lists.newArrayList();
        for (final Annotation a1 : v2) {
            v3.add(a1.getTypeName());
        }
        return v3;
    }
    
    private List<String> splitDescriptorToTypeNames(final String v-3) {
        final List<String> arrayList = (List<String>)Lists.newArrayList();
        if (v-3 != null && v-3.length() != 0) {
            final List<Integer> arrayList2 = (List<Integer>)Lists.newArrayList();
            final Descriptor.Iterator v0 = new Descriptor.Iterator(v-3);
            while (v0.hasNext()) {
                arrayList2.add(v0.next());
            }
            arrayList2.add(v-3.length());
            for (int v2 = 0; v2 < arrayList2.size() - 1; ++v2) {
                final String a1 = Descriptor.toString(v-3.substring(arrayList2.get(v2), arrayList2.get(v2 + 1)));
                arrayList.add(a1);
            }
        }
        return arrayList;
    }
    
    @Override
    public /* bridge */ String getMethodFullKey(final Object o, final Object o2) {
        return this.getMethodFullKey((ClassFile)o, (MethodInfo)o2);
    }
    
    @Override
    public /* bridge */ String getMethodKey(final Object o, final Object o2) {
        return this.getMethodKey((ClassFile)o, (MethodInfo)o2);
    }
    
    @Override
    public /* bridge */ String getMethodModifier(final Object o) {
        return this.getMethodModifier((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ Object getOfCreateClassObject(final Vfs.File v-1) throws Exception {
        return this.getOfCreateClassObject(v-1);
    }
    
    @Override
    public /* bridge */ String getFieldName(final Object o) {
        return this.getFieldName((FieldInfo)o);
    }
    
    @Override
    public /* bridge */ String getReturnTypeName(final Object o) {
        return this.getReturnTypeName((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ List getParameterAnnotationNames(final Object o, final int v-3) {
        return this.getParameterAnnotationNames((MethodInfo)o, v-3);
    }
    
    @Override
    public /* bridge */ List getMethodAnnotationNames(final Object o) {
        return this.getMethodAnnotationNames((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ List getFieldAnnotationNames(final Object o) {
        return this.getFieldAnnotationNames((FieldInfo)o);
    }
    
    @Override
    public /* bridge */ List getClassAnnotationNames(final Object o) {
        return this.getClassAnnotationNames((ClassFile)o);
    }
    
    @Override
    public /* bridge */ List getParameterNames(final Object o) {
        return this.getParameterNames((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ String getMethodName(final Object o) {
        return this.getMethodName((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ List getMethods(final Object o) {
        return this.getMethods((ClassFile)o);
    }
    
    @Override
    public /* bridge */ List getFields(final Object o) {
        return this.getFields((ClassFile)o);
    }
    
    @Override
    public /* bridge */ List getInterfacesNames(final Object o) {
        return this.getInterfacesNames((ClassFile)o);
    }
    
    @Override
    public /* bridge */ String getSuperclassName(final Object o) {
        return this.getSuperclassName((ClassFile)o);
    }
    
    @Override
    public /* bridge */ String getClassName(final Object o) {
        return this.getClassName((ClassFile)o);
    }
    
    static {
        JavassistAdapter.includeInvisibleTag = true;
    }
}
