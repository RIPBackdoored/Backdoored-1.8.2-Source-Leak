package org.reflections.vfs;

import java.util.jar.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.zip.*;
import org.reflections.*;
import java.io.*;

public class ZipDir implements Vfs.Dir
{
    final ZipFile jarFile;
    
    public ZipDir(final JarFile a1) {
        super();
        this.jarFile = a1;
    }
    
    @Override
    public String getPath() {
        return this.jarFile.getName();
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        return new Iterable<Vfs.File>() {
            final /* synthetic */ ZipDir this$0;
            
            ZipDir$1() {
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
                                return new org.reflections.vfs.ZipFile(this.this$1.this$0, v1);
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
        };
    }
    
    @Override
    public void close() {
        try {
            this.jarFile.close();
        }
        catch (IOException v1) {
            if (Reflections.log != null) {
                Reflections.log.warn("Could not close JarFile", v1);
            }
        }
    }
    
    @Override
    public String toString() {
        return this.jarFile.getName();
    }
}
