package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.gen.*;
import com.google.common.collect.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.obfuscation.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import java.util.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.apache.logging.log4j.*;

public class MixinTargetContext extends ClassContext implements IMixinContext
{
    private static final Logger logger;
    private final MixinInfo mixin;
    private final ClassNode classNode;
    private final TargetClassContext targetClass;
    private final String sessionId;
    private final ClassInfo targetClassInfo;
    private final BiMap<String, String> innerClasses;
    private final List<MethodNode> shadowMethods;
    private final Map<FieldNode, ClassInfo.Field> shadowFields;
    private final List<MethodNode> mergedMethods;
    private final InjectorGroupInfo.Map injectorGroups;
    private final List<InjectionInfo> injectors;
    private final List<AccessorInfo> accessors;
    private final boolean inheritsFromMixin;
    private final boolean detachedSuper;
    private final SourceMap.File stratum;
    private int minRequiredClassVersion;
    
    MixinTargetContext(final MixinInfo a3, final ClassNode v1, final TargetClassContext v2) {
        super();
        this.innerClasses = (BiMap<String, String>)HashBiMap.create();
        this.shadowMethods = new ArrayList<MethodNode>();
        this.shadowFields = new LinkedHashMap<FieldNode, ClassInfo.Field>();
        this.mergedMethods = new ArrayList<MethodNode>();
        this.injectorGroups = new InjectorGroupInfo.Map();
        this.injectors = new ArrayList<InjectionInfo>();
        this.accessors = new ArrayList<AccessorInfo>();
        this.minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
        this.mixin = a3;
        this.classNode = v1;
        this.targetClass = v2;
        this.targetClassInfo = ClassInfo.forName(this.getTarget().getClassRef());
        this.stratum = v2.getSourceMap().addFile(this.classNode);
        this.inheritsFromMixin = (a3.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy());
        this.detachedSuper = !this.classNode.superName.equals(this.getTarget().getClassNode().superName);
        this.sessionId = v2.getSessionId();
        this.requireVersion(v1.version);
        final InnerClassGenerator v3 = (InnerClassGenerator)v2.getExtensions().getGenerator((Class)InnerClassGenerator.class);
        for (final String a4 : this.mixin.getInnerClasses()) {
            this.innerClasses.put(a4, v3.registerInnerClass(this.mixin, a4, this));
        }
    }
    
    void addShadowMethod(final MethodNode a1) {
        this.shadowMethods.add(a1);
    }
    
    void addShadowField(final FieldNode a1, final ClassInfo.Field a2) {
        this.shadowFields.put(a1, a2);
    }
    
    void addAccessorMethod(final MethodNode a1, final Class<? extends Annotation> a2) {
        this.accessors.add(AccessorInfo.of(this, a1, a2));
    }
    
    void addMixinMethod(final MethodNode a1) {
        Annotations.setVisible(a1, MixinMerged.class, "mixin", this.getClassName());
        this.getTarget().addMixinMethod(a1);
    }
    
    void methodMerged(final MethodNode a1) {
        this.mergedMethods.add(a1);
        this.targetClassInfo.addMethod(a1);
        this.getTarget().methodMerged(a1);
        Annotations.setVisible(a1, MixinMerged.class, "mixin", this.getClassName(), "priority", this.getPriority(), "sessionId", this.sessionId);
    }
    
    @Override
    public String toString() {
        return this.mixin.toString();
    }
    
    public MixinEnvironment getEnvironment() {
        return this.mixin.getParent().getEnvironment();
    }
    
    @Override
    public boolean getOption(final MixinEnvironment.Option a1) {
        return this.getEnvironment().getOption(a1);
    }
    
    public ClassNode getClassNode() {
        return this.classNode;
    }
    
    @Override
    public String getClassName() {
        return this.mixin.getClassName();
    }
    
    @Override
    public String getClassRef() {
        return this.mixin.getClassRef();
    }
    
    public TargetClassContext getTarget() {
        return this.targetClass;
    }
    
    @Override
    public String getTargetClassRef() {
        return this.getTarget().getClassRef();
    }
    
    public ClassNode getTargetClassNode() {
        return this.getTarget().getClassNode();
    }
    
    public ClassInfo getTargetClassInfo() {
        return this.targetClassInfo;
    }
    
    protected ClassInfo getClassInfo() {
        return this.mixin.getClassInfo();
    }
    
    public ClassSignature getSignature() {
        return this.getClassInfo().getSignature();
    }
    
    public SourceMap.File getStratum() {
        return this.stratum;
    }
    
    public int getMinRequiredClassVersion() {
        return this.minRequiredClassVersion;
    }
    
    public int getDefaultRequiredInjections() {
        return this.mixin.getParent().getDefaultRequiredInjections();
    }
    
    public String getDefaultInjectorGroup() {
        return this.mixin.getParent().getDefaultInjectorGroup();
    }
    
    public int getMaxShiftByValue() {
        return this.mixin.getParent().getMaxShiftByValue();
    }
    
    public InjectorGroupInfo.Map getInjectorGroups() {
        return this.injectorGroups;
    }
    
    public boolean requireOverwriteAnnotations() {
        return this.mixin.getParent().requireOverwriteAnnotations();
    }
    
    public ClassInfo findRealType(final ClassInfo a1) {
        if (a1 == this.getClassInfo()) {
            return this.targetClassInfo;
        }
        final ClassInfo v1 = this.targetClassInfo.findCorrespondingType(a1);
        if (v1 == null) {
            throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + a1 + " in hierarchy of " + this.targetClassInfo);
        }
        return v1;
    }
    
    public void transformMethod(final MethodNode v-1) {
        this.validateMethod(v-1);
        this.transformDescriptor(v-1);
        this.transformLVT(v-1);
        this.stratum.applyOffset(v-1);
        AbstractInsnNode v0 = null;
        final Iterator<AbstractInsnNode> v2 = v-1.instructions.iterator();
        while (v2.hasNext()) {
            final AbstractInsnNode a1 = v2.next();
            if (a1 instanceof MethodInsnNode) {
                this.transformMethodRef(v-1, v2, new MemberRef.Method((MethodInsnNode)a1));
            }
            else if (a1 instanceof FieldInsnNode) {
                this.transformFieldRef(v-1, v2, new MemberRef.Field((FieldInsnNode)a1));
                this.checkFinal(v-1, v2, (FieldInsnNode)a1);
            }
            else if (a1 instanceof TypeInsnNode) {
                this.transformTypeNode(v-1, v2, (TypeInsnNode)a1, v0);
            }
            else if (a1 instanceof LdcInsnNode) {
                this.transformConstantNode(v-1, v2, (LdcInsnNode)a1);
            }
            else if (a1 instanceof InvokeDynamicInsnNode) {
                this.transformInvokeDynamicNode(v-1, v2, (InvokeDynamicInsnNode)a1);
            }
            v0 = a1;
        }
    }
    
    private void validateMethod(final MethodNode v2) {
        if (Annotations.getInvisible(v2, SoftOverride.class) != null) {
            final ClassInfo.Method a1 = this.targetClassInfo.findMethodInHierarchy(v2.name, v2.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
            if (a1 == null || !a1.isInjected()) {
                throw new InvalidMixinException(this, "Mixin method " + v2.name + v2.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + this.getTarget().getClassName());
            }
        }
    }
    
    private void transformLVT(final MethodNode v2) {
        if (v2.localVariables == null) {
            return;
        }
        for (final LocalVariableNode a1 : v2.localVariables) {
            if (a1 != null) {
                if (a1.desc == null) {
                    continue;
                }
                a1.desc = this.transformSingleDescriptor(Type.getType(a1.desc));
            }
        }
    }
    
    private void transformMethodRef(final MethodNode a3, final Iterator<AbstractInsnNode> v1, final MemberRef v2) {
        this.transformDescriptor(v2);
        if (v2.getOwner().equals(this.getClassRef())) {
            v2.setOwner(this.getTarget().getClassRef());
            final ClassInfo.Method a4 = this.getClassInfo().findMethod(v2.getName(), v2.getDesc(), 10);
            if (a4 != null && a4.isRenamed() && a4.getOriginalName().equals(v2.getName()) && a4.isSynthetic()) {
                v2.setName(a4.getName());
            }
            this.upgradeMethodRef(a3, v2, a4);
        }
        else if (this.innerClasses.containsKey(v2.getOwner())) {
            v2.setOwner(this.innerClasses.get(v2.getOwner()));
            v2.setDesc(this.transformMethodDescriptor(v2.getDesc()));
        }
        else if (this.detachedSuper || this.inheritsFromMixin) {
            if (v2.getOpcode() == 183) {
                this.updateStaticBinding(a3, v2);
            }
            else if (v2.getOpcode() == 182 && ClassInfo.forName(v2.getOwner()).isMixin()) {
                this.updateDynamicBinding(a3, v2);
            }
        }
    }
    
    private void transformFieldRef(final MethodNode v2, final Iterator<AbstractInsnNode> v3, final MemberRef v4) {
        if ("super$".equals(v4.getName())) {
            if (!(v4 instanceof MemberRef.Field)) {
                throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
            }
            this.processImaginarySuper(v2, ((MemberRef.Field)v4).insn);
            v3.remove();
        }
        this.transformDescriptor(v4);
        if (v4.getOwner().equals(this.getClassRef())) {
            v4.setOwner(this.getTarget().getClassRef());
            final ClassInfo.Field a1 = this.getClassInfo().findField(v4.getName(), v4.getDesc(), 10);
            if (a1 != null && a1.isRenamed() && a1.getOriginalName().equals(v4.getName()) && a1.isStatic()) {
                v4.setName(a1.getName());
            }
        }
        else {
            final ClassInfo a2 = ClassInfo.forName(v4.getOwner());
            if (a2.isMixin()) {
                final ClassInfo a3 = this.targetClassInfo.findCorrespondingType(a2);
                v4.setOwner((a3 != null) ? a3.getName() : this.getTarget().getClassRef());
            }
        }
    }
    
    private void checkFinal(final MethodNode v2, final Iterator<AbstractInsnNode> v3, final FieldInsnNode v4) {
        if (!v4.owner.equals(this.getTarget().getClassRef())) {
            return;
        }
        final int v5 = v4.getOpcode();
        if (v5 == 180 || v5 == 178) {
            return;
        }
        for (final Map.Entry<FieldNode, ClassInfo.Field> a3 : this.shadowFields.entrySet()) {
            final FieldNode a4 = a3.getKey();
            if (a4.desc.equals(v4.desc)) {
                if (!a4.name.equals(v4.name)) {
                    continue;
                }
                final ClassInfo.Field a5 = a3.getValue();
                if (a5.isDecoratedFinal()) {
                    if (a5.isDecoratedMutable()) {
                        if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                            MixinTargetContext.logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[] { a5, this.mixin, v2.name });
                        }
                    }
                    else if ("<init>".equals(v2.name) || "<clinit>".equals(v2.name)) {
                        MixinTargetContext.logger.warn("@Final field {} in {} should be final", new Object[] { a5, this.mixin });
                    }
                    else {
                        MixinTargetContext.logger.error("Write access detected to @Final field {} in {}::{}", new Object[] { a5, this.mixin, v2.name });
                        if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                            throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + a5 + " in " + this.mixin + "::" + v2.name);
                        }
                    }
                }
            }
        }
    }
    
    private void transformTypeNode(final MethodNode a3, final Iterator<AbstractInsnNode> a4, final TypeInsnNode v1, final AbstractInsnNode v2) {
        if (v1.getOpcode() == 192 && v1.desc.equals(this.getTarget().getClassRef()) && v2.getOpcode() == 25 && ((VarInsnNode)v2).var == 0) {
            a4.remove();
            return;
        }
        if (v1.desc.equals(this.getClassRef())) {
            v1.desc = this.getTarget().getClassRef();
        }
        else {
            final String a5 = this.innerClasses.get(v1.desc);
            if (a5 != null) {
                v1.desc = a5;
            }
        }
        this.transformDescriptor(v1);
    }
    
    private void transformConstantNode(final MethodNode a1, final Iterator<AbstractInsnNode> a2, final LdcInsnNode a3) {
        a3.cst = this.transformConstant(a1, a2, a3.cst);
    }
    
    private void transformInvokeDynamicNode(final MethodNode a3, final Iterator<AbstractInsnNode> v1, final InvokeDynamicInsnNode v2) {
        this.requireVersion(51);
        v2.desc = this.transformMethodDescriptor(v2.desc);
        v2.bsm = this.transformHandle(a3, v1, v2.bsm);
        for (int a4 = 0; a4 < v2.bsmArgs.length; ++a4) {
            v2.bsmArgs[a4] = this.transformConstant(a3, v1, v2.bsmArgs[a4]);
        }
    }
    
    private Object transformConstant(final MethodNode v1, final Iterator<AbstractInsnNode> v2, final Object v3) {
        if (v3 instanceof Type) {
            final Type a1 = (Type)v3;
            final String a2 = this.transformDescriptor(a1);
            if (!a1.toString().equals(a2)) {
                return Type.getType(a2);
            }
            return v3;
        }
        else {
            if (v3 instanceof Handle) {
                return this.transformHandle(v1, v2, (Handle)v3);
            }
            return v3;
        }
    }
    
    private Handle transformHandle(final MethodNode a1, final Iterator<AbstractInsnNode> a2, final Handle a3) {
        final MemberRef.Handle v1 = new MemberRef.Handle(a3);
        if (v1.isField()) {
            this.transformFieldRef(a1, a2, v1);
        }
        else {
            this.transformMethodRef(a1, a2, v1);
        }
        return v1.getMethodHandle();
    }
    
    private void processImaginarySuper(final MethodNode v-1, final FieldInsnNode v0) {
        if (v0.getOpcode() != 180) {
            if ("<init>".equals(v-1.name)) {
                throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + v0.name + " must not specify an initialiser");
            }
            throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(v0.getOpcode()) + " opcode in " + v-1.name + v-1.desc);
        }
        else {
            if ((v-1.access & 0x2) != 0x0 || (v-1.access & 0x8) != 0x0) {
                throw new InvalidMixinException(this, "Illegal imaginary super access: method " + v-1.name + v-1.desc + " is private or static");
            }
            if (Annotations.getInvisible(v-1, SoftOverride.class) == null) {
                throw new InvalidMixinException(this, "Illegal imaginary super access: method " + v-1.name + v-1.desc + " is not decorated with @SoftOverride");
            }
            final Iterator<AbstractInsnNode> v = v-1.instructions.iterator(v-1.instructions.indexOf(v0));
            while (v.hasNext()) {
                final AbstractInsnNode a2 = v.next();
                if (a2 instanceof MethodInsnNode) {
                    final MethodInsnNode a3 = (MethodInsnNode)a2;
                    if (a3.owner.equals(this.getClassRef()) && a3.name.equals(v-1.name) && a3.desc.equals(v-1.desc)) {
                        a3.setOpcode(183);
                        this.updateStaticBinding(v-1, new MemberRef.Method(a3));
                        return;
                    }
                    continue;
                }
            }
            throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + v-1.name + v-1.desc);
        }
    }
    
    private void updateStaticBinding(final MethodNode a1, final MemberRef a2) {
        this.updateBinding(a1, a2, ClassInfo.Traversal.SUPER);
    }
    
    private void updateDynamicBinding(final MethodNode a1, final MemberRef a2) {
        this.updateBinding(a1, a2, ClassInfo.Traversal.ALL);
    }
    
    private void updateBinding(final MethodNode a1, final MemberRef a2, final ClassInfo.Traversal a3) {
        if ("<init>".equals(a1.name) || a2.getOwner().equals(this.getTarget().getClassRef()) || this.getTarget().getClassRef().startsWith("<")) {
            return;
        }
        final ClassInfo.Method v1 = this.targetClassInfo.findMethodInHierarchy(a2.getName(), a2.getDesc(), a3.getSearchType(), a3);
        if (v1 != null) {
            if (v1.getOwner().isMixin()) {
                throw new InvalidMixinException(this, "Invalid " + a2 + " in " + this + " resolved " + v1.getOwner() + " but is mixin.");
            }
            a2.setOwner(v1.getImplementor().getName());
        }
        else if (ClassInfo.forName(a2.getOwner()).isMixin()) {
            throw new MixinTransformerError("Error resolving " + a2 + " in " + this);
        }
    }
    
    public void transformDescriptor(final FieldNode a1) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        a1.desc = this.transformSingleDescriptor(a1.desc, false);
    }
    
    public void transformDescriptor(final MethodNode a1) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        a1.desc = this.transformMethodDescriptor(a1.desc);
    }
    
    public void transformDescriptor(final MemberRef a1) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        if (a1.isField()) {
            a1.setDesc(this.transformSingleDescriptor(a1.getDesc(), false));
        }
        else {
            a1.setDesc(this.transformMethodDescriptor(a1.getDesc()));
        }
    }
    
    public void transformDescriptor(final TypeInsnNode a1) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        a1.desc = this.transformSingleDescriptor(a1.desc, true);
    }
    
    private String transformDescriptor(final Type a1) {
        if (a1.getSort() == 11) {
            return this.transformMethodDescriptor(a1.getDescriptor());
        }
        return this.transformSingleDescriptor(a1);
    }
    
    private String transformSingleDescriptor(final Type a1) {
        if (a1.getSort() < 9) {
            return a1.toString();
        }
        return this.transformSingleDescriptor(a1.toString(), false);
    }
    
    private String transformSingleDescriptor(final String a1, boolean a2) {
        String v1 = a1;
        while (v1.startsWith("[") || v1.startsWith("L")) {
            if (v1.startsWith("[")) {
                v1 = v1.substring(1);
            }
            else {
                v1 = v1.substring(1, v1.indexOf(";"));
                a2 = true;
            }
        }
        if (!a2) {
            return a1;
        }
        final String v2 = this.innerClasses.get(v1);
        if (v2 != null) {
            return a1.replace(v1, v2);
        }
        if (this.innerClasses.inverse().containsKey(v1)) {
            return a1;
        }
        final ClassInfo v3 = ClassInfo.forName(v1);
        if (!v3.isMixin()) {
            return a1;
        }
        return a1.replace(v1, this.findRealType(v3).toString());
    }
    
    private String transformMethodDescriptor(final String v2) {
        final StringBuilder v3 = new StringBuilder();
        v3.append('(');
        for (final Type a1 : Type.getArgumentTypes(v2)) {
            v3.append(this.transformSingleDescriptor(a1));
        }
        return v3.append(')').append(this.transformSingleDescriptor(Type.getReturnType(v2))).toString();
    }
    
    @Override
    public Target getTargetMethod(final MethodNode a1) {
        return this.getTarget().getTargetMethod(a1);
    }
    
    MethodNode findMethod(final MethodNode v1, final AnnotationNode v2) {
        final Deque<String> v3 = new LinkedList<String>();
        v3.add(v1.name);
        if (v2 != null) {
            final List<String> a1 = Annotations.getValue(v2, "aliases");
            if (a1 != null) {
                v3.addAll((Collection<?>)a1);
            }
        }
        return this.getTarget().findMethod(v3, v1.desc);
    }
    
    MethodNode findRemappedMethod(final MethodNode a1) {
        final RemapperChain v1 = this.getEnvironment().getRemappers();
        final String v2 = v1.mapMethodName(this.getTarget().getClassRef(), a1.name, a1.desc);
        if (v2.equals(a1.name)) {
            return null;
        }
        final Deque<String> v3 = new LinkedList<String>();
        v3.add(v2);
        return this.getTarget().findAliasedMethod(v3, a1.desc);
    }
    
    FieldNode findField(final FieldNode v1, final AnnotationNode v2) {
        final Deque<String> v3 = new LinkedList<String>();
        v3.add(v1.name);
        if (v2 != null) {
            final List<String> a1 = Annotations.getValue(v2, "aliases");
            if (a1 != null) {
                v3.addAll((Collection<?>)a1);
            }
        }
        return this.getTarget().findAliasedField(v3, v1.desc);
    }
    
    FieldNode findRemappedField(final FieldNode a1) {
        final RemapperChain v1 = this.getEnvironment().getRemappers();
        final String v2 = v1.mapFieldName(this.getTarget().getClassRef(), a1.name, a1.desc);
        if (v2.equals(a1.name)) {
            return null;
        }
        final Deque<String> v3 = new LinkedList<String>();
        v3.add(v2);
        return this.getTarget().findAliasedField(v3, a1.desc);
    }
    
    protected void requireVersion(final int a1) {
        this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, a1);
        if (a1 > MixinEnvironment.getCompatibilityLevel().classVersion()) {
            throw new InvalidMixinException(this, "Unsupported mixin class version " + a1);
        }
    }
    
    @Override
    public Extensions getExtensions() {
        return this.targetClass.getExtensions();
    }
    
    @Override
    public IMixinInfo getMixin() {
        return this.mixin;
    }
    
    MixinInfo getInfo() {
        return this.mixin;
    }
    
    @Override
    public int getPriority() {
        return this.mixin.getPriority();
    }
    
    public Set<String> getInterfaces() {
        return this.mixin.getInterfaces();
    }
    
    public Collection<MethodNode> getShadowMethods() {
        return this.shadowMethods;
    }
    
    public List<MethodNode> getMethods() {
        return this.classNode.methods;
    }
    
    public Set<Map.Entry<FieldNode, ClassInfo.Field>> getShadowFields() {
        return this.shadowFields.entrySet();
    }
    
    public List<FieldNode> getFields() {
        return this.classNode.fields;
    }
    
    public Level getLoggingLevel() {
        return this.mixin.getLoggingLevel();
    }
    
    public boolean shouldSetSourceFile() {
        return this.mixin.getParent().shouldSetSourceFile();
    }
    
    public String getSourceFile() {
        return this.classNode.sourceFile;
    }
    
    @Override
    public IReferenceMapper getReferenceMapper() {
        return this.mixin.getParent().getReferenceMapper();
    }
    
    public void preApply(final String a1, final ClassNode a2) {
        this.mixin.preApply(a1, a2);
    }
    
    public void postApply(final String v2, final ClassNode v3) {
        try {
            this.injectorGroups.validateAll();
        }
        catch (InjectionValidationException a2) {
            final InjectorGroupInfo a1 = a2.getGroup();
            throw new InjectionError(String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", a1, this.mixin, a2.getMessage()));
        }
        this.mixin.postApply(v2, v3);
    }
    
    public String getUniqueName(final MethodNode a1, final boolean a2) {
        return this.getTarget().getUniqueName(a1, a2);
    }
    
    public String getUniqueName(final FieldNode a1) {
        return this.getTarget().getUniqueName(a1);
    }
    
    public void prepareInjections() {
        this.injectors.clear();
        for (final MethodNode v0 : this.mergedMethods) {
            final InjectionInfo v2 = InjectionInfo.parse(this, v0);
            if (v2 == null) {
                continue;
            }
            if (v2.isValid()) {
                v2.prepare();
                this.injectors.add(v2);
            }
            v0.visibleAnnotations.remove(v2.getAnnotation());
        }
    }
    
    public void applyInjections() {
        for (final InjectionInfo v1 : this.injectors) {
            v1.inject();
        }
        for (final InjectionInfo v1 : this.injectors) {
            v1.postInject();
        }
        this.injectors.clear();
    }
    
    public List<MethodNode> generateAccessors() {
        for (final AccessorInfo v1 : this.accessors) {
            v1.locate();
        }
        final List<MethodNode> v2 = new ArrayList<MethodNode>();
        for (final AccessorInfo v3 : this.accessors) {
            final MethodNode v4 = v3.generate();
            this.getTarget().addMixinMethod(v4);
            v2.add(v4);
        }
        return v2;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
