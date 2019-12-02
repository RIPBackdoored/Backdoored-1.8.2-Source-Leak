package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.commons.*;
import org.spongepowered.asm.service.*;
import java.io.*;

static class InnerClassInfo extends Remapper
{
    private final String name;
    private final String originalName;
    private final MixinInfo owner;
    private final MixinTargetContext target;
    private final String ownerName;
    private final String targetName;
    
    InnerClassInfo(final String a1, final String a2, final MixinInfo a3, final MixinTargetContext a4) {
        super();
        this.name = a1;
        this.originalName = a2;
        this.owner = a3;
        this.ownerName = a3.getClassRef();
        this.target = a4;
        this.targetName = a4.getTargetClassRef();
    }
    
    String getName() {
        return this.name;
    }
    
    String getOriginalName() {
        return this.originalName;
    }
    
    MixinInfo getOwner() {
        return this.owner;
    }
    
    MixinTargetContext getTarget() {
        return this.target;
    }
    
    String getOwnerName() {
        return this.ownerName;
    }
    
    String getTargetName() {
        return this.targetName;
    }
    
    byte[] getClassBytes() throws ClassNotFoundException, IOException {
        return MixinService.getService().getBytecodeProvider().getClassBytes(this.originalName, true);
    }
    
    @Override
    public String mapMethodName(final String a3, final String v1, final String v2) {
        if (this.ownerName.equalsIgnoreCase(a3)) {
            final ClassInfo.Method a4 = this.owner.getClassInfo().findMethod(v1, v2, 10);
            if (a4 != null) {
                return a4.getName();
            }
        }
        return super.mapMethodName(a3, v1, v2);
    }
    
    @Override
    public String map(final String a1) {
        if (this.originalName.equals(a1)) {
            return this.name;
        }
        if (this.ownerName.equals(a1)) {
            return this.targetName;
        }
        return a1;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
