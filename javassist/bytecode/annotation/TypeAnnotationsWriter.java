package javassist.bytecode.annotation;

import javassist.bytecode.*;
import java.io.*;

public class TypeAnnotationsWriter extends AnnotationsWriter
{
    public TypeAnnotationsWriter(final OutputStream a1, final ConstPool a2) {
        super(a1, a2);
    }
    
    @Override
    public void numAnnotations(final int a1) throws IOException {
        super.numAnnotations(a1);
    }
    
    public void typeParameterTarget(final int a1, final int a2) throws IOException {
        this.output.write(a1);
        this.output.write(a2);
    }
    
    public void supertypeTarget(final int a1) throws IOException {
        this.output.write(16);
        this.write16bit(a1);
    }
    
    public void typeParameterBoundTarget(final int a1, final int a2, final int a3) throws IOException {
        this.output.write(a1);
        this.output.write(a2);
        this.output.write(a3);
    }
    
    public void emptyTarget(final int a1) throws IOException {
        this.output.write(a1);
    }
    
    public void formalParameterTarget(final int a1) throws IOException {
        this.output.write(22);
        this.output.write(a1);
    }
    
    public void throwsTarget(final int a1) throws IOException {
        this.output.write(23);
        this.write16bit(a1);
    }
    
    public void localVarTarget(final int a1, final int a2) throws IOException {
        this.output.write(a1);
        this.write16bit(a2);
    }
    
    public void localVarTargetTable(final int a1, final int a2, final int a3) throws IOException {
        this.write16bit(a1);
        this.write16bit(a2);
        this.write16bit(a3);
    }
    
    public void catchTarget(final int a1) throws IOException {
        this.output.write(66);
        this.write16bit(a1);
    }
    
    public void offsetTarget(final int a1, final int a2) throws IOException {
        this.output.write(a1);
        this.write16bit(a2);
    }
    
    public void typeArgumentTarget(final int a1, final int a2, final int a3) throws IOException {
        this.output.write(a1);
        this.write16bit(a2);
        this.output.write(a3);
    }
    
    public void typePath(final int a1) throws IOException {
        this.output.write(a1);
    }
    
    public void typePathPath(final int a1, final int a2) throws IOException {
        this.output.write(a1);
        this.output.write(a2);
    }
}
