package org.yaml.snakeyaml.introspector;

import org.yaml.snakeyaml.error.*;
import java.lang.annotation.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.logging.*;

public class PropertySubstitute extends Property
{
    private static final Logger log;
    protected Class<?> targetType;
    private final String readMethod;
    private final String writeMethod;
    private transient Method read;
    private transient Method write;
    private Field field;
    protected Class<?>[] parameters;
    private Property delegate;
    private boolean filler;
    
    public PropertySubstitute(final String a1, final Class<?> a2, final String a3, final String a4, final Class<?>... a5) {
        super(a1, a2);
        this.readMethod = a3;
        this.writeMethod = a4;
        this.setActualTypeArguments(a5);
        this.filler = false;
    }
    
    public PropertySubstitute(final String a1, final Class<?> a2, final Class<?>... a3) {
        this(a1, a2, null, (String)null, a3);
    }
    
    @Override
    public Class<?>[] getActualTypeArguments() {
        if (this.parameters == null && this.delegate != null) {
            return this.delegate.getActualTypeArguments();
        }
        return this.parameters;
    }
    
    public void setActualTypeArguments(final Class<?>... a1) {
        if (a1 != null && a1.length > 0) {
            this.parameters = a1;
        }
        else {
            this.parameters = null;
        }
    }
    
    @Override
    public void set(final Object v-3, final Object v-2) throws Exception {
        if (this.write != null) {
            if (!this.filler) {
                this.write.invoke(v-3, v-2);
            }
            else if (v-2 != null) {
                if (v-2 instanceof Collection) {
                    final Collection<?> a2 = (Collection<?>)v-2;
                    for (final Object a3 : a2) {
                        this.write.invoke(v-3, a3);
                    }
                }
                else if (v-2 instanceof Map) {
                    final Map<?, ?> map = (Map<?, ?>)v-2;
                    for (final Map.Entry<?, ?> v1 : map.entrySet()) {
                        this.write.invoke(v-3, v1.getKey(), v1.getValue());
                    }
                }
                else if (v-2.getClass().isArray()) {
                    for (int length = Array.getLength(v-2), v2 = 0; v2 < length; ++v2) {
                        this.write.invoke(v-3, Array.get(v-2, v2));
                    }
                }
            }
        }
        else if (this.field != null) {
            this.field.set(v-3, v-2);
        }
        else if (this.delegate != null) {
            this.delegate.set(v-3, v-2);
        }
        else {
            PropertySubstitute.log.warning("No setter/delegate for '" + this.getName() + "' on object " + v-3);
        }
    }
    
    @Override
    public Object get(final Object v2) {
        try {
            if (this.read != null) {
                return this.read.invoke(v2, new Object[0]);
            }
            if (this.field != null) {
                return this.field.get(v2);
            }
        }
        catch (Exception a1) {
            throw new YAMLException("Unable to find getter for property '" + this.getName() + "' on object " + v2 + ":" + a1);
        }
        if (this.delegate != null) {
            return this.delegate.get(v2);
        }
        throw new YAMLException("No getter or delegate for property '" + this.getName() + "' on object " + v2);
    }
    
    public List<Annotation> getAnnotations() {
        Annotation[] v1 = null;
        if (this.read != null) {
            v1 = this.read.getAnnotations();
        }
        else if (this.field != null) {
            v1 = this.field.getAnnotations();
        }
        return (v1 != null) ? Arrays.asList(v1) : this.delegate.getAnnotations();
    }
    
    public <A extends java.lang.Object> A getAnnotation(final Class<A> v0) {
        A v = null;
        if (this.read != null) {
            final A a1 = this.read.getAnnotation(v0);
        }
        else if (this.field != null) {
            v = this.field.getAnnotation(v0);
        }
        else {
            v = (A)this.delegate.getAnnotation((Class)v0);
        }
        return v;
    }
    
    public void setTargetType(final Class<?> v-5) {
        if (this.targetType != v-5) {
            this.targetType = v-5;
            final String name = this.getName();
            for (Class<?> superclass = v-5; superclass != null; superclass = superclass.getSuperclass()) {
                final Field[] declaredFields = superclass.getDeclaredFields();
                final int length = declaredFields.length;
                int i = 0;
                while (i < length) {
                    final Field v1 = declaredFields[i];
                    if (v1.getName().equals(name)) {
                        final int a1 = v1.getModifiers();
                        if (!Modifier.isStatic(a1) && !Modifier.isTransient(a1)) {
                            v1.setAccessible(true);
                            this.field = v1;
                            break;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
            if (this.field == null && PropertySubstitute.log.isLoggable(Level.FINE)) {
                PropertySubstitute.log.fine(String.format("Failed to find field for %s.%s", v-5.getName(), this.getName()));
            }
            if (this.readMethod != null) {
                this.read = this.discoverMethod(v-5, this.readMethod, (Class<?>[])new Class[0]);
            }
            if (this.writeMethod != null) {
                this.filler = false;
                this.write = this.discoverMethod(v-5, this.writeMethod, this.getType());
                if (this.write == null && this.parameters != null) {
                    this.filler = true;
                    this.write = this.discoverMethod(v-5, this.writeMethod, this.parameters);
                }
            }
        }
    }
    
    private Method discoverMethod(final Class<?> v-6, final String v-5, final Class<?>... v-4) {
        for (Class<?> superclass = v-6; superclass != null; superclass = superclass.getSuperclass()) {
            for (final Method v1 : superclass.getDeclaredMethods()) {
                if (v-5.equals(v1.getName())) {
                    final Class<?>[] a2 = v1.getParameterTypes();
                    if (a2.length == v-4.length) {
                        boolean a3 = true;
                        for (int a4 = 0; a4 < a2.length; ++a4) {
                            if (!a2[a4].isAssignableFrom(v-4[a4])) {
                                a3 = false;
                            }
                        }
                        if (a3) {
                            v1.setAccessible(true);
                            return v1;
                        }
                    }
                }
            }
        }
        if (PropertySubstitute.log.isLoggable(Level.FINE)) {
            PropertySubstitute.log.fine(String.format("Failed to find [%s(%d args)] for %s.%s", v-5, v-4.length, this.targetType.getName(), this.getName()));
        }
        return null;
    }
    
    @Override
    public String getName() {
        final String v1 = super.getName();
        if (v1 != null) {
            return v1;
        }
        return (this.delegate != null) ? this.delegate.getName() : null;
    }
    
    @Override
    public Class<?> getType() {
        final Class<?> v1 = super.getType();
        if (v1 != null) {
            return v1;
        }
        return (this.delegate != null) ? this.delegate.getType() : null;
    }
    
    @Override
    public boolean isReadable() {
        return this.read != null || this.field != null || (this.delegate != null && this.delegate.isReadable());
    }
    
    @Override
    public boolean isWritable() {
        return this.write != null || this.field != null || (this.delegate != null && this.delegate.isWritable());
    }
    
    public void setDelegate(final Property a1) {
        this.delegate = a1;
        if (this.writeMethod != null && this.write == null && !this.filler) {
            this.filler = true;
            this.write = this.discoverMethod(this.targetType, this.writeMethod, this.getActualTypeArguments());
        }
    }
    
    static {
        log = Logger.getLogger(PropertySubstitute.class.getPackage().getName());
    }
}
