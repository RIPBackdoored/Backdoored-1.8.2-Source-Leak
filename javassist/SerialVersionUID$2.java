package javassist;

import java.util.*;

static final class SerialVersionUID$2 implements Comparator {
    SerialVersionUID$2() {
        super();
    }
    
    @Override
    public int compare(final Object a1, final Object a2) {
        final CtConstructor v1 = (CtConstructor)a1;
        final CtConstructor v2 = (CtConstructor)a2;
        return v1.getMethodInfo2().getDescriptor().compareTo(v2.getMethodInfo2().getDescriptor());
    }
}