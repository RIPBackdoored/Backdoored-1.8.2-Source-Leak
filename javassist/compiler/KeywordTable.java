package javassist.compiler;

import java.util.*;

public final class KeywordTable extends HashMap
{
    public KeywordTable() {
        super();
    }
    
    public int lookup(final String a1) {
        final Object v1 = this.get(a1);
        if (v1 == null) {
            return -1;
        }
        return (int)v1;
    }
    
    public void append(final String a1, final int a2) {
        this.put(a1, new Integer(a2));
    }
}
