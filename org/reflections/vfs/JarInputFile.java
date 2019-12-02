package org.reflections.vfs;

import java.util.zip.*;
import java.io.*;

public class JarInputFile implements Vfs.File
{
    private final ZipEntry entry;
    private final JarInputDir jarInputDir;
    private final long fromIndex;
    private final long endIndex;
    
    public JarInputFile(final ZipEntry a1, final JarInputDir a2, final long a3, final long a4) {
        super();
        this.entry = a1;
        this.jarInputDir = a2;
        this.fromIndex = a3;
        this.endIndex = a4;
    }
    
    @Override
    public String getName() {
        final String v1 = this.entry.getName();
        return v1.substring(v1.lastIndexOf("/") + 1);
    }
    
    @Override
    public String getRelativePath() {
        return this.entry.getName();
    }
    
    @Override
    public InputStream openInputStream() throws IOException {
        return new InputStream() {
            final /* synthetic */ JarInputFile this$0;
            
            JarInputFile$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public int read() throws IOException {
                if (this.this$0.jarInputDir.cursor >= this.this$0.fromIndex && this.this$0.jarInputDir.cursor <= this.this$0.endIndex) {
                    final int v1 = this.this$0.jarInputDir.jarInputStream.read();
                    final JarInputDir access$000 = this.this$0.jarInputDir;
                    ++access$000.cursor;
                    return v1;
                }
                return -1;
            }
        };
    }
    
    static /* synthetic */ JarInputDir access$000(final JarInputFile a1) {
        return a1.jarInputDir;
    }
    
    static /* synthetic */ long access$100(final JarInputFile a1) {
        return a1.fromIndex;
    }
    
    static /* synthetic */ long access$200(final JarInputFile a1) {
        return a1.endIndex;
    }
}
