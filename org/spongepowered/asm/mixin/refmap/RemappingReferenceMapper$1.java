package org.spongepowered.asm.mixin.refmap;

import com.google.common.io.*;
import java.util.*;
import com.google.common.base.*;
import java.io.*;

static final class RemappingReferenceMapper$1 implements LineProcessor<Object> {
    final /* synthetic */ Map val$map;
    
    RemappingReferenceMapper$1(final Map val$map) {
        this.val$map = val$map;
        super();
    }
    
    public Object getResult() {
        return null;
    }
    
    public boolean processLine(final String v2) throws IOException {
        if (Strings.isNullOrEmpty(v2) || v2.startsWith("#")) {
            return true;
        }
        final int v3 = 0;
        int v4 = 0;
        int n2;
        final int n = v2.startsWith("MD: ") ? (n2 = 2) : (v2.startsWith("FD: ") ? (n2 = 1) : (n2 = 0));
        v4 = n2;
        if (n > 0) {
            final String[] a1 = v2.substring(4).split(" ", 4);
            this.val$map.put(a1[v3].substring(a1[v3].lastIndexOf(47) + 1), a1[v4].substring(a1[v4].lastIndexOf(47) + 1));
        }
        return true;
    }
}