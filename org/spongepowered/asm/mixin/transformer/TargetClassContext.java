package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import java.lang.annotation.*;
import org.spongepowered.asm.util.*;
import java.io.*;
import org.spongepowered.asm.lib.tree.*;
import org.apache.logging.log4j.*;

class TargetClassContext extends ClassContext implements ITargetClassContext
{
    private static final Logger logger;
    private final MixinEnvironment env;
    private final Extensions extensions;
    private final String sessionId;
    private final String className;
    private final ClassNode classNode;
    private final ClassInfo classInfo;
    private final SourceMap sourceMap;
    private final ClassSignature signature;
    private final SortedSet<MixinInfo> mixins;
    private final Map<String, Target> targetMethods;
    private final Set<MethodNode> mixinMethods;
    private int nextUniqueMethodIndex;
    private int nextUniqueFieldIndex;
    private boolean applied;
    private boolean forceExport;
    
    TargetClassContext(final MixinEnvironment a1, final Extensions a2, final String a3, final String a4, final ClassNode a5, final SortedSet<MixinInfo> a6) {
        super();
        this.targetMethods = new HashMap<String, Target>();
        this.mixinMethods = new HashSet<MethodNode>();
        this.env = a1;
        this.extensions = a2;
        this.sessionId = a3;
        this.className = a4;
        this.classNode = a5;
        this.classInfo = ClassInfo.fromClassNode(a5);
        this.signature = this.classInfo.getSignature();
        this.mixins = a6;
        (this.sourceMap = new SourceMap(a5.sourceFile)).addFile(this.classNode);
    }
    
    @Override
    public String toString() {
        return this.className;
    }
    
    boolean isApplied() {
        return this.applied;
    }
    
    boolean isExportForced() {
        return this.forceExport;
    }
    
    Extensions getExtensions() {
        return this.extensions;
    }
    
    String getSessionId() {
        return this.sessionId;
    }
    
    @Override
    String getClassRef() {
        return this.classNode.name;
    }
    
    String getClassName() {
        return this.className;
    }
    
    @Override
    public ClassNode getClassNode() {
        return this.classNode;
    }
    
    List<MethodNode> getMethods() {
        return this.classNode.methods;
    }
    
    List<FieldNode> getFields() {
        return this.classNode.fields;
    }
    
    @Override
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }
    
    SortedSet<MixinInfo> getMixins() {
        return this.mixins;
    }
    
    SourceMap getSourceMap() {
        return this.sourceMap;
    }
    
    void mergeSignature(final ClassSignature a1) {
        this.signature.merge(a1);
    }
    
    void addMixinMethod(final MethodNode a1) {
        this.mixinMethods.add(a1);
    }
    
    void methodMerged(final MethodNode a1) {
        if (!this.mixinMethods.remove(a1)) {
            TargetClassContext.logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[] { a1.name, a1.desc, this });
        }
    }
    
    MethodNode findMethod(final Deque<String> a1, final String a2) {
        return this.findAliasedMethod(a1, a2, true);
    }
    
    MethodNode findAliasedMethod(final Deque<String> a1, final String a2) {
        return this.findAliasedMethod(a1, a2, false);
    }
    
    private MethodNode findAliasedMethod(final Deque<String> v1, final String v2, final boolean v3) {
        final String v4 = v1.poll();
        if (v4 == null) {
            return null;
        }
        for (final MethodNode a1 : this.classNode.methods) {
            if (a1.name.equals(v4) && a1.desc.equals(v2)) {
                return a1;
            }
        }
        if (v3) {
            for (final MethodNode a2 : this.mixinMethods) {
                if (a2.name.equals(v4) && a2.desc.equals(v2)) {
                    return a2;
                }
            }
        }
        return this.findAliasedMethod(v1, v2);
    }
    
    FieldNode findAliasedField(final Deque<String> v1, final String v2) {
        final String v3 = v1.poll();
        if (v3 == null) {
            return null;
        }
        for (final FieldNode a1 : this.classNode.fields) {
            if (a1.name.equals(v3) && a1.desc.equals(v2)) {
                return a1;
            }
        }
        return this.findAliasedField(v1, v2);
    }
    
    Target getTargetMethod(final MethodNode a1) {
        if (!this.classNode.methods.contains(a1)) {
            throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
        }
        final String v1 = a1.name + a1.desc;
        Target v2 = this.targetMethods.get(v1);
        if (v2 == null) {
            v2 = new Target(this.classNode, a1);
            this.targetMethods.put(v1, v2);
        }
        return v2;
    }
    
    String getUniqueName(final MethodNode a1, final boolean a2) {
        final String v1 = Integer.toHexString(this.nextUniqueMethodIndex++);
        final String v2 = a2 ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s";
        return String.format(v2, this.sessionId.substring(30), a1.name, v1);
    }
    
    String getUniqueName(final FieldNode a1) {
        final String v1 = Integer.toHexString(this.nextUniqueFieldIndex++);
        return String.format("fd%s$%s$%s", this.sessionId.substring(30), a1.name, v1);
    }
    
    void applyMixins() {
        if (this.applied) {
            throw new IllegalStateException("Mixins already applied to target class " + this.className);
        }
        this.applied = true;
        final MixinApplicatorStandard v1 = this.createApplicator();
        v1.apply(this.mixins);
        this.applySignature();
        this.upgradeMethods();
        this.checkMerges();
    }
    
    private MixinApplicatorStandard createApplicator() {
        if (this.classInfo.isInterface()) {
            return new MixinApplicatorInterface(this);
        }
        return new MixinApplicatorStandard(this);
    }
    
    private void applySignature() {
        this.getClassNode().signature = this.signature.toString();
    }
    
    private void checkMerges() {
        for (final MethodNode v1 : this.mixinMethods) {
            if (!v1.name.startsWith("<")) {
                TargetClassContext.logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[] { v1.name, v1.desc, this });
            }
        }
    }
    
    void processDebugTasks() {
        if (!this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            return;
        }
        final AnnotationNode visible = Annotations.getVisible(this.classNode, Debug.class);
        if (visible != null) {
            this.forceExport = Boolean.TRUE.equals(Annotations.getValue(visible, "export"));
            if (Boolean.TRUE.equals(Annotations.getValue(visible, "print"))) {
                Bytecode.textify(this.classNode, System.err);
            }
        }
        for (final MethodNode v0 : this.classNode.methods) {
            final AnnotationNode v2 = Annotations.getVisible(v0, Debug.class);
            if (v2 != null && Boolean.TRUE.equals(Annotations.getValue(v2, "print"))) {
                Bytecode.textify(v0, System.err);
            }
        }
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
