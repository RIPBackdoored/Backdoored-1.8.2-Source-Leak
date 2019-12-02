package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.asm.mixin.refmap.*;
import java.io.*;
import javax.lang.model.element.*;
import javax.tools.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public class ReferenceManager implements IReferenceManager
{
    private final IMixinAnnotationProcessor ap;
    private final String outRefMapFileName;
    private final List<ObfuscationEnvironment> environments;
    private final ReferenceMapper refMapper;
    private boolean allowConflicts;
    
    public ReferenceManager(final IMixinAnnotationProcessor a1, final List<ObfuscationEnvironment> a2) {
        super();
        this.refMapper = new ReferenceMapper();
        this.ap = a1;
        this.environments = a2;
        this.outRefMapFileName = this.ap.getOption("outRefMapFile");
    }
    
    @Override
    public boolean getAllowConflicts() {
        return this.allowConflicts;
    }
    
    @Override
    public void setAllowConflicts(final boolean a1) {
        this.allowConflicts = a1;
    }
    
    @Override
    public void write() {
        if (this.outRefMapFileName == null) {
            return;
        }
        PrintWriter v0 = null;
        try {
            v0 = this.newWriter(this.outRefMapFileName, "refmap");
            this.refMapper.write(v0);
        }
        catch (IOException v2) {
            v2.printStackTrace();
        }
        finally {
            if (v0 != null) {
                try {
                    v0.close();
                }
                catch (Exception ex) {}
            }
        }
    }
    
    private PrintWriter newWriter(final String v1, final String v2) throws IOException {
        if (v1.matches("^.*[\\\\/:].*$")) {
            final File a1 = new File(v1);
            a1.getParentFile().mkdirs();
            this.ap.printMessage(Diagnostic.Kind.NOTE, "Writing " + v2 + " to " + a1.getAbsolutePath());
            return new PrintWriter(a1);
        }
        final FileObject v3 = this.ap.getProcessingEnvironment().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", v1, new Element[0]);
        this.ap.printMessage(Diagnostic.Kind.NOTE, "Writing " + v2 + " to " + new File(v3.toUri()).getAbsolutePath());
        return new PrintWriter(v3.openWriter());
    }
    
    @Override
    public ReferenceMapper getMapper() {
        return this.refMapper;
    }
    
    @Override
    public void addMethodMapping(final String v2, final String v3, final ObfuscationData<MappingMethod> v4) {
        for (final ObfuscationEnvironment a3 : this.environments) {
            final MappingMethod a4 = v4.get(a3.getType());
            if (a4 != null) {
                final MemberInfo a5 = new MemberInfo(a4);
                this.addMapping(a3.getType(), v2, v3, a5.toString());
            }
        }
    }
    
    @Override
    public void addMethodMapping(final String v1, final String v2, final MemberInfo v3, final ObfuscationData<MappingMethod> v4) {
        for (final ObfuscationEnvironment a3 : this.environments) {
            final MappingMethod a4 = v4.get(a3.getType());
            if (a4 != null) {
                final MemberInfo a5 = v3.remapUsing(a4, true);
                this.addMapping(a3.getType(), v1, v2, a5.toString());
            }
        }
    }
    
    @Override
    public void addFieldMapping(final String v1, final String v2, final MemberInfo v3, final ObfuscationData<MappingField> v4) {
        for (final ObfuscationEnvironment a3 : this.environments) {
            final MappingField a4 = v4.get(a3.getType());
            if (a4 != null) {
                final MemberInfo a5 = MemberInfo.fromMapping(a4.transform(a3.remapDescriptor(v3.desc)));
                this.addMapping(a3.getType(), v1, v2, a5.toString());
            }
        }
    }
    
    @Override
    public void addClassMapping(final String v1, final String v2, final ObfuscationData<String> v3) {
        for (final ObfuscationEnvironment a2 : this.environments) {
            final String a3 = v3.get(a2.getType());
            if (a3 != null) {
                this.addMapping(a2.getType(), v1, v2, a3);
            }
        }
    }
    
    protected void addMapping(final ObfuscationType a1, final String a2, final String a3, final String a4) {
        final String v1 = this.refMapper.addMapping(a1.getKey(), a2, a3, a4);
        if (a1.isDefault()) {
            this.refMapper.addMapping(null, a2, a3, a4);
        }
        if (!this.allowConflicts && v1 != null && !v1.equals(a4)) {
            throw new ReferenceConflictException(v1, a4);
        }
    }
    
    public static class ReferenceConflictException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        private final String oldReference;
        private final String newReference;
        
        public ReferenceConflictException(final String a1, final String a2) {
            super();
            this.oldReference = a1;
            this.newReference = a2;
        }
        
        public String getOld() {
            return this.oldReference;
        }
        
        public String getNew() {
            return this.newReference;
        }
    }
}
