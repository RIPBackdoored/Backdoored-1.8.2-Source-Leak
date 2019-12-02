package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;

public class AccessorGeneratorMethodProxy extends AccessorGenerator
{
    private final MethodNode targetMethod;
    private final Type[] argTypes;
    private final Type returnType;
    private final boolean isInstanceMethod;
    
    public AccessorGeneratorMethodProxy(final AccessorInfo a1) {
        super(a1);
        this.targetMethod = a1.getTargetMethod();
        this.argTypes = a1.getArgTypes();
        this.returnType = a1.getReturnType();
        this.isInstanceMethod = !Bytecode.hasFlag(this.targetMethod, 8);
    }
    
    @Override
    public MethodNode generate() {
        final int v1 = Bytecode.getArgsSize(this.argTypes) + this.returnType.getSize() + (this.isInstanceMethod ? 1 : 0);
        final MethodNode v2 = this.createMethod(v1, v1);
        if (this.isInstanceMethod) {
            v2.instructions.add(new VarInsnNode(25, 0));
        }
        Bytecode.loadArgs(this.argTypes, v2.instructions, this.isInstanceMethod ? 1 : 0);
        final boolean v3 = Bytecode.hasFlag(this.targetMethod, 2);
        final int v4 = this.isInstanceMethod ? (v3 ? 183 : 182) : 184;
        v2.instructions.add(new MethodInsnNode(v4, this.info.getClassNode().name, this.targetMethod.name, this.targetMethod.desc, false));
        v2.instructions.add(new InsnNode(this.returnType.getOpcode(172)));
        return v2;
    }
}
