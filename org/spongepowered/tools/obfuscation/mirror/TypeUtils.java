package org.spongepowered.tools.obfuscation.mirror;

import java.util.*;
import org.spongepowered.asm.util.*;
import javax.lang.model.type.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;

public abstract class TypeUtils
{
    private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
    private static final String OBJECT_SIG = "java.lang.Object";
    private static final String OBJECT_REF = "java/lang/Object";
    
    private TypeUtils() {
        super();
    }
    
    public static PackageElement getPackage(final TypeMirror a1) {
        if (!(a1 instanceof DeclaredType)) {
            return null;
        }
        return getPackage((TypeElement)((DeclaredType)a1).asElement());
    }
    
    public static PackageElement getPackage(final TypeElement a1) {
        Element v1;
        for (v1 = a1.getEnclosingElement(); v1 != null && !(v1 instanceof PackageElement); v1 = v1.getEnclosingElement()) {}
        return (PackageElement)v1;
    }
    
    public static String getElementType(final Element a1) {
        if (a1 instanceof TypeElement) {
            return "TypeElement";
        }
        if (a1 instanceof ExecutableElement) {
            return "ExecutableElement";
        }
        if (a1 instanceof VariableElement) {
            return "VariableElement";
        }
        if (a1 instanceof PackageElement) {
            return "PackageElement";
        }
        if (a1 instanceof TypeParameterElement) {
            return "TypeParameterElement";
        }
        return a1.getClass().getSimpleName();
    }
    
    public static String stripGenerics(final String v-1) {
        final StringBuilder v0 = new StringBuilder();
        int v2 = 0;
        int v3 = 0;
        while (v2 < v-1.length()) {
            final char a1 = v-1.charAt(v2);
            if (a1 == '<') {
                ++v3;
            }
            if (v3 == 0) {
                v0.append(a1);
            }
            else if (a1 == '>') {
                --v3;
            }
            ++v2;
        }
        return v0.toString();
    }
    
    public static String getName(final VariableElement a1) {
        return (a1 != null) ? a1.getSimpleName().toString() : null;
    }
    
    public static String getName(final ExecutableElement a1) {
        return (a1 != null) ? a1.getSimpleName().toString() : null;
    }
    
    public static String getJavaSignature(final Element v0) {
        if (v0 instanceof ExecutableElement) {
            final ExecutableElement v = (ExecutableElement)v0;
            final StringBuilder v2 = new StringBuilder().append("(");
            boolean v3 = false;
            for (final VariableElement a1 : v.getParameters()) {
                if (v3) {
                    v2.append(',');
                }
                v2.append(getTypeName(a1.asType()));
                v3 = true;
            }
            v2.append(')').append(getTypeName(v.getReturnType()));
            return v2.toString();
        }
        return getTypeName(v0.asType());
    }
    
    public static String getJavaSignature(final String a1) {
        return new SignaturePrinter("", a1).setFullyQualified(true).toDescriptor();
    }
    
    public static String getTypeName(final TypeMirror a1) {
        switch (a1.getKind()) {
            case ARRAY: {
                return getTypeName(((ArrayType)a1).getComponentType()) + "[]";
            }
            case DECLARED: {
                return getTypeName((DeclaredType)a1);
            }
            case TYPEVAR: {
                return getTypeName(getUpperBound(a1));
            }
            case ERROR: {
                return "java.lang.Object";
            }
            default: {
                return a1.toString();
            }
        }
    }
    
    public static String getTypeName(final DeclaredType a1) {
        if (a1 == null) {
            return "java.lang.Object";
        }
        return getInternalName((TypeElement)a1.asElement()).replace('/', '.');
    }
    
    public static String getDescriptor(final Element a1) {
        if (a1 instanceof ExecutableElement) {
            return getDescriptor((ExecutableElement)a1);
        }
        if (a1 instanceof VariableElement) {
            return getInternalName((VariableElement)a1);
        }
        return getInternalName(a1.asType());
    }
    
    public static String getDescriptor(final ExecutableElement v1) {
        if (v1 == null) {
            return null;
        }
        final StringBuilder v2 = new StringBuilder();
        for (final VariableElement a1 : v1.getParameters()) {
            v2.append(getInternalName(a1));
        }
        final String v3 = getInternalName(v1.getReturnType());
        return String.format("(%s)%s", v2, v3);
    }
    
    public static String getInternalName(final VariableElement a1) {
        if (a1 == null) {
            return null;
        }
        return getInternalName(a1.asType());
    }
    
    public static String getInternalName(final TypeMirror a1) {
        switch (a1.getKind()) {
            case ARRAY: {
                return "[" + getInternalName(((ArrayType)a1).getComponentType());
            }
            case DECLARED: {
                return "L" + getInternalName((DeclaredType)a1) + ";";
            }
            case TYPEVAR: {
                return "L" + getInternalName(getUpperBound(a1)) + ";";
            }
            case BOOLEAN: {
                return "Z";
            }
            case BYTE: {
                return "B";
            }
            case CHAR: {
                return "C";
            }
            case DOUBLE: {
                return "D";
            }
            case FLOAT: {
                return "F";
            }
            case INT: {
                return "I";
            }
            case LONG: {
                return "J";
            }
            case SHORT: {
                return "S";
            }
            case VOID: {
                return "V";
            }
            case ERROR: {
                return "Ljava/lang/Object;";
            }
            default: {
                throw new IllegalArgumentException("Unable to parse type symbol " + a1 + " with " + a1.getKind() + " to equivalent bytecode type");
            }
        }
    }
    
    public static String getInternalName(final DeclaredType a1) {
        if (a1 == null) {
            return "java/lang/Object";
        }
        return getInternalName((TypeElement)a1.asElement());
    }
    
    public static String getInternalName(final TypeElement a1) {
        if (a1 == null) {
            return null;
        }
        final StringBuilder v1 = new StringBuilder();
        v1.append(a1.getSimpleName());
        for (Element v2 = a1.getEnclosingElement(); v2 != null; v2 = v2.getEnclosingElement()) {
            if (v2 instanceof TypeElement) {
                v1.insert(0, "$").insert(0, v2.getSimpleName());
            }
            else if (v2 instanceof PackageElement) {
                v1.insert(0, "/").insert(0, ((PackageElement)v2).getQualifiedName().toString().replace('.', '/'));
            }
        }
        return v1.toString();
    }
    
    private static DeclaredType getUpperBound(final TypeMirror v0) {
        try {
            return getUpperBound0(v0, 5);
        }
        catch (IllegalStateException a1) {
            throw new IllegalArgumentException("Type symbol \"" + v0 + "\" is too complex", a1);
        }
        catch (IllegalArgumentException v) {
            throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + v0, v);
        }
    }
    
    private static DeclaredType getUpperBound0(final TypeMirror v-1, int v0) {
        if (v0 == 0) {
            throw new IllegalStateException("Generic symbol \"" + v-1 + "\" is too complex, exceeded " + 5 + " iterations attempting to determine upper bound");
        }
        if (v-1 instanceof DeclaredType) {
            return (DeclaredType)v-1;
        }
        if (v-1 instanceof TypeVariable) {
            try {
                final TypeMirror a1 = ((TypeVariable)v-1).getUpperBound();
                return getUpperBound0(a1, --v0);
            }
            catch (IllegalStateException a2) {
                throw a2;
            }
            catch (IllegalArgumentException v) {
                throw v;
            }
            catch (Exception v2) {
                throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + v-1);
            }
        }
        return null;
    }
    
    public static boolean isAssignable(final ProcessingEnvironment a3, final TypeMirror v1, final TypeMirror v2) {
        final boolean v3 = a3.getTypeUtils().isAssignable(v1, v2);
        if (!v3 && v1 instanceof DeclaredType && v2 instanceof DeclaredType) {
            final TypeMirror a4 = toRawType(a3, (DeclaredType)v1);
            final TypeMirror a5 = toRawType(a3, (DeclaredType)v2);
            return a3.getTypeUtils().isAssignable(a4, a5);
        }
        return v3;
    }
    
    private static TypeMirror toRawType(final ProcessingEnvironment a1, final DeclaredType a2) {
        return a1.getElementUtils().getTypeElement(((TypeElement)a2.asElement()).getQualifiedName()).asType();
    }
    
    public static Visibility getVisibility(final Element v1) {
        if (v1 == null) {
            return null;
        }
        for (final Modifier a1 : v1.getModifiers()) {
            switch (a1) {
                case PUBLIC: {
                    return Visibility.PUBLIC;
                }
                case PROTECTED: {
                    return Visibility.PROTECTED;
                }
                case PRIVATE: {
                    return Visibility.PRIVATE;
                }
                default: {
                    continue;
                }
            }
        }
        return Visibility.PACKAGE;
    }
}
