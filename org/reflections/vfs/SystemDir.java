package org.reflections.vfs;

import java.io.*;
import java.util.*;
import com.google.common.collect.*;

public class SystemDir implements Vfs.Dir
{
    private final java.io.File file;
    
    public SystemDir(final java.io.File a1) {
        super();
        if (a1 != null && (!a1.isDirectory() || !a1.canRead())) {
            throw new RuntimeException("cannot use dir " + a1);
        }
        this.file = a1;
    }
    
    @Override
    public String getPath() {
        if (this.file == null) {
            return "/NO-SUCH-DIRECTORY/";
        }
        return this.file.getPath().replace("\\", "/");
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        if (this.file == null || !this.file.exists()) {
            return (Iterable<Vfs.File>)Collections.emptyList();
        }
        return new Iterable<Vfs.File>() {
            final /* synthetic */ SystemDir this$0;
            
            SystemDir$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public Iterator<Vfs.File> iterator() {
                return new AbstractIterator<Vfs.File>() {
                    final Stack<java.io.File> stack;
                    final /* synthetic */ SystemDir$1 this$1;
                    
                    SystemDir$1$1() {
                        this.this$1 = a1;
                        super();
                    }
                    
                    {
                        (this.stack = new Stack<java.io.File>()).addAll((Collection<?>)listFiles(this.this$1.this$0.file));
                    }
                    
                    @Override
                    protected Vfs.File computeNext() {
                        while (!this.stack.isEmpty()) {
                            final java.io.File v1 = this.stack.pop();
                            if (!v1.isDirectory()) {
                                return new SystemFile(this.this$1.this$0, v1);
                            }
                            this.stack.addAll((Collection<?>)listFiles(v1));
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
    
    private static List<java.io.File> listFiles(final java.io.File a1) {
        final java.io.File[] v1 = a1.listFiles();
        if (v1 != null) {
            return Lists.newArrayList(v1);
        }
        return (List<java.io.File>)Lists.newArrayList();
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public String toString() {
        return this.getPath();
    }
    
    static /* synthetic */ java.io.File access$000(final SystemDir a1) {
        return a1.file;
    }
    
    static /* bridge */ List access$100(final java.io.File a1) {
        return listFiles(a1);
    }
}
