package org.reflections.scanners;

import javassist.bytecode.*;
import java.lang.reflect.*;
import com.google.common.base.*;
import org.reflections.adapters.*;
import java.util.*;

public class MethodParameterNamesScanner extends AbstractScanner
{
    public MethodParameterNamesScanner() {
        super();
    }
    
    @Override
    public void scan(final Object v-4) {
        final MetadataAdapter metadataAdapter = this.getMetadataAdapter();
        for (final Object next : metadataAdapter.getMethods(v-4)) {
            final String v0 = metadataAdapter.getMethodFullKey(v-4, next);
            if (this.acceptResult(v0)) {
                final LocalVariableAttribute v2 = (LocalVariableAttribute)((MethodInfo)next).getCodeAttribute().getAttribute("LocalVariableTable");
                final int v3 = v2.tableLength();
                int v4 = Modifier.isStatic(((MethodInfo)next).getAccessFlags()) ? 0 : 1;
                if (v4 >= v3) {
                    continue;
                }
                final List<String> a1 = new ArrayList<String>(v3 - v4);
                while (v4 < v3) {
                    a1.add(((MethodInfo)next).getConstPool().getUtf8Info(v2.nameIndex(v4++)));
                }
                this.getStore().put((Object)v0, (Object)Joiner.on(", ").join(a1));
            }
        }
    }
}
