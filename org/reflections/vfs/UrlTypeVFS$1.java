package org.reflections.vfs;

import com.google.common.base.*;
import java.io.*;

class UrlTypeVFS$1 implements Predicate<java.io.File> {
    final /* synthetic */ UrlTypeVFS this$0;
    
    UrlTypeVFS$1(final UrlTypeVFS a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public boolean apply(final java.io.File a1) {
        return a1.exists() && a1.isFile();
    }
    
    @Override
    public /* bridge */ boolean apply(final Object o) {
        return this.apply((java.io.File)o);
    }
}