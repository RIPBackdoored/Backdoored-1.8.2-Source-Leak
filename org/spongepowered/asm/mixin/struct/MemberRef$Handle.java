package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.util.*;

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
