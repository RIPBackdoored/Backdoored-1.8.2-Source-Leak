package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.base.*;

public class SignaturePrinter
{
    private final String name;
    private final Type returnType;
    private final Type[] argTypes;
    private final String[] argNames;
    private String modifiers;
    private boolean fullyQualified;
    
    public SignaturePrinter(final MethodNode a1) {
        this(a1.name, Type.VOID_TYPE, Type.getArgumentTypes(a1.desc));
        this.setModifiers(a1);
    }
    
    public SignaturePrinter(final MethodNode a1, final String[] a2) {
        this(a1.name, Type.VOID_TYPE, Type.getArgumentTypes(a1.desc), a2);
        this.setModifiers(a1);
    }
    
    public SignaturePrinter(final MemberInfo a1) {
        this(a1.name, a1.desc);
    }
    
    public SignaturePrinter(final String a1, final String a2) {
        this(a1, Type.getReturnType(a2), Type.getArgumentTypes(a2));
    }
    
    public SignaturePrinter(final String v1, final Type v2, final Type[] v3) {
        super();
        this.modifiers = "private void";
        this.name = v1;
        this.returnType = v2;
        this.argTypes = new Type[v3.length];
        this.argNames = new String[v3.length];
        int a1 = 0;
        int a2 = 0;
        while (a1 < v3.length) {
            if (v3[a1] != null) {
                this.argTypes[a1] = v3[a1];
                this.argNames[a1] = "var" + a2++;
            }
            ++a1;
        }
    }
    
    public SignaturePrinter(final String a3, final Type v1, final LocalVariableNode[] v2) {
        super();
        this.modifiers = "private void";
        this.name = a3;
        this.returnType = v1;
        this.argTypes = new Type[v2.length];
        this.argNames = new String[v2.length];
        for (int a4 = 0; a4 < v2.length; ++a4) {
            if (v2[a4] != null) {
                this.argTypes[a4] = Type.getType(v2[a4].desc);
                this.argNames[a4] = v2[a4].name;
            }
        }
    }
    
    public SignaturePrinter(final String a1, final Type a2, final Type[] a3, final String[] a4) {
        super();
        this.modifiers = "private void";
        this.name = a1;
        this.returnType = a2;
        this.argTypes = a3;
        this.argNames = a4;
        if (this.argTypes.length > this.argNames.length) {
            throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", this.argNames.length, this.argTypes.length));
        }
    }
    
    public String getFormattedArgs() {
        return this.appendArgs(new StringBuilder(), true, true).toString();
    }
    
    public String getReturnType() {
        return getTypeName(this.returnType, false, this.fullyQualified);
    }
    
    public void setModifiers(final MethodNode a1) {
        final String v1 = getTypeName(Type.getReturnType(a1.desc), false, this.fullyQualified);
        if ((a1.access & 0x1) != 0x0) {
            this.setModifiers("public " + v1);
        }
        else if ((a1.access & 0x4) != 0x0) {
            this.setModifiers("protected " + v1);
        }
        else if ((a1.access & 0x2) != 0x0) {
            this.setModifiers("private " + v1);
        }
        else {
            this.setModifiers(v1);
        }
    }
    
    public SignaturePrinter setModifiers(final String a1) {
        this.modifiers = a1.replace("${returnType}", this.getReturnType());
        return this;
    }
    
    public SignaturePrinter setFullyQualified(final boolean a1) {
        this.fullyQualified = a1;
        return this;
    }
    
    public boolean isFullyQualified() {
        return this.fullyQualified;
    }
    
    @Override
    public String toString() {
        return this.appendArgs(new StringBuilder().append(this.modifiers).append(" ").append(this.name), false, true).toString();
    }
    
    public String toDescriptor() {
        final StringBuilder v1 = this.appendArgs(new StringBuilder(), true, false);
        return v1.append(getTypeName(this.returnType, false, this.fullyQualified)).toString();
    }
    
    private StringBuilder appendArgs(final StringBuilder v2, final boolean v3, final boolean v4) {
        v2.append('(');
        for (int a3 = 0; a3 < this.argTypes.length; ++a3) {
            if (this.argTypes[a3] != null) {
                if (a3 > 0) {
                    v2.append(',');
                    if (v4) {
                        v2.append(' ');
                    }
                }
                try {
                    final String a4 = v3 ? null : (Strings.isNullOrEmpty(this.argNames[a3]) ? ("unnamed" + a3) : this.argNames[a3]);
                    this.appendType(v2, this.argTypes[a3], a4);
                }
                catch (Exception a5) {
                    throw new RuntimeException(a5);
                }
            }
        }
        return v2.append(")");
    }
    
    private StringBuilder appendType(final StringBuilder a1, final Type a2, final String a3) {
        switch (a2.getSort()) {
            case 9: {
                return appendArraySuffix(this.appendType(a1, a2.getElementType(), a3), a2);
            }
            case 10: {
                return this.appendType(a1, a2.getClassName(), a3);
            }
            default: {
                a1.append(getTypeName(a2, false, this.fullyQualified));
                if (a3 != null) {
                    a1.append(' ').append(a3);
                }
                return a1;
            }
        }
    }
    
    private StringBuilder appendType(final StringBuilder a1, String a2, final String a3) {
        if (!this.fullyQualified) {
            a2 = a2.substring(a2.lastIndexOf(46) + 1);
        }
        a1.append(a2);
        if (a2.endsWith("CallbackInfoReturnable")) {
            a1.append('<').append(getTypeName(this.returnType, true, this.fullyQualified)).append('>');
        }
        if (a3 != null) {
            a1.append(' ').append(a3);
        }
        return a1;
    }
    
    public static String getTypeName(final Type a1, final boolean a2) {
        return getTypeName(a1, a2, false);
    }
    
    public static String getTypeName(final Type a2, final boolean a3, final boolean v1) {
        switch (a2.getSort()) {
            case 0: {
                return a3 ? "Void" : "void";
            }
            case 1: {
                return a3 ? "Boolean" : "boolean";
            }
            case 2: {
                return a3 ? "Character" : "char";
            }
            case 3: {
                return a3 ? "Byte" : "byte";
            }
            case 4: {
                return a3 ? "Short" : "short";
            }
            case 5: {
                return a3 ? "Integer" : "int";
            }
            case 6: {
                return a3 ? "Float" : "float";
            }
            case 7: {
                return a3 ? "Long" : "long";
            }
            case 8: {
                return a3 ? "Double" : "double";
            }
            case 9: {
                return getTypeName(a2.getElementType(), a3, v1) + arraySuffix(a2);
            }
            case 10: {
                String a4 = a2.getClassName();
                if (!v1) {
                    a4 = a4.substring(a4.lastIndexOf(46) + 1);
                }
                return a4;
            }
            default: {
                return "Object";
            }
        }
    }
    
    private static String arraySuffix(final Type a1) {
        return Strings.repeat("[]", a1.getDimensions());
    }
    
    private static StringBuilder appendArraySuffix(final StringBuilder a2, final Type v1) {
        for (int a3 = 0; a3 < v1.getDimensions(); ++a3) {
            a2.append("[]");
        }
        return a2;
    }
}
