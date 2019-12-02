package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

class MixinPostProcessor extends TreeTransformer implements MixinConfig.IListener
{
    private final Set<String> syntheticInnerClasses;
    private final Map<String, MixinInfo> accessorMixins;
    private final Set<String> loadable;
    
    MixinPostProcessor() {
        super();
        this.syntheticInnerClasses = new HashSet<String>();
        this.accessorMixins = new HashMap<String, MixinInfo>();
        this.loadable = new HashSet<String>();
    }
    
    @Override
    public void onInit(final MixinInfo v2) {
        for (final String a1 : v2.getSyntheticInnerClasses()) {
            this.registerSyntheticInner(a1.replace('/', '.'));
        }
    }
    
    @Override
    public void onPrepare(final MixinInfo a1) {
        final String v1 = a1.getClassName();
        if (a1.isLoadable()) {
            this.registerLoadable(v1);
        }
        if (a1.isAccessor()) {
            this.registerAccessor(a1);
        }
    }
    
    void registerSyntheticInner(final String a1) {
        this.syntheticInnerClasses.add(a1);
    }
    
    void registerLoadable(final String a1) {
        this.loadable.add(a1);
    }
    
    void registerAccessor(final MixinInfo a1) {
        this.registerLoadable(a1.getClassName());
        this.accessorMixins.put(a1.getClassName(), a1);
    }
    
    boolean canTransform(final String a1) {
        return this.syntheticInnerClasses.contains(a1) || this.loadable.contains(a1);
    }
    
    @Override
    public String getName() {
        return this.getClass().getName();
    }
    
    @Override
    public boolean isDelegationExcluded() {
        return true;
    }
    
    @Override
    public byte[] transformClassBytes(final String a3, final String v1, final byte[] v2) {
        if (this.syntheticInnerClasses.contains(v1)) {
            return this.processSyntheticInner(v2);
        }
        if (this.accessorMixins.containsKey(v1)) {
            final MixinInfo a4 = this.accessorMixins.get(v1);
            return this.processAccessor(v2, a4);
        }
        return v2;
    }
    
    private byte[] processSyntheticInner(final byte[] a1) {
        final ClassReader v1 = new ClassReader(a1);
        final ClassWriter v2 = new MixinClassWriter(v1, 0);
        final ClassVisitor v3 = new ClassVisitor(327680, v2) {
            final /* synthetic */ MixinPostProcessor this$0;
            
            MixinPostProcessor$1(final int a2, final ClassVisitor a3) {
                this.this$0 = a1;
                super(a2, a3);
            }
            
            @Override
            public void visit(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
                super.visit(a1, a2 | 0x1, a3, a4, a5, a6);
            }
            
            @Override
            public FieldVisitor visitField(int a1, final String a2, final String a3, final String a4, final Object a5) {
                if ((a1 & 0x6) == 0x0) {
                    a1 |= 0x1;
                }
                return super.visitField(a1, a2, a3, a4, a5);
            }
            
            @Override
            public MethodVisitor visitMethod(int a1, final String a2, final String a3, final String a4, final String[] a5) {
                if ((a1 & 0x6) == 0x0) {
                    a1 |= 0x1;
                }
                return super.visitMethod(a1, a2, a3, a4, a5);
            }
        };
        v1.accept(v3, 8);
        return v2.toByteArray();
    }
    
    private byte[] processAccessor(final byte[] v-6, final MixinInfo v-5) {
        if (!MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8)) {
            return v-6;
        }
        boolean b = false;
        final MixinInfo.MixinClassNode classNode = v-5.getClassNode(0);
        final ClassInfo classInfo = v-5.getTargets().get(0);
        for (final MixinInfo.MixinMethodNode a2 : classNode.mixinMethods) {
            if (!Bytecode.hasFlag(a2, 8)) {
                continue;
            }
            final AnnotationNode v1 = a2.getVisibleAnnotation(Accessor.class);
            final AnnotationNode v2 = a2.getVisibleAnnotation(Invoker.class);
            if (v1 == null && v2 == null) {
                continue;
            }
            final ClassInfo.Method a3 = getAccessorMethod(v-5, a2, classInfo);
            createProxy(a2, classInfo, a3);
            b = true;
        }
        if (b) {
            return this.writeClass(classNode);
        }
        return v-6;
    }
    
    private static ClassInfo.Method getAccessorMethod(final MixinInfo a1, final MethodNode a2, final ClassInfo a3) throws MixinTransformerError {
        final ClassInfo.Method v1 = a1.getClassInfo().findMethod(a2, 10);
        if (!v1.isRenamed()) {
            throw new MixinTransformerError("Unexpected state: " + a1 + " loaded before " + a3 + " was conformed");
        }
        return v1;
    }
    
    private static void createProxy(final MethodNode a1, final ClassInfo a2, final ClassInfo.Method a3) {
        a1.instructions.clear();
        final Type[] v1 = Type.getArgumentTypes(a1.desc);
        final Type v2 = Type.getReturnType(a1.desc);
        Bytecode.loadArgs(v1, a1.instructions, 0);
        a1.instructions.add(new MethodInsnNode(184, a2.getName(), a3.getName(), a1.desc, false));
        a1.instructions.add(new InsnNode(v2.getOpcode(172)));
        a1.maxStack = Bytecode.getFirstNonArgLocalIndex(v1, false);
        a1.maxLocals = 0;
    }
}
