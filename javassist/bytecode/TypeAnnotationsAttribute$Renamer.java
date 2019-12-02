package javassist.bytecode;

import java.util.*;

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
