package org.spongepowered.tools.obfuscation.mapping.mcp;

import org.spongepowered.tools.obfuscation.mapping.common.*;
import javax.annotation.processing.*;
import java.nio.charset.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import org.spongepowered.asm.obfuscation.mapping.mcp.*;
import org.spongepowered.asm.mixin.throwables.*;
import java.io.*;
import com.google.common.io.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public class MappingProviderSrg extends MappingProvider
{
    public MappingProviderSrg(final Messager a1, final Filer a2) {
        super(a1, a2);
    }
    
    @Override
    public void read(final File a1) throws IOException {
        final BiMap<String, String> v1 = this.packageMap;
        final BiMap<String, String> v2 = this.classMap;
        final BiMap<MappingField, MappingField> v3 = this.fieldMap;
        final BiMap<MappingMethod, MappingMethod> v4 = this.methodMap;
        Files.readLines(a1, Charset.defaultCharset(), (LineProcessor)new LineProcessor<String>() {
            final /* synthetic */ BiMap val$packageMap;
            final /* synthetic */ BiMap val$classMap;
            final /* synthetic */ BiMap val$fieldMap;
            final /* synthetic */ BiMap val$methodMap;
            final /* synthetic */ File val$input;
            final /* synthetic */ MappingProviderSrg this$0;
            
            MappingProviderSrg$1() {
                this.this$0 = a1;
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
                    v1.forcePut((Object)v2[0], (Object)v2[1]);
                }
                else if (v1.equals("CL")) {
                    v2.forcePut((Object)v2[0], (Object)v2[1]);
                }
                else if (v1.equals("FD")) {
                    v3.forcePut((Object)new MappingFieldSrg(v2[0]).copy(), (Object)new MappingFieldSrg(v2[1]).copy());
                }
                else {
                    if (!v1.equals("MD")) {
                        throw new MixinException("Invalid SRG file: " + a1);
                    }
                    v4.forcePut((Object)new MappingMethod(v2[0], v2[1]), (Object)new MappingMethod(v2[2], v2[3]));
                }
                return true;
            }
            
            public /* bridge */ Object getResult() {
                return this.getResult();
            }
        });
    }
    
    @Override
    public MappingField getFieldMapping(MappingField a1) {
        if (a1.getDesc() != null) {
            a1 = new MappingFieldSrg(a1);
        }
        return this.fieldMap.get(a1);
    }
}
