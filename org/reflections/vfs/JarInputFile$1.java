package org.reflections.vfs;

import java.io.*;

class JarInputFile$1 extends InputStream {
    final /* synthetic */ JarInputFile this$0;
    
    JarInputFile$1(final JarInputFile a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public int read() throws IOException {
        if (JarInputFile.access$000(this.this$0).cursor >= JarInputFile.access$100(this.this$0) && JarInputFile.access$000(this.this$0).cursor <= JarInputFile.access$200(this.this$0)) {
            final int v1 = JarInputFile.access$000(this.this$0).jarInputStream.read();
            final JarInputDir access$000 = JarInputFile.access$000(this.this$0);
            ++access$000.cursor;
            return v1;
        }
        return -1;
    }
}