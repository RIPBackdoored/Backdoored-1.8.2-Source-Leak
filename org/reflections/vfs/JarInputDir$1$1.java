package org.reflections.vfs;

import com.google.common.collect.*;
import java.util.jar.*;
import org.reflections.*;
import java.io.*;
import java.util.zip.*;

class JarInputDir$1$1 extends AbstractIterator<Vfs.File> {
    final /* synthetic */ JarInputDir$1 this$1;
    
    JarInputDir$1$1(final JarInputDir$1 v2) throws Error {
        this.this$1 = v2;
        super();
    }
    
    {
        try {
            this.this$1.this$0.jarInputStream = new JarInputStream(JarInputDir.access$000(this.this$1.this$0).openConnection().getInputStream());
        }
        catch (Exception a1) {
            throw new ReflectionsException("Could not open url connection", a1);
        }
    }
    
    @Override
    protected Vfs.File computeNext() {
        try {
            while (true) {
                final ZipEntry v1 = this.this$1.this$0.jarInputStream.getNextJarEntry();
                if (v1 == null) {
                    return this.endOfData();
                }
                long v2 = v1.getSize();
                if (v2 < 0L) {
                    v2 += 4294967295L;
                }
                final JarInputDir this$0 = this.this$1.this$0;
                this$0.nextCursor += v2;
                if (!v1.isDirectory()) {
                    return new JarInputFile(v1, this.this$1.this$0, this.this$1.this$0.cursor, this.this$1.this$0.nextCursor);
                }
            }
        }
        catch (IOException v3) {
            throw new ReflectionsException("could not get next zip entry", v3);
        }
    }
    
    @Override
    protected /* bridge */ Object computeNext() {
        return this.computeNext();
    }
}