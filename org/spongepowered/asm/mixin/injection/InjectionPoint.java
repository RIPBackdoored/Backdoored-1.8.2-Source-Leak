package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.util.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.injection.modify.*;
import org.spongepowered.asm.mixin.injection.points.*;
import com.google.common.base.*;
import java.lang.reflect.*;
import java.util.*;
import java.lang.annotation.*;

public abstract class InjectionPoint
{
    public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
    public static final int MAX_ALLOWED_SHIFT_BY = 5;
    private static Map<String, Class<? extends InjectionPoint>> types;
    private final String slice;
    private final Selector selector;
    private final String id;
    
    protected InjectionPoint() {
        this("", Selector.DEFAULT, null);
    }
    
    protected InjectionPoint(final InjectionPointData a1) {
        this(a1.getSlice(), a1.getSelector(), a1.getId());
    }
    
    public InjectionPoint(final String a1, final Selector a2, final String a3) {
        super();
        this.slice = a1;
        this.selector = a2;
        this.id = a3;
    }
    
    public String getSlice() {
        return this.slice;
    }
    
    public Selector getSelector() {
        return this.selector;
    }
    
    public String getId() {
        return this.id;
    }
    
    public boolean checkPriority(final int a1, final int a2) {
        return a1 < a2;
    }
    
    public abstract boolean find(final String p0, final InsnList p1, final Collection<AbstractInsnNode> p2);
    
    @Override
    public String toString() {
        return String.format("@At(\"%s\")", this.getAtCode());
    }
    
    protected static AbstractInsnNode nextNode(final InsnList a1, final AbstractInsnNode a2) {
        final int v1 = a1.indexOf(a2) + 1;
        if (v1 > 0 && v1 < a1.size()) {
            return a1.get(v1);
        }
        return a2;
    }
    
    public static InjectionPoint and(final InjectionPoint... a1) {
        return new Intersection(a1);
    }
    
    public static InjectionPoint or(final InjectionPoint... a1) {
        return new Union(a1);
    }
    
    public static InjectionPoint after(final InjectionPoint a1) {
        return new Shift(a1, 1);
    }
    
    public static InjectionPoint before(final InjectionPoint a1) {
        return new Shift(a1, -1);
    }
    
    public static InjectionPoint shift(final InjectionPoint a1, final int a2) {
        return new Shift(a1, a2);
    }
    
    public static List<InjectionPoint> parse(final IInjectionPointContext a1, final List<AnnotationNode> a2) {
        return parse(a1.getContext(), a1.getMethod(), a1.getAnnotation(), a2);
    }
    
    public static List<InjectionPoint> parse(final IMixinContext a3, final MethodNode a4, final AnnotationNode v1, final List<AnnotationNode> v2) {
        final ImmutableList.Builder<InjectionPoint> v3 = (ImmutableList.Builder<InjectionPoint>)ImmutableList.builder();
        for (final AnnotationNode a5 : v2) {
            final InjectionPoint a6 = parse(a3, a4, v1, a5);
            if (a6 != null) {
                v3.add(a6);
            }
        }
        return v3.build();
    }
    
    public static InjectionPoint parse(final IInjectionPointContext a1, final At a2) {
        return parse(a1.getContext(), a1.getMethod(), a1.getAnnotation(), a2.value(), a2.shift(), a2.by(), Arrays.asList(a2.args()), a2.target(), a2.slice(), a2.ordinal(), a2.opcode(), a2.id());
    }
    
    public static InjectionPoint parse(final IMixinContext a1, final MethodNode a2, final AnnotationNode a3, final At a4) {
        return parse(a1, a2, a3, a4.value(), a4.shift(), a4.by(), Arrays.asList(a4.args()), a4.target(), a4.slice(), a4.ordinal(), a4.opcode(), a4.id());
    }
    
    public static InjectionPoint parse(final IInjectionPointContext a1, final AnnotationNode a2) {
        return parse(a1.getContext(), a1.getMethod(), a1.getAnnotation(), a2);
    }
    
    public static InjectionPoint parse(final IMixinContext a1, final MethodNode a2, final AnnotationNode a3, final AnnotationNode a4) {
        final String v1 = Annotations.getValue(a4, "value");
        List<String> v2 = Annotations.getValue(a4, "args");
        final String v3 = Annotations.getValue(a4, "target", "");
        final String v4 = Annotations.getValue(a4, "slice", "");
        final At.Shift v5 = Annotations.getValue(a4, "shift", At.Shift.class, At.Shift.NONE);
        final int v6 = Annotations.getValue(a4, "by", 0);
        final int v7 = Annotations.getValue(a4, "ordinal", -1);
        final int v8 = Annotations.getValue(a4, "opcode", 0);
        final String v9 = Annotations.getValue(a4, "id");
        if (v2 == null) {
            v2 = (List<String>)ImmutableList.of();
        }
        return parse(a1, a2, a3, v1, v5, v6, v2, v3, v4, v7, v8, v9);
    }
    
    public static InjectionPoint parse(final IMixinContext a1, final MethodNode a2, final AnnotationNode a3, final String a4, final At.Shift a5, final int a6, final List<String> a7, final String a8, final String a9, final int a10, final int a11, final String a12) {
        final InjectionPointData v1 = new InjectionPointData(a1, a2, a3, a4, a7, a8, a9, a10, a11, a12);
        final Class<? extends InjectionPoint> v2 = findClass(a1, v1);
        final InjectionPoint v3 = create(a1, v1, v2);
        return shift(a1, a2, a3, v3, a5, a6);
    }
    
    private static Class<? extends InjectionPoint> findClass(final IMixinContext a2, final InjectionPointData v1) {
        final String v2 = v1.getType();
        Class<? extends InjectionPoint> v3 = InjectionPoint.types.get(v2);
        if (v3 == null) {
            if (v2.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
                try {
                    v3 = (Class<? extends InjectionPoint>)Class.forName(v2);
                    InjectionPoint.types.put(v2, v3);
                    return v3;
                }
                catch (Exception a3) {
                    throw new InvalidInjectionException(a2, v1 + " could not be loaded or is not a valid InjectionPoint", a3);
                }
            }
            throw new InvalidInjectionException(a2, v1 + " is not a valid injection point specifier");
        }
        return v3;
    }
    
    private static InjectionPoint create(final IMixinContext a3, final InjectionPointData v1, final Class<? extends InjectionPoint> v2) {
        Constructor<? extends InjectionPoint> v3 = null;
        try {
            v3 = v2.getDeclaredConstructor(InjectionPointData.class);
            v3.setAccessible(true);
        }
        catch (NoSuchMethodException a4) {
            throw new InvalidInjectionException(a3, v2.getName() + " must contain a constructor which accepts an InjectionPointData", a4);
        }
        InjectionPoint v4 = null;
        try {
            v4 = (InjectionPoint)v3.newInstance(v1);
        }
        catch (Exception a5) {
            throw new InvalidInjectionException(a3, "Error whilst instancing injection point " + v2.getName() + " for " + v1.getAt(), a5);
        }
        return v4;
    }
    
    private static InjectionPoint shift(final IMixinContext a1, final MethodNode a2, final AnnotationNode a3, final InjectionPoint a4, final At.Shift a5, final int a6) {
        if (a4 != null) {
            if (a5 == At.Shift.BEFORE) {
                return before(a4);
            }
            if (a5 == At.Shift.AFTER) {
                return after(a4);
            }
            if (a5 == At.Shift.BY) {
                validateByValue(a1, a2, a3, a4, a6);
                return shift(a4, a6);
            }
        }
        return a4;
    }
    
    private static void validateByValue(final IMixinContext a1, final MethodNode a2, final AnnotationNode a3, final InjectionPoint a4, final int a5) {
        final MixinEnvironment v1 = a1.getMixin().getConfig().getEnvironment();
        final ShiftByViolationBehaviour v2 = v1.getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, ShiftByViolationBehaviour.WARN);
        if (v2 == ShiftByViolationBehaviour.IGNORE) {
            return;
        }
        String v3 = "the maximum allowed value: ";
        String v4 = "Increase the value of maxShiftBy to suppress this warning.";
        int v5 = 0;
        if (a1 instanceof MixinTargetContext) {
            v5 = ((MixinTargetContext)a1).getMaxShiftByValue();
        }
        if (a5 <= v5) {
            return;
        }
        if (a5 > 5) {
            v3 = "MAX_ALLOWED_SHIFT_BY=";
            v4 = "You must use an alternate query or a custom injection point.";
            v5 = 5;
        }
        final String v6 = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds %s%d. %s", Bytecode.getSimpleName(a3), a4, a5, a1, a2.name, v3, v5, v4);
        if (v2 == ShiftByViolationBehaviour.WARN && v5 < 5) {
            LogManager.getLogger("mixin").warn(v6);
            return;
        }
        throw new InvalidInjectionException(a1, v6);
    }
    
    protected String getAtCode() {
        final AtCode v1 = this.getClass().getAnnotation(AtCode.class);
        return (v1 == null) ? this.getClass().getName() : v1.value();
    }
    
    public static void register(final Class<? extends InjectionPoint> a1) {
        final AtCode v1 = a1.getAnnotation(AtCode.class);
        if (v1 == null) {
            throw new IllegalArgumentException("Injection point class " + a1 + " is not annotated with @AtCode");
        }
        final Class<? extends InjectionPoint> v2 = InjectionPoint.types.get(v1.value());
        if (v2 != null && !v2.equals(a1)) {
            LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[] { v1.value(), a1.getName(), v2.getName() });
        }
        InjectionPoint.types.put(v1.value(), a1);
    }
    
    static {
        InjectionPoint.types = new HashMap<String, Class<? extends InjectionPoint>>();
        register(BeforeFieldAccess.class);
        register(BeforeInvoke.class);
        register(BeforeNew.class);
        register(BeforeReturn.class);
        register(BeforeStringInvoke.class);
        register(JumpInsnPoint.class);
        register(MethodHead.class);
        register(AfterInvoke.class);
        register(BeforeLoadLocal.class);
        register(AfterStoreLocal.class);
        register(BeforeFinalReturn.class);
        register(BeforeConstant.class);
    }
    
    public enum Selector
    {
        FIRST, 
        LAST, 
        ONE;
        
        public static final Selector DEFAULT;
        private static final /* synthetic */ Selector[] $VALUES;
        
        public static Selector[] values() {
            return Selector.$VALUES.clone();
        }
        
        public static Selector valueOf(final String a1) {
            return Enum.valueOf(Selector.class, a1);
        }
        
        static {
            $VALUES = new Selector[] { Selector.FIRST, Selector.LAST, Selector.ONE };
            DEFAULT = Selector.FIRST;
        }
    }
    
    enum ShiftByViolationBehaviour
    {
        IGNORE, 
        WARN, 
        ERROR;
        
        private static final /* synthetic */ ShiftByViolationBehaviour[] $VALUES;
        
        public static ShiftByViolationBehaviour[] values() {
            return ShiftByViolationBehaviour.$VALUES.clone();
        }
        
        public static ShiftByViolationBehaviour valueOf(final String a1) {
            return Enum.valueOf(ShiftByViolationBehaviour.class, a1);
        }
        
        static {
            $VALUES = new ShiftByViolationBehaviour[] { ShiftByViolationBehaviour.IGNORE, ShiftByViolationBehaviour.WARN, ShiftByViolationBehaviour.ERROR };
        }
    }
    
    abstract static class CompositeInjectionPoint extends InjectionPoint
    {
        protected final InjectionPoint[] components;
        
        protected CompositeInjectionPoint(final InjectionPoint... a1) {
            super();
            if (a1 == null || a1.length < 2) {
                throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
            }
            this.components = a1;
        }
        
        @Override
        public String toString() {
            return "CompositeInjectionPoint(" + this.getClass().getSimpleName() + ")[" + Joiner.on(',').join((Object[])this.components) + "]";
        }
    }
    
    static final class Intersection extends CompositeInjectionPoint
    {
        public Intersection(final InjectionPoint... a1) {
            super(a1);
        }
        
        @Override
        public boolean find(final String v-7, final InsnList v-6, final Collection<AbstractInsnNode> v-5) {
            boolean b = false;
            final ArrayList<AbstractInsnNode>[] array = (ArrayList<AbstractInsnNode>[])Array.newInstance(ArrayList.class, this.components.length);
            for (int a1 = 0; a1 < this.components.length; ++a1) {
                array[a1] = new ArrayList<AbstractInsnNode>();
                this.components[a1].find(v-7, v-6, array[a1]);
            }
            final ArrayList<AbstractInsnNode> list = array[0];
            for (int i = 0; i < list.size(); ++i) {
                final AbstractInsnNode a2 = list.get(i);
                final boolean v1 = true;
                for (int a3 = 1; a3 < array.length && array[a3].contains(a2); ++a3) {}
                if (v1) {
                    v-5.add(a2);
                    b = true;
                }
            }
            return b;
        }
    }
    
    static final class Union extends CompositeInjectionPoint
    {
        public Union(final InjectionPoint... a1) {
            super(a1);
        }
        
        @Override
        public boolean find(final String a3, final InsnList v1, final Collection<AbstractInsnNode> v2) {
            final LinkedHashSet<AbstractInsnNode> v3 = new LinkedHashSet<AbstractInsnNode>();
            for (int a4 = 0; a4 < this.components.length; ++a4) {
                this.components[a4].find(a3, v1, v3);
            }
            v2.addAll(v3);
            return v3.size() > 0;
        }
    }
    
    static final class Shift extends InjectionPoint
    {
        private final InjectionPoint input;
        private final int shift;
        
        public Shift(final InjectionPoint a1, final int a2) {
            super();
            if (a1 == null) {
                throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
            }
            this.input = a1;
            this.shift = a2;
        }
        
        @Override
        public String toString() {
            return "InjectionPoint(" + this.getClass().getSimpleName() + ")[" + this.input + "]";
        }
        
        @Override
        public boolean find(final String a3, final InsnList v1, final Collection<AbstractInsnNode> v2) {
            final List<AbstractInsnNode> v3 = (v2 instanceof List) ? ((List)v2) : new ArrayList<AbstractInsnNode>(v2);
            this.input.find(a3, v1, v2);
            for (int a4 = 0; a4 < v3.size(); ++a4) {
                v3.set(a4, v1.get(v1.indexOf(v3.get(a4)) + this.shift));
            }
            if (v2 != v3) {
                v2.clear();
                v2.addAll(v3);
            }
            return v2.size() > 0;
        }
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    public @interface AtCode {
        String value();
    }
}
