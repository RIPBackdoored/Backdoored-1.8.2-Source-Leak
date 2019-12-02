package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.*;

public class AccessorGeneratorFieldGetter extends AccessorGeneratorField
{
    public AccessorGeneratorFieldGetter(final AccessorInfo a1) {
        super(a1);
    }
    
    @Override
    public MethodNode generate() {
        final MethodNode v1 = this.createMethod(this.targetType.getSize(), this.targetType.getSize());
        if (this.isInstanceField) {
            v1.instructions.add(new VarInsnNode(25, 0));
        }
        final int v2 = this.isInstanceField ? 180 : 178;
        v1.instructions.add(new FieldInsnNode(v2, this.info.getClassNode().name, this.targetField.name, this.targetField.desc));
        v1.instructions.add(new InsnNode(this.targetType.getOpcode(172)));
        return v1;
    }
}
