package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.io.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import org.spongepowered.asm.obfuscation.mapping.mcp.*;
import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.io.*;

class MappingProviderSrg$1 implements LineProcessor<String> {
    final /* synthetic */ BiMap val$packageMap;
    final /* synthetic */ BiMap val$classMap;
    final /* synthetic */ BiMap val$fieldMap;
    final /* synthetic */ BiMap val$methodMap;
    final /* synthetic */ File val$input;
    final /* synthetic */ MappingProviderSrg this$0;
    
    MappingProviderSrg$1(final MappingProviderSrg a1, final BiMap val$packageMap, final BiMap val$classMap, final BiMap val$fieldMap, final BiMap val$methodMap, final File val$input) {
        this.this$0 = a1;
        this.val$packageMap = val$packageMap;
        this.val$classMap = val$classMap;
        this.val$fieldMap = val$fieldMap;
        this.val$methodMap = val$methodMap;
        this.val$input = val$input;
        super();
    }
    
    public String getResult() {
        return null;
    }
    
    public boolean processLine(final String a1) throws IOException {
        if (Strings.isNullOrEmpty(a1) || a1.startsWith("#")) {
            return true;
        }
        final String v1 = a1.substring(0, 2);
        final String[] v2 = a1.substring(4).split(" ");
        if (v1.equals("PK")) {
            this.val$packageMap.forcePut((Object)v2[0], (Object)v2[1]);
        }
        else if (v1.equals("CL")) {
            this.val$classMap.forcePut((Object)v2[0], (Object)v2[1]);
        }
        else if (v1.equals("FD")) {
            this.val$fieldMap.forcePut((Object)new MappingFieldSrg(v2[0]).copy(), (Object)new MappingFieldSrg(v2[1]).copy());
        }
        else {
            if (!v1.equals("MD")) {
                throw new MixinException("Invalid SRG file: " + this.val$input);
            }
            this.val$methodMap.forcePut((Object)new MappingMethod(v2[0], v2[1]), (Object)new MappingMethod(v2[2], v2[3]));
        }
        return true;
    }
    
    public /* bridge */ Object getResult() {
        return this.getResult();
    }
}