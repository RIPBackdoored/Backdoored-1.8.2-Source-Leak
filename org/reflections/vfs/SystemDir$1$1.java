package org.reflections.vfs;

import com.google.common.collect.*;
import java.io.*;
import java.util.*;

class SystemDir$1$1 extends AbstractIterator<Vfs.File> {
    final Stack<java.io.File> stack;
    final /* synthetic */ SystemDir$1 this$1;
    
    SystemDir$1$1(final SystemDir$1 a1) {
        this.this$1 = a1;
        super();
    }
    
    {
        (this.stack = new Stack<java.io.File>()).addAll((Collection<?>)SystemDir.access$100(SystemDir.access$000(this.this$1.this$0)));
    }
    
    @Override
    protected Vfs.File computeNext() {
        while (!this.stack.isEmpty()) {
            final java.io.File v1 = this.stack.pop();
            if (!v1.isDirectory()) {
                return new SystemFile(this.this$1.this$0, v1);
            }
            this.stack.addAll((Collection<?>)SystemDir.access$100(v1));
        }
        return this.endOfData();
    }
    
    @Override
    protected /* bridge */ Object computeNext() {
        return this.computeNext();
    }
}