package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.*;

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
