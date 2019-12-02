package org.reflections.scanners;

import java.util.*;

public class MethodAnnotationsScanner extends AbstractScanner
{
    public MethodAnnotationsScanner() {
        super();
    }
    
    @Override
    public void scan(final Object v-1) {
        for (final Object v1 : this.getMetadataAdapter().getMethods(v-1)) {
            for (final String a1 : this.getMetadataAdapter().getMethodAnnotationNames(v1)) {
                if (this.acceptResult(a1)) {
                    this.getStore().put((Object)a1, (Object)this.getMetadataAdapter().getMethodFullKey(v-1, v1));
                }
            }
        }
    }
}
