package org.reflections.scanners;

import java.util.*;

public class FieldAnnotationsScanner extends AbstractScanner
{
    public FieldAnnotationsScanner() {
        super();
    }
    
    @Override
    public void scan(final Object v-6) {
        final String className = this.getMetadataAdapter().getClassName(v-6);
        final List<Object> fields = this.getMetadataAdapter().getFields(v-6);
        for (final Object next : fields) {
            final List<String> fieldAnnotationNames = this.getMetadataAdapter().getFieldAnnotationNames(next);
            for (final String v1 : fieldAnnotationNames) {
                if (this.acceptResult(v1)) {
                    final String a1 = this.getMetadataAdapter().getFieldName(next);
                    this.getStore().put((Object)v1, (Object)String.format("%s.%s", className, a1));
                }
            }
        }
    }
}
