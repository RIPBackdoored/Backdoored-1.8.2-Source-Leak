package com.sun.jna;

import java.io.*;

static final class Native$5 implements FilenameFilter {
    Native$5() {
        super();
    }
    
    @Override
    public boolean accept(final File a1, final String a2) {
        return a2.endsWith(".x") && a2.startsWith("jna");
    }
}