package org.reflections.scanners;

import org.reflections.vfs.*;

@Deprecated
public class TypesScanner extends AbstractScanner
{
    public TypesScanner() {
        super();
    }
    
    @Override
    public Object scan(final Vfs.File a1, Object a2) {
        a2 = super.scan(a1, a2);
        final String v1 = this.getMetadataAdapter().getClassName(a2);
        this.getStore().put((Object)v1, (Object)v1);
        return a2;
    }
    
    @Override
    public void scan(final Object a1) {
        throw new UnsupportedOperationException("should not get here");
    }
}
