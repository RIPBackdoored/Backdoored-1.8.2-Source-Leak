package javassist.bytecode;

import java.util.*;
import javassist.bytecode.annotation.*;
import java.io.*;

public class TypeAnnotationsAttribute extends AttributeInfo
{
    public static final String visibleTag = "RuntimeVisibleTypeAnnotations";
    public static final String invisibleTag = "RuntimeInvisibleTypeAnnotations";
    
    public TypeAnnotationsAttribute(final ConstPool a1, final String a2, final byte[] a3) {
        super(a1, a2, a3);
    }
    
    TypeAnnotationsAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
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
            return new TypeAnnotationsAttribute(v1, this.getName(), v3.close());
        }
        catch (Exception a1) {
            throw new RuntimeException(a1);
        }
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
    
    static class TAWalker extends AnnotationsAttribute.Walker
    {
        SubWalker subWalker;
        
        TAWalker(final byte[] a1) {
            super(a1);
            this.subWalker = new SubWalker(a1);
        }
        
        @Override
        int annotationArray(int v2, final int v3) throws Exception {
            for (int a2 = 0; a2 < v3; ++a2) {
                final int a3 = this.info[v2] & 0xFF;
                v2 = this.subWalker.targetInfo(v2 + 1, a3);
                v2 = this.subWalker.typePath(v2);
                v2 = this.annotation(v2);
            }
            return v2;
        }
    }
    
    static class SubWalker
    {
        byte[] info;
        
        SubWalker(final byte[] a1) {
            super();
            this.info = a1;
        }
        
        final int targetInfo(final int v-1, final int v0) throws Exception {
            switch (v0) {
                case 0:
                case 1: {
                    final int a1 = this.info[v-1] & 0xFF;
                    this.typeParameterTarget(v-1, v0, a1);
                    return v-1 + 1;
                }
                case 16: {
                    final int a2 = ByteArray.readU16bit(this.info, v-1);
                    this.supertypeTarget(v-1, a2);
                    return v-1 + 2;
                }
                case 17:
                case 18: {
                    final int v = this.info[v-1] & 0xFF;
                    final int v2 = this.info[v-1 + 1] & 0xFF;
                    this.typeParameterBoundTarget(v-1, v0, v, v2);
                    return v-1 + 2;
                }
                case 19:
                case 20:
                case 21: {
                    this.emptyTarget(v-1, v0);
                    return v-1;
                }
                case 22: {
                    final int v = this.info[v-1] & 0xFF;
                    this.formalParameterTarget(v-1, v);
                    return v-1 + 1;
                }
                case 23: {
                    final int v = ByteArray.readU16bit(this.info, v-1);
                    this.throwsTarget(v-1, v);
                    return v-1 + 2;
                }
                case 64:
                case 65: {
                    final int v = ByteArray.readU16bit(this.info, v-1);
                    return this.localvarTarget(v-1 + 2, v0, v);
                }
                case 66: {
                    final int v = ByteArray.readU16bit(this.info, v-1);
                    this.catchTarget(v-1, v);
                    return v-1 + 2;
                }
                case 67:
                case 68:
                case 69:
                case 70: {
                    final int v = ByteArray.readU16bit(this.info, v-1);
                    this.offsetTarget(v-1, v0, v);
                    return v-1 + 2;
                }
                case 71:
                case 72:
                case 73:
                case 74:
                case 75: {
                    final int v = ByteArray.readU16bit(this.info, v-1);
                    final int v2 = this.info[v-1 + 2] & 0xFF;
                    this.typeArgumentTarget(v-1, v0, v, v2);
                    return v-1 + 3;
                }
                default: {
                    throw new RuntimeException("invalid target type: " + v0);
                }
            }
        }
        
        void typeParameterTarget(final int a1, final int a2, final int a3) throws Exception {
        }
        
        void supertypeTarget(final int a1, final int a2) throws Exception {
        }
        
        void typeParameterBoundTarget(final int a1, final int a2, final int a3, final int a4) throws Exception {
        }
        
        void emptyTarget(final int a1, final int a2) throws Exception {
        }
        
        void formalParameterTarget(final int a1, final int a2) throws Exception {
        }
        
        void throwsTarget(final int a1, final int a2) throws Exception {
        }
        
        int localvarTarget(int v-2, final int v-1, final int v0) throws Exception {
            for (int v = 0; v < v0; ++v) {
                final int a1 = ByteArray.readU16bit(this.info, v-2);
                final int a2 = ByteArray.readU16bit(this.info, v-2 + 2);
                final int a3 = ByteArray.readU16bit(this.info, v-2 + 4);
                this.localvarTarget(v-2, v-1, a1, a2, a3);
                v-2 += 6;
            }
            return v-2;
        }
        
        void localvarTarget(final int a1, final int a2, final int a3, final int a4, final int a5) throws Exception {
        }
        
        void catchTarget(final int a1, final int a2) throws Exception {
        }
        
        void offsetTarget(final int a1, final int a2, final int a3) throws Exception {
        }
        
        void typeArgumentTarget(final int a1, final int a2, final int a3, final int a4) throws Exception {
        }
        
        final int typePath(int a1) throws Exception {
            final int v1 = this.info[a1++] & 0xFF;
            return this.typePath(a1, v1);
        }
        
        int typePath(int v-1, final int v0) throws Exception {
            for (int v = 0; v < v0; ++v) {
                final int a1 = this.info[v-1] & 0xFF;
                final int a2 = this.info[v-1 + 1] & 0xFF;
                this.typePath(v-1, a1, a2);
                v-1 += 2;
            }
            return v-1;
        }
        
        void typePath(final int a1, final int a2, final int a3) throws Exception {
        }
    }
    
    static class Renamer extends AnnotationsAttribute.Renamer
    {
        SubWalker sub;
        
        Renamer(final byte[] a1, final ConstPool a2, final Map a3) {
            super(a1, a2, a3);
            this.sub = new SubWalker(a1);
        }
        
        @Override
        int annotationArray(int v2, final int v3) throws Exception {
            for (int a2 = 0; a2 < v3; ++a2) {
                final int a3 = this.info[v2] & 0xFF;
                v2 = this.sub.targetInfo(v2 + 1, a3);
                v2 = this.sub.typePath(v2);
                v2 = this.annotation(v2);
            }
            return v2;
        }
    }
    
    static class Copier extends AnnotationsAttribute.Copier
    {
        SubCopier sub;
        
        Copier(final byte[] a1, final ConstPool a2, final ConstPool a3, final Map a4) {
            super(a1, a2, a3, a4, false);
            final TypeAnnotationsWriter v1 = new TypeAnnotationsWriter(this.output, a3);
            this.writer = v1;
            this.sub = new SubCopier(a1, a2, a3, a4, v1);
        }
        
        @Override
        int annotationArray(int v2, final int v3) throws Exception {
            this.writer.numAnnotations(v3);
            for (int a2 = 0; a2 < v3; ++a2) {
                final int a3 = this.info[v2] & 0xFF;
                v2 = this.sub.targetInfo(v2 + 1, a3);
                v2 = this.sub.typePath(v2);
                v2 = this.annotation(v2);
            }
            return v2;
        }
    }
    
    static class SubCopier extends SubWalker
    {
        ConstPool srcPool;
        ConstPool destPool;
        Map classnames;
        TypeAnnotationsWriter writer;
        
        SubCopier(final byte[] a1, final ConstPool a2, final ConstPool a3, final Map a4, final TypeAnnotationsWriter a5) {
            super(a1);
            this.srcPool = a2;
            this.destPool = a3;
            this.classnames = a4;
            this.writer = a5;
        }
        
        @Override
        void typeParameterTarget(final int a1, final int a2, final int a3) throws Exception {
            this.writer.typeParameterTarget(a2, a3);
        }
        
        @Override
        void supertypeTarget(final int a1, final int a2) throws Exception {
            this.writer.supertypeTarget(a2);
        }
        
        @Override
        void typeParameterBoundTarget(final int a1, final int a2, final int a3, final int a4) throws Exception {
            this.writer.typeParameterBoundTarget(a2, a3, a4);
        }
        
        @Override
        void emptyTarget(final int a1, final int a2) throws Exception {
            this.writer.emptyTarget(a2);
        }
        
        @Override
        void formalParameterTarget(final int a1, final int a2) throws Exception {
            this.writer.formalParameterTarget(a2);
        }
        
        @Override
        void throwsTarget(final int a1, final int a2) throws Exception {
            this.writer.throwsTarget(a2);
        }
        
        @Override
        int localvarTarget(final int a1, final int a2, final int a3) throws Exception {
            this.writer.localVarTarget(a2, a3);
            return super.localvarTarget(a1, a2, a3);
        }
        
        @Override
        void localvarTarget(final int a1, final int a2, final int a3, final int a4, final int a5) throws Exception {
            this.writer.localVarTargetTable(a3, a4, a5);
        }
        
        @Override
        void catchTarget(final int a1, final int a2) throws Exception {
            this.writer.catchTarget(a2);
        }
        
        @Override
        void offsetTarget(final int a1, final int a2, final int a3) throws Exception {
            this.writer.offsetTarget(a2, a3);
        }
        
        @Override
        void typeArgumentTarget(final int a1, final int a2, final int a3, final int a4) throws Exception {
            this.writer.typeArgumentTarget(a2, a3, a4);
        }
        
        @Override
        int typePath(final int a1, final int a2) throws Exception {
            this.writer.typePath(a2);
            return super.typePath(a1, a2);
        }
        
        @Override
        void typePath(final int a1, final int a2, final int a3) throws Exception {
            this.writer.typePathPath(a2, a3);
        }
    }
}
