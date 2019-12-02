package javassist.bytecode;

import javassist.bytecode.annotation.*;
import java.util.*;
import java.io.*;

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
