package org.reflections.adapters;

import com.google.common.collect.*;
import java.lang.annotation.*;
import org.reflections.vfs.*;
import javax.annotation.*;
import org.reflections.*;
import java.lang.reflect.*;
import com.google.common.base.*;
import java.util.*;
import org.reflections.util.*;

public class JavaReflectionAdapter implements MetadataAdapter<Class, Field, Member>
{
    public JavaReflectionAdapter() {
        super();
    }
    
    @Override
    public List<Field> getFields(final Class a1) {
        return Lists.newArrayList(a1.getDeclaredFields());
    }
    
    @Override
    public List<Member> getMethods(final Class a1) {
        final List<Member> v1 = (List<Member>)Lists.newArrayList();
        v1.addAll(Arrays.asList(a1.getDeclaredMethods()));
        v1.addAll(Arrays.asList(a1.getDeclaredConstructors()));
        return v1;
    }
    
    @Override
    public String getMethodName(final Member a1) {
        return (a1 instanceof Method) ? a1.getName() : ((a1 instanceof Constructor) ? "<init>" : null);
    }
    
    @Override
    public List<String> getParameterNames(final Member v-5) {
        final List<String> arrayList = (List<String>)Lists.newArrayList();
        final Class<?>[] array = (v-5 instanceof Method) ? ((Method)v-5).getParameterTypes() : ((v-5 instanceof Constructor) ? ((Constructor)v-5).getParameterTypes() : null);
        if (array != null) {
            for (final Class<?> v1 : array) {
                final String a1 = getName(v1);
                arrayList.add(a1);
            }
        }
        return arrayList;
    }
    
    @Override
    public List<String> getClassAnnotationNames(final Class a1) {
        return this.getAnnotationNames(a1.getDeclaredAnnotations());
    }
    
    @Override
    public List<String> getFieldAnnotationNames(final Field a1) {
        return this.getAnnotationNames(a1.getDeclaredAnnotations());
    }
    
    @Override
    public List<String> getMethodAnnotationNames(final Member a1) {
        final Annotation[] v1 = (a1 instanceof Method) ? ((Method)a1).getDeclaredAnnotations() : ((a1 instanceof Constructor) ? ((Constructor)a1).getDeclaredAnnotations() : null);
        return this.getAnnotationNames(v1);
    }
    
    @Override
    public List<String> getParameterAnnotationNames(final Member a1, final int a2) {
        final Annotation[][] v1 = (a1 instanceof Method) ? ((Method)a1).getParameterAnnotations() : ((a1 instanceof Constructor) ? ((Constructor)a1).getParameterAnnotations() : null);
        return this.getAnnotationNames((Annotation[])((v1 != null) ? v1[a2] : null));
    }
    
    @Override
    public String getReturnTypeName(final Member a1) {
        return ((Method)a1).getReturnType().getName();
    }
    
    @Override
    public String getFieldName(final Field a1) {
        return a1.getName();
    }
    
    @Override
    public Class getOfCreateClassObject(final Vfs.File a1) throws Exception {
        return this.getOfCreateClassObject(a1, (ClassLoader[])null);
    }
    
    public Class getOfCreateClassObject(final Vfs.File a1, @Nullable final ClassLoader... a2) throws Exception {
        final String v1 = a1.getRelativePath().replace("/", ".").replace(".class", "");
        return ReflectionUtils.forName(v1, a2);
    }
    
    @Override
    public String getMethodModifier(final Member a1) {
        return Modifier.toString(a1.getModifiers());
    }
    
    @Override
    public String getMethodKey(final Class a1, final Member a2) {
        return this.getMethodName(a2) + "(" + Joiner.on(", ").join(this.getParameterNames(a2)) + ")";
    }
    
    @Override
    public String getMethodFullKey(final Class a1, final Member a2) {
        return this.getClassName(a1) + "." + this.getMethodKey(a1, a2);
    }
    
    @Override
    public boolean isPublic(final Object a1) {
        final Integer v1 = (a1 instanceof Class) ? ((Class)a1).getModifiers() : ((a1 instanceof Member) ? Integer.valueOf(((Member)a1).getModifiers()) : null);
        return v1 != null && Modifier.isPublic(v1);
    }
    
    @Override
    public String getClassName(final Class a1) {
        return a1.getName();
    }
    
    @Override
    public String getSuperclassName(final Class a1) {
        final Class v1 = a1.getSuperclass();
        return (v1 != null) ? v1.getName() : "";
    }
    
    @Override
    public List<String> getInterfacesNames(final Class v2) {
        final Class[] v3 = v2.getInterfaces();
        final List<String> v4 = new ArrayList<String>((v3 != null) ? v3.length : 0);
        if (v3 != null) {
            for (final Class a1 : v3) {
                v4.add(a1.getName());
            }
        }
        return v4;
    }
    
    @Override
    public boolean acceptsInput(final String a1) {
        return a1.endsWith(".class");
    }
    
    private List<String> getAnnotationNames(final Annotation[] v2) {
        final List<String> v3 = new ArrayList<String>(v2.length);
        for (final Annotation a1 : v2) {
            v3.add(a1.annotationType().getName());
        }
        return v3;
    }
    
    public static String getName(final Class v-1) {
        if (v-1.isArray()) {
            try {
                Class a1 = v-1;
                int v1 = 0;
                while (a1.isArray()) {
                    ++v1;
                    a1 = a1.getComponentType();
                }
                return a1.getName() + Utils.repeat("[]", v1);
            }
            catch (Throwable t) {}
        }
        return v-1.getName();
    }
    
    @Override
    public /* bridge */ String getMethodFullKey(final Object o, final Object o2) {
        return this.getMethodFullKey((Class)o, (Member)o2);
    }
    
    @Override
    public /* bridge */ String getMethodKey(final Object o, final Object o2) {
        return this.getMethodKey((Class)o, (Member)o2);
    }
    
    @Override
    public /* bridge */ String getMethodModifier(final Object o) {
        return this.getMethodModifier((Member)o);
    }
    
    @Override
    public /* bridge */ Object getOfCreateClassObject(final Vfs.File a1) throws Exception {
        return this.getOfCreateClassObject(a1);
    }
    
    @Override
    public /* bridge */ String getFieldName(final Object o) {
        return this.getFieldName((Field)o);
    }
    
    @Override
    public /* bridge */ String getReturnTypeName(final Object o) {
        return this.getReturnTypeName((Member)o);
    }
    
    @Override
    public /* bridge */ List getParameterAnnotationNames(final Object o, final int a2) {
        return this.getParameterAnnotationNames((Member)o, a2);
    }
    
    @Override
    public /* bridge */ List getMethodAnnotationNames(final Object o) {
        return this.getMethodAnnotationNames((Member)o);
    }
    
    @Override
    public /* bridge */ List getFieldAnnotationNames(final Object o) {
        return this.getFieldAnnotationNames((Field)o);
    }
    
    @Override
    public /* bridge */ List getClassAnnotationNames(final Object o) {
        return this.getClassAnnotationNames((Class)o);
    }
    
    @Override
    public /* bridge */ List getParameterNames(final Object o) {
        return this.getParameterNames((Member)o);
    }
    
    @Override
    public /* bridge */ String getMethodName(final Object o) {
        return this.getMethodName((Member)o);
    }
    
    @Override
    public /* bridge */ List getMethods(final Object o) {
        return this.getMethods((Class)o);
    }
    
    @Override
    public /* bridge */ List getFields(final Object o) {
        return this.getFields((Class)o);
    }
    
    @Override
    public /* bridge */ List getInterfacesNames(final Object o) {
        return this.getInterfacesNames((Class)o);
    }
    
    @Override
    public /* bridge */ String getSuperclassName(final Object o) {
        return this.getSuperclassName((Class)o);
    }
    
    @Override
    public /* bridge */ String getClassName(final Object o) {
        return this.getClassName((Class)o);
    }
}
