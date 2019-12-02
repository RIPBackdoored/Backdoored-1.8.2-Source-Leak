package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.*;

public class AccessorGeneratorFieldSetter extends AccessorGeneratorField
{
    public AccessorGeneratorFieldSetter(final AccessorInfo a1) {
        super(a1);
    }
    
    @Override
    public MethodNode generate() {
        final int v1 = this.isInstanceField ? 1 : 0;
        final int v2 = v1 + this.targetType.getSize();
        final int v3 = v1 + this.targetType.getSize();
        final MethodNode v4 = this.createMethod(v2, v3);
        if (this.isInstanceField) {
            v4.instructions.add(new VarInsnNode(25, 0));
        }
        v4.instructions.add(new VarInsnNode(this.targetType.getOpcode(21), v1));
        final int v5 = this.isInstanceField ? 181 : 179;
        v4.instructions.add(new FieldInsnNode(v5, this.info.getClassNode().name, this.targetField.name, this.targetField.desc));
        v4.instructions.add(new InsnNode(177));
        return v4;
    }
}
