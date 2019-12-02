package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;

public abstract class MemberRef
{
    private static final int[] H_OPCODES;
    
    public MemberRef() {
        super();
    }
    
    public abstract boolean isField();
    
    public abstract int getOpcode();
    
    public abstract void setOpcode(final int p0);
    
    public abstract String getOwner();
    
    public abstract void setOwner(final String p0);
    
    public abstract String getName();
    
    public abstract void setName(final String p0);
    
    public abstract String getDesc();
    
    public abstract void setDesc(final String p0);
    
    @Override
    public String toString() {
        final String v1 = Bytecode.getOpcodeName(this.getOpcode());
        return String.format("%s for %s.%s%s%s", v1, this.getOwner(), this.getName(), this.isField() ? ":" : "", this.getDesc());
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (!(a1 instanceof MemberRef)) {
            return false;
        }
        final MemberRef v1 = (MemberRef)a1;
        return this.getOpcode() == v1.getOpcode() && this.getOwner().equals(v1.getOwner()) && this.getName().equals(v1.getName()) && this.getDesc().equals(v1.getDesc());
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    static int opcodeFromTag(final int a1) {
        return (a1 >= 0 && a1 < MemberRef.H_OPCODES.length) ? MemberRef.H_OPCODES[a1] : 0;
    }
    
    static int tagFromOpcode(final int v1) {
        for (int a1 = 1; a1 < MemberRef.H_OPCODES.length; ++a1) {
            if (MemberRef.H_OPCODES[a1] == v1) {
                return a1;
            }
        }
        return 0;
    }
    
    static {
        H_OPCODES = new int[] { 0, 180, 178, 181, 179, 182, 184, 183, 183, 185 };
    }
    
    public static final class Method extends MemberRef
    {
        private static final int OPCODES = 191;
        public final MethodInsnNode insn;
        
        public Method(final MethodInsnNode a1) {
            super();
            this.insn = a1;
        }
        
        @Override
        public boolean isField() {
            return false;
        }
        
        @Override
        public int getOpcode() {
            return this.insn.getOpcode();
        }
        
        @Override
        public void setOpcode(final int a1) {
            if ((a1 & 0xBF) == 0x0) {
                throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(a1));
            }
            this.insn.setOpcode(a1);
        }
        
        @Override
        public String getOwner() {
            return this.insn.owner;
        }
        
        @Override
        public void setOwner(final String a1) {
            this.insn.owner = a1;
        }
        
        @Override
        public String getName() {
            return this.insn.name;
        }
        
        @Override
        public void setName(final String a1) {
            this.insn.name = a1;
        }
        
        @Override
        public String getDesc() {
            return this.insn.desc;
        }
        
        @Override
        public void setDesc(final String a1) {
            this.insn.desc = a1;
        }
    }
    
    public static final class Field extends MemberRef
    {
        private static final int OPCODES = 183;
        public final FieldInsnNode insn;
        
        public Field(final FieldInsnNode a1) {
            super();
            this.insn = a1;
        }
        
        @Override
        public boolean isField() {
            return true;
        }
        
        @Override
        public int getOpcode() {
            return this.insn.getOpcode();
        }
        
        @Override
        public void setOpcode(final int a1) {
            if ((a1 & 0xB7) == 0x0) {
                throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(a1));
            }
            this.insn.setOpcode(a1);
        }
        
        @Override
        public String getOwner() {
            return this.insn.owner;
        }
        
        @Override
        public void setOwner(final String a1) {
            this.insn.owner = a1;
        }
        
        @Override
        public String getName() {
            return this.insn.name;
        }
        
        @Override
        public void setName(final String a1) {
            this.insn.name = a1;
        }
        
        @Override
        public String getDesc() {
            return this.insn.desc;
        }
        
        @Override
        public void setDesc(final String a1) {
            this.insn.desc = a1;
        }
    }
    
    public static final class Handle extends MemberRef
    {
        private org.spongepowered.asm.lib.Handle handle;
        
        public Handle(final org.spongepowered.asm.lib.Handle a1) {
            super();
            this.handle = a1;
        }
        
        public org.spongepowered.asm.lib.Handle getMethodHandle() {
            return this.handle;
        }
        
        @Override
        public boolean isField() {
            switch (this.handle.getTag()) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9: {
                    return false;
                }
                case 1:
                case 2:
                case 3:
                case 4: {
                    return true;
                }
                default: {
                    throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
                }
            }
        }
        
        @Override
        public int getOpcode() {
            final int v1 = MemberRef.opcodeFromTag(this.handle.getTag());
            if (v1 == 0) {
                throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
            }
            return v1;
        }
        
        @Override
        public void setOpcode(final int a1) {
            final int v1 = MemberRef.tagFromOpcode(a1);
            if (v1 == 0) {
                throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(a1) + " for method handle " + this.handle + ".");
            }
            final boolean v2 = v1 == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(v1, this.handle.getOwner(), this.handle.getName(), this.handle.getDesc(), v2);
        }
        
        @Override
        public String getOwner() {
            return this.handle.getOwner();
        }
        
        @Override
        public void setOwner(final String a1) {
            final boolean v1 = this.handle.getTag() == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), a1, this.handle.getName(), this.handle.getDesc(), v1);
        }
        
        @Override
        public String getName() {
            return this.handle.getName();
        }
        
        @Override
        public void setName(final String a1) {
            final boolean v1 = this.handle.getTag() == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), a1, this.handle.getDesc(), v1);
        }
        
        @Override
        public String getDesc() {
            return this.handle.getDesc();
        }
        
        @Override
        public void setDesc(final String a1) {
            final boolean v1 = this.handle.getTag() == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), a1, v1);
        }
    }
}
