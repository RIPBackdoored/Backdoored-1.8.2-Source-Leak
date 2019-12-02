package org.reflections.scanners;

import org.reflections.adapters.*;
import java.util.*;

public class MethodParameterScanner extends AbstractScanner
{
    public MethodParameterScanner() {
        super();
    }
    
    @Override
    public void scan(final Object v-6) {
        final MetadataAdapter metadataAdapter = this.getMetadataAdapter();
        for (final Object next : metadataAdapter.getMethods(v-6)) {
            final String string = metadataAdapter.getParameterNames(next).toString();
            if (this.acceptResult(string)) {
                this.getStore().put((Object)string, (Object)metadataAdapter.getMethodFullKey(v-6, next));
            }
            final String returnTypeName = metadataAdapter.getReturnTypeName(next);
            if (this.acceptResult(returnTypeName)) {
                this.getStore().put((Object)returnTypeName, (Object)metadataAdapter.getMethodFullKey(v-6, next));
            }
            final List<String> v0 = metadataAdapter.getParameterNames(next);
            for (int v2 = 0; v2 < v0.size(); ++v2) {
                for (final Object a1 : metadataAdapter.getParameterAnnotationNames(next, v2)) {
                    if (this.acceptResult((String)a1)) {
                        this.getStore().put((Object)a1, (Object)metadataAdapter.getMethodFullKey(v-6, next));
                    }
                }
            }
        }
    }
}
