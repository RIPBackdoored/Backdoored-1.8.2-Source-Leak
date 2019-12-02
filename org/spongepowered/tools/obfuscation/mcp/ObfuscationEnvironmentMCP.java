package org.spongepowered.tools.obfuscation.mcp;

import org.spongepowered.tools.obfuscation.*;
import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mapping.mcp.*;

public class ObfuscationEnvironmentMCP extends ObfuscationEnvironment
{
    protected ObfuscationEnvironmentMCP(final ObfuscationType a1) {
        super(a1);
    }
    
    @Override
    protected IMappingProvider getMappingProvider(final Messager a1, final Filer a2) {
        return new MappingProviderSrg(a1, a2);
    }
    
    @Override
    protected IMappingWriter getMappingWriter(final Messager a1, final Filer a2) {
        return new MappingWriterSrg(a1, a2);
    }
}
