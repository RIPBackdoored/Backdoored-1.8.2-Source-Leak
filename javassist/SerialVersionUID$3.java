package javassist;

import java.util.*;

static final class SerialVersionUID$3 implements Comparator {
    SerialVersionUID$3() {
        super();
    }
    
    @Override
    public int compare(final Object a1, final Object a2) {
        final CtMethod v1 = (CtMethod)a1;
        final CtMethod v2 = (CtMethod)a2;
        int v3 = v1.getName().compareTo(v2.getName());
        if (v3 == 0) {
            v3 = v1.getMethodInfo2().getDescriptor().compareTo(v2.getMethodInfo2().getDescriptor());
        }
        return v3;
    }
}