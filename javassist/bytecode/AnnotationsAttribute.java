package javassist.bytecode;

import java.io.*;
import java.util.*;
import javassist.bytecode.annotation.*;

public class AnnotationsAttribute extends AttributeInfo
{
    public static final String visibleTag = "RuntimeVisibleAnnotations";
    public static final String invisibleTag = "RuntimeInvisibleAnnotations";
    
    public AnnotationsAttribute(final ConstPool a1, final String a2, final byte[] a3) {
        super(a1, a2, a3);
    }
    
    public AnnotationsAttribute(final ConstPool a1, final String a2) {
        this(a1, a2, new byte[] { 0, 0 });
    }
    
    AnnotationsAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    public int numAnnotations() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v1, final Map v2) {
        final Copier v3 = new Copier(this.info, this.constPool, v1, v2);
        try {
            v3.annotationArray();
            return new AnnotationsAttribute(v1, this.getName(), v3.close());
        }
        catch (Exception a1) {
            throw new RuntimeException(a1);
        }
    }
    
    public Annotation getAnnotation(final String v2) {
        final Annotation[] v3 = this.getAnnotations();
        for (int a1 = 0; a1 < v3.length; ++a1) {
            if (v3[a1].getTypeName().equals(v2)) {
                return v3[a1];
            }
        }
        return null;
    }
    
    public void addAnnotation(final Annotation v2) {
        final String v3 = v2.getTypeName();
        final Annotation[] v4 = this.getAnnotations();
        for (int a1 = 0; a1 < v4.length; ++a1) {
            if (v4[a1].getTypeName().equals(v3)) {
                v4[a1] = v2;
                this.setAnnotations(v4);
                return;
            }
        }
        final Annotation[] v5 = new Annotation[v4.length + 1];
        System.arraycopy(v4, 0, v5, 0, v4.length);
        v5[v4.length] = v2;
        this.setAnnotations(v5);
    }
    
    public boolean removeAnnotation(final String v-1) {
        final Annotation[] v0 = this.getAnnotations();
        for (int v2 = 0; v2 < v0.length; ++v2) {
            if (v0[v2].getTypeName().equals(v-1)) {
                final Annotation[] a1 = new Annotation[v0.length - 1];
                System.arraycopy(v0, 0, a1, 0, v2);
                if (v2 < v0.length - 1) {
                    System.arraycopy(v0, v2 + 1, a1, v2, v0.length - v2 - 1);
                }
                this.setAnnotations(a1);
                return true;
            }
        }
        return false;
    }
    
    public Annotation[] getAnnotations() {
        try {
            return new Parser(this.info, this.constPool).parseAnnotations();
        }
        catch (Exception v1) {
            throw new RuntimeException(v1);
        }
    }
    
    public void setAnnotations(final Annotation[] v-2) {
        final ByteArrayOutputStream a2 = new ByteArrayOutputStream();
        final AnnotationsWriter v0 = new AnnotationsWriter(a2, this.constPool);
        try {
            final int v2 = v-2.length;
            v0.numAnnotations(v2);
            for (int a1 = 0; a1 < v2; ++a1) {
                v-2[a1].write(v0);
            }
            v0.close();
        }
        catch (IOException v3) {
            throw new RuntimeException(v3);
        }
        this.set(a2.toByteArray());
    }
    
    public void setAnnotation(final Annotation a1) {
        this.setAnnotations(new Annotation[] { a1 });
    }
    
    @Override
    void renameClass(final String a1, final String a2) {
        final HashMap v1 = new HashMap();
        v1.put(a1, a2);
        this.renameClass(v1);
    }
    
    @Override
    void renameClass(final Map v2) {
        final Renamer v3 = new Renamer(this.info, this.getConstPool(), v2);
        try {
            v3.annotationArray();
        }
        catch (Exception a1) {
            throw new RuntimeException(a1);
        }
    }
    
    @Override
    void getRefClasses(final Map a1) {
        this.renameClass(a1);
    }
    
    @Override
    public String toString() {
        final Annotation[] v1 = this.getAnnotations();
        final StringBuilder v2 = new StringBuilder();
        int v3 = 0;
        while (v3 < v1.length) {
            v2.append(v1[v3++].toString());
            if (v3 != v1.length) {
                v2.append(", ");
            }
        }
        return v2.toString();
    }
    
    static class Walker
    {
        byte[] info;
        
        Walker(final byte[] a1) {
            super();
            this.info = a1;
        }
        
        final void parameters() throws Exception {
            final int v1 = this.info[0] & 0xFF;
            this.parameters(v1, 1);
        }
        
        void parameters(final int v1, int v2) throws Exception {
            for (int a1 = 0; a1 < v1; ++a1) {
                v2 = this.annotationArray(v2);
            }
        }
        
        final void annotationArray() throws Exception {
            this.annotationArray(0);
        }
        
        final int annotationArray(final int a1) throws Exception {
            final int v1 = ByteArray.readU16bit(this.info, a1);
            return this.annotationArray(a1 + 2, v1);
        }
        
        int annotationArray(int v1, final int v2) throws Exception {
            for (int a1 = 0; a1 < v2; ++a1) {
                v1 = this.annotation(v1);
            }
            return v1;
        }
        
        final int annotation(final int a1) throws Exception {
            final int v1 = ByteArray.readU16bit(this.info, a1);
            final int v2 = ByteArray.readU16bit(this.info, a1 + 2);
            return this.annotation(a1 + 4, v1, v2);
        }
        
        int annotation(int a3, final int v1, final int v2) throws Exception {
            for (int a4 = 0; a4 < v2; ++a4) {
                a3 = this.memberValuePair(a3);
            }
            return a3;
        }
        
        final int memberValuePair(final int a1) throws Exception {
            final int v1 = ByteArray.readU16bit(this.info, a1);
            return this.memberValuePair(a1 + 2, v1);
        }
        
        int memberValuePair(final int a1, final int a2) throws Exception {
            return this.memberValue(a1);
        }
        
        final int memberValue(final int v-2) throws Exception {
            final int a2 = this.info[v-2] & 0xFF;
            if (a2 == 101) {
                final int a1 = ByteArray.readU16bit(this.info, v-2 + 1);
                final int v1 = ByteArray.readU16bit(this.info, v-2 + 3);
                this.enumMemberValue(v-2, a1, v1);
                return v-2 + 5;
            }
            if (a2 == 99) {
                final int v2 = ByteArray.readU16bit(this.info, v-2 + 1);
                this.classMemberValue(v-2, v2);
                return v-2 + 3;
            }
            if (a2 == 64) {
                return this.annotationMemberValue(v-2 + 1);
            }
            if (a2 == 91) {
                final int v2 = ByteArray.readU16bit(this.info, v-2 + 1);
                return this.arrayMemberValue(v-2 + 3, v2);
            }
            final int v2 = ByteArray.readU16bit(this.info, v-2 + 1);
            this.constValueMember(a2, v2);
            return v-2 + 3;
        }
        
        void constValueMember(final int a1, final int a2) throws Exception {
        }
        
        void enumMemberValue(final int a1, final int a2, final int a3) throws Exception {
        }
        
        void classMemberValue(final int a1, final int a2) throws Exception {
        }
        
        int annotationMemberValue(final int a1) throws Exception {
            return this.annotation(a1);
        }
        
        int arrayMemberValue(int v1, final int v2) throws Exception {
            for (int a1 = 0; a1 < v2; ++a1) {
                v1 = this.memberValue(v1);
            }
            return v1;
        }
    }
    
    static class Renamer extends Walker
    {
        ConstPool cpool;
        Map classnames;
        
        Renamer(final byte[] a1, final ConstPool a2, final Map a3) {
            super(a1);
            this.cpool = a2;
            this.classnames = a3;
        }
        
        @Override
        int annotation(final int a1, final int a2, final int a3) throws Exception {
            this.renameType(a1 - 4, a2);
            return super.annotation(a1, a2, a3);
        }
        
        @Override
        void enumMemberValue(final int a1, final int a2, final int a3) throws Exception {
            this.renameType(a1 + 1, a2);
            super.enumMemberValue(a1, a2, a3);
        }
        
        @Override
        void classMemberValue(final int a1, final int a2) throws Exception {
            this.renameType(a1 + 1, a2);
            super.classMemberValue(a1, a2);
        }
        
        private void renameType(final int v1, final int v2) {
            final String v3 = this.cpool.getUtf8Info(v2);
            final String v4 = Descriptor.rename(v3, this.classnames);
            if (!v3.equals(v4)) {
                final int a1 = this.cpool.addUtf8Info(v4);
                ByteArray.write16bit(a1, this.info, v1);
            }
        }
    }
    
    static class Copier extends Walker
    {
        ByteArrayOutputStream output;
        AnnotationsWriter writer;
        ConstPool srcPool;
        ConstPool destPool;
        Map classnames;
        
        Copier(final byte[] a1, final ConstPool a2, final ConstPool a3, final Map a4) {
            this(a1, a2, a3, a4, true);
        }
        
        Copier(final byte[] a1, final ConstPool a2, final ConstPool a3, final Map a4, final boolean a5) {
            super(a1);
            this.output = new ByteArrayOutputStream();
            if (a5) {
                this.writer = new AnnotationsWriter(this.output, a3);
            }
            this.srcPool = a2;
            this.destPool = a3;
            this.classnames = a4;
        }
        
        byte[] close() throws IOException {
            this.writer.close();
            return this.output.toByteArray();
        }
        
        @Override
        void parameters(final int a1, final int a2) throws Exception {
            this.writer.numParameters(a1);
            super.parameters(a1, a2);
        }
        
        @Override
        int annotationArray(final int a1, final int a2) throws Exception {
            this.writer.numAnnotations(a2);
            return super.annotationArray(a1, a2);
        }
        
        @Override
        int annotation(final int a1, final int a2, final int a3) throws Exception {
            this.writer.annotation(this.copyType(a2), a3);
            return super.annotation(a1, a2, a3);
        }
        
        @Override
        int memberValuePair(final int a1, final int a2) throws Exception {
            this.writer.memberValuePair(this.copy(a2));
            return super.memberValuePair(a1, a2);
        }
        
        @Override
        void constValueMember(final int a1, final int a2) throws Exception {
            this.writer.constValueIndex(a1, this.copy(a2));
            super.constValueMember(a1, a2);
        }
        
        @Override
        void enumMemberValue(final int a1, final int a2, final int a3) throws Exception {
            this.writer.enumConstValue(this.copyType(a2), this.copy(a3));
            super.enumMemberValue(a1, a2, a3);
        }
        
        @Override
        void classMemberValue(final int a1, final int a2) throws Exception {
            this.writer.classInfoIndex(this.copyType(a2));
            super.classMemberValue(a1, a2);
        }
        
        @Override
        int annotationMemberValue(final int a1) throws Exception {
            this.writer.annotationValue();
            return super.annotationMemberValue(a1);
        }
        
        @Override
        int arrayMemberValue(final int a1, final int a2) throws Exception {
            this.writer.arrayValue(a2);
            return super.arrayMemberValue(a1, a2);
        }
        
        int copy(final int a1) {
            return this.srcPool.copy(a1, this.destPool, this.classnames);
        }
        
        int copyType(final int a1) {
            final String v1 = this.srcPool.getUtf8Info(a1);
            final String v2 = Descriptor.rename(v1, this.classnames);
            return this.destPool.addUtf8Info(v2);
        }
    }
    
    static class Parser extends Walker
    {
        ConstPool pool;
        Annotation[][] allParams;
        Annotation[] allAnno;
        Annotation currentAnno;
        MemberValue currentMember;
        
        Parser(final byte[] a1, final ConstPool a2) {
            super(a1);
            this.pool = a2;
        }
        
        Annotation[][] parseParameters() throws Exception {
            this.parameters();
            return this.allParams;
        }
        
        Annotation[] parseAnnotations() throws Exception {
            this.annotationArray();
            return this.allAnno;
        }
        
        MemberValue parseMemberValue() throws Exception {
            this.memberValue(0);
            return this.currentMember;
        }
        
        @Override
        void parameters(final int v1, int v2) throws Exception {
            final Annotation[][] v3 = new Annotation[v1][];
            for (int a1 = 0; a1 < v1; ++a1) {
                v2 = this.annotationArray(v2);
                v3[a1] = this.allAnno;
            }
            this.allParams = v3;
        }
        
        @Override
        int annotationArray(int v1, final int v2) throws Exception {
            final Annotation[] v3 = new Annotation[v2];
            for (int a1 = 0; a1 < v2; ++a1) {
                v1 = this.annotation(v1);
                v3[a1] = this.currentAnno;
            }
            this.allAnno = v3;
            return v1;
        }
        
        @Override
        int annotation(final int a1, final int a2, final int a3) throws Exception {
            this.currentAnno = new Annotation(a2, this.pool);
            return super.annotation(a1, a2, a3);
        }
        
        @Override
        int memberValuePair(int a1, final int a2) throws Exception {
            a1 = super.memberValuePair(a1, a2);
            this.currentAnno.addMemberValue(a2, this.currentMember);
            return a1;
        }
        
        @Override
        void constValueMember(final int v-1, final int v0) throws Exception {
            final ConstPool v = this.pool;
            MemberValue v2 = null;
            switch (v-1) {
                case 66: {
                    final MemberValue a1 = new ByteMemberValue(v0, v);
                    break;
                }
                case 67: {
                    final MemberValue a2 = new CharMemberValue(v0, v);
                    break;
                }
                case 68: {
                    v2 = new DoubleMemberValue(v0, v);
                    break;
                }
                case 70: {
                    v2 = new FloatMemberValue(v0, v);
                    break;
                }
                case 73: {
                    v2 = new IntegerMemberValue(v0, v);
                    break;
                }
                case 74: {
                    v2 = new LongMemberValue(v0, v);
                    break;
                }
                case 83: {
                    v2 = new ShortMemberValue(v0, v);
                    break;
                }
                case 90: {
                    v2 = new BooleanMemberValue(v0, v);
                    break;
                }
                case 115: {
                    v2 = new StringMemberValue(v0, v);
                    break;
                }
                default: {
                    throw new RuntimeException("unknown tag:" + v-1);
                }
            }
            this.currentMember = v2;
            super.constValueMember(v-1, v0);
        }
        
        @Override
        void enumMemberValue(final int a1, final int a2, final int a3) throws Exception {
            this.currentMember = new EnumMemberValue(a2, a3, this.pool);
            super.enumMemberValue(a1, a2, a3);
        }
        
        @Override
        void classMemberValue(final int a1, final int a2) throws Exception {
            this.currentMember = new ClassMemberValue(a2, this.pool);
            super.classMemberValue(a1, a2);
        }
        
        @Override
        int annotationMemberValue(int a1) throws Exception {
            final Annotation v1 = this.currentAnno;
            a1 = super.annotationMemberValue(a1);
            this.currentMember = new AnnotationMemberValue(this.currentAnno, this.pool);
            this.currentAnno = v1;
            return a1;
        }
        
        @Override
        int arrayMemberValue(int v1, final int v2) throws Exception {
            final ArrayMemberValue v3 = new ArrayMemberValue(this.pool);
            final MemberValue[] v4 = new MemberValue[v2];
            for (int a1 = 0; a1 < v2; ++a1) {
                v1 = this.memberValue(v1);
                v4[a1] = this.currentMember;
            }
            v3.setValue(v4);
            this.currentMember = v3;
            return v1;
        }
    }
}
