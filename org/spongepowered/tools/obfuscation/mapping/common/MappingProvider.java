package org.spongepowered.tools.obfuscation.mapping.common;

import org.spongepowered.tools.obfuscation.mapping.*;
import javax.annotation.processing.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import com.google.common.collect.*;

public abstract class MappingProvider implements IMappingProvider
{
    protected final Messager messager;
    protected final Filer filer;
    protected final BiMap<String, String> packageMap;
    protected final BiMap<String, String> classMap;
    protected final BiMap<MappingField, MappingField> fieldMap;
    protected final BiMap<MappingMethod, MappingMethod> methodMap;
    
    public MappingProvider(final Messager a1, final Filer a2) {
        super();
        this.packageMap = (BiMap<String, String>)HashBiMap.create();
        this.classMap = (BiMap<String, String>)HashBiMap.create();
        this.fieldMap = (BiMap<MappingField, MappingField>)HashBiMap.create();
        this.methodMap = (BiMap<MappingMethod, MappingMethod>)HashBiMap.create();
        this.messager = a1;
        this.filer = a2;
    }
    
    @Override
    public void clear() {
        this.packageMap.clear();
        this.classMap.clear();
        this.fieldMap.clear();
        this.methodMap.clear();
    }
    
    @Override
    public boolean isEmpty() {
        return this.packageMap.isEmpty() && this.classMap.isEmpty() && this.fieldMap.isEmpty() && this.methodMap.isEmpty();
    }
    
    @Override
    public MappingMethod getMethodMapping(final MappingMethod a1) {
        return this.methodMap.get(a1);
    }
    
    @Override
    public MappingField getFieldMapping(final MappingField a1) {
        return this.fieldMap.get(a1);
    }
    
    @Override
    public String getClassMapping(final String a1) {
        return this.classMap.get(a1);
    }
    
    @Override
    public String getPackageMapping(final String a1) {
        return this.packageMap.get(a1);
    }
}
