package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.refmap.*;
import com.google.common.base.*;

public final class MemberInfo
{
    public final String owner;
    public final String name;
    public final String desc;
    public final boolean matchAll;
    private final boolean forceField;
    private final String unparsed;
    
    public MemberInfo(final String a1, final boolean a2) {
        this(a1, null, null, a2);
    }
    
    public MemberInfo(final String a1, final String a2, final boolean a3) {
        this(a1, a2, null, a3);
    }
    
    public MemberInfo(final String a1, final String a2, final String a3) {
        this(a1, a2, a3, false);
    }
    
    public MemberInfo(final String a1, final String a2, final String a3, final boolean a4) {
        this(a1, a2, a3, a4, null);
    }
    
    public MemberInfo(final String a1, final String a2, final String a3, final boolean a4, final String a5) {
        super();
        if (a2 != null && a2.contains(".")) {
            throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format");
        }
        this.owner = a2;
        this.name = a1;
        this.desc = a3;
        this.matchAll = a4;
        this.forceField = false;
        this.unparsed = a5;
    }
    
    public MemberInfo(final AbstractInsnNode v0) {
        super();
        this.matchAll = false;
        this.forceField = false;
        this.unparsed = null;
        if (v0 instanceof MethodInsnNode) {
            final MethodInsnNode a1 = (MethodInsnNode)v0;
            this.owner = a1.owner;
            this.name = a1.name;
            this.desc = a1.desc;
        }
        else {
            if (!(v0 instanceof FieldInsnNode)) {
                throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
            }
            final FieldInsnNode v = (FieldInsnNode)v0;
            this.owner = v.owner;
            this.name = v.name;
            this.desc = v.desc;
        }
    }
    
    public MemberInfo(final IMapping<?> a1) {
        super();
        this.owner = a1.getOwner();
        this.name = a1.getSimpleName();
        this.desc = a1.getDesc();
        this.matchAll = false;
        this.forceField = (a1.getType() == IMapping.Type.FIELD);
        this.unparsed = null;
    }
    
    private MemberInfo(final MemberInfo a1, final MappingMethod a2, final boolean a3) {
        super();
        this.owner = (a3 ? a2.getOwner() : a1.owner);
        this.name = a2.getSimpleName();
        this.desc = a2.getDesc();
        this.matchAll = a1.matchAll;
        this.forceField = false;
        this.unparsed = null;
    }
    
    private MemberInfo(final MemberInfo a1, final String a2) {
        super();
        this.owner = a2;
        this.name = a1.name;
        this.desc = a1.desc;
        this.matchAll = a1.matchAll;
        this.forceField = a1.forceField;
        this.unparsed = null;
    }
    
    @Override
    public String toString() {
        final String v1 = (this.owner != null) ? ("L" + this.owner + ";") : "";
        final String v2 = (this.name != null) ? this.name : "";
        final String v3 = this.matchAll ? "*" : "";
        final String v4 = (this.desc != null) ? this.desc : "";
        final String v5 = v4.startsWith("(") ? "" : ((this.desc != null) ? ":" : "");
        return v1 + v2 + v3 + v5 + v4;
    }
    
    @Deprecated
    public String toSrg() {
        if (!this.isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference to SRG mapping");
        }
        if (this.desc.startsWith("(")) {
            return this.owner + "/" + this.name + " " + this.desc;
        }
        return this.owner + "/" + this.name;
    }
    
    public String toDescriptor() {
        if (this.desc == null) {
            return "";
        }
        return new SignaturePrinter(this).setFullyQualified(true).toDescriptor();
    }
    
    public String toCtorType() {
        if (this.unparsed == null) {
            return null;
        }
        final String v1 = this.getReturnType();
        if (v1 != null) {
            return v1;
        }
        if (this.owner != null) {
            return this.owner;
        }
        if (this.name != null && this.desc == null) {
            return this.name;
        }
        return (this.desc != null) ? this.desc : this.unparsed;
    }
    
    public String toCtorDesc() {
        if (this.desc != null && this.desc.startsWith("(") && this.desc.indexOf(41) > -1) {
            return this.desc.substring(0, this.desc.indexOf(41) + 1) + "V";
        }
        return null;
    }
    
    public String getReturnType() {
        if (this.desc == null || this.desc.indexOf(41) == -1 || this.desc.indexOf(40) != 0) {
            return null;
        }
        final String v1 = this.desc.substring(this.desc.indexOf(41) + 1);
        if (v1.startsWith("L") && v1.endsWith(";")) {
            return v1.substring(1, v1.length() - 1);
        }
        return v1;
    }
    
    public IMapping<?> asMapping() {
        return (IMapping<?>)(this.isField() ? this.asFieldMapping() : this.asMethodMapping());
    }
    
    public MappingMethod asMethodMapping() {
        if (!this.isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping");
        }
        if (this.isField()) {
            throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping");
        }
        return new MappingMethod(this.owner, this.name, this.desc);
    }
    
    public MappingField asFieldMapping() {
        if (!this.isField()) {
            throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping");
        }
        return new MappingField(this.owner, this.name, this.desc);
    }
    
    public boolean isFullyQualified() {
        return this.owner != null && this.name != null && this.desc != null;
    }
    
    public boolean isField() {
        return this.forceField || (this.desc != null && !this.desc.startsWith("("));
    }
    
    public boolean isConstructor() {
        return "<init>".equals(this.name);
    }
    
    public boolean isClassInitialiser() {
        return "<clinit>".equals(this.name);
    }
    
    public boolean isInitialiser() {
        return this.isConstructor() || this.isClassInitialiser();
    }
    
    public MemberInfo validate() throws InvalidMemberDescriptorException {
        if (this.owner != null) {
            if (!this.owner.matches("(?i)^[\\w\\p{Sc}/]+$")) {
                throw new InvalidMemberDescriptorException("Invalid owner: " + this.owner);
            }
            if (this.unparsed != null && this.unparsed.lastIndexOf(46) > 0 && this.owner.startsWith("L")) {
                throw new InvalidMemberDescriptorException("Malformed owner: " + this.owner + " If you are seeing this message unexpectedly and the owner appears to be correct, replace the owner descriptor with formal type L" + this.owner + "; to suppress this error");
            }
        }
        if (this.name != null && !this.name.matches("(?i)^<?[\\w\\p{Sc}]+>?$")) {
            throw new InvalidMemberDescriptorException("Invalid name: " + this.name);
        }
        if (this.desc != null) {
            if (!this.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$")) {
                throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
            }
            if (this.isField()) {
                if (!this.desc.equals(Type.getType(this.desc).getDescriptor())) {
                    throw new InvalidMemberDescriptorException("Invalid field type in descriptor: " + this.desc);
                }
            }
            else {
                try {
                    Type.getArgumentTypes(this.desc);
                }
                catch (Exception v3) {
                    throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
                }
                final String v1 = this.desc.substring(this.desc.indexOf(41) + 1);
                try {
                    final Type v2 = Type.getType(v1);
                    if (!v1.equals(v2.getDescriptor())) {
                        throw new InvalidMemberDescriptorException("Invalid return type \"" + v1 + "\" in descriptor: " + this.desc);
                    }
                }
                catch (Exception v4) {
                    throw new InvalidMemberDescriptorException("Invalid return type \"" + v1 + "\" in descriptor: " + this.desc);
                }
            }
        }
        return this;
    }
    
    public boolean matches(final String a1, final String a2, final String a3) {
        return this.matches(a1, a2, a3, 0);
    }
    
    public boolean matches(final String a1, final String a2, final String a3, final int a4) {
        return (this.desc == null || a3 == null || this.desc.equals(a3)) && (this.name == null || a2 == null || this.name.equals(a2)) && (this.owner == null || a1 == null || this.owner.equals(a1)) && (a4 == 0 || this.matchAll);
    }
    
    public boolean matches(final String a1, final String a2) {
        return this.matches(a1, a2, 0);
    }
    
    public boolean matches(final String a1, final String a2, final int a3) {
        return (this.name == null || this.name.equals(a1)) && (this.desc == null || (a2 != null && a2.equals(this.desc))) && (a3 == 0 || this.matchAll);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == null || a1.getClass() != MemberInfo.class) {
            return false;
        }
        final MemberInfo v1 = (MemberInfo)a1;
        return this.matchAll == v1.matchAll && this.forceField == v1.forceField && Objects.equal(this.owner, v1.owner) && Objects.equal(this.name, v1.name) && Objects.equal(this.desc, v1.desc);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.matchAll, this.owner, this.name, this.desc);
    }
    
    public MemberInfo move(final String a1) {
        if ((a1 == null && this.owner == null) || (a1 != null && a1.equals(this.owner))) {
            return this;
        }
        return new MemberInfo(this, a1);
    }
    
    public MemberInfo transform(final String a1) {
        if ((a1 == null && this.desc == null) || (a1 != null && a1.equals(this.desc))) {
            return this;
        }
        return new MemberInfo(this.name, this.owner, a1, this.matchAll);
    }
    
    public MemberInfo remapUsing(final MappingMethod a1, final boolean a2) {
        return new MemberInfo(this, a1, a2);
    }
    
    public static MemberInfo parseAndValidate(final String a1) throws InvalidMemberDescriptorException {
        return parse(a1, null, null).validate();
    }
    
    public static MemberInfo parseAndValidate(final String a1, final IMixinContext a2) throws InvalidMemberDescriptorException {
        return parse(a1, a2.getReferenceMapper(), a2.getClassRef()).validate();
    }
    
    public static MemberInfo parse(final String a1) {
        return parse(a1, null, null);
    }
    
    public static MemberInfo parse(final String a1, final IMixinContext a2) {
        return parse(a1, a2.getReferenceMapper(), a2.getClassRef());
    }
    
    private static MemberInfo parse(final String a1, final IReferenceMapper a2, final String a3) {
        String v1 = null;
        String v2 = null;
        String v3 = Strings.nullToEmpty(a1).replaceAll("\\s", "");
        if (a2 != null) {
            v3 = a2.remap(a3, v3);
        }
        final int v4 = v3.lastIndexOf(46);
        final int v5 = v3.indexOf(59);
        if (v4 > -1) {
            v2 = v3.substring(0, v4).replace('.', '/');
            v3 = v3.substring(v4 + 1);
        }
        else if (v5 > -1 && v3.startsWith("L")) {
            v2 = v3.substring(1, v5).replace('.', '/');
            v3 = v3.substring(v5 + 1);
        }
        final int v6 = v3.indexOf(40);
        final int v7 = v3.indexOf(58);
        if (v6 > -1) {
            v1 = v3.substring(v6);
            v3 = v3.substring(0, v6);
        }
        else if (v7 > -1) {
            v1 = v3.substring(v7 + 1);
            v3 = v3.substring(0, v7);
        }
        if ((v3.indexOf(47) > -1 || v3.indexOf(46) > -1) && v2 == null) {
            v2 = v3;
            v3 = "";
        }
        final boolean v8 = v3.endsWith("*");
        if (v8) {
            v3 = v3.substring(0, v3.length() - 1);
        }
        if (v3.isEmpty()) {
            v3 = null;
        }
        return new MemberInfo(v3, v2, v1, v8, a1);
    }
    
    public static MemberInfo fromMapping(final IMapping<?> a1) {
        return new MemberInfo(a1);
    }
}
