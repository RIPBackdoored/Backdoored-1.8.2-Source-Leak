package org.spongepowered.tools.obfuscation.mirror;

import java.lang.annotation.*;
import javax.lang.model.type.*;
import com.google.common.collect.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.tools.obfuscation.mirror.mapping.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import javax.lang.model.element.*;

public class TypeHandle
{
    private final String name;
    private final PackageElement pkg;
    private final TypeElement element;
    private TypeReference reference;
    
    public TypeHandle(final PackageElement a1, final String a2) {
        super();
        this.name = a2.replace('.', '/');
        this.pkg = a1;
        this.element = null;
    }
    
    public TypeHandle(final TypeElement a1) {
        super();
        this.pkg = TypeUtils.getPackage(a1);
        this.name = TypeUtils.getInternalName(a1);
        this.element = a1;
    }
    
    public TypeHandle(final DeclaredType a1) {
        this((TypeElement)a1.asElement());
    }
    
    @Override
    public final String toString() {
        return this.name.replace('/', '.');
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final PackageElement getPackage() {
        return this.pkg;
    }
    
    public final TypeElement getElement() {
        return this.element;
    }
    
    protected TypeElement getTargetElement() {
        return this.element;
    }
    
    public AnnotationHandle getAnnotation(final Class<? extends Annotation> a1) {
        return AnnotationHandle.of(this.getTargetElement(), a1);
    }
    
    public final List<? extends Element> getEnclosedElements() {
        return getEnclosedElements(this.getTargetElement());
    }
    
    public <T extends java.lang.Object> List<T> getEnclosedElements(final ElementKind... a1) {
        return getEnclosedElements(this.getTargetElement(), a1);
    }
    
    public TypeMirror getType() {
        return (this.getTargetElement() != null) ? this.getTargetElement().asType() : null;
    }
    
    public TypeHandle getSuperclass() {
        final TypeElement v1 = this.getTargetElement();
        if (v1 == null) {
            return null;
        }
        final TypeMirror v2 = v1.getSuperclass();
        if (v2 == null || v2.getKind() == TypeKind.NONE) {
            return null;
        }
        return new TypeHandle((DeclaredType)v2);
    }
    
    public List<TypeHandle> getInterfaces() {
        if (this.getTargetElement() == null) {
            return Collections.emptyList();
        }
        final ImmutableList.Builder<TypeHandle> builder = (ImmutableList.Builder<TypeHandle>)ImmutableList.builder();
        for (final TypeMirror v1 : this.getTargetElement().getInterfaces()) {
            builder.add(new TypeHandle((DeclaredType)v1));
        }
        return builder.build();
    }
    
    public boolean isPublic() {
        return this.getTargetElement() != null && this.getTargetElement().getModifiers().contains(Modifier.PUBLIC);
    }
    
    public boolean isImaginary() {
        return this.getTargetElement() == null;
    }
    
    public boolean isSimulated() {
        return false;
    }
    
    public final TypeReference getReference() {
        if (this.reference == null) {
            this.reference = new TypeReference(this);
        }
        return this.reference;
    }
    
    public MappingMethod getMappingMethod(final String a1, final String a2) {
        return new ResolvableMappingMethod(this, a1, a2);
    }
    
    public String findDescriptor(final MemberInfo v2) {
        String v3 = v2.desc;
        if (v3 == null) {
            for (final ExecutableElement a1 : this.getEnclosedElements(ElementKind.METHOD)) {
                if (a1.getSimpleName().toString().equals(v2.name)) {
                    v3 = TypeUtils.getDescriptor(a1);
                    break;
                }
            }
        }
        return v3;
    }
    
    public final FieldHandle findField(final VariableElement a1) {
        return this.findField(a1, true);
    }
    
    public final FieldHandle findField(final VariableElement a1, final boolean a2) {
        return this.findField(a1.getSimpleName().toString(), TypeUtils.getTypeName(a1.asType()), a2);
    }
    
    public final FieldHandle findField(final String a1, final String a2) {
        return this.findField(a1, a2, true);
    }
    
    public FieldHandle findField(final String a3, final String v1, final boolean v2) {
        final String v3 = TypeUtils.stripGenerics(v1);
        for (final VariableElement a4 : this.getEnclosedElements(ElementKind.FIELD)) {
            if (compareElement(a4, a3, v1, v2)) {
                return new FieldHandle(this.getTargetElement(), a4);
            }
            if (compareElement(a4, a3, v3, v2)) {
                return new FieldHandle(this.getTargetElement(), a4, true);
            }
        }
        return null;
    }
    
    public final MethodHandle findMethod(final ExecutableElement a1) {
        return this.findMethod(a1, true);
    }
    
    public final MethodHandle findMethod(final ExecutableElement a1, final boolean a2) {
        return this.findMethod(a1.getSimpleName().toString(), TypeUtils.getJavaSignature(a1), a2);
    }
    
    public final MethodHandle findMethod(final String a1, final String a2) {
        return this.findMethod(a1, a2, true);
    }
    
    public MethodHandle findMethod(final String a1, final String a2, final boolean a3) {
        final String v1 = TypeUtils.stripGenerics(a2);
        return findMethod(this, a1, a2, v1, a3);
    }
    
    protected static MethodHandle findMethod(final TypeHandle a2, final String a3, final String a4, final String a5, final boolean v1) {
        for (final ExecutableElement a6 : getEnclosedElements(a2.getTargetElement(), ElementKind.CONSTRUCTOR, ElementKind.METHOD)) {
            if (compareElement(a6, a3, a4, v1) || compareElement(a6, a3, a5, v1)) {
                return new MethodHandle(a2, a6);
            }
        }
        return null;
    }
    
    protected static boolean compareElement(final Element v-3, final String v-2, final String v-1, final boolean v0) {
        try {
            final String a1 = v-3.getSimpleName().toString();
            final String a2 = TypeUtils.getJavaSignature(v-3);
            final String a3 = TypeUtils.stripGenerics(a2);
            final boolean a4 = v0 ? v-2.equals(a1) : v-2.equalsIgnoreCase(a1);
            return a4 && (v-1.length() == 0 || v-1.equals(a2) || v-1.equals(a3));
        }
        catch (NullPointerException v) {
            return false;
        }
    }
    
    protected static <T extends java.lang.Object> List<T> getEnclosedElements(final TypeElement v1, final ElementKind... v2) {
        if (v2 == null || v2.length < 1) {
            return (List<T>)getEnclosedElements(v1);
        }
        if (v1 == null) {
            return Collections.emptyList();
        }
        final ImmutableList.Builder<T> v3 = (ImmutableList.Builder<T>)ImmutableList.builder();
        for (final Element a2 : v1.getEnclosedElements()) {
            for (final ElementKind a3 : v2) {
                if (a2.getKind() == a3) {
                    v3.add((T)a2);
                    break;
                }
            }
        }
        return v3.build();
    }
    
    protected static List<? extends Element> getEnclosedElements(final TypeElement a1) {
        return (a1 != null) ? a1.getEnclosedElements() : Collections.emptyList();
    }
}
