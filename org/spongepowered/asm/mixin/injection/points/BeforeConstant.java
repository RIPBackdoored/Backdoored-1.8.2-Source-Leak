package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import com.google.common.primitives.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.apache.logging.log4j.*;

@AtCode("CONSTANT")
public class BeforeConstant extends InjectionPoint
{
    private static final Logger logger;
    private final int ordinal;
    private final boolean nullValue;
    private final Integer intValue;
    private final Float floatValue;
    private final Long longValue;
    private final Double doubleValue;
    private final String stringValue;
    private final Type typeValue;
    private final int[] expandOpcodes;
    private final boolean expand;
    private final String matchByType;
    private final boolean log;
    
    public BeforeConstant(final IMixinContext a1, final AnnotationNode a2, final String a3) {
        super(Annotations.getValue(a2, "slice", ""), Selector.DEFAULT, null);
        final Boolean v1 = Annotations.getValue(a2, "nullValue", (Boolean)null);
        this.ordinal = Annotations.getValue(a2, "ordinal", -1);
        this.nullValue = (v1 != null && v1);
        this.intValue = Annotations.getValue(a2, "intValue", (Integer)null);
        this.floatValue = Annotations.getValue(a2, "floatValue", (Float)null);
        this.longValue = Annotations.getValue(a2, "longValue", (Long)null);
        this.doubleValue = Annotations.getValue(a2, "doubleValue", (Double)null);
        this.stringValue = Annotations.getValue(a2, "stringValue", (String)null);
        this.typeValue = Annotations.getValue(a2, "classValue", (Type)null);
        this.matchByType = this.validateDiscriminator(a1, a3, v1, "on @Constant annotation");
        this.expandOpcodes = this.parseExpandOpcodes(Annotations.getValue(a2, "expandZeroConditions", true, Constant.Condition.class));
        this.expand = (this.expandOpcodes.length > 0);
        this.log = Annotations.getValue(a2, "log", Boolean.FALSE);
    }
    
    public BeforeConstant(final InjectionPointData v2) {
        super(v2);
        final String v3 = v2.get("nullValue", null);
        final Boolean v4 = (v3 != null) ? Boolean.valueOf(Boolean.parseBoolean(v3)) : null;
        this.ordinal = v2.getOrdinal();
        this.nullValue = (v4 != null && v4);
        this.intValue = Ints.tryParse(v2.get("intValue", ""));
        this.floatValue = Floats.tryParse(v2.get("floatValue", ""));
        this.longValue = Longs.tryParse(v2.get("longValue", ""));
        this.doubleValue = Doubles.tryParse(v2.get("doubleValue", ""));
        this.stringValue = v2.get("stringValue", null);
        final String v5 = v2.get("classValue", null);
        this.typeValue = ((v5 != null) ? Type.getObjectType(v5.replace('.', '/')) : null);
        this.matchByType = this.validateDiscriminator(v2.getContext(), "V", v4, "in @At(\"CONSTANT\") args");
        if ("V".equals(this.matchByType)) {
            throw new InvalidInjectionException(v2.getContext(), "No constant discriminator could be parsed in @At(\"CONSTANT\") args");
        }
        final List<Constant.Condition> v6 = new ArrayList<Constant.Condition>();
        final String v7 = v2.get("expandZeroConditions", "").toLowerCase();
        for (final Constant.Condition a1 : Constant.Condition.values()) {
            if (v7.contains(a1.name().toLowerCase())) {
                v6.add(a1);
            }
        }
        this.expandOpcodes = this.parseExpandOpcodes(v6);
        this.expand = (this.expandOpcodes.length > 0);
        this.log = v2.get("log", false);
    }
    
    private String validateDiscriminator(final IMixinContext a1, String a2, final Boolean a3, final String a4) {
        final int v1 = count(a3, this.intValue, this.floatValue, this.longValue, this.doubleValue, this.stringValue, this.typeValue);
        if (v1 == 1) {
            a2 = null;
        }
        else if (v1 > 1) {
            throw new InvalidInjectionException(a1, "Conflicting constant discriminators specified " + a4 + " for " + a1);
        }
        return a2;
    }
    
    private int[] parseExpandOpcodes(final List<Constant.Condition> v-3) {
        final Set<Integer> set = new HashSet<Integer>();
        for (final Constant.Condition v0 : v-3) {
            final Constant.Condition v2 = v0.getEquivalentCondition();
            for (final int a1 : v2.getOpcodes()) {
                set.add(a1);
            }
        }
        return Ints.toArray((Collection)set);
    }
    
    @Override
    public boolean find(final String v-5, final InsnList v-4, final Collection<AbstractInsnNode> v-3) {
        boolean b = false;
        this.log("BeforeConstant is searching for constants in method with descriptor {}", v-5);
        final ListIterator<AbstractInsnNode> iterator = v-4.iterator();
        int a3 = 0;
        int v1 = 0;
        while (iterator.hasNext()) {
            final AbstractInsnNode a4 = iterator.next();
            final boolean a5 = this.expand ? this.matchesConditionalInsn(v1, a4) : this.matchesConstantInsn(a4);
            if (a5) {
                this.log("    BeforeConstant found a matching constant{} at ordinal {}", (this.matchByType != null) ? " TYPE" : " value", a3);
                if (this.ordinal == -1 || this.ordinal == a3) {
                    this.log("      BeforeConstant found {}", Bytecode.describeNode(a4).trim());
                    v-3.add(a4);
                    b = true;
                }
                ++a3;
            }
            if (!(a4 instanceof LabelNode) && !(a4 instanceof FrameNode)) {
                v1 = a4.getOpcode();
            }
        }
        return b;
    }
    
    private boolean matchesConditionalInsn(final int v-1, final AbstractInsnNode v0) {
        final int[] expandOpcodes = this.expandOpcodes;
        final int length = expandOpcodes.length;
        int i = 0;
        while (i < length) {
            final int a2 = expandOpcodes[i];
            final int a3 = v0.getOpcode();
            if (a3 == a2) {
                if (v-1 == 148 || v-1 == 149 || v-1 == 150 || v-1 == 151 || v-1 == 152) {
                    this.log("  BeforeConstant is ignoring {} following {}", Bytecode.getOpcodeName(a3), Bytecode.getOpcodeName(v-1));
                    return false;
                }
                this.log("  BeforeConstant found {} instruction", Bytecode.getOpcodeName(a3));
                return true;
            }
            else {
                ++i;
            }
        }
        if (this.intValue != null && this.intValue == 0 && Bytecode.isConstant(v0)) {
            final Object v = Bytecode.getConstant(v0);
            this.log("  BeforeConstant found INTEGER constant: value = {}", v);
            return v instanceof Integer && (int)v == 0;
        }
        return false;
    }
    
    private boolean matchesConstantInsn(final AbstractInsnNode a1) {
        if (!Bytecode.isConstant(a1)) {
            return false;
        }
        final Object v1 = Bytecode.getConstant(a1);
        if (v1 == null) {
            this.log("  BeforeConstant found NULL constant: nullValue = {}", this.nullValue);
            return this.nullValue || "Ljava/lang/Object;".equals(this.matchByType);
        }
        if (v1 instanceof Integer) {
            this.log("  BeforeConstant found INTEGER constant: value = {}, intValue = {}", v1, this.intValue);
            return v1.equals(this.intValue) || "I".equals(this.matchByType);
        }
        if (v1 instanceof Float) {
            this.log("  BeforeConstant found FLOAT constant: value = {}, floatValue = {}", v1, this.floatValue);
            return v1.equals(this.floatValue) || "F".equals(this.matchByType);
        }
        if (v1 instanceof Long) {
            this.log("  BeforeConstant found LONG constant: value = {}, longValue = {}", v1, this.longValue);
            return v1.equals(this.longValue) || "J".equals(this.matchByType);
        }
        if (v1 instanceof Double) {
            this.log("  BeforeConstant found DOUBLE constant: value = {}, doubleValue = {}", v1, this.doubleValue);
            return v1.equals(this.doubleValue) || "D".equals(this.matchByType);
        }
        if (v1 instanceof String) {
            this.log("  BeforeConstant found STRING constant: value = {}, stringValue = {}", v1, this.stringValue);
            return v1.equals(this.stringValue) || "Ljava/lang/String;".equals(this.matchByType);
        }
        if (v1 instanceof Type) {
            this.log("  BeforeConstant found CLASS constant: value = {}, typeValue = {}", v1, this.typeValue);
            return v1.equals(this.typeValue) || "Ljava/lang/Class;".equals(this.matchByType);
        }
        return false;
    }
    
    protected void log(final String a1, final Object... a2) {
        if (this.log) {
            BeforeConstant.logger.info(a1, a2);
        }
    }
    
    private static int count(final Object... v1) {
        int v2 = 0;
        for (final Object a1 : v1) {
            if (a1 != null) {
                ++v2;
            }
        }
        return v2;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
