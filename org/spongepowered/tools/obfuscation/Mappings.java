package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.*;

class Mappings implements IMappingConsumer
{
    private final Map<ObfuscationType, MappingSet<MappingField>> fieldMappings;
    private final Map<ObfuscationType, MappingSet<MappingMethod>> methodMappings;
    private UniqueMappings unique;
    
    public Mappings() {
        super();
        this.fieldMappings = new HashMap<ObfuscationType, MappingSet<MappingField>>();
        this.methodMappings = new HashMap<ObfuscationType, MappingSet<MappingMethod>>();
        this.init();
    }
    
    private void init() {
        for (final ObfuscationType v1 : ObfuscationType.types()) {
            this.fieldMappings.put(v1, new MappingSet<MappingField>());
            this.methodMappings.put(v1, new MappingSet<MappingMethod>());
        }
    }
    
    public IMappingConsumer asUnique() {
        if (this.unique == null) {
            this.unique = new UniqueMappings(this);
        }
        return this.unique;
    }
    
    @Override
    public MappingSet<MappingField> getFieldMappings(final ObfuscationType a1) {
        final MappingSet<MappingField> v1 = this.fieldMappings.get(a1);
        return (v1 != null) ? v1 : new MappingSet<MappingField>();
    }
    
    @Override
    public MappingSet<MappingMethod> getMethodMappings(final ObfuscationType a1) {
        final MappingSet<MappingMethod> v1 = this.methodMappings.get(a1);
        return (v1 != null) ? v1 : new MappingSet<MappingMethod>();
    }
    
    @Override
    public void clear() {
        this.fieldMappings.clear();
        this.methodMappings.clear();
        if (this.unique != null) {
            this.unique.clearMaps();
        }
        this.init();
    }
    
    @Override
    public void addFieldMapping(final ObfuscationType a1, final MappingField a2, final MappingField a3) {
        MappingSet<MappingField> v1 = this.fieldMappings.get(a1);
        if (v1 == null) {
            v1 = new MappingSet<MappingField>();
            this.fieldMappings.put(a1, v1);
        }
        v1.add(new MappingSet.Pair<MappingField>((IMapping)a2, (IMapping)a3));
    }
    
    @Override
    public void addMethodMapping(final ObfuscationType a1, final MappingMethod a2, final MappingMethod a3) {
        MappingSet<MappingMethod> v1 = this.methodMappings.get(a1);
        if (v1 == null) {
            v1 = new MappingSet<MappingMethod>();
            this.methodMappings.put(a1, v1);
        }
        v1.add(new MappingSet.Pair<MappingMethod>((IMapping)a2, (IMapping)a3));
    }
    
    public static class MappingConflictException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        private final IMapping<?> oldMapping;
        private final IMapping<?> newMapping;
        
        public MappingConflictException(final IMapping<?> a1, final IMapping<?> a2) {
            super();
            this.oldMapping = a1;
            this.newMapping = a2;
        }
        
        public IMapping<?> getOld() {
            return this.oldMapping;
        }
        
        public IMapping<?> getNew() {
            return this.newMapping;
        }
    }
    
    static class UniqueMappings implements IMappingConsumer
    {
        private final IMappingConsumer mappings;
        private final Map<ObfuscationType, Map<MappingField, MappingField>> fields;
        private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods;
        
        public UniqueMappings(final IMappingConsumer a1) {
            super();
            this.fields = new HashMap<ObfuscationType, Map<MappingField, MappingField>>();
            this.methods = new HashMap<ObfuscationType, Map<MappingMethod, MappingMethod>>();
            this.mappings = a1;
        }
        
        @Override
        public void clear() {
            this.clearMaps();
            this.mappings.clear();
        }
        
        protected void clearMaps() {
            this.fields.clear();
            this.methods.clear();
        }
        
        @Override
        public void addFieldMapping(final ObfuscationType a1, final MappingField a2, final MappingField a3) {
            if (!this.checkForExistingMapping(a1, (IMapping)a2, (IMapping)a3, (Map)this.fields)) {
                this.mappings.addFieldMapping(a1, a2, a3);
            }
        }
        
        @Override
        public void addMethodMapping(final ObfuscationType a1, final MappingMethod a2, final MappingMethod a3) {
            if (!this.checkForExistingMapping(a1, (IMapping)a2, (IMapping)a3, (Map)this.methods)) {
                this.mappings.addMethodMapping(a1, a2, a3);
            }
        }
        
        private <TMapping extends java.lang.Object> boolean checkForExistingMapping(final ObfuscationType a1, final TMapping a2, final TMapping a3, final Map<ObfuscationType, Map<TMapping, TMapping>> a4) throws MappingConflictException {
            Map<TMapping, TMapping> v1 = a4.get(a1);
            if (v1 == null) {
                v1 = new HashMap<TMapping, TMapping>();
                a4.put(a1, v1);
            }
            final TMapping v2 = v1.get(a2);
            if (v2 == null) {
                v1.put(a2, a3);
                return false;
            }
            if (v2.equals((Object)a3)) {
                return true;
            }
            throw new MappingConflictException((IMapping<?>)v2, (IMapping<?>)a3);
        }
        
        @Override
        public MappingSet<MappingField> getFieldMappings(final ObfuscationType a1) {
            return this.mappings.getFieldMappings(a1);
        }
        
        @Override
        public MappingSet<MappingMethod> getMethodMappings(final ObfuscationType a1) {
            return this.mappings.getMethodMappings(a1);
        }
    }
}
