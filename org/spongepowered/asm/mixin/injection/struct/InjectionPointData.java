package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.regex.*;
import java.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.modify.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import com.google.common.base.*;

public class InjectionPointData
{
    private static final Pattern AT_PATTERN;
    private final Map<String, String> args;
    private final IMixinContext context;
    private final MethodNode method;
    private final AnnotationNode parent;
    private final String at;
    private final String type;
    private final InjectionPoint.Selector selector;
    private final String target;
    private final String slice;
    private final int ordinal;
    private final int opcode;
    private final String id;
    
    public InjectionPointData(final IMixinContext a1, final MethodNode a2, final AnnotationNode a3, final String a4, final List<String> a5, final String a6, final String a7, final int a8, final int a9, final String a10) {
        super();
        this.args = new HashMap<String, String>();
        this.context = a1;
        this.method = a2;
        this.parent = a3;
        this.at = a4;
        this.target = a6;
        this.slice = Strings.nullToEmpty(a7);
        this.ordinal = Math.max(-1, a8);
        this.opcode = a9;
        this.id = a10;
        this.parseArgs(a5);
        this.args.put("target", a6);
        this.args.put("ordinal", String.valueOf(a8));
        this.args.put("opcode", String.valueOf(a9));
        final Matcher v1 = InjectionPointData.AT_PATTERN.matcher(a4);
        this.type = parseType(v1, a4);
        this.selector = parseSelector(v1);
    }
    
    private void parseArgs(final List<String> v-1) {
        if (v-1 == null) {
            return;
        }
        for (final String v1 : v-1) {
            if (v1 != null) {
                final int a1 = v1.indexOf(61);
                if (a1 > -1) {
                    this.args.put(v1.substring(0, a1), v1.substring(a1 + 1));
                }
                else {
                    this.args.put(v1, "");
                }
            }
        }
    }
    
    public String getAt() {
        return this.at;
    }
    
    public String getType() {
        return this.type;
    }
    
    public InjectionPoint.Selector getSelector() {
        return this.selector;
    }
    
    public IMixinContext getContext() {
        return this.context;
    }
    
    public MethodNode getMethod() {
        return this.method;
    }
    
    public Type getMethodReturnType() {
        return Type.getReturnType(this.method.desc);
    }
    
    public AnnotationNode getParent() {
        return this.parent;
    }
    
    public String getSlice() {
        return this.slice;
    }
    
    public LocalVariableDiscriminator getLocalVariableDiscriminator() {
        return LocalVariableDiscriminator.parse(this.parent);
    }
    
    public String get(final String a1, final String a2) {
        final String v1 = this.args.get(a1);
        return (v1 != null) ? v1 : a2;
    }
    
    public int get(final String a1, final int a2) {
        return parseInt(this.get(a1, String.valueOf(a2)), a2);
    }
    
    public boolean get(final String a1, final boolean a2) {
        return parseBoolean(this.get(a1, String.valueOf(a2)), a2);
    }
    
    public MemberInfo get(final String v2) {
        try {
            return MemberInfo.parseAndValidate(this.get(v2, ""), this.context);
        }
        catch (InvalidMemberDescriptorException a1) {
            throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", new Object[] { this.at, v2, this.target, InjectionInfo.describeInjector(this.context, this.parent, this.method) });
        }
    }
    
    public MemberInfo getTarget() {
        try {
            return MemberInfo.parseAndValidate(this.target, this.context);
        }
        catch (InvalidMemberDescriptorException v1) {
            throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\") descriptor \"%s\" on %s", new Object[] { this.at, this.target, InjectionInfo.describeInjector(this.context, this.parent, this.method) });
        }
    }
    
    public int getOrdinal() {
        return this.ordinal;
    }
    
    public int getOpcode() {
        return this.opcode;
    }
    
    public int getOpcode(final int a1) {
        return (this.opcode > 0) ? this.opcode : a1;
    }
    
    public int getOpcode(final int v1, final int... v2) {
        for (final int a1 : v2) {
            if (this.opcode == a1) {
                return this.opcode;
            }
        }
        return v1;
    }
    
    public String getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return this.type;
    }
    
    private static Pattern createPattern() {
        return Pattern.compile(String.format("^([^:]+):?(%s)?$", Joiner.on('|').join((Object[])InjectionPoint.Selector.values())));
    }
    
    public static String parseType(final String a1) {
        final Matcher v1 = InjectionPointData.AT_PATTERN.matcher(a1);
        return parseType(v1, a1);
    }
    
    private static String parseType(final Matcher a1, final String a2) {
        return a1.matches() ? a1.group(1) : a2;
    }
    
    private static InjectionPoint.Selector parseSelector(final Matcher a1) {
        return (a1.matches() && a1.group(2) != null) ? InjectionPoint.Selector.valueOf(a1.group(2)) : InjectionPoint.Selector.DEFAULT;
    }
    
    private static int parseInt(final String a2, final int v1) {
        try {
            return Integer.parseInt(a2);
        }
        catch (Exception a3) {
            return v1;
        }
    }
    
    private static boolean parseBoolean(final String a2, final boolean v1) {
        try {
            return Boolean.parseBoolean(a2);
        }
        catch (Exception a3) {
            return v1;
        }
    }
    
    static {
        AT_PATTERN = createPattern();
    }
}
