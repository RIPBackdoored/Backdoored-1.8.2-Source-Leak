package javassist;

import java.util.*;

static final class SerialVersionUID$1 implements Comparator {
    SerialVersionUID$1() {
        super();
    }
    
    @Override
    public int compare(final Object a1, final Object a2) {
        final CtField v1 = (CtField)a1;
        final CtField v2 = (CtField)a2;
        return v1.getName().compareTo(v2.getName());
    }
}