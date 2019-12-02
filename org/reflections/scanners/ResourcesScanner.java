package org.reflections.scanners;

import org.reflections.vfs.*;

public class ResourcesScanner extends AbstractScanner
{
    public ResourcesScanner() {
        super();
    }
    
    @Override
    public boolean acceptsInput(final String a1) {
        return !a1.endsWith(".class");
    }
    
    @Override
    public Object scan(final Vfs.File a1, final Object a2) {
        this.getStore().put((Object)a1.getName(), (Object)a1.getRelativePath());
        return a2;
    }
    
    @Override
    public void scan(final Object a1) {
        throw new UnsupportedOperationException();
    }
}
