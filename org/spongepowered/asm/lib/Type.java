package org.spongepowered.asm.lib;

import java.lang.reflect.*;

public class Type
{
    public static final int VOID = 0;
    public static final int BOOLEAN = 1;
    public static final int CHAR = 2;
    public static final int BYTE = 3;
    public static final int SHORT = 4;
    public static final int INT = 5;
    public static final int FLOAT = 6;
    public static final int LONG = 7;
    public static final int DOUBLE = 8;
    public static final int ARRAY = 9;
    public static final int OBJECT = 10;
    public static final int METHOD = 11;
    public static final Type VOID_TYPE;
    public static final Type BOOLEAN_TYPE;
    public static final Type CHAR_TYPE;
    public static final Type BYTE_TYPE;
    public static final Type SHORT_TYPE;
    public static final Type INT_TYPE;
    public static final Type FLOAT_TYPE;
    public static final Type LONG_TYPE;
    public static final Type DOUBLE_TYPE;
    private final int sort;
    private final char[] buf;
    private final int off;
    private final int len;
    
    private Type(final int a1, final char[] a2, final int a3, final int a4) {
        super();
        this.sort = a1;
        this.buf = a2;
        this.off = a3;
        this.len = a4;
    }
    
    public static Type getType(final String a1) {
        return getType(a1.toCharArray(), 0);
    }
    
    public static Type getObjectType(final String a1) {
        final char[] v1 = a1.toCharArray();
        return new Type((v1[0] == '[') ? 9 : 10, v1, 0, v1.length);
    }
    
    public static Type getMethodType(final String a1) {
        return getType(a1.toCharArray(), 0);
    }
    
    public static Type getMethodType(final Type a1, final Type... a2) {
        return getType(getMethodDescriptor(a1, a2));
    }
    
    public static Type getType(final Class<?> a1) {
        if (!a1.isPrimitive()) {
            return getType(getDescriptor(a1));
        }
        if (a1 == Integer.TYPE) {
            return Type.INT_TYPE;
        }
        if (a1 == Void.TYPE) {
            return Type.VOID_TYPE;
        }
        if (a1 == Boolean.TYPE) {
            return Type.BOOLEAN_TYPE;
        }
        if (a1 == Byte.TYPE) {
            return Type.BYTE_TYPE;
        }
        if (a1 == Character.TYPE) {
            return Type.CHAR_TYPE;
        }
        if (a1 == Short.TYPE) {
            return Type.SHORT_TYPE;
        }
        if (a1 == Double.TYPE) {
            return Type.DOUBLE_TYPE;
        }
        if (a1 == Float.TYPE) {
            return Type.FLOAT_TYPE;
        }
        return Type.LONG_TYPE;
    }
    
    public static Type getType(final Constructor<?> a1) {
        return getType(getConstructorDescriptor(a1));
    }
    
    public static Type getType(final Method a1) {
        return getType(getMethodDescriptor(a1));
    }
    
    public static Type[] getArgumentTypes(final String v1) {
        final char[] v2 = v1.toCharArray();
        int v3 = 1;
        int v4 = 0;
        while (true) {
            final char a1 = v2[v3++];
            if (a1 == ')') {
                break;
            }
            if (a1 == 'L') {
                while (v2[v3++] != ';') {}
                ++v4;
            }
            else {
                if (a1 == '[') {
                    continue;
                }
                ++v4;
            }
        }
        Type[] v5;
        for (v5 = new Type[v4], v3 = 1, v4 = 0; v2[v3] != ')'; v3 += v5[v4].len + ((v5[v4].sort == 10) ? 2 : 0), ++v4) {
            v5[v4] = getType(v2, v3);
        }
        return v5;
    }
    
    public static Type[] getArgumentTypes(final Method v1) {
        final Class<?>[] v2 = v1.getParameterTypes();
        final Type[] v3 = new Type[v2.length];
        for (int a1 = v2.length - 1; a1 >= 0; --a1) {
            v3[a1] = getType(v2[a1]);
        }
        return v3;
    }
    
    public static Type getReturnType(final String v1) {
        final char[] v2 = v1.toCharArray();
        int v3 = 1;
        while (true) {
            final char a1 = v2[v3++];
            if (a1 == ')') {
                break;
            }
            if (a1 != 'L') {
                continue;
            }
            while (v2[v3++] != ';') {}
        }
        return getType(v2, v3);
    }
    
    public static Type getReturnType(final Method a1) {
        return getType(a1.getReturnType());
    }
    
    public static int getArgumentsAndReturnSizes(final String v1) {
        int v2 = 1;
        int v3 = 1;
        while (true) {
            char a1 = v1.charAt(v3++);
            if (a1 == ')') {
                break;
            }
            if (a1 == 'L') {
                while (v1.charAt(v3++) != ';') {}
                ++v2;
            }
            else if (a1 == '[') {
                while ((a1 = v1.charAt(v3)) == '[') {
                    ++v3;
                }
                if (a1 != 'D' && a1 != 'J') {
                    continue;
                }
                --v2;
            }
            else if (a1 == 'D' || a1 == 'J') {
                v2 += 2;
            }
            else {
                ++v2;
            }
        }
        char a1 = v1.charAt(v3);
        return v2 << 2 | ((a1 == 'V') ? 0 : ((a1 == 'D' || a1 == 'J') ? 2 : 1));
    }
    
    private static Type getType(final char[] v1, final int v2) {
        switch (v1[v2]) {
            case 'V': {
                return Type.VOID_TYPE;
            }
            case 'Z': {
                return Type.BOOLEAN_TYPE;
            }
            case 'C': {
                return Type.CHAR_TYPE;
            }
            case 'B': {
                return Type.BYTE_TYPE;
            }
            case 'S': {
                return Type.SHORT_TYPE;
            }
            case 'I': {
                return Type.INT_TYPE;
            }
            case 'F': {
                return Type.FLOAT_TYPE;
            }
            case 'J': {
                return Type.LONG_TYPE;
            }
            case 'D': {
                return Type.DOUBLE_TYPE;
            }
            case '[': {
                int a1;
                for (a1 = 1; v1[v2 + a1] == '['; ++a1) {}
                if (v1[v2 + a1] == 'L') {
                    ++a1;
                    while (v1[v2 + a1] != ';') {
                        ++a1;
                    }
                }
                return new Type(9, v1, v2, a1 + 1);
            }
            case 'L': {
                int a2;
                for (a2 = 1; v1[v2 + a2] != ';'; ++a2) {}
                return new Type(10, v1, v2 + 1, a2 - 1);
            }
            default: {
                return new Type(11, v1, v2, v1.length - v2);
            }
        }
    }
    
    public int getSort() {
        return this.sort;
    }
    
    public int getDimensions() {
        int v1;
        for (v1 = 1; this.buf[this.off + v1] == '['; ++v1) {}
        return v1;
    }
    
    public Type getElementType() {
        return getType(this.buf, this.off + this.getDimensions());
    }
    
    public String getClassName() {
        switch (this.sort) {
            case 0: {
                return "void";
            }
            case 1: {
                return "boolean";
            }
            case 2: {
                return "char";
            }
            case 3: {
                return "byte";
            }
            case 4: {
                return "short";
            }
            case 5: {
                return "int";
            }
            case 6: {
                return "float";
            }
            case 7: {
                return "long";
            }
            case 8: {
                return "double";
            }
            case 9: {
                final StringBuilder v0 = new StringBuilder(this.getElementType().getClassName());
                for (int v2 = this.getDimensions(); v2 > 0; --v2) {
                    v0.append("[]");
                }
                return v0.toString();
            }
            case 10: {
                return new String(this.buf, this.off, this.len).replace('/', '.');
            }
            default: {
                return null;
            }
        }
    }
    
    public String getInternalName() {
        return new String(this.buf, this.off, this.len);
    }
    
    public Type[] getArgumentTypes() {
        return getArgumentTypes(this.getDescriptor());
    }
    
    public Type getReturnType() {
        return getReturnType(this.getDescriptor());
    }
    
    public int getArgumentsAndReturnSizes() {
        return getArgumentsAndReturnSizes(this.getDescriptor());
    }
    
    public String getDescriptor() {
        final StringBuilder v1 = new StringBuilder();
        this.getDescriptor(v1);
        return v1.toString();
    }
    
    public static String getMethodDescriptor(final Type a2, final Type... v1) {
        final StringBuilder v2 = new StringBuilder();
        v2.append('(');
        for (int a3 = 0; a3 < v1.length; ++a3) {
            v1[a3].getDescriptor(v2);
        }
        v2.append(')');
        a2.getDescriptor(v2);
        return v2.toString();
    }
    
    private void getDescriptor(final StringBuilder a1) {
        if (this.buf == null) {
            a1.append((char)((this.off & 0xFF000000) >>> 24));
        }
        else if (this.sort == 10) {
            a1.append('L');
            a1.append(this.buf, this.off, this.len);
            a1.append(';');
        }
        else {
            a1.append(this.buf, this.off, this.len);
        }
    }
    
    public static String getInternalName(final Class<?> a1) {
        return a1.getName().replace('.', '/');
    }
    
    public static String getDescriptor(final Class<?> a1) {
        final StringBuilder v1 = new StringBuilder();
        getDescriptor(v1, a1);
        return v1.toString();
    }
    
    public static String getConstructorDescriptor(final Constructor<?> v1) {
        final Class<?>[] v2 = v1.getParameterTypes();
        final StringBuilder v3 = new StringBuilder();
        v3.append('(');
        for (int a1 = 0; a1 < v2.length; ++a1) {
            getDescriptor(v3, v2[a1]);
        }
        return v3.append(")V").toString();
    }
    
    public static String getMethodDescriptor(final Method v1) {
        final Class<?>[] v2 = v1.getParameterTypes();
        final StringBuilder v3 = new StringBuilder();
        v3.append('(');
        for (int a1 = 0; a1 < v2.length; ++a1) {
            getDescriptor(v3, v2[a1]);
        }
        v3.append(')');
        getDescriptor(v3, v1.getReturnType());
        return v3.toString();
    }
    
    private static void getDescriptor(final StringBuilder v-2, final Class<?> v-1) {
        Class<?> v0;
        for (v0 = v-1; !v0.isPrimitive(); v0 = v0.getComponentType()) {
            if (!v0.isArray()) {
                v-2.append('L');
                final String v2 = v0.getName();
                for (int v3 = v2.length(), v4 = 0; v4 < v3; ++v4) {
                    final char v5 = v2.charAt(v4);
                    v-2.append((v5 == '.') ? '/' : v5);
                }
                v-2.append(';');
                return;
            }
            v-2.append('[');
        }
        char v6 = '\0';
        if (v0 == Integer.TYPE) {
            final char a1 = 'I';
        }
        else if (v0 == Void.TYPE) {
            final char a2 = 'V';
        }
        else if (v0 == Boolean.TYPE) {
            v6 = 'Z';
        }
        else if (v0 == Byte.TYPE) {
            v6 = 'B';
        }
        else if (v0 == Character.TYPE) {
            v6 = 'C';
        }
        else if (v0 == Short.TYPE) {
            v6 = 'S';
        }
        else if (v0 == Double.TYPE) {
            v6 = 'D';
        }
        else if (v0 == Float.TYPE) {
            v6 = 'F';
        }
        else {
            v6 = 'J';
        }
        v-2.append(v6);
    }
    
    public int getSize() {
        return (this.buf == null) ? (this.off & 0xFF) : 1;
    }
    
    public int getOpcode(final int a1) {
        if (a1 == 46 || a1 == 79) {
            return a1 + ((this.buf == null) ? ((this.off & 0xFF00) >> 8) : 4);
        }
        return a1 + ((this.buf == null) ? ((this.off & 0xFF0000) >> 16) : 4);
    }
    
    @Override
    public boolean equals(final Object v-2) {
        if (this == v-2) {
            return true;
        }
        if (!(v-2 instanceof Type)) {
            return false;
        }
        final Type type = (Type)v-2;
        if (this.sort != type.sort) {
            return false;
        }
        if (this.sort >= 9) {
            if (this.len != type.len) {
                return false;
            }
            for (int a1 = this.off, v1 = type.off, v2 = a1 + this.len; a1 < v2; ++a1, ++v1) {
                if (this.buf[a1] != type.buf[v1]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int v0 = 13 * this.sort;
        if (this.sort >= 9) {
            for (int v2 = this.off, v3 = v2 + this.len; v2 < v3; ++v2) {
                v0 = 17 * (v0 + this.buf[v2]);
            }
        }
        return v0;
    }
    
    @Override
    public String toString() {
        return this.getDescriptor();
    }
    
    static {
        VOID_TYPE = new Type(0, null, 1443168256, 1);
        BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
        CHAR_TYPE = new Type(2, null, 1124075009, 1);
        BYTE_TYPE = new Type(3, null, 1107297537, 1);
        SHORT_TYPE = new Type(4, null, 1392510721, 1);
        INT_TYPE = new Type(5, null, 1224736769, 1);
        FLOAT_TYPE = new Type(6, null, 1174536705, 1);
        LONG_TYPE = new Type(7, null, 1241579778, 1);
        DOUBLE_TYPE = new Type(8, null, 1141048066, 1);
    }
}
