package com.sun.jna;

import java.io.*;

static final class NativeLibrary$2 implements FilenameFilter {
    final /* synthetic */ String val$libName;
    
    NativeLibrary$2(final String val$libName) {
        this.val$libName = val$libName;
        super();
    }
    
    @Override
    public boolean accept(final File a1, final String a2) {
        return (a2.startsWith("lib" + this.val$libName + ".so") || (a2.startsWith(this.val$libName + ".so") && this.val$libName.startsWith("lib"))) && NativeLibrary.access$000(a2);
    }
}