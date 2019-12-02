package org.reflections.vfs;

import com.google.common.collect.*;
import java.util.*;
import java.util.zip.*;

class ZipDir$1 implements Iterable<Vfs.File> {
    final /* synthetic */ ZipDir this$0;
    
    ZipDir$1(final ZipDir a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public Iterator<Vfs.File> iterator() {
        return new AbstractIterator<Vfs.File>() {
            final Enumeration<? extends ZipEntry> entries = this.this$1.this$0.jarFile.entries();
            final /* synthetic */ ZipDir$1 this$1;
            
            ZipDir$1$1() {
                this.this$1 = a1;
                super();
            }
            
            @Override
            protected Vfs.File computeNext() {
                while (this.entries.hasMoreElements()) {
                    final ZipEntry v1 = (ZipEntry)this.entries.nextElement();
                    if (!v1.isDirectory()) {
                        return new ZipFile(this.this$1.this$0, v1);
                    }
                }
                return this.endOfData();
            }
            
            @Override
            protected /* bridge */ Object computeNext() {
                return this.computeNext();
            }
        };
    }
}