package org.reflections.scanners;

import java.lang.annotation.*;
import java.util.*;

public class TypeAnnotationsScanner extends AbstractScanner
{
    public TypeAnnotationsScanner() {
        super();
    }
    
    @Override
    public void scan(final Object v2) {
        final String v3 = this.getMetadataAdapter().getClassName(v2);
        for (final String a1 : this.getMetadataAdapter().getClassAnnotationNames(v2)) {
            if (this.acceptResult(a1) || a1.equals(Inherited.class.getName())) {
                this.getStore().put((Object)a1, (Object)v3);
            }
        }
    }
}
