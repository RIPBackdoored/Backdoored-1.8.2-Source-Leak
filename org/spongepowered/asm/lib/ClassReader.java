package org.spongepowered.asm.lib;

import java.io.*;

public class ClassReader
{
    static final boolean SIGNATURES = true;
    static final boolean ANNOTATIONS = true;
    static final boolean FRAMES = true;
    static final boolean WRITER = true;
    static final boolean RESIZE = true;
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;
    public static final int EXPAND_FRAMES = 8;
    static final int EXPAND_ASM_INSNS = 256;
    public final byte[] b;
    private final int[] items;
    private final String[] strings;
    private final int maxStringLength;
    public final int header;
    
    public ClassReader(final byte[] a1) {
        this(a1, 0, a1.length);
    }
    
    public ClassReader(final byte[] v-6, final int v-5, final int v-4) {
        super();
        this.b = v-6;
        if (this.readShort(v-5 + 6) > 52) {
            throw new IllegalArgumentException();
        }
        this.items = new int[this.readUnsignedShort(v-5 + 8)];
        final int length = this.items.length;
        this.strings = new String[length];
        int maxStringLength = 0;
        int header = v-5 + 10;
        for (int v0 = 1; v0 < length; ++v0) {
            this.items[v0] = header + 1;
            int v2 = 0;
            switch (v-6[header]) {
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                case 18: {
                    final int a1 = 5;
                    break;
                }
                case 5:
                case 6: {
                    final int a2 = 9;
                    ++v0;
                    break;
                }
                case 1: {
                    final int a3 = 3 + this.readUnsignedShort(header + 1);
                    if (a3 > maxStringLength) {
                        maxStringLength = a3;
                        break;
                    }
                    break;
                }
                case 15: {
                    v2 = 4;
                    break;
                }
                default: {
                    v2 = 3;
                    break;
                }
            }
            header += v2;
        }
        this.maxStringLength = maxStringLength;
        this.header = header;
    }
    
    public int getAccess() {
        return this.readUnsignedShort(this.header);
    }
    
    public String getClassName() {
        return this.readClass(this.header + 2, new char[this.maxStringLength]);
    }
    
    public String getSuperName() {
        return this.readClass(this.header + 4, new char[this.maxStringLength]);
    }
    
    public String[] getInterfaces() {
        int n = this.header + 6;
        final int unsignedShort = this.readUnsignedShort(n);
        final String[] array = new String[unsignedShort];
        if (unsignedShort > 0) {
            final char[] v0 = new char[this.maxStringLength];
            for (int v2 = 0; v2 < unsignedShort; ++v2) {
                n += 2;
                array[v2] = this.readClass(n, v0);
            }
        }
        return array;
    }
    
    void copyPool(final ClassWriter v-8) {
        final char[] a2 = new char[this.maxStringLength];
        final int length = this.items.length;
        final Item[] array = new Item[length];
        for (int i = 1; i < length; ++i) {
            int n = this.items[i];
            final int n2 = this.b[n - 1];
            final Item item = new Item(i);
            switch (n2) {
                case 9:
                case 10:
                case 11: {
                    final int a1 = this.items[this.readUnsignedShort(n + 2)];
                    item.set(n2, this.readClass(n, a2), this.readUTF8(a1, a2), this.readUTF8(a1 + 2, a2));
                    break;
                }
                case 3: {
                    item.set(this.readInt(n));
                    break;
                }
                case 4: {
                    item.set(Float.intBitsToFloat(this.readInt(n)));
                    break;
                }
                case 12: {
                    item.set(n2, this.readUTF8(n, a2), this.readUTF8(n + 2, a2), null);
                    break;
                }
                case 5: {
                    item.set(this.readLong(n));
                    ++i;
                    break;
                }
                case 6: {
                    item.set(Double.longBitsToDouble(this.readLong(n)));
                    ++i;
                    break;
                }
                case 1: {
                    String v1 = this.strings[i];
                    if (v1 == null) {
                        n = this.items[i];
                        final String[] strings = this.strings;
                        final int n3 = i;
                        final String utf = this.readUTF(n + 2, this.readUnsignedShort(n), a2);
                        strings[n3] = utf;
                        v1 = utf;
                    }
                    item.set(n2, v1, null, null);
                    break;
                }
                case 15: {
                    final int v2 = this.items[this.readUnsignedShort(n + 1)];
                    final int v3 = this.items[this.readUnsignedShort(v2 + 2)];
                    item.set(20 + this.readByte(n), this.readClass(v2, a2), this.readUTF8(v3, a2), this.readUTF8(v3 + 2, a2));
                    break;
                }
                case 18: {
                    if (v-8.bootstrapMethods == null) {
                        this.copyBootstrapMethods(v-8, array, a2);
                    }
                    final int v3 = this.items[this.readUnsignedShort(n + 2)];
                    item.set(this.readUTF8(v3, a2), this.readUTF8(v3 + 2, a2), this.readUnsignedShort(n));
                    break;
                }
                default: {
                    item.set(n2, this.readUTF8(n, a2), null, null);
                    break;
                }
            }
            final int v2 = item.hashCode % array.length;
            item.next = array[v2];
            array[v2] = item;
        }
        int i = this.items[1] - 1;
        v-8.pool.putByteArray(this.b, i, this.header - i);
        v-8.items = array;
        v-8.threshold = (int)(0.75 * length);
        v-8.index = length;
    }
    
    private void copyBootstrapMethods(final ClassWriter v-7, final Item[] v-6, final char[] v-5) {
        int attributes = this.getAttributes();
        boolean b = false;
        for (int a2 = this.readUnsignedShort(attributes); a2 > 0; --a2) {
            final String a3 = this.readUTF8(attributes + 2, v-5);
            if ("BootstrapMethods".equals(a3)) {
                b = true;
                break;
            }
            attributes += 6 + this.readInt(attributes + 4);
        }
        if (!b) {
            return;
        }
        final int unsignedShort = this.readUnsignedShort(attributes + 8);
        int i = 0;
        int v0 = attributes + 10;
        while (i < unsignedShort) {
            final int v2 = v0 - attributes - 10;
            int v3 = this.readConst(this.readUnsignedShort(v0), v-5).hashCode();
            for (int a4 = this.readUnsignedShort(v0 + 2); a4 > 0; --a4) {
                v3 ^= this.readConst(this.readUnsignedShort(v0 + 4), v-5).hashCode();
                v0 += 2;
            }
            v0 += 4;
            final Item v4 = new Item(i);
            v4.set(v2, v3 & 0x0);
            final int v5 = v4.hashCode % v-6.length;
            v4.next = v-6[v5];
            v-6[v5] = v4;
            ++i;
        }
        i = this.readInt(attributes + 4);
        final ByteVector v6 = new ByteVector(i + 62);
        v6.putByteArray(this.b, attributes + 10, i - 2);
        v-7.bootstrapMethodsCount = unsignedShort;
        v-7.bootstrapMethods = v6;
    }
    
    public ClassReader(final InputStream a1) throws IOException {
        this(readClass(a1, false));
    }
    
    public ClassReader(final String a1) throws IOException {
        this(readClass(ClassLoader.getSystemResourceAsStream(a1.replace('.', '/') + ".class"), true));
    }
    
    private static byte[] readClass(final InputStream v-5, final boolean v-4) throws IOException {
        if (v-5 == null) {
            throw new IOException("Class not found");
        }
        try {
            byte[] array = new byte[v-5.available()];
            int n = 0;
            while (true) {
                final int read = v-5.read(array, n, array.length - n);
                if (read == -1) {
                    if (n < array.length) {
                        final byte[] a1 = new byte[n];
                        System.arraycopy(array, 0, a1, 0, n);
                        array = a1;
                    }
                    return array;
                }
                n += read;
                if (n != array.length) {
                    continue;
                }
                final int a2 = v-5.read();
                if (a2 < 0) {
                    return array;
                }
                final byte[] v1 = new byte[array.length + 1000];
                System.arraycopy(array, 0, v1, 0, n);
                v1[n++] = (byte)a2;
                array = v1;
            }
        }
        finally {
            if (v-4) {
                v-5.close();
            }
        }
    }
    
    public void accept(final ClassVisitor a1, final int a2) {
        this.accept(a1, new Attribute[0], a2);
    }
    
    public void accept(final ClassVisitor v-24, final Attribute[] v-23, final int v-22) {
        int v-25 = this.header;
        final char[] array = new char[this.maxStringLength];
        final Context context = new Context();
        context.attrs = v-23;
        context.flags = v-22;
        context.buffer = array;
        int unsignedShort = this.readUnsignedShort(v-25);
        final String class1 = this.readClass(v-25 + 2, array);
        final String class2 = this.readClass(v-25 + 4, array);
        final String[] a4 = new String[this.readUnsignedShort(v-25 + 6)];
        v-25 += 8;
        for (int a1 = 0; a1 < a4.length; ++a1) {
            a4[a1] = this.readClass(v-25, array);
            v-25 += 2;
        }
        String utf8 = null;
        String utf9 = null;
        String utf10 = null;
        String class3 = null;
        String utf11 = null;
        String utf12 = null;
        int a5 = 0;
        int a6 = 0;
        int a7 = 0;
        int a8 = 0;
        int a9 = 0;
        Attribute attribute = null;
        v-25 = this.getAttributes();
        for (int i = this.readUnsignedShort(v-25); i > 0; --i) {
            final String utf13 = this.readUTF8(v-25 + 2, array);
            if ("SourceFile".equals(utf13)) {
                utf9 = this.readUTF8(v-25 + 8, array);
            }
            else if ("InnerClasses".equals(utf13)) {
                a9 = v-25 + 8;
            }
            else if ("EnclosingMethod".equals(utf13)) {
                class3 = this.readClass(v-25 + 8, array);
                final int a2 = this.readUnsignedShort(v-25 + 10);
                if (a2 != 0) {
                    utf11 = this.readUTF8(this.items[a2], array);
                    utf12 = this.readUTF8(this.items[a2] + 2, array);
                }
            }
            else if ("Signature".equals(utf13)) {
                utf8 = this.readUTF8(v-25 + 8, array);
            }
            else if ("RuntimeVisibleAnnotations".equals(utf13)) {
                a5 = v-25 + 8;
            }
            else if ("RuntimeVisibleTypeAnnotations".equals(utf13)) {
                a7 = v-25 + 8;
            }
            else if ("Deprecated".equals(utf13)) {
                unsignedShort |= 0x20000;
            }
            else if ("Synthetic".equals(utf13)) {
                unsignedShort |= 0x41000;
            }
            else if ("SourceDebugExtension".equals(utf13)) {
                final int a3 = this.readInt(v-25 + 4);
                utf10 = this.readUTF(v-25 + 8, a3, new char[a3]);
            }
            else if ("RuntimeInvisibleAnnotations".equals(utf13)) {
                a6 = v-25 + 8;
            }
            else if ("RuntimeInvisibleTypeAnnotations".equals(utf13)) {
                a8 = v-25 + 8;
            }
            else if ("BootstrapMethods".equals(utf13)) {
                final int[] v0 = new int[this.readUnsignedShort(v-25 + 8)];
                int v2 = 0;
                int v3 = v-25 + 10;
                while (v2 < v0.length) {
                    v0[v2] = v3;
                    v3 += 2 + this.readUnsignedShort(v3 + 2) << 1;
                    ++v2;
                }
                context.bootstrapMethods = v0;
            }
            else {
                final Attribute v4 = this.readAttribute(v-23, utf13, v-25 + 8, this.readInt(v-25 + 4), array, -1, null);
                if (v4 != null) {
                    v4.next = attribute;
                    attribute = v4;
                }
            }
            v-25 += 6 + this.readInt(v-25 + 4);
        }
        v-24.visit(this.readInt(this.items[1] - 7), unsignedShort, class1, utf8, class2, a4);
        if ((v-22 & 0x2) == 0x0 && (utf9 != null || utf10 != null)) {
            v-24.visitSource(utf9, utf10);
        }
        if (class3 != null) {
            v-24.visitOuterClass(class3, utf11, utf12);
        }
        if (a5 != 0) {
            int i = this.readUnsignedShort(a5);
            int j = a5 + 2;
            while (i > 0) {
                j = this.readAnnotationValues(j + 2, array, true, v-24.visitAnnotation(this.readUTF8(j, array), true));
                --i;
            }
        }
        if (a6 != 0) {
            int i = this.readUnsignedShort(a6);
            int j = a6 + 2;
            while (i > 0) {
                j = this.readAnnotationValues(j + 2, array, true, v-24.visitAnnotation(this.readUTF8(j, array), false));
                --i;
            }
        }
        if (a7 != 0) {
            int i = this.readUnsignedShort(a7);
            int j = a7 + 2;
            while (i > 0) {
                j = this.readAnnotationTarget(context, j);
                j = this.readAnnotationValues(j + 2, array, true, v-24.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(j, array), true));
                --i;
            }
        }
        if (a8 != 0) {
            int i = this.readUnsignedShort(a8);
            int j = a8 + 2;
            while (i > 0) {
                j = this.readAnnotationTarget(context, j);
                j = this.readAnnotationValues(j + 2, array, true, v-24.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(j, array), false));
                --i;
            }
        }
        while (attribute != null) {
            final Attribute next = attribute.next;
            attribute.next = null;
            v-24.visitAttribute(attribute);
            attribute = next;
        }
        if (a9 != 0) {
            int i = a9 + 2;
            for (int j = this.readUnsignedShort(a9); j > 0; --j) {
                v-24.visitInnerClass(this.readClass(i, array), this.readClass(i + 2, array), this.readUTF8(i + 4, array), this.readUnsignedShort(i + 6));
                i += 8;
            }
        }
        v-25 = this.header + 10 + 2 * a4.length;
        for (int i = this.readUnsignedShort(v-25 - 2); i > 0; --i) {
            v-25 = this.readField(v-24, context, v-25);
        }
        v-25 += 2;
        for (int i = this.readUnsignedShort(v-25 - 2); i > 0; --i) {
            v-25 = this.readMethod(v-24, context, v-25);
        }
        v-24.visitEnd();
    }
    
    private int readField(final ClassVisitor v-13, final Context v-12, int v-11) {
        final char[] buffer = v-12.buffer;
        int unsignedShort = this.readUnsignedShort(v-11);
        final String utf8 = this.readUTF8(v-11 + 2, buffer);
        final String utf9 = this.readUTF8(v-11 + 4, buffer);
        v-11 += 6;
        String utf10 = null;
        int a6 = 0;
        int a7 = 0;
        int a8 = 0;
        int a9 = 0;
        Object a10 = null;
        Attribute v0 = null;
        for (int v2 = this.readUnsignedShort(v-11); v2 > 0; --v2) {
            final String a3 = this.readUTF8(v-11 + 2, buffer);
            if ("ConstantValue".equals(a3)) {
                final int a4 = this.readUnsignedShort(v-11 + 8);
                a10 = ((a4 == 0) ? null : this.readConst(a4, buffer));
            }
            else if ("Signature".equals(a3)) {
                utf10 = this.readUTF8(v-11 + 8, buffer);
            }
            else if ("Deprecated".equals(a3)) {
                unsignedShort |= 0x20000;
            }
            else if ("Synthetic".equals(a3)) {
                unsignedShort |= 0x41000;
            }
            else if ("RuntimeVisibleAnnotations".equals(a3)) {
                a6 = v-11 + 8;
            }
            else if ("RuntimeVisibleTypeAnnotations".equals(a3)) {
                a8 = v-11 + 8;
            }
            else if ("RuntimeInvisibleAnnotations".equals(a3)) {
                a7 = v-11 + 8;
            }
            else if ("RuntimeInvisibleTypeAnnotations".equals(a3)) {
                a9 = v-11 + 8;
            }
            else {
                final Attribute a5 = this.readAttribute(v-12.attrs, a3, v-11 + 8, this.readInt(v-11 + 4), buffer, -1, null);
                if (a5 != null) {
                    a5.next = v0;
                    v0 = a5;
                }
            }
            v-11 += 6 + this.readInt(v-11 + 4);
        }
        v-11 += 2;
        final FieldVisitor v3 = v-13.visitField(unsignedShort, utf8, utf9, utf10, a10);
        if (v3 == null) {
            return v-11;
        }
        if (a6 != 0) {
            int v4 = this.readUnsignedShort(a6);
            int v5 = a6 + 2;
            while (v4 > 0) {
                v5 = this.readAnnotationValues(v5 + 2, buffer, true, v3.visitAnnotation(this.readUTF8(v5, buffer), true));
                --v4;
            }
        }
        if (a7 != 0) {
            int v4 = this.readUnsignedShort(a7);
            int v5 = a7 + 2;
            while (v4 > 0) {
                v5 = this.readAnnotationValues(v5 + 2, buffer, true, v3.visitAnnotation(this.readUTF8(v5, buffer), false));
                --v4;
            }
        }
        if (a8 != 0) {
            int v4 = this.readUnsignedShort(a8);
            int v5 = a8 + 2;
            while (v4 > 0) {
                v5 = this.readAnnotationTarget(v-12, v5);
                v5 = this.readAnnotationValues(v5 + 2, buffer, true, v3.visitTypeAnnotation(v-12.typeRef, v-12.typePath, this.readUTF8(v5, buffer), true));
                --v4;
            }
        }
        if (a9 != 0) {
            int v4 = this.readUnsignedShort(a9);
            int v5 = a9 + 2;
            while (v4 > 0) {
                v5 = this.readAnnotationTarget(v-12, v5);
                v5 = this.readAnnotationValues(v5 + 2, buffer, true, v3.visitTypeAnnotation(v-12.typeRef, v-12.typePath, this.readUTF8(v5, buffer), false));
                --v4;
            }
        }
        while (v0 != null) {
            final Attribute v6 = v0.next;
            v0.next = null;
            v3.visitAttribute(v0);
            v0 = v6;
        }
        v3.visitEnd();
        return v-11;
    }
    
    private int readMethod(final ClassVisitor v-17, final Context v-16, int v-15) {
        final char[] buffer = v-16.buffer;
        v-16.access = this.readUnsignedShort(v-15);
        v-16.name = this.readUTF8(v-15 + 2, buffer);
        v-16.desc = this.readUTF8(v-15 + 4, buffer);
        v-15 += 6;
        int v-18 = 0;
        int n = 0;
        String[] a6 = null;
        String utf8 = null;
        int n2 = 0;
        int a7 = 0;
        int a8 = 0;
        int a9 = 0;
        int a10 = 0;
        int v-19 = 0;
        int v11 = 0;
        int v12 = 0;
        final int classReaderOffset = v-15;
        Attribute v0 = null;
        for (int v2 = this.readUnsignedShort(v-15); v2 > 0; --v2) {
            final String a3 = this.readUTF8(v-15 + 2, buffer);
            if ("Code".equals(a3)) {
                if ((v-16.flags & 0x1) == 0x0) {
                    v-18 = v-15 + 8;
                }
            }
            else if ("Exceptions".equals(a3)) {
                a6 = new String[this.readUnsignedShort(v-15 + 8)];
                n = v-15 + 10;
                for (int a4 = 0; a4 < a6.length; ++a4) {
                    a6[a4] = this.readClass(n, buffer);
                    n += 2;
                }
            }
            else if ("Signature".equals(a3)) {
                utf8 = this.readUTF8(v-15 + 8, buffer);
            }
            else if ("Deprecated".equals(a3)) {
                v-16.access |= 0x20000;
            }
            else if ("RuntimeVisibleAnnotations".equals(a3)) {
                a7 = v-15 + 8;
            }
            else if ("RuntimeVisibleTypeAnnotations".equals(a3)) {
                a9 = v-15 + 8;
            }
            else if ("AnnotationDefault".equals(a3)) {
                v-19 = v-15 + 8;
            }
            else if ("Synthetic".equals(a3)) {
                v-16.access |= 0x41000;
            }
            else if ("RuntimeInvisibleAnnotations".equals(a3)) {
                a8 = v-15 + 8;
            }
            else if ("RuntimeInvisibleTypeAnnotations".equals(a3)) {
                a10 = v-15 + 8;
            }
            else if ("RuntimeVisibleParameterAnnotations".equals(a3)) {
                v11 = v-15 + 8;
            }
            else if ("RuntimeInvisibleParameterAnnotations".equals(a3)) {
                v12 = v-15 + 8;
            }
            else if ("MethodParameters".equals(a3)) {
                n2 = v-15 + 8;
            }
            else {
                final Attribute a5 = this.readAttribute(v-16.attrs, a3, v-15 + 8, this.readInt(v-15 + 4), buffer, -1, null);
                if (a5 != null) {
                    a5.next = v0;
                    v0 = a5;
                }
            }
            v-15 += 6 + this.readInt(v-15 + 4);
        }
        v-15 += 2;
        final MethodVisitor v3 = v-17.visitMethod(v-16.access, v-16.name, v-16.desc, utf8, a6);
        if (v3 == null) {
            return v-15;
        }
        if (v3 instanceof MethodWriter) {
            final MethodWriter v4 = (MethodWriter)v3;
            if (v4.cw.cr == this && utf8 == v4.signature) {
                boolean v5 = false;
                if (a6 == null) {
                    v5 = (v4.exceptionCount == 0);
                }
                else if (a6.length == v4.exceptionCount) {
                    v5 = true;
                    for (int v6 = a6.length - 1; v6 >= 0; --v6) {
                        n -= 2;
                        if (v4.exceptions[v6] != this.readUnsignedShort(n)) {
                            v5 = false;
                            break;
                        }
                    }
                }
                if (v5) {
                    v4.classReaderOffset = classReaderOffset;
                    v4.classReaderLength = v-15 - classReaderOffset;
                    return v-15;
                }
            }
        }
        if (n2 != 0) {
            for (int v7 = this.b[n2] & 0xFF, v8 = n2 + 1; v7 > 0; --v7, v8 += 4) {
                v3.visitParameter(this.readUTF8(v8, buffer), this.readUnsignedShort(v8 + 2));
            }
        }
        if (v-19 != 0) {
            final AnnotationVisitor v9 = v3.visitAnnotationDefault();
            this.readAnnotationValue(v-19, buffer, null, v9);
            if (v9 != null) {
                v9.visitEnd();
            }
        }
        if (a7 != 0) {
            int v7 = this.readUnsignedShort(a7);
            int v8 = a7 + 2;
            while (v7 > 0) {
                v8 = this.readAnnotationValues(v8 + 2, buffer, true, v3.visitAnnotation(this.readUTF8(v8, buffer), true));
                --v7;
            }
        }
        if (a8 != 0) {
            int v7 = this.readUnsignedShort(a8);
            int v8 = a8 + 2;
            while (v7 > 0) {
                v8 = this.readAnnotationValues(v8 + 2, buffer, true, v3.visitAnnotation(this.readUTF8(v8, buffer), false));
                --v7;
            }
        }
        if (a9 != 0) {
            int v7 = this.readUnsignedShort(a9);
            int v8 = a9 + 2;
            while (v7 > 0) {
                v8 = this.readAnnotationTarget(v-16, v8);
                v8 = this.readAnnotationValues(v8 + 2, buffer, true, v3.visitTypeAnnotation(v-16.typeRef, v-16.typePath, this.readUTF8(v8, buffer), true));
                --v7;
            }
        }
        if (a10 != 0) {
            int v7 = this.readUnsignedShort(a10);
            int v8 = a10 + 2;
            while (v7 > 0) {
                v8 = this.readAnnotationTarget(v-16, v8);
                v8 = this.readAnnotationValues(v8 + 2, buffer, true, v3.visitTypeAnnotation(v-16.typeRef, v-16.typePath, this.readUTF8(v8, buffer), false));
                --v7;
            }
        }
        if (v11 != 0) {
            this.readParameterAnnotations(v3, v-16, v11, true);
        }
        if (v12 != 0) {
            this.readParameterAnnotations(v3, v-16, v12, false);
        }
        while (v0 != null) {
            final Attribute v10 = v0.next;
            v0.next = null;
            v3.visitAttribute(v0);
            v0 = v10;
        }
        if (v-18 != 0) {
            v3.visitCode();
            this.readCode(v3, v-16, v-18);
        }
        v3.visitEnd();
        return v-15;
    }
    
    private void readCode(final MethodVisitor v-11, final Context v-10, int v-9) {
        final byte[] b = this.b;
        final char[] buffer = v-10.buffer;
        final int unsignedShort = this.readUnsignedShort(v-9);
        final int unsignedShort2 = this.readUnsignedShort(v-9 + 2);
        final int int1 = this.readInt(v-9 + 4);
        v-9 += 8;
        final int n = v-9;
        final int n2 = v-9 + int1;
        final Label[] labels = new Label[int1 + 2];
        v-10.labels = labels;
        final Label[] array = labels;
        this.readLabel(int1 + 1, array);
        while (v-9 < n2) {
            final int a3 = v-9 - n;
            int v1 = b[v-9] & 0xFF;
            switch (ClassWriter.TYPE[v1]) {
                case 0:
                case 4: {
                    ++v-9;
                    continue;
                }
                case 9: {
                    this.readLabel(a3 + this.readShort(v-9 + 1), array);
                    v-9 += 3;
                    continue;
                }
                case 18: {
                    this.readLabel(a3 + this.readUnsignedShort(v-9 + 1), array);
                    v-9 += 3;
                    continue;
                }
                case 10: {
                    this.readLabel(a3 + this.readInt(v-9 + 1), array);
                    v-9 += 5;
                    continue;
                }
                case 17: {
                    v1 = (b[v-9 + 1] & 0xFF);
                    if (v1 == 132) {
                        v-9 += 6;
                        continue;
                    }
                    v-9 += 4;
                    continue;
                }
                case 14: {
                    v-9 = v-9 + 4 - (a3 & 0x3);
                    this.readLabel(a3 + this.readInt(v-9), array);
                    for (int a4 = this.readInt(v-9 + 8) - this.readInt(v-9 + 4) + 1; a4 > 0; --a4) {
                        this.readLabel(a3 + this.readInt(v-9 + 12), array);
                        v-9 += 4;
                    }
                    v-9 += 12;
                    continue;
                }
                case 15: {
                    v-9 = v-9 + 4 - (a3 & 0x3);
                    this.readLabel(a3 + this.readInt(v-9), array);
                    for (int a5 = this.readInt(v-9 + 4); a5 > 0; --a5) {
                        this.readLabel(a3 + this.readInt(v-9 + 12), array);
                        v-9 += 8;
                    }
                    v-9 += 8;
                    continue;
                }
                case 1:
                case 3:
                case 11: {
                    v-9 += 2;
                    continue;
                }
                case 2:
                case 5:
                case 6:
                case 12:
                case 13: {
                    v-9 += 3;
                    continue;
                }
                case 7:
                case 8: {
                    v-9 += 5;
                    continue;
                }
                default: {
                    v-9 += 4;
                    continue;
                }
            }
        }
        for (int v2 = this.readUnsignedShort(v-9); v2 > 0; --v2) {
            final Label v3 = this.readLabel(this.readUnsignedShort(v-9 + 2), array);
            final Label v4 = this.readLabel(this.readUnsignedShort(v-9 + 4), array);
            final Label v5 = this.readLabel(this.readUnsignedShort(v-9 + 6), array);
            final String v6 = this.readUTF8(this.items[this.readUnsignedShort(v-9 + 8)], buffer);
            v-11.visitTryCatchBlock(v3, v4, v5, v6);
            v-9 += 8;
        }
        v-9 += 2;
        int[] v7 = null;
        int[] v8 = null;
        int v9 = 0;
        int v10 = 0;
        int v11 = -1;
        int v12 = -1;
        int v13 = 0;
        int v14 = 0;
        boolean v15 = true;
        final boolean v16 = (v-10.flags & 0x8) != 0x0;
        int v17 = 0;
        int v18 = 0;
        int v19 = 0;
        Context v20 = null;
        Attribute v21 = null;
        for (int v22 = this.readUnsignedShort(v-9); v22 > 0; --v22) {
            final String v23 = this.readUTF8(v-9 + 2, buffer);
            if ("LocalVariableTable".equals(v23)) {
                if ((v-10.flags & 0x2) == 0x0) {
                    v13 = v-9 + 8;
                    int v24 = this.readUnsignedShort(v-9 + 8);
                    int v25 = v-9;
                    while (v24 > 0) {
                        int v26 = this.readUnsignedShort(v25 + 10);
                        if (array[v26] == null) {
                            final Label label = this.readLabel(v26, array);
                            label.status |= 0x1;
                        }
                        v26 += this.readUnsignedShort(v25 + 12);
                        if (array[v26] == null) {
                            final Label label2 = this.readLabel(v26, array);
                            label2.status |= 0x1;
                        }
                        v25 += 10;
                        --v24;
                    }
                }
            }
            else if ("LocalVariableTypeTable".equals(v23)) {
                v14 = v-9 + 8;
            }
            else if ("LineNumberTable".equals(v23)) {
                if ((v-10.flags & 0x2) == 0x0) {
                    int v24 = this.readUnsignedShort(v-9 + 8);
                    int v25 = v-9;
                    while (v24 > 0) {
                        final int v26 = this.readUnsignedShort(v25 + 10);
                        if (array[v26] == null) {
                            final Label label3 = this.readLabel(v26, array);
                            label3.status |= 0x1;
                        }
                        Label v27;
                        for (v27 = array[v26]; v27.line > 0; v27 = v27.next) {
                            if (v27.next == null) {
                                v27.next = new Label();
                            }
                        }
                        v27.line = this.readUnsignedShort(v25 + 12);
                        v25 += 4;
                        --v24;
                    }
                }
            }
            else if ("RuntimeVisibleTypeAnnotations".equals(v23)) {
                v7 = this.readTypeAnnotations(v-11, v-10, v-9 + 8, true);
                v11 = ((v7.length == 0 || this.readByte(v7[0]) < 67) ? -1 : this.readUnsignedShort(v7[0] + 1));
            }
            else if ("RuntimeInvisibleTypeAnnotations".equals(v23)) {
                v8 = this.readTypeAnnotations(v-11, v-10, v-9 + 8, false);
                v12 = ((v8.length == 0 || this.readByte(v8[0]) < 67) ? -1 : this.readUnsignedShort(v8[0] + 1));
            }
            else if ("StackMapTable".equals(v23)) {
                if ((v-10.flags & 0x4) == 0x0) {
                    v17 = v-9 + 10;
                    v18 = this.readInt(v-9 + 4);
                    v19 = this.readUnsignedShort(v-9 + 8);
                }
            }
            else if ("StackMap".equals(v23)) {
                if ((v-10.flags & 0x4) == 0x0) {
                    v15 = false;
                    v17 = v-9 + 10;
                    v18 = this.readInt(v-9 + 4);
                    v19 = this.readUnsignedShort(v-9 + 8);
                }
            }
            else {
                for (int v24 = 0; v24 < v-10.attrs.length; ++v24) {
                    if (v-10.attrs[v24].type.equals(v23)) {
                        final Attribute v28 = v-10.attrs[v24].read(this, v-9 + 8, this.readInt(v-9 + 4), buffer, n - 8, array);
                        if (v28 != null) {
                            v28.next = v21;
                            v21 = v28;
                        }
                    }
                }
            }
            v-9 += 6 + this.readInt(v-9 + 4);
        }
        v-9 += 2;
        if (v17 != 0) {
            v20 = v-10;
            v20.offset = -1;
            v20.mode = 0;
            v20.localCount = 0;
            v20.localDiff = 0;
            v20.stackCount = 0;
            v20.local = new Object[unsignedShort2];
            v20.stack = new Object[unsignedShort];
            if (v16) {
                this.getImplicitFrame(v-10);
            }
            for (int v22 = v17; v22 < v17 + v18 - 2; ++v22) {
                if (b[v22] == 8) {
                    final int v29 = this.readUnsignedShort(v22 + 1);
                    if (v29 >= 0 && v29 < int1 && (b[n + v29] & 0xFF) == 0xBB) {
                        this.readLabel(v29, array);
                    }
                }
            }
        }
        if ((v-10.flags & 0x100) != 0x0) {
            v-11.visitFrame(-1, unsignedShort2, null, 0, null);
        }
        int v22 = ((v-10.flags & 0x100) == 0x0) ? -33 : 0;
        v-9 = n;
        while (v-9 < n2) {
            final int v29 = v-9 - n;
            final Label v30 = array[v29];
            if (v30 != null) {
                Label v31 = v30.next;
                v30.next = null;
                v-11.visitLabel(v30);
                if ((v-10.flags & 0x2) == 0x0 && v30.line > 0) {
                    v-11.visitLineNumber(v30.line, v30);
                    while (v31 != null) {
                        v-11.visitLineNumber(v31.line, v30);
                        v31 = v31.next;
                    }
                }
            }
            while (v20 != null && (v20.offset == v29 || v20.offset == -1)) {
                if (v20.offset != -1) {
                    if (!v15 || v16) {
                        v-11.visitFrame(-1, v20.localCount, v20.local, v20.stackCount, v20.stack);
                    }
                    else {
                        v-11.visitFrame(v20.mode, v20.localDiff, v20.local, v20.stackCount, v20.stack);
                    }
                }
                if (v19 > 0) {
                    v17 = this.readFrame(v17, v15, v16, v20);
                    --v19;
                }
                else {
                    v20 = null;
                }
            }
            int v25 = b[v-9] & 0xFF;
            switch (ClassWriter.TYPE[v25]) {
                case 0: {
                    v-11.visitInsn(v25);
                    ++v-9;
                    break;
                }
                case 4: {
                    if (v25 > 54) {
                        v25 -= 59;
                        v-11.visitVarInsn(54 + (v25 >> 2), v25 & 0x3);
                    }
                    else {
                        v25 -= 26;
                        v-11.visitVarInsn(21 + (v25 >> 2), v25 & 0x3);
                    }
                    ++v-9;
                    break;
                }
                case 9: {
                    v-11.visitJumpInsn(v25, array[v29 + this.readShort(v-9 + 1)]);
                    v-9 += 3;
                    break;
                }
                case 10: {
                    v-11.visitJumpInsn(v25 + v22, array[v29 + this.readInt(v-9 + 1)]);
                    v-9 += 5;
                    break;
                }
                case 18: {
                    v25 = ((v25 < 218) ? (v25 - 49) : (v25 - 20));
                    final Label v32 = array[v29 + this.readUnsignedShort(v-9 + 1)];
                    if (v25 == 167 || v25 == 168) {
                        v-11.visitJumpInsn(v25 + 33, v32);
                    }
                    else {
                        v25 = ((v25 <= 166) ? ((v25 + 1 ^ 0x1) - 1) : (v25 ^ 0x1));
                        final Label v27 = new Label();
                        v-11.visitJumpInsn(v25, v27);
                        v-11.visitJumpInsn(200, v32);
                        v-11.visitLabel(v27);
                        if (v17 != 0 && (v20 == null || v20.offset != v29 + 3)) {
                            v-11.visitFrame(256, 0, null, 0, null);
                        }
                    }
                    v-9 += 3;
                    break;
                }
                case 17: {
                    v25 = (b[v-9 + 1] & 0xFF);
                    if (v25 == 132) {
                        v-11.visitIincInsn(this.readUnsignedShort(v-9 + 2), this.readShort(v-9 + 4));
                        v-9 += 6;
                        break;
                    }
                    v-11.visitVarInsn(v25, this.readUnsignedShort(v-9 + 2));
                    v-9 += 4;
                    break;
                }
                case 14: {
                    v-9 = v-9 + 4 - (v29 & 0x3);
                    final int v26 = v29 + this.readInt(v-9);
                    final int v33 = this.readInt(v-9 + 4);
                    final int v34 = this.readInt(v-9 + 8);
                    final Label[] v35 = new Label[v34 - v33 + 1];
                    v-9 += 12;
                    for (int v36 = 0; v36 < v35.length; ++v36) {
                        v35[v36] = array[v29 + this.readInt(v-9)];
                        v-9 += 4;
                    }
                    v-11.visitTableSwitchInsn(v33, v34, array[v26], v35);
                    break;
                }
                case 15: {
                    v-9 = v-9 + 4 - (v29 & 0x3);
                    final int v26 = v29 + this.readInt(v-9);
                    final int v33 = this.readInt(v-9 + 4);
                    final int[] v37 = new int[v33];
                    final Label[] v35 = new Label[v33];
                    v-9 += 8;
                    for (int v36 = 0; v36 < v33; ++v36) {
                        v37[v36] = this.readInt(v-9);
                        v35[v36] = array[v29 + this.readInt(v-9 + 4)];
                        v-9 += 8;
                    }
                    v-11.visitLookupSwitchInsn(array[v26], v37, v35);
                    break;
                }
                case 3: {
                    v-11.visitVarInsn(v25, b[v-9 + 1] & 0xFF);
                    v-9 += 2;
                    break;
                }
                case 1: {
                    v-11.visitIntInsn(v25, b[v-9 + 1]);
                    v-9 += 2;
                    break;
                }
                case 2: {
                    v-11.visitIntInsn(v25, this.readShort(v-9 + 1));
                    v-9 += 3;
                    break;
                }
                case 11: {
                    v-11.visitLdcInsn(this.readConst(b[v-9 + 1] & 0xFF, buffer));
                    v-9 += 2;
                    break;
                }
                case 12: {
                    v-11.visitLdcInsn(this.readConst(this.readUnsignedShort(v-9 + 1), buffer));
                    v-9 += 3;
                    break;
                }
                case 6:
                case 7: {
                    int v26 = this.items[this.readUnsignedShort(v-9 + 1)];
                    final boolean v38 = b[v26 - 1] == 11;
                    final String v39 = this.readClass(v26, buffer);
                    v26 = this.items[this.readUnsignedShort(v26 + 2)];
                    final String v40 = this.readUTF8(v26, buffer);
                    final String v41 = this.readUTF8(v26 + 2, buffer);
                    if (v25 < 182) {
                        v-11.visitFieldInsn(v25, v39, v40, v41);
                    }
                    else {
                        v-11.visitMethodInsn(v25, v39, v40, v41, v38);
                    }
                    if (v25 == 185) {
                        v-9 += 5;
                        break;
                    }
                    v-9 += 3;
                    break;
                }
                case 8: {
                    int v26 = this.items[this.readUnsignedShort(v-9 + 1)];
                    int v33 = v-10.bootstrapMethods[this.readUnsignedShort(v26)];
                    final Handle v42 = (Handle)this.readConst(this.readUnsignedShort(v33), buffer);
                    final int v43 = this.readUnsignedShort(v33 + 2);
                    final Object[] v44 = new Object[v43];
                    v33 += 4;
                    for (int v45 = 0; v45 < v43; ++v45) {
                        v44[v45] = this.readConst(this.readUnsignedShort(v33), buffer);
                        v33 += 2;
                    }
                    v26 = this.items[this.readUnsignedShort(v26 + 2)];
                    final String v46 = this.readUTF8(v26, buffer);
                    final String v47 = this.readUTF8(v26 + 2, buffer);
                    v-11.visitInvokeDynamicInsn(v46, v47, v42, v44);
                    v-9 += 5;
                    break;
                }
                case 5: {
                    v-11.visitTypeInsn(v25, this.readClass(v-9 + 1, buffer));
                    v-9 += 3;
                    break;
                }
                case 13: {
                    v-11.visitIincInsn(b[v-9 + 1] & 0xFF, b[v-9 + 2]);
                    v-9 += 3;
                    break;
                }
                default: {
                    v-11.visitMultiANewArrayInsn(this.readClass(v-9 + 1, buffer), b[v-9 + 3] & 0xFF);
                    v-9 += 4;
                    break;
                }
            }
            while (v7 != null && v9 < v7.length && v11 <= v29) {
                if (v11 == v29) {
                    final int v26 = this.readAnnotationTarget(v-10, v7[v9]);
                    this.readAnnotationValues(v26 + 2, buffer, true, v-11.visitInsnAnnotation(v-10.typeRef, v-10.typePath, this.readUTF8(v26, buffer), true));
                }
                v11 = ((++v9 >= v7.length || this.readByte(v7[v9]) < 67) ? -1 : this.readUnsignedShort(v7[v9] + 1));
            }
            while (v8 != null && v10 < v8.length && v12 <= v29) {
                if (v12 == v29) {
                    final int v26 = this.readAnnotationTarget(v-10, v8[v10]);
                    this.readAnnotationValues(v26 + 2, buffer, true, v-11.visitInsnAnnotation(v-10.typeRef, v-10.typePath, this.readUTF8(v26, buffer), false));
                }
                v12 = ((++v10 >= v8.length || this.readByte(v8[v10]) < 67) ? -1 : this.readUnsignedShort(v8[v10] + 1));
            }
        }
        if (array[int1] != null) {
            v-11.visitLabel(array[int1]);
        }
        if ((v-10.flags & 0x2) == 0x0 && v13 != 0) {
            int[] v48 = null;
            if (v14 != 0) {
                v-9 = v14 + 2;
                v48 = new int[this.readUnsignedShort(v14) * 3];
                for (int v24 = v48.length; v24 > 0; v48[--v24] = v-9 + 6, v48[--v24] = this.readUnsignedShort(v-9 + 8), v48[--v24] = this.readUnsignedShort(v-9), v-9 += 10) {}
            }
            v-9 = v13 + 2;
            for (int v24 = this.readUnsignedShort(v13); v24 > 0; --v24) {
                final int v25 = this.readUnsignedShort(v-9);
                final int v26 = this.readUnsignedShort(v-9 + 2);
                final int v33 = this.readUnsignedShort(v-9 + 8);
                String v39 = null;
                if (v48 != null) {
                    for (int v43 = 0; v43 < v48.length; v43 += 3) {
                        if (v48[v43] == v25 && v48[v43 + 1] == v33) {
                            v39 = this.readUTF8(v48[v43 + 2], buffer);
                            break;
                        }
                    }
                }
                v-11.visitLocalVariable(this.readUTF8(v-9 + 4, buffer), this.readUTF8(v-9 + 6, buffer), v39, array[v25], array[v25 + v26], v33);
                v-9 += 10;
            }
        }
        if (v7 != null) {
            for (int v29 = 0; v29 < v7.length; ++v29) {
                if (this.readByte(v7[v29]) >> 1 == 32) {
                    int v24 = this.readAnnotationTarget(v-10, v7[v29]);
                    v24 = this.readAnnotationValues(v24 + 2, buffer, true, v-11.visitLocalVariableAnnotation(v-10.typeRef, v-10.typePath, v-10.start, v-10.end, v-10.index, this.readUTF8(v24, buffer), true));
                }
            }
        }
        if (v8 != null) {
            for (int v29 = 0; v29 < v8.length; ++v29) {
                if (this.readByte(v8[v29]) >> 1 == 32) {
                    int v24 = this.readAnnotationTarget(v-10, v8[v29]);
                    v24 = this.readAnnotationValues(v24 + 2, buffer, true, v-11.visitLocalVariableAnnotation(v-10.typeRef, v-10.typePath, v-10.start, v-10.end, v-10.index, this.readUTF8(v24, buffer), false));
                }
            }
        }
        while (v21 != null) {
            final Attribute v49 = v21.next;
            v21.next = null;
            v-11.visitAttribute(v21);
            v21 = v49;
        }
        v-11.visitMaxs(unsignedShort, unsignedShort2);
    }
    
    private int[] readTypeAnnotations(final MethodVisitor v-6, final Context v-5, int v-4, final boolean v-3) {
        final char[] buffer = v-5.buffer;
        final int[] array = new int[this.readUnsignedShort(v-4)];
        v-4 += 2;
        for (int v0 = 0; v0 < array.length; ++v0) {
            array[v0] = v-4;
            final int v2 = this.readInt(v-4);
            switch (v2 >>> 24) {
                case 0:
                case 1:
                case 22: {
                    v-4 += 2;
                    break;
                }
                case 19:
                case 20:
                case 21: {
                    ++v-4;
                    break;
                }
                case 64:
                case 65: {
                    for (int a3 = this.readUnsignedShort(v-4 + 1); a3 > 0; --a3) {
                        final int a4 = this.readUnsignedShort(v-4 + 3);
                        final int a5 = this.readUnsignedShort(v-4 + 5);
                        this.readLabel(a4, v-5.labels);
                        this.readLabel(a4 + a5, v-5.labels);
                        v-4 += 6;
                    }
                    v-4 += 3;
                    break;
                }
                case 71:
                case 72:
                case 73:
                case 74:
                case 75: {
                    v-4 += 4;
                    break;
                }
                default: {
                    v-4 += 3;
                    break;
                }
            }
            final int v3 = this.readByte(v-4);
            if (v2 >>> 24 == 66) {
                final TypePath a6 = (v3 == 0) ? null : new TypePath(this.b, v-4);
                v-4 += 1 + 2 * v3;
                v-4 = this.readAnnotationValues(v-4 + 2, buffer, true, v-6.visitTryCatchAnnotation(v2, a6, this.readUTF8(v-4, buffer), v-3));
            }
            else {
                v-4 = this.readAnnotationValues(v-4 + 3 + 2 * v3, buffer, true, null);
            }
        }
        return array;
    }
    
    private int readAnnotationTarget(final Context v-3, int v-2) {
        int int1 = this.readInt(v-2);
        switch (int1 >>> 24) {
            case 0:
            case 1:
            case 22: {
                int1 &= 0xFFFF0000;
                v-2 += 2;
                break;
            }
            case 19:
            case 20:
            case 21: {
                int1 &= 0xFF000000;
                ++v-2;
                break;
            }
            case 64:
            case 65: {
                int1 &= 0xFF000000;
                final int v0 = this.readUnsignedShort(v-2 + 1);
                v-3.start = new Label[v0];
                v-3.end = new Label[v0];
                v-3.index = new int[v0];
                v-2 += 3;
                for (int v2 = 0; v2 < v0; ++v2) {
                    final int a1 = this.readUnsignedShort(v-2);
                    final int a2 = this.readUnsignedShort(v-2 + 2);
                    v-3.start[v2] = this.readLabel(a1, v-3.labels);
                    v-3.end[v2] = this.readLabel(a1 + a2, v-3.labels);
                    v-3.index[v2] = this.readUnsignedShort(v-2 + 4);
                    v-2 += 6;
                }
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                int1 &= 0xFF0000FF;
                v-2 += 4;
                break;
            }
            default: {
                int1 &= ((int1 >>> 24 < 67) ? -256 : -16777216);
                v-2 += 3;
                break;
            }
        }
        final int v0 = this.readByte(v-2);
        v-3.typeRef = int1;
        v-3.typePath = ((v0 == 0) ? null : new TypePath(this.b, v-2));
        return v-2 + 1 + 2 * v0;
    }
    
    private void readParameterAnnotations(final MethodVisitor v1, final Context v2, int v3, final boolean v4) {
        final int v5 = this.b[v3++] & 0xFF;
        int v6;
        int v7;
        for (v6 = Type.getArgumentTypes(v2.desc).length - v5, v7 = 0; v7 < v6; ++v7) {
            final AnnotationVisitor a1 = v1.visitParameterAnnotation(v7, "Ljava/lang/Synthetic;", false);
            if (a1 != null) {
                a1.visitEnd();
            }
        }
        final char[] v8 = v2.buffer;
        while (v7 < v5 + v6) {
            int a2 = this.readUnsignedShort(v3);
            v3 += 2;
            while (a2 > 0) {
                final AnnotationVisitor a3 = v1.visitParameterAnnotation(v7, this.readUTF8(v3, v8), v4);
                v3 = this.readAnnotationValues(v3 + 2, v8, true, a3);
                --a2;
            }
            ++v7;
        }
    }
    
    private int readAnnotationValues(int a1, final char[] a2, final boolean a3, final AnnotationVisitor a4) {
        int v1 = this.readUnsignedShort(a1);
        a1 += 2;
        if (a3) {
            while (v1 > 0) {
                a1 = this.readAnnotationValue(a1 + 2, a2, this.readUTF8(a1, a2), a4);
                --v1;
            }
        }
        else {
            while (v1 > 0) {
                a1 = this.readAnnotationValue(a1, a2, null, a4);
                --v1;
            }
        }
        if (a4 != null) {
            a4.visitEnd();
        }
        return a1;
    }
    
    private int readAnnotationValue(int v-5, final char[] v-4, final String v-3, final AnnotationVisitor v-2) {
        if (v-2 != null) {
            Label_1209: {
                switch (this.b[v-5++] & 0xFF) {
                    case 68:
                    case 70:
                    case 73:
                    case 74: {
                        v-2.visit(v-3, this.readConst(this.readUnsignedShort(v-5), v-4));
                        v-5 += 2;
                        break;
                    }
                    case 66: {
                        v-2.visit(v-3, (byte)this.readInt(this.items[this.readUnsignedShort(v-5)]));
                        v-5 += 2;
                        break;
                    }
                    case 90: {
                        v-2.visit(v-3, (this.readInt(this.items[this.readUnsignedShort(v-5)]) == 0) ? Boolean.FALSE : Boolean.TRUE);
                        v-5 += 2;
                        break;
                    }
                    case 83: {
                        v-2.visit(v-3, (short)this.readInt(this.items[this.readUnsignedShort(v-5)]));
                        v-5 += 2;
                        break;
                    }
                    case 67: {
                        v-2.visit(v-3, (char)this.readInt(this.items[this.readUnsignedShort(v-5)]));
                        v-5 += 2;
                        break;
                    }
                    case 115: {
                        v-2.visit(v-3, this.readUTF8(v-5, v-4));
                        v-5 += 2;
                        break;
                    }
                    case 101: {
                        v-2.visitEnum(v-3, this.readUTF8(v-5, v-4), this.readUTF8(v-5 + 2, v-4));
                        v-5 += 4;
                        break;
                    }
                    case 99: {
                        v-2.visit(v-3, Type.getType(this.readUTF8(v-5, v-4)));
                        v-5 += 2;
                        break;
                    }
                    case 64: {
                        v-5 = this.readAnnotationValues(v-5 + 2, v-4, true, v-2.visitAnnotation(v-3, this.readUTF8(v-5, v-4)));
                        break;
                    }
                    case 91: {
                        final int unsignedShort = this.readUnsignedShort(v-5);
                        v-5 += 2;
                        if (unsignedShort == 0) {
                            return this.readAnnotationValues(v-5 - 2, v-4, false, v-2.visitArray(v-3));
                        }
                        switch (this.b[v-5++] & 0xFF) {
                            case 66: {
                                final byte[] a2 = new byte[unsignedShort];
                                for (int a3 = 0; a3 < unsignedShort; ++a3) {
                                    a2[a3] = (byte)this.readInt(this.items[this.readUnsignedShort(v-5)]);
                                    v-5 += 3;
                                }
                                v-2.visit(v-3, a2);
                                --v-5;
                                break Label_1209;
                            }
                            case 90: {
                                final boolean[] a4 = new boolean[unsignedShort];
                                for (int a5 = 0; a5 < unsignedShort; ++a5) {
                                    a4[a5] = (this.readInt(this.items[this.readUnsignedShort(v-5)]) != 0);
                                    v-5 += 3;
                                }
                                v-2.visit(v-3, a4);
                                --v-5;
                                break Label_1209;
                            }
                            case 83: {
                                final short[] v3 = new short[unsignedShort];
                                for (int v4 = 0; v4 < unsignedShort; ++v4) {
                                    v3[v4] = (short)this.readInt(this.items[this.readUnsignedShort(v-5)]);
                                    v-5 += 3;
                                }
                                v-2.visit(v-3, v3);
                                --v-5;
                                break Label_1209;
                            }
                            case 67: {
                                final char[] v5 = new char[unsignedShort];
                                for (int v4 = 0; v4 < unsignedShort; ++v4) {
                                    v5[v4] = (char)this.readInt(this.items[this.readUnsignedShort(v-5)]);
                                    v-5 += 3;
                                }
                                v-2.visit(v-3, v5);
                                --v-5;
                                break Label_1209;
                            }
                            case 73: {
                                final int[] v6 = new int[unsignedShort];
                                for (int v4 = 0; v4 < unsignedShort; ++v4) {
                                    v6[v4] = this.readInt(this.items[this.readUnsignedShort(v-5)]);
                                    v-5 += 3;
                                }
                                v-2.visit(v-3, v6);
                                --v-5;
                                break Label_1209;
                            }
                            case 74: {
                                final long[] v7 = new long[unsignedShort];
                                for (int v4 = 0; v4 < unsignedShort; ++v4) {
                                    v7[v4] = this.readLong(this.items[this.readUnsignedShort(v-5)]);
                                    v-5 += 3;
                                }
                                v-2.visit(v-3, v7);
                                --v-5;
                                break Label_1209;
                            }
                            case 70: {
                                final float[] v8 = new float[unsignedShort];
                                for (int v4 = 0; v4 < unsignedShort; ++v4) {
                                    v8[v4] = Float.intBitsToFloat(this.readInt(this.items[this.readUnsignedShort(v-5)]));
                                    v-5 += 3;
                                }
                                v-2.visit(v-3, v8);
                                --v-5;
                                break Label_1209;
                            }
                            case 68: {
                                final double[] v9 = new double[unsignedShort];
                                for (int v4 = 0; v4 < unsignedShort; ++v4) {
                                    v9[v4] = Double.longBitsToDouble(this.readLong(this.items[this.readUnsignedShort(v-5)]));
                                    v-5 += 3;
                                }
                                v-2.visit(v-3, v9);
                                --v-5;
                                break Label_1209;
                            }
                            default: {
                                v-5 = this.readAnnotationValues(v-5 - 3, v-4, false, v-2.visitArray(v-3));
                                break Label_1209;
                            }
                        }
                        break;
                    }
                }
            }
            return v-5;
        }
        switch (this.b[v-5] & 0xFF) {
            case 101: {
                return v-5 + 5;
            }
            case 64: {
                return this.readAnnotationValues(v-5 + 3, v-4, true, null);
            }
            case 91: {
                return this.readAnnotationValues(v-5 + 1, v-4, false, null);
            }
            default: {
                return v-5 + 3;
            }
        }
    }
    
    private void getImplicitFrame(final Context v2) {
        final String v3 = v2.desc;
        final Object[] v4 = v2.local;
        int v5 = 0;
        if ((v2.access & 0x8) == 0x0) {
            if ("<init>".equals(v2.name)) {
                v4[v5++] = Opcodes.UNINITIALIZED_THIS;
            }
            else {
                v4[v5++] = this.readClass(this.header + 2, v2.buffer);
            }
        }
        int v6 = 1;
        while (true) {
            final int a1 = v6;
            switch (v3.charAt(v6++)) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z': {
                    v4[v5++] = Opcodes.INTEGER;
                    continue;
                }
                case 'F': {
                    v4[v5++] = Opcodes.FLOAT;
                    continue;
                }
                case 'J': {
                    v4[v5++] = Opcodes.LONG;
                    continue;
                }
                case 'D': {
                    v4[v5++] = Opcodes.DOUBLE;
                    continue;
                }
                case '[': {
                    while (v3.charAt(v6) == '[') {
                        ++v6;
                    }
                    if (v3.charAt(v6) == 'L') {
                        ++v6;
                        while (v3.charAt(v6) != ';') {
                            ++v6;
                        }
                    }
                    v4[v5++] = v3.substring(a1, ++v6);
                    continue;
                }
                case 'L': {
                    while (v3.charAt(v6) != ';') {
                        ++v6;
                    }
                    v4[v5++] = v3.substring(a1 + 1, v6++);
                    continue;
                }
                default: {
                    v2.localCount = v5;
                }
            }
        }
    }
    
    private int readFrame(int v-7, final boolean v-6, final boolean v-5, final Context v-4) {
        final char[] buffer = v-4.buffer;
        final Label[] labels = v-4.labels;
        int n = 0;
        if (v-6) {
            final int a1 = this.b[v-7++] & 0xFF;
        }
        else {
            n = 255;
            v-4.offset = -1;
        }
        v-4.localDiff = 0;
        int v0 = 0;
        if (n < 64) {
            final int a2 = n;
            v-4.mode = 3;
            v-4.stackCount = 0;
        }
        else if (n < 128) {
            final int a3 = n - 64;
            v-7 = this.readFrameType(v-4.stack, 0, v-7, buffer, labels);
            v-4.mode = 4;
            v-4.stackCount = 1;
        }
        else {
            v0 = this.readUnsignedShort(v-7);
            v-7 += 2;
            if (n == 247) {
                v-7 = this.readFrameType(v-4.stack, 0, v-7, buffer, labels);
                v-4.mode = 4;
                v-4.stackCount = 1;
            }
            else if (n >= 248 && n < 251) {
                v-4.mode = 2;
                v-4.localDiff = 251 - n;
                v-4.localCount -= v-4.localDiff;
                v-4.stackCount = 0;
            }
            else if (n == 251) {
                v-4.mode = 3;
                v-4.stackCount = 0;
            }
            else if (n < 255) {
                int v2 = v-5 ? v-4.localCount : 0;
                for (int a4 = n - 251; a4 > 0; --a4) {
                    v-7 = this.readFrameType(v-4.local, v2++, v-7, buffer, labels);
                }
                v-4.mode = 1;
                v-4.localDiff = n - 251;
                v-4.localCount += v-4.localDiff;
                v-4.stackCount = 0;
            }
            else {
                v-4.mode = 0;
                int v2 = this.readUnsignedShort(v-7);
                v-7 += 2;
                v-4.localDiff = v2;
                v-4.localCount = v2;
                int v3 = 0;
                while (v2 > 0) {
                    v-7 = this.readFrameType(v-4.local, v3++, v-7, buffer, labels);
                    --v2;
                }
                v2 = this.readUnsignedShort(v-7);
                v-7 += 2;
                v-4.stackCount = v2;
                v3 = 0;
                while (v2 > 0) {
                    v-7 = this.readFrameType(v-4.stack, v3++, v-7, buffer, labels);
                    --v2;
                }
            }
        }
        this.readLabel(v-4.offset += v0 + 1, labels);
        return v-7;
    }
    
    private int readFrameType(final Object[] a1, final int a2, int a3, final char[] a4, final Label[] a5) {
        final int v1 = this.b[a3++] & 0xFF;
        switch (v1) {
            case 0: {
                a1[a2] = Opcodes.TOP;
                break;
            }
            case 1: {
                a1[a2] = Opcodes.INTEGER;
                break;
            }
            case 2: {
                a1[a2] = Opcodes.FLOAT;
                break;
            }
            case 3: {
                a1[a2] = Opcodes.DOUBLE;
                break;
            }
            case 4: {
                a1[a2] = Opcodes.LONG;
                break;
            }
            case 5: {
                a1[a2] = Opcodes.NULL;
                break;
            }
            case 6: {
                a1[a2] = Opcodes.UNINITIALIZED_THIS;
                break;
            }
            case 7: {
                a1[a2] = this.readClass(a3, a4);
                a3 += 2;
                break;
            }
            default: {
                a1[a2] = this.readLabel(this.readUnsignedShort(a3), a5);
                a3 += 2;
                break;
            }
        }
        return a3;
    }
    
    protected Label readLabel(final int a1, final Label[] a2) {
        if (a2[a1] == null) {
            a2[a1] = new Label();
        }
        return a2[a1];
    }
    
    private int getAttributes() {
        int n = this.header + 8 + this.readUnsignedShort(this.header + 6) * 2;
        for (int v0 = this.readUnsignedShort(n); v0 > 0; --v0) {
            for (int v2 = this.readUnsignedShort(n + 8); v2 > 0; --v2) {
                n += 6 + this.readInt(n + 12);
            }
            n += 8;
        }
        n += 2;
        for (int v0 = this.readUnsignedShort(n); v0 > 0; --v0) {
            for (int v2 = this.readUnsignedShort(n + 8); v2 > 0; --v2) {
                n += 6 + this.readInt(n + 12);
            }
            n += 8;
        }
        return n + 2;
    }
    
    private Attribute readAttribute(final Attribute[] a3, final String a4, final int a5, final int a6, final char[] a7, final int v1, final Label[] v2) {
        for (int a8 = 0; a8 < a3.length; ++a8) {
            if (a3[a8].type.equals(a4)) {
                return a3[a8].read(this, a5, a6, a7, v1, v2);
            }
        }
        return new Attribute(a4).read(this, a5, a6, null, -1, null);
    }
    
    public int getItemCount() {
        return this.items.length;
    }
    
    public int getItem(final int a1) {
        return this.items[a1];
    }
    
    public int getMaxStringLength() {
        return this.maxStringLength;
    }
    
    public int readByte(final int a1) {
        return this.b[a1] & 0xFF;
    }
    
    public int readUnsignedShort(final int a1) {
        final byte[] v1 = this.b;
        return (v1[a1] & 0xFF) << 8 | (v1[a1 + 1] & 0xFF);
    }
    
    public short readShort(final int a1) {
        final byte[] v1 = this.b;
        return (short)((v1[a1] & 0xFF) << 8 | (v1[a1 + 1] & 0xFF));
    }
    
    public int readInt(final int a1) {
        final byte[] v1 = this.b;
        return (v1[a1] & 0xFF) << 24 | (v1[a1 + 1] & 0xFF) << 16 | (v1[a1 + 2] & 0xFF) << 8 | (v1[a1 + 3] & 0xFF);
    }
    
    public long readLong(final int a1) {
        final long v1 = this.readInt(a1);
        final long v2 = (long)this.readInt(a1 + 4) & 0xFFFFFFFFL;
        return v1 << 32 | v2;
    }
    
    public String readUTF8(int a1, final char[] a2) {
        final int v1 = this.readUnsignedShort(a1);
        if (a1 == 0 || v1 == 0) {
            return null;
        }
        final String v2 = this.strings[v1];
        if (v2 != null) {
            return v2;
        }
        a1 = this.items[v1];
        return this.strings[v1] = this.readUTF(a1 + 2, this.readUnsignedShort(a1), a2);
    }
    
    private String readUTF(int a3, final int v1, final char[] v2) {
        final int v3 = a3 + v1;
        final byte[] v4 = this.b;
        int v5 = 0;
        int v6 = 0;
        char v7 = '\0';
        while (a3 < v3) {
            int a4 = v4[a3++];
            switch (v6) {
                case 0: {
                    a4 &= 0xFF;
                    if (a4 < 128) {
                        v2[v5++] = (char)a4;
                        continue;
                    }
                    if (a4 < 224 && a4 > 191) {
                        v7 = (char)(a4 & 0x1F);
                        v6 = 1;
                        continue;
                    }
                    v7 = (char)(a4 & 0xF);
                    v6 = 2;
                    continue;
                }
                case 1: {
                    v2[v5++] = (char)(v7 << 6 | (a4 & 0x3F));
                    v6 = 0;
                    continue;
                }
                case 2: {
                    v7 = (char)(v7 << 6 | (a4 & 0x3F));
                    v6 = 1;
                    continue;
                }
            }
        }
        return new String(v2, 0, v5);
    }
    
    public String readClass(final int a1, final char[] a2) {
        return this.readUTF8(this.items[this.readUnsignedShort(a1)], a2);
    }
    
    public Object readConst(final int v-4, final char[] v-3) {
        final int n = this.items[v-4];
        switch (this.b[n - 1]) {
            case 3: {
                return this.readInt(n);
            }
            case 4: {
                return Float.intBitsToFloat(this.readInt(n));
            }
            case 5: {
                return this.readLong(n);
            }
            case 6: {
                return Double.longBitsToDouble(this.readLong(n));
            }
            case 7: {
                return Type.getObjectType(this.readUTF8(n, v-3));
            }
            case 8: {
                return this.readUTF8(n, v-3);
            }
            case 16: {
                return Type.getMethodType(this.readUTF8(n, v-3));
            }
            default: {
                final int a1 = this.readByte(n);
                final int[] a2 = this.items;
                int v1 = a2[this.readUnsignedShort(n + 1)];
                final boolean v2 = this.b[v1 - 1] == 11;
                final String v3 = this.readClass(v1, v-3);
                v1 = a2[this.readUnsignedShort(v1 + 2)];
                final String v4 = this.readUTF8(v1, v-3);
                final String v5 = this.readUTF8(v1 + 2, v-3);
                return new Handle(a1, v3, v4, v5, v2);
            }
        }
    }
}
