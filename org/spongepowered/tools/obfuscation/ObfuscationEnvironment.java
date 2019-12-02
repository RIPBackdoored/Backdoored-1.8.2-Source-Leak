package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.annotation.processing.*;
import javax.tools.*;
import java.io.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.type.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mapping.*;

public abstract class ObfuscationEnvironment implements IObfuscationEnvironment
{
    protected final ObfuscationType type;
    protected final IMappingProvider mappingProvider;
    protected final IMappingWriter mappingWriter;
    protected final RemapperProxy remapper;
    protected final IMixinAnnotationProcessor ap;
    protected final String outFileName;
    protected final List<String> inFileNames;
    private boolean initDone;
    
    protected ObfuscationEnvironment(final ObfuscationType a1) {
        super();
        this.remapper = new RemapperProxy();
        this.type = a1;
        this.ap = a1.getAnnotationProcessor();
        this.inFileNames = a1.getInputFileNames();
        this.outFileName = a1.getOutputFileName();
        this.mappingProvider = this.getMappingProvider(this.ap, this.ap.getProcessingEnvironment().getFiler());
        this.mappingWriter = this.getMappingWriter(this.ap, this.ap.getProcessingEnvironment().getFiler());
    }
    
    @Override
    public String toString() {
        return this.type.toString();
    }
    
    protected abstract IMappingProvider getMappingProvider(final Messager p0, final Filer p1);
    
    protected abstract IMappingWriter getMappingWriter(final Messager p0, final Filer p1);
    
    private boolean initMappings() {
        if (!this.initDone) {
            this.initDone = true;
            if (this.inFileNames == null) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, "The " + this.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur");
                return false;
            }
            int n = 0;
            for (final String s : this.inFileNames) {
                final File v0 = new File(s);
                try {
                    if (!v0.isFile()) {
                        continue;
                    }
                    this.ap.printMessage(Diagnostic.Kind.NOTE, "Loading " + this.type + " mappings from " + v0.getAbsolutePath());
                    this.mappingProvider.read(v0);
                    ++n;
                }
                catch (Exception v2) {
                    v2.printStackTrace();
                }
            }
            if (n < 1) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, "No valid input files for " + this.type + " could be read, processing may not be sucessful.");
                this.mappingProvider.clear();
            }
        }
        return !this.mappingProvider.isEmpty();
    }
    
    public ObfuscationType getType() {
        return this.type;
    }
    
    @Override
    public MappingMethod getObfMethod(final MemberInfo a1) {
        final MappingMethod v1 = this.getObfMethod(a1.asMethodMapping());
        if (v1 != null || !a1.isFullyQualified()) {
            return v1;
        }
        final TypeHandle v2 = this.ap.getTypeProvider().getTypeHandle(a1.owner);
        if (v2 == null || v2.isImaginary()) {
            return null;
        }
        final TypeMirror v3 = v2.getElement().getSuperclass();
        if (v3.getKind() != TypeKind.DECLARED) {
            return null;
        }
        final String v4 = ((TypeElement)((DeclaredType)v3).asElement()).getQualifiedName().toString();
        return this.getObfMethod(new MemberInfo(a1.name, v4.replace('.', '/'), a1.desc, a1.matchAll));
    }
    
    @Override
    public MappingMethod getObfMethod(final MappingMethod a1) {
        return this.getObfMethod(a1, true);
    }
    
    @Override
    public MappingMethod getObfMethod(final MappingMethod v-2, final boolean v-1) {
        if (!this.initMappings()) {
            return null;
        }
        boolean a2 = true;
        MappingMethod v1 = null;
        for (MappingMethod a3 = v-2; a3 != null && v1 == null; v1 = this.mappingProvider.getMethodMapping(a3), a3 = a3.getSuper()) {}
        if (v1 == null) {
            if (v-1) {
                return null;
            }
            v1 = v-2.copy();
            a2 = false;
        }
        final String v2 = this.getObfClass(v1.getOwner());
        if (v2 == null || v2.equals(v-2.getOwner()) || v2.equals(v1.getOwner())) {
            return a2 ? v1 : null;
        }
        if (a2) {
            return v1.move(v2);
        }
        final String v3 = ObfuscationUtil.mapDescriptor(v1.getDesc(), this.remapper);
        return new MappingMethod(v2, v1.getSimpleName(), v3);
    }
    
    @Override
    public MemberInfo remapDescriptor(final MemberInfo v-3) {
        boolean b = false;
        String owner = v-3.owner;
        if (owner != null) {
            final String a1 = this.remapper.map(owner);
            if (a1 != null) {
                owner = a1;
                b = true;
            }
        }
        String v0 = v-3.desc;
        if (v0 != null) {
            final String v2 = ObfuscationUtil.mapDescriptor(v-3.desc, this.remapper);
            if (!v2.equals(v-3.desc)) {
                v0 = v2;
                b = true;
            }
        }
        return b ? new MemberInfo(v-3.name, owner, v0, v-3.matchAll) : null;
    }
    
    @Override
    public String remapDescriptor(final String a1) {
        return ObfuscationUtil.mapDescriptor(a1, this.remapper);
    }
    
    @Override
    public MappingField getObfField(final MemberInfo a1) {
        return this.getObfField(a1.asFieldMapping(), true);
    }
    
    @Override
    public MappingField getObfField(final MappingField a1) {
        return this.getObfField(a1, true);
    }
    
    @Override
    public MappingField getObfField(final MappingField a1, final boolean a2) {
        if (!this.initMappings()) {
            return null;
        }
        MappingField v1 = this.mappingProvider.getFieldMapping(a1);
        if (v1 == null) {
            if (a2) {
                return null;
            }
            v1 = a1;
        }
        final String v2 = this.getObfClass(v1.getOwner());
        if (v2 == null || v2.equals(a1.getOwner()) || v2.equals(v1.getOwner())) {
            return (v1 != a1) ? v1 : null;
        }
        return v1.move(v2);
    }
    
    @Override
    public String getObfClass(final String a1) {
        if (!this.initMappings()) {
            return null;
        }
        return this.mappingProvider.getClassMapping(a1);
    }
    
    @Override
    public void writeMappings(final Collection<IMappingConsumer> v2) {
        final IMappingConsumer.MappingSet<MappingField> v3 = new IMappingConsumer.MappingSet<MappingField>();
        final IMappingConsumer.MappingSet<MappingMethod> v4 = new IMappingConsumer.MappingSet<MappingMethod>();
        for (final IMappingConsumer a1 : v2) {
            v3.addAll((Collection<?>)a1.getFieldMappings(this.type));
            v4.addAll((Collection<?>)a1.getMethodMappings(this.type));
        }
        this.mappingWriter.write(this.outFileName, this.type, v3, v4);
    }
    
    final class RemapperProxy implements ObfuscationUtil.IClassRemapper
    {
        final /* synthetic */ ObfuscationEnvironment this$0;
        
        RemapperProxy(final ObfuscationEnvironment a1) {
            this.this$0 = a1;
            super();
        }
        
        @Override
        public String map(final String a1) {
            if (this.this$0.mappingProvider == null) {
                return null;
            }
            return this.this$0.mappingProvider.getClassMapping(a1);
        }
        
        @Override
        public String unmap(final String a1) {
            if (this.this$0.mappingProvider == null) {
                return null;
            }
            return this.this$0.mappingProvider.getClassMapping(a1);
        }
    }
}
