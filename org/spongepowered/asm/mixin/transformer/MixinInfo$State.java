package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;

class State
{
    private byte[] mixinBytes;
    private final ClassInfo classInfo;
    private boolean detachedSuper;
    private boolean unique;
    protected final Set<String> interfaces;
    protected final List<InterfaceInfo> softImplements;
    protected final Set<String> syntheticInnerClasses;
    protected final Set<String> innerClasses;
    protected MixinClassNode classNode;
    final /* synthetic */ MixinInfo this$0;
    
    State(final MixinInfo a1, final byte[] a2) {
        this(a1, a2, null);
    }
    
    State(final MixinInfo a1, final byte[] a2, final ClassInfo a3) {
        this.this$0 = a1;
        super();
        this.interfaces = new HashSet<String>();
        this.softImplements = new ArrayList<InterfaceInfo>();
        this.syntheticInnerClasses = new HashSet<String>();
        this.innerClasses = new HashSet<String>();
        this.mixinBytes = a2;
        this.connect();
        this.classInfo = ((a3 != null) ? a3 : ClassInfo.fromClassNode(this.getClassNode()));
    }
    
    private void connect() {
        this.classNode = this.createClassNode(0);
    }
    
    private void complete() {
        this.classNode = null;
    }
    
    ClassInfo getClassInfo() {
        return this.classInfo;
    }
    
    byte[] getClassBytes() {
        return this.mixinBytes;
    }
    
    MixinClassNode getClassNode() {
        return this.classNode;
    }
    
    boolean isDetachedSuper() {
        return this.detachedSuper;
    }
    
    boolean isUnique() {
        return this.unique;
    }
    
    List<? extends InterfaceInfo> getSoftImplements() {
        return this.softImplements;
    }
    
    Set<String> getSyntheticInnerClasses() {
        return this.syntheticInnerClasses;
    }
    
    Set<String> getInnerClasses() {
        return this.innerClasses;
    }
    
    Set<String> getInterfaces() {
        return this.interfaces;
    }
    
    MixinClassNode createClassNode(final int a1) {
        final MixinClassNode v1 = this.this$0.new MixinClassNode(this.this$0);
        final ClassReader v2 = new ClassReader(this.mixinBytes);
        v2.accept(v1, a1);
        return v1;
    }
    
    void validate(final SubType v1, final List<ClassInfo> v2) {
        final MixinPreProcessorStandard v3 = v1.createPreProcessor(this.getClassNode()).prepare();
        for (final ClassInfo a1 : v2) {
            v3.conform(a1);
        }
        v1.validate(this, v2);
        this.detachedSuper = v1.isDetachedSuper();
        this.unique = (Annotations.getVisible(this.getClassNode(), Unique.class) != null);
        this.validateInner();
        this.validateClassVersion();
        this.validateRemappables(v2);
        this.readImplementations(v1);
        this.readInnerClasses();
        this.validateChanges(v1, v2);
        this.complete();
    }
    
    private void validateInner() {
        if (!this.classInfo.isProbablyStatic()) {
            throw new InvalidMixinException(this.this$0, "Inner class mixin must be declared static");
        }
    }
    
    private void validateClassVersion() {
        if (this.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
            String format = ".";
            for (final MixinEnvironment.CompatibilityLevel v1 : MixinEnvironment.CompatibilityLevel.values()) {
                if (v1.classVersion() >= this.classNode.version) {
                    format = String.format(". Mixin requires compatibility level %s or above.", v1.name());
                }
            }
            throw new InvalidMixinException(this.this$0, "Unsupported mixin class version " + this.classNode.version + format);
        }
    }
    
    private void validateRemappables(final List<ClassInfo> v-2) {
        if (v-2.size() > 1) {
            for (final FieldNode a1 : this.classNode.fields) {
                this.validateRemappable(Shadow.class, a1.name, Annotations.getVisible(a1, Shadow.class));
            }
            for (final MethodNode v0 : this.classNode.methods) {
                this.validateRemappable(Shadow.class, v0.name, Annotations.getVisible(v0, Shadow.class));
                final AnnotationNode v2 = Annotations.getVisible(v0, Overwrite.class);
                if (v2 != null && ((v0.access & 0x8) == 0x0 || (v0.access & 0x1) == 0x0)) {
                    throw new InvalidMixinException(this.this$0, "Found @Overwrite annotation on " + v0.name + " in " + this.this$0);
                }
            }
        }
    }
    
    private void validateRemappable(final Class<Shadow> a1, final String a2, final AnnotationNode a3) {
        if (a3 != null && Annotations.getValue(a3, "remap", Boolean.TRUE)) {
            throw new InvalidMixinException(this.this$0, "Found a remappable @" + a1.getSimpleName() + " annotation on " + a2 + " in " + this);
        }
    }
    
    void readImplementations(final SubType v-3) {
        this.interfaces.addAll(this.classNode.interfaces);
        this.interfaces.addAll(v-3.getInterfaces());
        final AnnotationNode invisible = Annotations.getInvisible(this.classNode, Implements.class);
        if (invisible == null) {
            return;
        }
        final List<AnnotationNode> list = Annotations.getValue(invisible);
        if (list == null) {
            return;
        }
        for (final AnnotationNode v1 : list) {
            final InterfaceInfo a1 = InterfaceInfo.fromAnnotation(this.this$0, v1);
            this.softImplements.add(a1);
            this.interfaces.add(a1.getInternalName());
            if (!(this instanceof Reloaded)) {
                this.classInfo.addInterface(a1.getInternalName());
            }
        }
    }
    
    void readInnerClasses() {
        for (final InnerClassNode v0 : this.classNode.innerClasses) {
            final ClassInfo v2 = ClassInfo.forName(v0.name);
            if ((v0.outerName != null && v0.outerName.equals(this.classInfo.getName())) || v0.name.startsWith(this.classNode.name + "$")) {
                if (v2.isProbablyStatic() && v2.isSynthetic()) {
                    this.syntheticInnerClasses.add(v0.name);
                }
                else {
                    this.innerClasses.add(v0.name);
                }
            }
        }
    }
    
    protected void validateChanges(final SubType a1, final List<ClassInfo> a2) {
        a1.createPreProcessor(this.classNode).prepare();
    }
}
