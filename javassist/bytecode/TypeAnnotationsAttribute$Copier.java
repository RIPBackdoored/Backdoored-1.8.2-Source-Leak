package javassist.bytecode;

import java.util.*;
import javassist.bytecode.annotation.*;
import java.io.*;

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
