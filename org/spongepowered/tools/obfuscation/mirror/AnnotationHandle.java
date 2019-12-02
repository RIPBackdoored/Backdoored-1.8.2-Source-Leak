package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.*;
import java.util.*;
import java.lang.annotation.*;
import javax.lang.model.element.*;

public final class AnnotationHandle
{
    public static final AnnotationHandle MISSING;
    private final AnnotationMirror annotation;
    
    private AnnotationHandle(final AnnotationMirror a1) {
        super();
        this.annotation = a1;
    }
    
    public AnnotationMirror asMirror() {
        return this.annotation;
    }
    
    public boolean exists() {
        return this.annotation != null;
    }
    
    @Override
    public String toString() {
        if (this.annotation == null) {
            return "@{UnknownAnnotation}";
        }
        return "@" + (Object)this.annotation.getAnnotationType().asElement().getSimpleName();
    }
    
    public <T> T getValue(final String v1, final T v2) {
        if (this.annotation == null) {
            return v2;
        }
        final AnnotationValue v3 = this.getAnnotationValue(v1);
        if (!(v2 instanceof Enum) || v3 == null) {
            return (T)((v3 != null) ? v3.getValue() : v2);
        }
        final VariableElement a1 = (VariableElement)v3.getValue();
        if (a1 == null) {
            return v2;
        }
        return Enum.valueOf(v2.getClass(), a1.getSimpleName().toString());
    }
    
    public <T> T getValue() {
        return this.getValue("value", (T)null);
    }
    
    public <T> T getValue(final String a1) {
        return this.getValue(a1, (T)null);
    }
    
    public boolean getBoolean(final String a1, final boolean a2) {
        return this.getValue(a1, a2);
    }
    
    public AnnotationHandle getAnnotation(final String v2) {
        final Object v3 = this.getValue(v2);
        if (v3 instanceof AnnotationMirror) {
            return of((AnnotationMirror)v3);
        }
        if (v3 instanceof AnnotationValue) {
            final Object a1 = ((AnnotationValue)v3).getValue();
            if (a1 instanceof AnnotationMirror) {
                return of((AnnotationMirror)a1);
            }
        }
        return null;
    }
    
    public <T> List<T> getList() {
        return this.getList("value");
    }
    
    public <T> List<T> getList(final String a1) {
        final List<AnnotationValue> v1 = this.getValue(a1, Collections.emptyList());
        return unwrapAnnotationValueList(v1);
    }
    
    public List<AnnotationHandle> getAnnotationList(final String v2) {
        final Object v3 = this.getValue(v2, (Object)null);
        if (v3 == null) {
            return Collections.emptyList();
        }
        if (v3 instanceof AnnotationMirror) {
            return ImmutableList.of(of((AnnotationMirror)v3));
        }
        final List<AnnotationValue> v4 = (List<AnnotationValue>)v3;
        final List<AnnotationHandle> v5 = new ArrayList<AnnotationHandle>(v4.size());
        for (final AnnotationValue a1 : v4) {
            v5.add(new AnnotationHandle((AnnotationMirror)a1.getValue()));
        }
        return Collections.unmodifiableList((List<? extends AnnotationHandle>)v5);
    }
    
    protected AnnotationValue getAnnotationValue(final String v2) {
        for (final ExecutableElement a1 : this.annotation.getElementValues().keySet()) {
            if (a1.getSimpleName().contentEquals(v2)) {
                return (AnnotationValue)this.annotation.getElementValues().get(a1);
            }
        }
        return null;
    }
    
    protected static <T> List<T> unwrapAnnotationValueList(final List<AnnotationValue> v1) {
        if (v1 == null) {
            return Collections.emptyList();
        }
        final List<T> v2 = new ArrayList<T>(v1.size());
        for (final AnnotationValue a1 : v1) {
            v2.add((T)a1.getValue());
        }
        return v2;
    }
    
    protected static AnnotationMirror getAnnotation(final Element v-3, final Class<? extends Annotation> v-2) {
        if (v-3 == null) {
            return null;
        }
        final List<? extends AnnotationMirror> annotationMirrors = v-3.getAnnotationMirrors();
        if (annotationMirrors == null) {
            return null;
        }
        for (final AnnotationMirror v1 : annotationMirrors) {
            final Element a1 = v1.getAnnotationType().asElement();
            if (!(a1 instanceof TypeElement)) {
                continue;
            }
            final TypeElement a2 = (TypeElement)a1;
            if (a2.getQualifiedName().contentEquals(v-2.getName())) {
                return v1;
            }
        }
        return null;
    }
    
    public static AnnotationHandle of(final AnnotationMirror a1) {
        return new AnnotationHandle(a1);
    }
    
    public static AnnotationHandle of(final Element a1, final Class<? extends Annotation> a2) {
        return new AnnotationHandle(getAnnotation(a1, a2));
    }
    
    static {
        MISSING = new AnnotationHandle(null);
    }
}
