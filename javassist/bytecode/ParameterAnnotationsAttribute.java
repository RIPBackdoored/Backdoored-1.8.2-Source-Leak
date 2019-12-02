package javassist.bytecode;

import javassist.bytecode.annotation.*;
import java.io.*;
import java.util.*;

public class ParameterAnnotationsAttribute extends AttributeInfo
{
    public static final String visibleTag = "RuntimeVisibleParameterAnnotations";
    public static final String invisibleTag = "RuntimeInvisibleParameterAnnotations";
    
    public ParameterAnnotationsAttribute(final ConstPool a1, final String a2, final byte[] a3) {
        super(a1, a2, a3);
    }
    
    public ParameterAnnotationsAttribute(final ConstPool a1, final String a2) {
        this(a1, a2, new byte[] { 0 });
    }
    
    ParameterAnnotationsAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    public int numParameters() {
        return this.info[0] & 0xFF;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v1, final Map v2) {
        final AnnotationsAttribute.Copier v3 = new AnnotationsAttribute.Copier(this.info, this.constPool, v1, v2);
        try {
            v3.parameters();
            return new ParameterAnnotationsAttribute(v1, this.getName(), v3.close());
        }
        catch (Exception a1) {
            throw new RuntimeException(a1.toString());
        }
    }
    
    public Annotation[][] getAnnotations() {
        try {
            return new AnnotationsAttribute.Parser(this.info, this.constPool).parseParameters();
        }
        catch (Exception v1) {
            throw new RuntimeException(v1.toString());
        }
    }
    
    public void setAnnotations(final Annotation[][] v-4) {
        final ByteArrayOutputStream a2 = new ByteArrayOutputStream();
        final AnnotationsWriter v3 = new AnnotationsWriter(a2, this.constPool);
        try {
            final int length = v-4.length;
            v3.numParameters(length);
            for (final Annotation[] v2 : v-4) {
                v3.numAnnotations(v2.length);
                for (int a1 = 0; a1 < v2.length; ++a1) {
                    v2[a1].write(v3);
                }
            }
            v3.close();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.set(a2.toByteArray());
    }
    
    @Override
    void renameClass(final String a1, final String a2) {
        final HashMap v1 = new HashMap();
        v1.put(a1, a2);
        this.renameClass(v1);
    }
    
    @Override
    void renameClass(final Map v2) {
        final AnnotationsAttribute.Renamer v3 = new AnnotationsAttribute.Renamer(this.info, this.getConstPool(), v2);
        try {
            v3.parameters();
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
        final Annotation[][] annotations = this.getAnnotations();
        final StringBuilder sb = new StringBuilder();
        int v0 = 0;
        while (v0 < annotations.length) {
            final Annotation[] v2 = annotations[v0++];
            int v3 = 0;
            while (v3 < v2.length) {
                sb.append(v2[v3++].toString());
                if (v3 != v2.length) {
                    sb.append(" ");
                }
            }
            if (v0 != annotations.length) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
