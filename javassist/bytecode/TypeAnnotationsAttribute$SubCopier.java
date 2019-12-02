package javassist.bytecode;

import java.util.*;
import javassist.bytecode.annotation.*;

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
