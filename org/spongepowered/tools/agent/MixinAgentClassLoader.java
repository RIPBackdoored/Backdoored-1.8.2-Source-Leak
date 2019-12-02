package org.spongepowered.tools.agent;

import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.*;
import org.apache.logging.log4j.*;

class MixinAgentClassLoader extends ClassLoader
{
    private static final Logger logger;
    private Map<Class<?>, byte[]> mixins;
    private Map<String, byte[]> targets;
    
    MixinAgentClassLoader() {
        super();
        this.mixins = new HashMap<Class<?>, byte[]>();
        this.targets = new HashMap<String, byte[]>();
    }
    
    void addMixinClass(final String v-1) {
        MixinAgentClassLoader.logger.debug("Mixin class {} added to class loader", new Object[] { v-1 });
        try {
            final byte[] a1 = this.materialise(v-1);
            final Class<?> v1 = this.defineClass(v-1, a1, 0, a1.length);
            v1.newInstance();
            this.mixins.put(v1, a1);
        }
        catch (Throwable v2) {
            MixinAgentClassLoader.logger.catching(v2);
        }
    }
    
    void addTargetClass(final String a1, final byte[] a2) {
        this.targets.put(a1, a2);
    }
    
    byte[] getFakeMixinBytecode(final Class<?> a1) {
        return this.mixins.get(a1);
    }
    
    byte[] getOriginalTargetBytecode(final String a1) {
        return this.targets.get(a1);
    }
    
    private byte[] materialise(final String a1) {
        final ClassWriter v1 = new ClassWriter(3);
        v1.visit(MixinEnvironment.getCompatibilityLevel().classVersion(), 1, a1.replace('.', '/'), null, Type.getInternalName(Object.class), null);
        final MethodVisitor v2 = v1.visitMethod(1, "<init>", "()V", null, null);
        v2.visitCode();
        v2.visitVarInsn(25, 0);
        v2.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
        v2.visitInsn(177);
        v2.visitMaxs(1, 1);
        v2.visitEnd();
        v1.visitEnd();
        return v1.toByteArray();
    }
    
    static {
        logger = LogManager.getLogger("mixin.agent");
    }
}
