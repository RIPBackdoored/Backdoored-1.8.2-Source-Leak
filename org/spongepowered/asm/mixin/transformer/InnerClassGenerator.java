package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.service.*;
import java.io.*;
import org.spongepowered.asm.lib.commons.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.extensibility.*;

final class InnerClassGenerator implements IClassGenerator
{
    private static final Logger logger;
    private final Map<String, String> innerClassNames;
    private final Map<String, InnerClassInfo> innerClasses;
    
    InnerClassGenerator() {
        super();
        this.innerClassNames = new HashMap<String, String>();
        this.innerClasses = new HashMap<String, InnerClassInfo>();
    }
    
    public String registerInnerClass(final MixinInfo a1, final String a2, final MixinTargetContext a3) {
        final String v1 = String.format("%s%s", a2, a3);
        String v2 = this.innerClassNames.get(v1);
        if (v2 == null) {
            v2 = getUniqueReference(a2, a3);
            this.innerClassNames.put(v1, v2);
            this.innerClasses.put(v2, new InnerClassInfo(v2, a2, a1, a3));
            InnerClassGenerator.logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[] { a2, a1.getClassRef(), a3.getTargetClassRef(), v2 });
        }
        return v2;
    }
    
    @Override
    public byte[] generate(final String a1) {
        final String v1 = a1.replace('.', '/');
        final InnerClassInfo v2 = this.innerClasses.get(v1);
        if (v2 != null) {
            return this.generate(v2);
        }
        return null;
    }
    
    private byte[] generate(final InnerClassInfo v-1) {
        try {
            InnerClassGenerator.logger.debug("Generating mapped inner class {} (originally {})", new Object[] { v-1.getName(), v-1.getOriginalName() });
            final ClassReader a1 = new ClassReader(v-1.getClassBytes());
            final ClassWriter v1 = new MixinClassWriter(a1, 0);
            a1.accept(new InnerClassAdapter(v1, v-1), 8);
            return v1.toByteArray();
        }
        catch (InvalidMixinException v2) {
            throw v2;
        }
        catch (Exception v3) {
            InnerClassGenerator.logger.catching((Throwable)v3);
            return null;
        }
    }
    
    private static String getUniqueReference(final String a1, final MixinTargetContext a2) {
        String v1 = a1.substring(a1.lastIndexOf(36) + 1);
        if (v1.matches("^[0-9]+$")) {
            v1 = "Anonymous";
        }
        return String.format("%s$%s$%s", a2.getTargetClassRef(), v1, UUID.randomUUID().toString().replace("-", ""));
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    static class InnerClassInfo extends Remapper
    {
        private final String name;
        private final String originalName;
        private final MixinInfo owner;
        private final MixinTargetContext target;
        private final String ownerName;
        private final String targetName;
        
        InnerClassInfo(final String a1, final String a2, final MixinInfo a3, final MixinTargetContext a4) {
            super();
            this.name = a1;
            this.originalName = a2;
            this.owner = a3;
            this.ownerName = a3.getClassRef();
            this.target = a4;
            this.targetName = a4.getTargetClassRef();
        }
        
        String getName() {
            return this.name;
        }
        
        String getOriginalName() {
            return this.originalName;
        }
        
        MixinInfo getOwner() {
            return this.owner;
        }
        
        MixinTargetContext getTarget() {
            return this.target;
        }
        
        String getOwnerName() {
            return this.ownerName;
        }
        
        String getTargetName() {
            return this.targetName;
        }
        
        byte[] getClassBytes() throws ClassNotFoundException, IOException {
            return MixinService.getService().getBytecodeProvider().getClassBytes(this.originalName, true);
        }
        
        @Override
        public String mapMethodName(final String a3, final String v1, final String v2) {
            if (this.ownerName.equalsIgnoreCase(a3)) {
                final ClassInfo.Method a4 = this.owner.getClassInfo().findMethod(v1, v2, 10);
                if (a4 != null) {
                    return a4.getName();
                }
            }
            return super.mapMethodName(a3, v1, v2);
        }
        
        @Override
        public String map(final String a1) {
            if (this.originalName.equals(a1)) {
                return this.name;
            }
            if (this.ownerName.equals(a1)) {
                return this.targetName;
            }
            return a1;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    static class InnerClassAdapter extends ClassRemapper
    {
        private final InnerClassInfo info;
        
        public InnerClassAdapter(final ClassVisitor a1, final InnerClassInfo a2) {
            super(327680, a1, a2);
            this.info = a2;
        }
        
        @Override
        public void visitSource(final String a1, final String a2) {
            super.visitSource(a1, a2);
            final AnnotationVisitor v1 = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
            v1.visit("mixin", this.info.getOwner().toString());
            v1.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf(47) + 1));
            v1.visitEnd();
        }
        
        @Override
        public void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
            if (a1.startsWith(this.info.getOriginalName() + "$")) {
                throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + a1 + " in " + this.info.getOriginalName());
            }
            super.visitInnerClass(a1, a2, a3, a4);
        }
    }
}
