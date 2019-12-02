package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.perf.*;
import java.util.*;
import com.google.common.collect.*;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.lib.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.*;

public final class ClassInfo
{
    public static final int INCLUDE_PRIVATE = 2;
    public static final int INCLUDE_STATIC = 8;
    public static final int INCLUDE_ALL = 10;
    private static final Logger logger;
    private static final Profiler profiler;
    private static final String JAVA_LANG_OBJECT = "java/lang/Object";
    private static final Map<String, ClassInfo> cache;
    private static final ClassInfo OBJECT;
    private final String name;
    private final String superName;
    private final String outerName;
    private final boolean isProbablyStatic;
    private final Set<String> interfaces;
    private final Set<Method> methods;
    private final Set<Field> fields;
    private final Set<MixinInfo> mixins;
    private final Map<ClassInfo, ClassInfo> correspondingTypes;
    private final MixinInfo mixin;
    private final MethodMapper methodMapper;
    private final boolean isMixin;
    private final boolean isInterface;
    private final int access;
    private ClassInfo superClass;
    private ClassInfo outerClass;
    private ClassSignature signature;
    
    private ClassInfo() {
        super();
        this.mixins = new HashSet<MixinInfo>();
        this.correspondingTypes = new HashMap<ClassInfo, ClassInfo>();
        this.name = "java/lang/Object";
        this.superName = null;
        this.outerName = null;
        this.isProbablyStatic = true;
        this.methods = (Set<Method>)ImmutableSet.of((Object)new Method("getClass", "()Ljava/lang/Class;"), (Object)new Method("hashCode", "()I"), (Object)new Method("equals", "(Ljava/lang/Object;)Z"), (Object)new Method("clone", "()Ljava/lang/Object;"), (Object)new Method("toString", "()Ljava/lang/String;"), (Object)new Method("notify", "()V"), (Object[])new Method[] { new Method("notifyAll", "()V"), new Method("wait", "(J)V"), new Method("wait", "(JI)V"), new Method("wait", "()V"), new Method("finalize", "()V") });
        this.fields = Collections.emptySet();
        this.isInterface = false;
        this.interfaces = Collections.emptySet();
        this.access = 1;
        this.isMixin = false;
        this.mixin = null;
        this.methodMapper = null;
    }
    
    private ClassInfo(final ClassNode v-4) {
        super();
        this.mixins = new HashSet<MixinInfo>();
        this.correspondingTypes = new HashMap<ClassInfo, ClassInfo>();
        final Profiler.Section begin = ClassInfo.profiler.begin(1, "class.meta");
        try {
            this.name = v-4.name;
            this.superName = ((v-4.superName != null) ? v-4.superName : "java/lang/Object");
            this.methods = new HashSet<Method>();
            this.fields = new HashSet<Field>();
            this.isInterface = ((v-4.access & 0x200) != 0x0);
            this.interfaces = new HashSet<String>();
            this.access = v-4.access;
            this.isMixin = (v-4 instanceof MixinInfo.MixinClassNode);
            this.mixin = (this.isMixin ? ((MixinInfo.MixinClassNode)v-4).getMixin() : null);
            this.interfaces.addAll(v-4.interfaces);
            for (final MethodNode a1 : v-4.methods) {
                this.addMethod(a1, this.isMixin);
            }
            boolean isProbablyStatic = true;
            String outerName = v-4.outerClass;
            for (final FieldNode v1 : v-4.fields) {
                if ((v1.access & 0x1000) != 0x0 && v1.name.startsWith("this$")) {
                    isProbablyStatic = false;
                    if (outerName == null) {
                        outerName = v1.desc;
                        if (outerName != null && outerName.startsWith("L")) {
                            outerName = outerName.substring(1, outerName.length() - 1);
                        }
                    }
                }
                this.fields.add(new Field(v1, this.isMixin));
            }
            this.isProbablyStatic = isProbablyStatic;
            this.outerName = outerName;
            this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
            this.signature = ClassSignature.ofLazy(v-4);
        }
        finally {
            begin.end();
        }
    }
    
    void addInterface(final String a1) {
        this.interfaces.add(a1);
        this.getSignature().addInterface(a1);
    }
    
    void addMethod(final MethodNode a1) {
        this.addMethod(a1, true);
    }
    
    private void addMethod(final MethodNode a1, final boolean a2) {
        if (!a1.name.startsWith("<")) {
            this.methods.add(new Method(a1, a2));
        }
    }
    
    void addMixin(final MixinInfo a1) {
        if (this.isMixin) {
            throw new IllegalArgumentException("Cannot add target " + this.name + " for " + a1.getClassName() + " because the target is a mixin");
        }
        this.mixins.add(a1);
    }
    
    public Set<MixinInfo> getMixins() {
        return Collections.unmodifiableSet((Set<? extends MixinInfo>)this.mixins);
    }
    
    public boolean isMixin() {
        return this.isMixin;
    }
    
    public boolean isPublic() {
        return (this.access & 0x1) != 0x0;
    }
    
    public boolean isAbstract() {
        return (this.access & 0x400) != 0x0;
    }
    
    public boolean isSynthetic() {
        return (this.access & 0x1000) != 0x0;
    }
    
    public boolean isProbablyStatic() {
        return this.isProbablyStatic;
    }
    
    public boolean isInner() {
        return this.outerName != null;
    }
    
    public boolean isInterface() {
        return this.isInterface;
    }
    
    public Set<String> getInterfaces() {
        return Collections.unmodifiableSet((Set<? extends String>)this.interfaces);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public MethodMapper getMethodMapper() {
        return this.methodMapper;
    }
    
    public int getAccess() {
        return this.access;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getClassName() {
        return this.name.replace('/', '.');
    }
    
    public String getSuperName() {
        return this.superName;
    }
    
    public ClassInfo getSuperClass() {
        if (this.superClass == null && this.superName != null) {
            this.superClass = forName(this.superName);
        }
        return this.superClass;
    }
    
    public String getOuterName() {
        return this.outerName;
    }
    
    public ClassInfo getOuterClass() {
        if (this.outerClass == null && this.outerName != null) {
            this.outerClass = forName(this.outerName);
        }
        return this.outerClass;
    }
    
    public ClassSignature getSignature() {
        return this.signature.wake();
    }
    
    List<ClassInfo> getTargets() {
        if (this.mixin != null) {
            final List<ClassInfo> v1 = new ArrayList<ClassInfo>();
            v1.add(this);
            v1.addAll(this.mixin.getTargets());
            return v1;
        }
        return ImmutableList.of(this);
    }
    
    public Set<Method> getMethods() {
        return Collections.unmodifiableSet((Set<? extends Method>)this.methods);
    }
    
    public Set<Method> getInterfaceMethods(final boolean v2) {
        final Set<Method> v3 = new HashSet<Method>();
        ClassInfo v4 = this.addMethodsRecursive(v3, v2);
        if (!this.isInterface) {
            while (v4 != null && v4 != ClassInfo.OBJECT) {
                v4 = v4.addMethodsRecursive(v3, v2);
            }
        }
        final Iterator<Method> a1 = v3.iterator();
        while (a1.hasNext()) {
            if (!a1.next().isAbstract()) {
                a1.remove();
            }
        }
        return Collections.unmodifiableSet((Set<? extends Method>)v3);
    }
    
    private ClassInfo addMethodsRecursive(final Set<Method> v-2, final boolean v-1) {
        if (this.isInterface) {
            for (final Method a1 : this.methods) {
                if (!a1.isAbstract()) {
                    v-2.remove(a1);
                }
                v-2.add(a1);
            }
        }
        else if (!this.isMixin && v-1) {
            for (final MixinInfo a2 : this.mixins) {
                a2.getClassInfo().addMethodsRecursive(v-2, v-1);
            }
        }
        for (final String v1 : this.interfaces) {
            forName(v1).addMethodsRecursive(v-2, v-1);
        }
        return this.getSuperClass();
    }
    
    public boolean hasSuperClass(final String a1) {
        return this.hasSuperClass(a1, Traversal.NONE);
    }
    
    public boolean hasSuperClass(final String a1, final Traversal a2) {
        return "java/lang/Object".equals(a1) || this.findSuperClass(a1, a2) != null;
    }
    
    public boolean hasSuperClass(final ClassInfo a1) {
        return this.hasSuperClass(a1, Traversal.NONE, false);
    }
    
    public boolean hasSuperClass(final ClassInfo a1, final Traversal a2) {
        return this.hasSuperClass(a1, a2, false);
    }
    
    public boolean hasSuperClass(final ClassInfo a1, final Traversal a2, final boolean a3) {
        return ClassInfo.OBJECT == a1 || this.findSuperClass(a1.name, a2, a3) != null;
    }
    
    public ClassInfo findSuperClass(final String a1) {
        return this.findSuperClass(a1, Traversal.NONE);
    }
    
    public ClassInfo findSuperClass(final String a1, final Traversal a2) {
        return this.findSuperClass(a1, a2, false, new HashSet<String>());
    }
    
    public ClassInfo findSuperClass(final String a1, final Traversal a2, final boolean a3) {
        if (ClassInfo.OBJECT.name.equals(a1)) {
            return null;
        }
        return this.findSuperClass(a1, a2, a3, new HashSet<String>());
    }
    
    private ClassInfo findSuperClass(final String v-7, final Traversal v-6, final boolean v-5, final Set<String> v-4) {
        final ClassInfo superClass = this.getSuperClass();
        if (superClass != null) {
            for (final ClassInfo a2 : superClass.getTargets()) {
                if (v-7.equals(a2.getName())) {
                    return superClass;
                }
                final ClassInfo a3 = a2.findSuperClass(v-7, v-6.next(), v-5, v-4);
                if (a3 != null) {
                    return a3;
                }
            }
        }
        if (v-5) {
            final ClassInfo a4 = this.findInterface(v-7);
            if (a4 != null) {
                return a4;
            }
        }
        if (v-6.canTraverse()) {
            for (final MixinInfo mixinInfo : this.mixins) {
                final String a5 = mixinInfo.getClassName();
                if (v-4.contains(a5)) {
                    continue;
                }
                v-4.add(a5);
                final ClassInfo v1 = mixinInfo.getClassInfo();
                if (v-7.equals(v1.getName())) {
                    return v1;
                }
                final ClassInfo v2 = v1.findSuperClass(v-7, Traversal.ALL, v-5, v-4);
                if (v2 != null) {
                    return v2;
                }
            }
        }
        return null;
    }
    
    private ClassInfo findInterface(final String v-3) {
        for (final String v-4 : this.getInterfaces()) {
            final ClassInfo a1 = forName(v-4);
            if (v-3.equals(v-4)) {
                return a1;
            }
            final ClassInfo v1 = a1.findInterface(v-3);
            if (v1 != null) {
                return v1;
            }
        }
        return null;
    }
    
    ClassInfo findCorrespondingType(final ClassInfo a1) {
        if (a1 == null || !a1.isMixin || this.isMixin) {
            return null;
        }
        ClassInfo v1 = this.correspondingTypes.get(a1);
        if (v1 == null) {
            v1 = this.findSuperTypeForMixin(a1);
            this.correspondingTypes.put(a1, v1);
        }
        return v1;
    }
    
    private ClassInfo findSuperTypeForMixin(final ClassInfo v2) {
        for (ClassInfo v3 = this; v3 != null && v3 != ClassInfo.OBJECT; v3 = v3.getSuperClass()) {
            for (final MixinInfo a1 : v3.mixins) {
                if (a1.getClassInfo().equals(v2)) {
                    return v3;
                }
            }
        }
        return null;
    }
    
    public boolean hasMixinInHierarchy() {
        if (!this.isMixin) {
            return false;
        }
        for (ClassInfo v1 = this.getSuperClass(); v1 != null && v1 != ClassInfo.OBJECT; v1 = v1.getSuperClass()) {
            if (v1.isMixin) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasMixinTargetInHierarchy() {
        if (this.isMixin) {
            return false;
        }
        for (ClassInfo v1 = this.getSuperClass(); v1 != null && v1 != ClassInfo.OBJECT; v1 = v1.getSuperClass()) {
            if (v1.mixins.size() > 0) {
                return true;
            }
        }
        return false;
    }
    
    public Method findMethodInHierarchy(final MethodNode a1, final SearchType a2) {
        return this.findMethodInHierarchy(a1.name, a1.desc, a2, Traversal.NONE);
    }
    
    public Method findMethodInHierarchy(final MethodNode a1, final SearchType a2, final int a3) {
        return this.findMethodInHierarchy(a1.name, a1.desc, a2, Traversal.NONE, a3);
    }
    
    public Method findMethodInHierarchy(final MethodInsnNode a1, final SearchType a2) {
        return this.findMethodInHierarchy(a1.name, a1.desc, a2, Traversal.NONE);
    }
    
    public Method findMethodInHierarchy(final MethodInsnNode a1, final SearchType a2, final int a3) {
        return this.findMethodInHierarchy(a1.name, a1.desc, a2, Traversal.NONE, a3);
    }
    
    public Method findMethodInHierarchy(final String a1, final String a2, final SearchType a3) {
        return this.findMethodInHierarchy(a1, a2, a3, Traversal.NONE);
    }
    
    public Method findMethodInHierarchy(final String a1, final String a2, final SearchType a3, final Traversal a4) {
        return this.findMethodInHierarchy(a1, a2, a3, a4, 0);
    }
    
    public Method findMethodInHierarchy(final String a1, final String a2, final SearchType a3, final Traversal a4, final int a5) {
        return this.findInHierarchy(a1, a2, a3, a4, a5, Member.Type.METHOD);
    }
    
    public Field findFieldInHierarchy(final FieldNode a1, final SearchType a2) {
        return this.findFieldInHierarchy(a1.name, a1.desc, a2, Traversal.NONE);
    }
    
    public Field findFieldInHierarchy(final FieldNode a1, final SearchType a2, final int a3) {
        return this.findFieldInHierarchy(a1.name, a1.desc, a2, Traversal.NONE, a3);
    }
    
    public Field findFieldInHierarchy(final FieldInsnNode a1, final SearchType a2) {
        return this.findFieldInHierarchy(a1.name, a1.desc, a2, Traversal.NONE);
    }
    
    public Field findFieldInHierarchy(final FieldInsnNode a1, final SearchType a2, final int a3) {
        return this.findFieldInHierarchy(a1.name, a1.desc, a2, Traversal.NONE, a3);
    }
    
    public Field findFieldInHierarchy(final String a1, final String a2, final SearchType a3) {
        return this.findFieldInHierarchy(a1, a2, a3, Traversal.NONE);
    }
    
    public Field findFieldInHierarchy(final String a1, final String a2, final SearchType a3, final Traversal a4) {
        return this.findFieldInHierarchy(a1, a2, a3, a4, 0);
    }
    
    public Field findFieldInHierarchy(final String a1, final String a2, final SearchType a3, final Traversal a4, final int a5) {
        return this.findInHierarchy(a1, a2, a3, a4, a5, Member.Type.FIELD);
    }
    
    private <M extends Member> M findInHierarchy(final String v-9, final String v-8, final SearchType v-7, final Traversal v-6, final int v-5, final Member.Type v-4) {
        if (v-7 == SearchType.ALL_CLASSES) {
            final M a3 = this.findMember(v-9, v-8, v-5, v-4);
            if (a3 != null) {
                return a3;
            }
            if (v-6.canTraverse()) {
                for (final MixinInfo a4 : this.mixins) {
                    final M a5 = a4.getClassInfo().findMember(v-9, v-8, v-5, v-4);
                    if (a5 != null) {
                        return this.cloneMember(a5);
                    }
                }
            }
        }
        final ClassInfo superClass = this.getSuperClass();
        if (superClass != null) {
            for (final ClassInfo a6 : superClass.getTargets()) {
                final M a7 = (M)a6.findInHierarchy(v-9, v-8, SearchType.ALL_CLASSES, v-6.next(), v-5 & 0xFFFFFFFD, v-4);
                if (a7 != null) {
                    return a7;
                }
            }
        }
        if (v-4 == Member.Type.METHOD && (this.isInterface || MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())) {
            for (final String v-10 : this.interfaces) {
                final ClassInfo a8 = forName(v-10);
                if (a8 == null) {
                    ClassInfo.logger.debug("Failed to resolve declared interface {} on {}", new Object[] { v-10, this.name });
                }
                else {
                    final M v1 = (M)a8.findInHierarchy(v-9, v-8, SearchType.ALL_CLASSES, v-6.next(), v-5 & 0xFFFFFFFD, v-4);
                    if (v1 != null) {
                        return (M)(this.isInterface ? v1 : new InterfaceMethod(v1));
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    private <M extends Member> M cloneMember(final M a1) {
        if (a1 instanceof Method) {
            return (M)new Method(a1);
        }
        return (M)new Field(a1);
    }
    
    public Method findMethod(final MethodNode a1) {
        return this.findMethod(a1.name, a1.desc, a1.access);
    }
    
    public Method findMethod(final MethodNode a1, final int a2) {
        return this.findMethod(a1.name, a1.desc, a2);
    }
    
    public Method findMethod(final MethodInsnNode a1) {
        return this.findMethod(a1.name, a1.desc, 0);
    }
    
    public Method findMethod(final MethodInsnNode a1, final int a2) {
        return this.findMethod(a1.name, a1.desc, a2);
    }
    
    public Method findMethod(final String a1, final String a2, final int a3) {
        return this.findMember(a1, a2, a3, Member.Type.METHOD);
    }
    
    public Field findField(final FieldNode a1) {
        return this.findField(a1.name, a1.desc, a1.access);
    }
    
    public Field findField(final FieldInsnNode a1, final int a2) {
        return this.findField(a1.name, a1.desc, a2);
    }
    
    public Field findField(final String a1, final String a2, final int a3) {
        return this.findMember(a1, a2, a3, Member.Type.FIELD);
    }
    
    private <M extends Member> M findMember(final String a3, final String a4, final int v1, final Member.Type v2) {
        final Set<M> v3 = (Set<M>)((v2 == Member.Type.METHOD) ? this.methods : this.fields);
        for (final M a5 : v3) {
            if (a5.equals(a3, a4) && a5.matchesFlags(v1)) {
                return a5;
            }
        }
        return null;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof ClassInfo && ((ClassInfo)a1).name.equals(this.name);
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    static ClassInfo fromClassNode(final ClassNode a1) {
        ClassInfo v1 = ClassInfo.cache.get(a1.name);
        if (v1 == null) {
            v1 = new ClassInfo(a1);
            ClassInfo.cache.put(a1.name, v1);
        }
        return v1;
    }
    
    public static ClassInfo forName(String v-1) {
        v-1 = v-1.replace('.', '/');
        ClassInfo v0 = ClassInfo.cache.get(v-1);
        if (v0 == null) {
            try {
                final ClassNode a1 = MixinService.getService().getBytecodeProvider().getClassNode(v-1);
                v0 = new ClassInfo(a1);
            }
            catch (Exception v2) {
                ClassInfo.logger.catching(Level.TRACE, (Throwable)v2);
                ClassInfo.logger.warn("Error loading class: {} ({}: {})", new Object[] { v-1, v2.getClass().getName(), v2.getMessage() });
            }
            ClassInfo.cache.put(v-1, v0);
            ClassInfo.logger.trace("Added class metadata for {} to metadata cache", new Object[] { v-1 });
        }
        return v0;
    }
    
    public static ClassInfo forType(final Type a1) {
        if (a1.getSort() == 9) {
            return forType(a1.getElementType());
        }
        if (a1.getSort() < 9) {
            return null;
        }
        return forName(a1.getClassName().replace('.', '/'));
    }
    
    public static ClassInfo getCommonSuperClass(final String a1, final String a2) {
        if (a1 == null || a2 == null) {
            return ClassInfo.OBJECT;
        }
        return getCommonSuperClass(forName(a1), forName(a2));
    }
    
    public static ClassInfo getCommonSuperClass(final Type a1, final Type a2) {
        if (a1 == null || a2 == null || a1.getSort() != 10 || a2.getSort() != 10) {
            return ClassInfo.OBJECT;
        }
        return getCommonSuperClass(forType(a1), forType(a2));
    }
    
    private static ClassInfo getCommonSuperClass(final ClassInfo a1, final ClassInfo a2) {
        return getCommonSuperClass(a1, a2, false);
    }
    
    public static ClassInfo getCommonSuperClassOrInterface(final String a1, final String a2) {
        if (a1 == null || a2 == null) {
            return ClassInfo.OBJECT;
        }
        return getCommonSuperClassOrInterface(forName(a1), forName(a2));
    }
    
    public static ClassInfo getCommonSuperClassOrInterface(final Type a1, final Type a2) {
        if (a1 == null || a2 == null || a1.getSort() != 10 || a2.getSort() != 10) {
            return ClassInfo.OBJECT;
        }
        return getCommonSuperClassOrInterface(forType(a1), forType(a2));
    }
    
    public static ClassInfo getCommonSuperClassOrInterface(final ClassInfo a1, final ClassInfo a2) {
        return getCommonSuperClass(a1, a2, true);
    }
    
    private static ClassInfo getCommonSuperClass(ClassInfo a1, final ClassInfo a2, final boolean a3) {
        if (a1.hasSuperClass(a2, Traversal.NONE, a3)) {
            return a2;
        }
        if (a2.hasSuperClass(a1, Traversal.NONE, a3)) {
            return a1;
        }
        if (a1.isInterface() || a2.isInterface()) {
            return ClassInfo.OBJECT;
        }
        do {
            a1 = a1.getSuperClass();
            if (a1 == null) {
                return ClassInfo.OBJECT;
            }
        } while (!a2.hasSuperClass(a1, Traversal.NONE, a3));
        return a1;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
        profiler = MixinEnvironment.getProfiler();
        cache = new HashMap<String, ClassInfo>();
        OBJECT = new ClassInfo();
        ClassInfo.cache.put("java/lang/Object", ClassInfo.OBJECT);
    }
    
    public enum SearchType
    {
        ALL_CLASSES, 
        SUPER_CLASSES_ONLY;
        
        private static final /* synthetic */ SearchType[] $VALUES;
        
        public static SearchType[] values() {
            return SearchType.$VALUES.clone();
        }
        
        public static SearchType valueOf(final String a1) {
            return Enum.valueOf(SearchType.class, a1);
        }
        
        static {
            $VALUES = new SearchType[] { SearchType.ALL_CLASSES, SearchType.SUPER_CLASSES_ONLY };
        }
    }
    
    public enum Traversal
    {
        NONE((Traversal)null, false, SearchType.SUPER_CLASSES_ONLY), 
        ALL((Traversal)null, true, SearchType.ALL_CLASSES), 
        IMMEDIATE(Traversal.NONE, true, SearchType.SUPER_CLASSES_ONLY), 
        SUPER(Traversal.ALL, false, SearchType.SUPER_CLASSES_ONLY);
        
        private final Traversal next;
        private final boolean traverse;
        private final SearchType searchType;
        private static final /* synthetic */ Traversal[] $VALUES;
        
        public static Traversal[] values() {
            return Traversal.$VALUES.clone();
        }
        
        public static Traversal valueOf(final String a1) {
            return Enum.valueOf(Traversal.class, a1);
        }
        
        private Traversal(final Traversal a1, final boolean a2, final SearchType a3) {
            this.next = ((a1 != null) ? a1 : this);
            this.traverse = a2;
            this.searchType = a3;
        }
        
        public Traversal next() {
            return this.next;
        }
        
        public boolean canTraverse() {
            return this.traverse;
        }
        
        public SearchType getSearchType() {
            return this.searchType;
        }
        
        static {
            $VALUES = new Traversal[] { Traversal.NONE, Traversal.ALL, Traversal.IMMEDIATE, Traversal.SUPER };
        }
    }
    
    public static class FrameData
    {
        private static final String[] FRAMETYPES;
        public final int index;
        public final int type;
        public final int locals;
        
        FrameData(final int a1, final int a2, final int a3) {
            super();
            this.index = a1;
            this.type = a2;
            this.locals = a3;
        }
        
        FrameData(final int a1, final FrameNode a2) {
            super();
            this.index = a1;
            this.type = a2.type;
            this.locals = ((a2.local != null) ? a2.local.size() : 0);
        }
        
        @Override
        public String toString() {
            return String.format("FrameData[index=%d, type=%s, locals=%d]", this.index, FrameData.FRAMETYPES[this.type + 1], this.locals);
        }
        
        static {
            FRAMETYPES = new String[] { "NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1" };
        }
    }
    
    abstract static class Member
    {
        private final Type type;
        private final String memberName;
        private final String memberDesc;
        private final boolean isInjected;
        private final int modifiers;
        private String currentName;
        private String currentDesc;
        private boolean decoratedFinal;
        private boolean decoratedMutable;
        private boolean unique;
        
        protected Member(final Member a1) {
            this(a1.type, a1.memberName, a1.memberDesc, a1.modifiers, a1.isInjected);
            this.currentName = a1.currentName;
            this.currentDesc = a1.currentDesc;
            this.unique = a1.unique;
        }
        
        protected Member(final Type a1, final String a2, final String a3, final int a4) {
            this(a1, a2, a3, a4, false);
        }
        
        protected Member(final Type a1, final String a2, final String a3, final int a4, final boolean a5) {
            super();
            this.type = a1;
            this.memberName = a2;
            this.memberDesc = a3;
            this.isInjected = a5;
            this.currentName = a2;
            this.currentDesc = a3;
            this.modifiers = a4;
        }
        
        public String getOriginalName() {
            return this.memberName;
        }
        
        public String getName() {
            return this.currentName;
        }
        
        public String getOriginalDesc() {
            return this.memberDesc;
        }
        
        public String getDesc() {
            return this.currentDesc;
        }
        
        public boolean isInjected() {
            return this.isInjected;
        }
        
        public boolean isRenamed() {
            return !this.currentName.equals(this.memberName);
        }
        
        public boolean isRemapped() {
            return !this.currentDesc.equals(this.memberDesc);
        }
        
        public boolean isPrivate() {
            return (this.modifiers & 0x2) != 0x0;
        }
        
        public boolean isStatic() {
            return (this.modifiers & 0x8) != 0x0;
        }
        
        public boolean isAbstract() {
            return (this.modifiers & 0x400) != 0x0;
        }
        
        public boolean isFinal() {
            return (this.modifiers & 0x10) != 0x0;
        }
        
        public boolean isSynthetic() {
            return (this.modifiers & 0x1000) != 0x0;
        }
        
        public boolean isUnique() {
            return this.unique;
        }
        
        public void setUnique(final boolean a1) {
            this.unique = a1;
        }
        
        public boolean isDecoratedFinal() {
            return this.decoratedFinal;
        }
        
        public boolean isDecoratedMutable() {
            return this.decoratedMutable;
        }
        
        public void setDecoratedFinal(final boolean a1, final boolean a2) {
            this.decoratedFinal = a1;
            this.decoratedMutable = a2;
        }
        
        public boolean matchesFlags(final int a1) {
            return ((~this.modifiers | (a1 & 0x2)) & 0x2) != 0x0 && ((~this.modifiers | (a1 & 0x8)) & 0x8) != 0x0;
        }
        
        public abstract ClassInfo getOwner();
        
        public ClassInfo getImplementor() {
            return this.getOwner();
        }
        
        public int getAccess() {
            return this.modifiers;
        }
        
        public String renameTo(final String a1) {
            return this.currentName = a1;
        }
        
        public String remapTo(final String a1) {
            return this.currentDesc = a1;
        }
        
        public boolean equals(final String a1, final String a2) {
            return (this.memberName.equals(a1) || this.currentName.equals(a1)) && (this.memberDesc.equals(a2) || this.currentDesc.equals(a2));
        }
        
        @Override
        public boolean equals(final Object a1) {
            if (!(a1 instanceof Member)) {
                return false;
            }
            final Member v1 = (Member)a1;
            return (v1.memberName.equals(this.memberName) || v1.currentName.equals(this.currentName)) && (v1.memberDesc.equals(this.memberDesc) || v1.currentDesc.equals(this.currentDesc));
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        @Override
        public String toString() {
            return String.format(this.getDisplayFormat(), this.memberName, this.memberDesc);
        }
        
        protected String getDisplayFormat() {
            return "%s%s";
        }
        
        enum Type
        {
            METHOD, 
            FIELD;
            
            private static final /* synthetic */ Type[] $VALUES;
            
            public static Type[] values() {
                return Type.$VALUES.clone();
            }
            
            public static Type valueOf(final String a1) {
                return Enum.valueOf(Type.class, a1);
            }
            
            static {
                $VALUES = new Type[] { Type.METHOD, Type.FIELD };
            }
        }
    }
    
    public class Method extends Member
    {
        private final List<FrameData> frames;
        private boolean isAccessor;
        final /* synthetic */ ClassInfo this$0;
        
        public Method(final ClassInfo a1, final Member a2) {
            this.this$0 = a1;
            super(a2);
            this.frames = ((a2 instanceof Method) ? ((Method)a2).frames : null);
        }
        
        public Method(final ClassInfo a1, final MethodNode a2) {
            this(a1, a2, false);
            this.setUnique(Annotations.getVisible(a2, Unique.class) != null);
            this.isAccessor = (Annotations.getSingleVisible(a2, Accessor.class, Invoker.class) != null);
        }
        
        public Method(final ClassInfo a1, final MethodNode a2, final boolean a3) {
            this.this$0 = a1;
            super(Type.METHOD, a2.name, a2.desc, a2.access, a3);
            this.frames = this.gatherFrames(a2);
            this.setUnique(Annotations.getVisible(a2, Unique.class) != null);
            this.isAccessor = (Annotations.getSingleVisible(a2, Accessor.class, Invoker.class) != null);
        }
        
        public Method(final ClassInfo a1, final String a2, final String a3) {
            this.this$0 = a1;
            super(Type.METHOD, a2, a3, 1, false);
            this.frames = null;
        }
        
        public Method(final ClassInfo a1, final String a2, final String a3, final int a4) {
            this.this$0 = a1;
            super(Type.METHOD, a2, a3, a4, false);
            this.frames = null;
        }
        
        public Method(final ClassInfo a1, final String a2, final String a3, final int a4, final boolean a5) {
            this.this$0 = a1;
            super(Type.METHOD, a2, a3, a4, a5);
            this.frames = null;
        }
        
        private List<FrameData> gatherFrames(final MethodNode v-1) {
            final List<FrameData> v0 = new ArrayList<FrameData>();
            for (final AbstractInsnNode a1 : v-1.instructions) {
                if (a1 instanceof FrameNode) {
                    v0.add(new FrameData(v-1.instructions.indexOf(a1), (FrameNode)a1));
                }
            }
            return v0;
        }
        
        public List<FrameData> getFrames() {
            return this.frames;
        }
        
        @Override
        public ClassInfo getOwner() {
            return this.this$0;
        }
        
        public boolean isAccessor() {
            return this.isAccessor;
        }
        
        @Override
        public boolean equals(final Object a1) {
            return a1 instanceof Method && super.equals(a1);
        }
        
        @Override
        public /* bridge */ String toString() {
            return super.toString();
        }
        
        @Override
        public /* bridge */ int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public /* bridge */ boolean equals(final String a1, final String a2) {
            return super.equals(a1, a2);
        }
        
        @Override
        public /* bridge */ String remapTo(final String a1) {
            return super.remapTo(a1);
        }
        
        @Override
        public /* bridge */ String renameTo(final String a1) {
            return super.renameTo(a1);
        }
        
        @Override
        public /* bridge */ int getAccess() {
            return super.getAccess();
        }
        
        @Override
        public /* bridge */ ClassInfo getImplementor() {
            return super.getImplementor();
        }
        
        @Override
        public /* bridge */ boolean matchesFlags(final int a1) {
            return super.matchesFlags(a1);
        }
        
        @Override
        public /* bridge */ void setDecoratedFinal(final boolean a1, final boolean a2) {
            super.setDecoratedFinal(a1, a2);
        }
        
        @Override
        public /* bridge */ boolean isDecoratedMutable() {
            return super.isDecoratedMutable();
        }
        
        @Override
        public /* bridge */ boolean isDecoratedFinal() {
            return super.isDecoratedFinal();
        }
        
        @Override
        public /* bridge */ void setUnique(final boolean unique) {
            super.setUnique(unique);
        }
        
        @Override
        public /* bridge */ boolean isUnique() {
            return super.isUnique();
        }
        
        @Override
        public /* bridge */ boolean isSynthetic() {
            return super.isSynthetic();
        }
        
        @Override
        public /* bridge */ boolean isFinal() {
            return super.isFinal();
        }
        
        @Override
        public /* bridge */ boolean isAbstract() {
            return super.isAbstract();
        }
        
        @Override
        public /* bridge */ boolean isStatic() {
            return super.isStatic();
        }
        
        @Override
        public /* bridge */ boolean isPrivate() {
            return super.isPrivate();
        }
        
        @Override
        public /* bridge */ boolean isRemapped() {
            return super.isRemapped();
        }
        
        @Override
        public /* bridge */ boolean isRenamed() {
            return super.isRenamed();
        }
        
        @Override
        public /* bridge */ boolean isInjected() {
            return super.isInjected();
        }
        
        @Override
        public /* bridge */ String getDesc() {
            return super.getDesc();
        }
        
        @Override
        public /* bridge */ String getOriginalDesc() {
            return super.getOriginalDesc();
        }
        
        @Override
        public /* bridge */ String getName() {
            return super.getName();
        }
        
        @Override
        public /* bridge */ String getOriginalName() {
            return super.getOriginalName();
        }
    }
    
    public class InterfaceMethod extends Method
    {
        private final ClassInfo owner;
        final /* synthetic */ ClassInfo this$0;
        
        public InterfaceMethod(final ClassInfo a1, final Member a2) {
            this.this$0 = a1;
            a1.super(a2);
            this.owner = a2.getOwner();
        }
        
        @Override
        public ClassInfo getOwner() {
            return this.owner;
        }
        
        @Override
        public ClassInfo getImplementor() {
            return this.this$0;
        }
    }
    
    class Field extends Member
    {
        final /* synthetic */ ClassInfo this$0;
        
        public Field(final ClassInfo a1, final Member a2) {
            this.this$0 = a1;
            super(a2);
        }
        
        public Field(final ClassInfo a1, final FieldNode a2) {
            this(a1, a2, false);
        }
        
        public Field(final ClassInfo v1, final FieldNode v2, final boolean v3) {
            this.this$0 = v1;
            super(Type.FIELD, v2.name, v2.desc, v2.access, v3);
            this.setUnique(Annotations.getVisible(v2, Unique.class) != null);
            if (Annotations.getVisible(v2, Shadow.class) != null) {
                final boolean a1 = Annotations.getVisible(v2, Final.class) != null;
                final boolean a2 = Annotations.getVisible(v2, Mutable.class) != null;
                this.setDecoratedFinal(a1, a2);
            }
        }
        
        public Field(final ClassInfo a1, final String a2, final String a3, final int a4) {
            this.this$0 = a1;
            super(Type.FIELD, a2, a3, a4, false);
        }
        
        public Field(final ClassInfo a1, final String a2, final String a3, final int a4, final boolean a5) {
            this.this$0 = a1;
            super(Type.FIELD, a2, a3, a4, a5);
        }
        
        @Override
        public ClassInfo getOwner() {
            return this.this$0;
        }
        
        @Override
        public boolean equals(final Object a1) {
            return a1 instanceof Field && super.equals(a1);
        }
        
        @Override
        protected String getDisplayFormat() {
            return "%s:%s";
        }
    }
}
