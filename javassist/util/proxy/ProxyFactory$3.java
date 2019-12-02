package javassist.util.proxy;

import java.util.*;

static final class ProxyFactory$3 implements Comparator {
    ProxyFactory$3() {
        super();
    }
    
    @Override
    public int compare(final Object a1, final Object a2) {
        final Map.Entry v1 = (Map.Entry)a1;
        final Map.Entry v2 = (Map.Entry)a2;
        final String v3 = v1.getKey();
        final String v4 = v2.getKey();
        return v3.compareTo(v4);
    }
}