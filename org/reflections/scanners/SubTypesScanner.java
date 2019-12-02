package org.reflections.scanners;

import org.reflections.util.*;
import com.google.common.base.*;
import java.util.*;

public class SubTypesScanner extends AbstractScanner
{
    public SubTypesScanner() {
        this(true);
    }
    
    public SubTypesScanner(final boolean a1) {
        super();
        if (a1) {
            this.filterResultsBy(new FilterBuilder().exclude(Object.class.getName()));
        }
    }
    
    @Override
    public void scan(final Object v2) {
        final String v3 = this.getMetadataAdapter().getClassName(v2);
        final String v4 = this.getMetadataAdapter().getSuperclassName(v2);
        if (this.acceptResult(v4)) {
            this.getStore().put((Object)v4, (Object)v3);
        }
        for (final String a1 : this.getMetadataAdapter().getInterfacesNames(v2)) {
            if (this.acceptResult(a1)) {
                this.getStore().put((Object)a1, (Object)v3);
            }
        }
    }
}
