package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("LOAD")
public class BeforeLoadLocal extends ModifyVariableInjector.ContextualInjectionPoint
{
    private final Type returnType;
    private final LocalVariableDiscriminator discriminator;
    private final int opcode;
    private final int ordinal;
    private boolean opcodeAfter;
    
    protected BeforeLoadLocal(final InjectionPointData a1) {
        this(a1, 21, false);
    }
    
    protected BeforeLoadLocal(final InjectionPointData a1, final int a2, final boolean a3) {
        super(a1.getContext());
        this.returnType = a1.getMethodReturnType();
        this.discriminator = a1.getLocalVariableDiscriminator();
        this.opcode = a1.getOpcode(this.returnType.getOpcode(a2));
        this.ordinal = a1.getOrdinal();
        this.opcodeAfter = a3;
    }
    
    @Override
    boolean find(final Target v-3, final Collection<AbstractInsnNode> v-2) {
        final SearchState searchState = new SearchState(this.ordinal, this.discriminator.printLVT());
        for (final AbstractInsnNode v2 : v-3.method.instructions) {
            if (searchState.isPendingCheck()) {
                final int a1 = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), v-3, v2);
                searchState.check(v-2, v2, a1);
            }
            else {
                if (!(v2 instanceof VarInsnNode) || v2.getOpcode() != this.opcode || (this.ordinal != -1 && searchState.success())) {
                    continue;
                }
                searchState.register((VarInsnNode)v2);
                if (this.opcodeAfter) {
                    searchState.setPendingCheck();
                }
                else {
                    final int a2 = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), v-3, v2);
                    searchState.check(v-2, v2, a2);
                }
            }
        }
        return searchState.success();
    }
    
    @Override
    public /* bridge */ boolean find(final String a1, final InsnList a2, final Collection a3) {
        return super.find(a1, a2, a3);
    }
    
    static class SearchState
    {
        private final boolean print;
        private final int targetOrdinal;
        private int ordinal;
        private boolean pendingCheck;
        private boolean found;
        private VarInsnNode varNode;
        
        SearchState(final int a1, final boolean a2) {
            super();
            this.ordinal = 0;
            this.pendingCheck = false;
            this.found = false;
            this.targetOrdinal = a1;
            this.print = a2;
        }
        
        boolean success() {
            return this.found;
        }
        
        boolean isPendingCheck() {
            return this.pendingCheck;
        }
        
        void setPendingCheck() {
            this.pendingCheck = true;
        }
        
        void register(final VarInsnNode a1) {
            this.varNode = a1;
        }
        
        void check(final Collection<AbstractInsnNode> a1, final AbstractInsnNode a2, final int a3) {
            this.pendingCheck = false;
            if (a3 != this.varNode.var && (a3 > -2 || !this.print)) {
                return;
            }
            if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
                a1.add(a2);
                this.found = true;
            }
            ++this.ordinal;
            this.varNode = null;
        }
    }
}
