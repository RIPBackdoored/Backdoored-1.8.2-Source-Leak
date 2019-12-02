package javassist.bytecode;

import java.util.*;
import javassist.*;

public class Descriptor
{
    public Descriptor() {
        super();
    }
    
    public static String toJvmName(final String a1) {
        return a1.replace('.', '/');
    }
    
    public static String toJavaName(final String a1) {
        return a1.replace('/', '.');
    }
    
    public static String toJvmName(final CtClass a1) {
        if (a1.isArray()) {
            return of(a1);
        }
        return toJvmName(a1.getName());
    }
    
    public static String toClassName(final String v-3) {
        int n = 0;
        int n2;
        char v0;
        for (n2 = 0, v0 = v-3.charAt(0); v0 == '['; v0 = v-3.charAt(++n2)) {
            ++n;
        }
        String v2;
        if (v0 == 'L') {
            final int a1 = v-3.indexOf(59, n2++);
            v2 = v-3.substring(n2, a1).replace('/', '.');
            n2 = a1;
        }
        else if (v0 == 'V') {
            v2 = "void";
        }
        else if (v0 == 'I') {
            v2 = "int";
        }
        else if (v0 == 'B') {
            v2 = "byte";
        }
        else if (v0 == 'J') {
            v2 = "long";
        }
        else if (v0 == 'D') {
            v2 = "double";
        }
        else if (v0 == 'F') {
            v2 = "float";
        }
        else if (v0 == 'C') {
            v2 = "char";
        }
        else if (v0 == 'S') {
            v2 = "short";
        }
        else {
            if (v0 != 'Z') {
                throw new RuntimeException("bad descriptor: " + v-3);
            }
            v2 = "boolean";
        }
        if (n2 + 1 != v-3.length()) {
            throw new RuntimeException("multiple descriptors?: " + v-3);
        }
        if (n == 0) {
            return v2;
        }
        final StringBuffer v3 = new StringBuffer(v2);
        do {
            v3.append("[]");
        } while (--n > 0);
        return v3.toString();
    }
    
    public static String of(final String a1) {
        if (a1.equals("void")) {
            return "V";
        }
        if (a1.equals("int")) {
            return "I";
        }
        if (a1.equals("byte")) {
            return "B";
        }
        if (a1.equals("long")) {
            return "J";
        }
        if (a1.equals("double")) {
            return "D";
        }
        if (a1.equals("float")) {
            return "F";
        }
        if (a1.equals("char")) {
            return "C";
        }
        if (a1.equals("short")) {
            return "S";
        }
        if (a1.equals("boolean")) {
            return "Z";
        }
        return "L" + toJvmName(a1) + ";";
    }
    
    public static String rename(final String a3, final String v1, final String v2) {
        if (a3.indexOf(v1) < 0) {
            return a3;
        }
        final StringBuffer v3 = new StringBuffer();
        int v4 = 0;
        int v5 = 0;
        while (true) {
            final int a4 = a3.indexOf(76, v5);
            if (a4 < 0) {
                break;
            }
            if (a3.startsWith(v1, a4 + 1) && a3.charAt(a4 + v1.length() + 1) == ';') {
                v3.append(a3.substring(v4, a4));
                v3.append('L');
                v3.append(v2);
                v3.append(';');
                v5 = (v4 = a4 + v1.length() + 2);
            }
            else {
                v5 = a3.indexOf(59, a4) + 1;
                if (v5 < 1) {
                    break;
                }
                continue;
            }
        }
        if (v4 == 0) {
            return a3;
        }
        final int a5 = a3.length();
        if (v4 < a5) {
            v3.append(a3.substring(v4, a5));
        }
        return v3.toString();
    }
    
    public static String rename(final String v-6, final Map v-5) {
        if (v-5 == null) {
            return v-6;
        }
        final StringBuffer sb = new StringBuffer();
        int n = 0;
        int n2 = 0;
        while (true) {
            final int a1 = v-6.indexOf(76, n2);
            if (a1 < 0) {
                break;
            }
            final int a2 = v-6.indexOf(59, a1);
            if (a2 < 0) {
                break;
            }
            n2 = a2 + 1;
            final String v1 = v-6.substring(a1 + 1, a2);
            final String v2 = v-5.get(v1);
            if (v2 == null) {
                continue;
            }
            sb.append(v-6.substring(n, a1));
            sb.append('L');
            sb.append(v2);
            sb.append(';');
            n = n2;
        }
        if (n == 0) {
            return v-6;
        }
        final int length = v-6.length();
        if (n < length) {
            sb.append(v-6.substring(n, length));
        }
        return sb.toString();
    }
    
    public static String of(final CtClass a1) {
        final StringBuffer v1 = new StringBuffer();
        toDescriptor(v1, a1);
        return v1.toString();
    }
    
    private static void toDescriptor(final StringBuffer v-1, final CtClass v0) {
        if (v0.isArray()) {
            v-1.append('[');
            try {
                toDescriptor(v-1, v0.getComponentType());
            }
            catch (NotFoundException a2) {
                v-1.append('L');
                final String a1 = v0.getName();
                v-1.append(toJvmName(a1.substring(0, a1.length() - 2)));
                v-1.append(';');
            }
        }
        else if (v0.isPrimitive()) {
            final CtPrimitiveType v = (CtPrimitiveType)v0;
            v-1.append(v.getDescriptor());
        }
        else {
            v-1.append('L');
            v-1.append(v0.getName().replace('.', '/'));
            v-1.append(';');
        }
    }
    
    public static String ofConstructor(final CtClass[] a1) {
        return ofMethod(CtClass.voidType, a1);
    }
    
    public static String ofMethod(final CtClass v1, final CtClass[] v2) {
        final StringBuffer v3 = new StringBuffer();
        v3.append('(');
        if (v2 != null) {
            for (int a2 = v2.length, a3 = 0; a3 < a2; ++a3) {
                toDescriptor(v3, v2[a3]);
            }
        }
        v3.append(')');
        if (v1 != null) {
            toDescriptor(v3, v1);
        }
        return v3.toString();
    }
    
    public static String ofParameters(final CtClass[] a1) {
        return ofMethod(null, a1);
    }
    
    public static String appendParameter(final String a2, final String v1) {
        final int v2 = v1.indexOf(41);
        if (v2 < 0) {
            return v1;
        }
        final StringBuffer a3 = new StringBuffer();
        a3.append(v1.substring(0, v2));
        a3.append('L');
        a3.append(a2.replace('.', '/'));
        a3.append(';');
        a3.append(v1.substring(v2));
        return a3.toString();
    }
    
    public static String insertParameter(final String a1, final String a2) {
        if (a2.charAt(0) != '(') {
            return a2;
        }
        return "(L" + a1.replace('.', '/') + ';' + a2.substring(1);
    }
    
    public static String appendParameter(final CtClass a2, final String v1) {
        final int v2 = v1.indexOf(41);
        if (v2 < 0) {
            return v1;
        }
        final StringBuffer a3 = new StringBuffer();
        a3.append(v1.substring(0, v2));
        toDescriptor(a3, a2);
        a3.append(v1.substring(v2));
        return a3.toString();
    }
    
    public static String insertParameter(final CtClass a1, final String a2) {
        if (a2.charAt(0) != '(') {
            return a2;
        }
        return "(" + of(a1) + a2.substring(1);
    }
    
    public static String changeReturnType(final String a2, final String v1) {
        final int v2 = v1.indexOf(41);
        if (v2 < 0) {
            return v1;
        }
        final StringBuffer a3 = new StringBuffer();
        a3.append(v1.substring(0, v2 + 1));
        a3.append('L');
        a3.append(a2.replace('.', '/'));
        a3.append(';');
        return a3.toString();
    }
    
    public static CtClass[] getParameterTypes(final String v-3, final ClassPool v-2) throws NotFoundException {
        if (v-3.charAt(0) != '(') {
            return null;
        }
        final int a1 = numOfParameters(v-3);
        final CtClass[] a2 = new CtClass[a1];
        int v1 = 0;
        int v2 = 1;
        do {
            v2 = toCtClass(v-2, v-3, v2, a2, v1++);
        } while (v2 > 0);
        return a2;
    }
    
    public static boolean eqParamTypes(final String v1, final String v2) {
        if (v1.charAt(0) != '(') {
            return false;
        }
        int a2 = 0;
        while (true) {
            final char a3 = v1.charAt(a2);
            if (a3 != v2.charAt(a2)) {
                return false;
            }
            if (a3 == ')') {
                return true;
            }
            ++a2;
        }
    }
    
    public static String getParamDescriptor(final String a1) {
        return a1.substring(0, a1.indexOf(41) + 1);
    }
    
    public static CtClass getReturnType(final String a2, final ClassPool v1) throws NotFoundException {
        final int v2 = a2.indexOf(41);
        if (v2 < 0) {
            return null;
        }
        final CtClass[] a3 = { null };
        toCtClass(v1, a2, v2 + 1, a3, 0);
        return a3[0];
    }
    
    public static int numOfParameters(final String v1) {
        int v2 = 0;
        int v3 = 1;
        while (true) {
            char a1 = v1.charAt(v3);
            if (a1 == ')') {
                return v2;
            }
            while (a1 == '[') {
                a1 = v1.charAt(++v3);
            }
            if (a1 == 'L') {
                v3 = v1.indexOf(59, v3) + 1;
                if (v3 <= 0) {
                    throw new IndexOutOfBoundsException("bad descriptor");
                }
            }
            else {
                ++v3;
            }
            ++v2;
        }
    }
    
    public static CtClass toCtClass(final String a1, final ClassPool a2) throws NotFoundException {
        final CtClass[] v1 = { null };
        final int v2 = toCtClass(a2, a1, 0, v1, 0);
        if (v2 >= 0) {
            return v1[0];
        }
        return a2.get(a1.replace('/', '.'));
    }
    
    private static int toCtClass(final ClassPool a5, final String v1, int v2, final CtClass[] v3, final int v4) throws NotFoundException {
        int v5 = 0;
        char v6;
        for (v6 = v1.charAt(v2); v6 == '['; v6 = v1.charAt(++v2)) {
            ++v5;
        }
        int v7 = 0;
        String v8 = null;
        if (v6 == 'L') {
            int a6 = v1.indexOf(59, ++v2);
            final String a7 = v1.substring(v2, a6++).replace('/', '.');
        }
        else {
            final CtClass a8 = toPrimitiveClass(v6);
            if (a8 == null) {
                return -1;
            }
            v7 = v2 + 1;
            if (v5 == 0) {
                v3[v4] = a8;
                return v7;
            }
            v8 = a8.getName();
        }
        if (v5 > 0) {
            final StringBuffer a9 = new StringBuffer(v8);
            while (v5-- > 0) {
                a9.append("[]");
            }
            v8 = a9.toString();
        }
        v3[v4] = a5.get(v8);
        return v7;
    }
    
    static CtClass toPrimitiveClass(final char a1) {
        CtClass v1 = null;
        switch (a1) {
            case 'Z': {
                v1 = CtClass.booleanType;
                break;
            }
            case 'C': {
                v1 = CtClass.charType;
                break;
            }
            case 'B': {
                v1 = CtClass.byteType;
                break;
            }
            case 'S': {
                v1 = CtClass.shortType;
                break;
            }
            case 'I': {
                v1 = CtClass.intType;
                break;
            }
            case 'J': {
                v1 = CtClass.longType;
                break;
            }
            case 'F': {
                v1 = CtClass.floatType;
                break;
            }
            case 'D': {
                v1 = CtClass.doubleType;
                break;
            }
            case 'V': {
                v1 = CtClass.voidType;
                break;
            }
        }
        return v1;
    }
    
    public static int arrayDimension(final String a1) {
        int v1;
        for (v1 = 0; a1.charAt(v1) == '['; ++v1) {}
        return v1;
    }
    
    public static String toArrayComponent(final String a1, final int a2) {
        return a1.substring(a2);
    }
    
    public static int dataSize(final String a1) {
        return dataSize(a1, true);
    }
    
    public static int paramSize(final String a1) {
        return -dataSize(a1, false);
    }
    
    private static int dataSize(final String v1, final boolean v2) {
        int v3 = 0;
        char v4 = v1.charAt(0);
        if (v4 == '(') {
            int a2 = 1;
            while (true) {
                v4 = v1.charAt(a2);
                if (v4 == ')') {
                    v4 = v1.charAt(a2 + 1);
                    break;
                }
                boolean a3 = false;
                while (v4 == '[') {
                    a3 = true;
                    v4 = v1.charAt(++a2);
                }
                if (v4 == 'L') {
                    a2 = v1.indexOf(59, a2) + 1;
                    if (a2 <= 0) {
                        throw new IndexOutOfBoundsException("bad descriptor");
                    }
                }
                else {
                    ++a2;
                }
                if (!a3 && (v4 == 'J' || v4 == 'D')) {
                    v3 -= 2;
                }
                else {
                    --v3;
                }
            }
        }
        if (v2) {
            if (v4 == 'J' || v4 == 'D') {
                v3 += 2;
            }
            else if (v4 != 'V') {
                ++v3;
            }
        }
        return v3;
    }
    
    public static String toString(final String a1) {
        return PrettyPrinter.toString(a1);
    }
    
    static class PrettyPrinter
    {
        PrettyPrinter() {
            super();
        }
        
        static String toString(final String v1) {
            final StringBuffer v2 = new StringBuffer();
            if (v1.charAt(0) == '(') {
                int a1 = 1;
                v2.append('(');
                while (v1.charAt(a1) != ')') {
                    if (a1 > 1) {
                        v2.append(',');
                    }
                    a1 = readType(v2, a1, v1);
                }
                v2.append(')');
            }
            else {
                readType(v2, 0, v1);
            }
            return v2.toString();
        }
        
        static int readType(final StringBuffer a2, int a3, final String v1) {
            char v2 = v1.charAt(a3);
            int v3 = 0;
            while (v2 == '[') {
                ++v3;
                v2 = v1.charAt(++a3);
            }
            if (v2 == 'L') {
                while (true) {
                    v2 = v1.charAt(++a3);
                    if (v2 == ';') {
                        break;
                    }
                    if (v2 == '/') {
                        v2 = '.';
                    }
                    a2.append(v2);
                }
            }
            else {
                final CtClass a4 = Descriptor.toPrimitiveClass(v2);
                a2.append(a4.getName());
            }
            while (v3-- > 0) {
                a2.append("[]");
            }
            return a3 + 1;
        }
    }
    
    public static class Iterator
    {
        private String desc;
        private int index;
        private int curPos;
        private boolean param;
        
        public Iterator(final String a1) {
            super();
            this.desc = a1;
            final int n = 0;
            this.curPos = n;
            this.index = n;
            this.param = false;
        }
        
        public boolean hasNext() {
            return this.index < this.desc.length();
        }
        
        public boolean isParameter() {
            return this.param;
        }
        
        public char currentChar() {
            return this.desc.charAt(this.curPos);
        }
        
        public boolean is2byte() {
            final char v1 = this.currentChar();
            return v1 == 'D' || v1 == 'J';
        }
        
        public int next() {
            int v1 = this.index;
            char v2 = this.desc.charAt(v1);
            if (v2 == '(') {
                ++this.index;
                v2 = this.desc.charAt(++v1);
                this.param = true;
            }
            if (v2 == ')') {
                ++this.index;
                v2 = this.desc.charAt(++v1);
                this.param = false;
            }
            while (v2 == '[') {
                v2 = this.desc.charAt(++v1);
            }
            if (v2 == 'L') {
                v1 = this.desc.indexOf(59, v1) + 1;
                if (v1 <= 0) {
                    throw new IndexOutOfBoundsException("bad descriptor");
                }
            }
            else {
                ++v1;
            }
            this.curPos = this.index;
            this.index = v1;
            return this.curPos;
        }
    }
}
