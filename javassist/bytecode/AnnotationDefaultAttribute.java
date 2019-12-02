package javassist.bytecode;

import java.util.*;
import javassist.bytecode.annotation.*;
import java.io.*;

public class AnnotationDefaultAttribute extends AttributeInfo
{
    public static final String tag = "AnnotationDefault";
    
    public AnnotationDefaultAttribute(final ConstPool a1, final byte[] a2) {
        super(a1, "AnnotationDefault", a2);
    }
    
    public AnnotationDefaultAttribute(final ConstPool a1) {
        this(a1, new byte[] { 0, 0 });
    }
    
    AnnotationDefaultAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v1, final Map v2) {
        final AnnotationsAttribute.Copier v3 = new AnnotationsAttribute.Copier(this.info, this.constPool, v1, v2);
        try {
            v3.memberValue(0);
            return new AnnotationDefaultAttribute(v1, v3.close());
        }
        catch (Exception a1) {
            throw new RuntimeException(a1.toString());
        }
    }
    
    public MemberValue getDefaultValue() {
        try {
            return new AnnotationsAttribute.Parser(this.info, this.constPool).parseMemberValue();
        }
        catch (Exception v1) {
            throw new RuntimeException(v1.toString());
        }
    }
    
    public void setDefaultValue(final MemberValue v2) {
        final ByteArrayOutputStream v3 = new ByteArrayOutputStream();
        final AnnotationsWriter v4 = new AnnotationsWriter(v3, this.constPool);
        try {
            v2.write(v4);
            v4.close();
        }
        catch (IOException a1) {
            throw new RuntimeException(a1);
        }
        this.set(v3.toByteArray());
    }
    
    @Override
    public String toString() {
        return this.getDefaultValue().toString();
    }
}
