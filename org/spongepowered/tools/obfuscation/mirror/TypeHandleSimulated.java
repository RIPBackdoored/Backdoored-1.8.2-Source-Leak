package org.spongepowered.tools.obfuscation.mirror;

import javax.lang.model.element.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.util.*;
import javax.lang.model.type.*;
import java.util.*;

public class TypeHandleSimulated extends TypeHandle
{
    private final TypeElement simulatedType;
    
    public TypeHandleSimulated(final String a1, final TypeMirror a2) {
        this(TypeUtils.getPackage(a2), a1, a2);
    }
    
    public TypeHandleSimulated(final PackageElement a1, final String a2, final TypeMirror a3) {
        super(a1, a2);
        this.simulatedType = (TypeElement)((DeclaredType)a3).asElement();
    }
    
    @Override
    protected TypeElement getTargetElement() {
        return this.simulatedType;
    }
    
    @Override
    public boolean isPublic() {
        return true;
    }
    
    @Override
    public boolean isImaginary() {
        return false;
    }
    
    @Override
    public boolean isSimulated() {
        return true;
    }
    
    @Override
    public AnnotationHandle getAnnotation(final Class<? extends Annotation> a1) {
        return null;
    }
    
    @Override
    public TypeHandle getSuperclass() {
        return null;
    }
    
    @Override
    public String findDescriptor(final MemberInfo a1) {
        return (a1 != null) ? a1.desc : null;
    }
    
    @Override
    public FieldHandle findField(final String a1, final String a2, final boolean a3) {
        return new FieldHandle(null, a1, a2);
    }
    
    @Override
    public MethodHandle findMethod(final String a1, final String a2, final boolean a3) {
        return new MethodHandle((TypeHandle)null, a1, a2);
    }
    
    @Override
    public MappingMethod getMappingMethod(final String a1, final String a2) {
        final String v1 = new SignaturePrinter(a1, a2).setFullyQualified(true).toDescriptor();
        final String v2 = TypeUtils.stripGenerics(v1);
        final MethodHandle v3 = findMethodRecursive(this, a1, v1, v2, true);
        return (v3 != null) ? v3.asMapping(true) : super.getMappingMethod(a1, a2);
    }
    
    private static MethodHandle findMethodRecursive(final TypeHandle a2, final String a3, final String a4, final String a5, final boolean v1) {
        final TypeElement v2 = a2.getTargetElement();
        if (v2 == null) {
            return null;
        }
        MethodHandle v3 = TypeHandle.findMethod(a2, a3, a4, a5, v1);
        if (v3 != null) {
            return v3;
        }
        for (final TypeMirror a6 : v2.getInterfaces()) {
            v3 = findMethodRecursive(a6, a3, a4, a5, v1);
            if (v3 != null) {
                return v3;
            }
        }
        final TypeMirror v4 = v2.getSuperclass();
        if (v4 == null || v4.getKind() == TypeKind.NONE) {
            return null;
        }
        return findMethodRecursive(v4, a3, a4, a5, v1);
    }
    
    private static MethodHandle findMethodRecursive(final TypeMirror a1, final String a2, final String a3, final String a4, final boolean a5) {
        if (!(a1 instanceof DeclaredType)) {
            return null;
        }
        final TypeElement v1 = (TypeElement)((DeclaredType)a1).asElement();
        return findMethodRecursive(new TypeHandle(v1), a2, a3, a4, a5);
    }
}
