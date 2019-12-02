package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.throwables.*;

class MixinApplicatorInterface extends MixinApplicatorStandard
{
    MixinApplicatorInterface(final TargetClassContext a1) {
        super(a1);
    }
    
    @Override
    protected void applyInterfaces(final MixinTargetContext v2) {
        for (final String a1 : v2.getInterfaces()) {
            if (!this.targetClass.name.equals(a1) && !this.targetClass.interfaces.contains(a1)) {
                this.targetClass.interfaces.add(a1);
                v2.getTargetClassInfo().addInterface(a1);
            }
        }
    }
    
    @Override
    protected void applyFields(final MixinTargetContext v-1) {
        for (final Map.Entry<FieldNode, ClassInfo.Field> v1 : v-1.getShadowFields()) {
            final FieldNode a1 = v1.getKey();
            this.logger.error("Ignoring redundant @Shadow field {}:{} in {}", new Object[] { a1.name, a1.desc, v-1 });
        }
        this.mergeNewFields(v-1);
    }
    
    @Override
    protected void applyInitialisers(final MixinTargetContext a1) {
    }
    
    @Override
    protected void prepareInjections(final MixinTargetContext v-3) {
        for (final MethodNode a2 : this.targetClass.methods) {
            try {
                final InjectionInfo a1 = InjectionInfo.parse(v-3, a2);
                if (a1 != null) {
                    throw new InvalidInterfaceMixinException(v-3, a1 + " is not supported on interface mixin method " + a2.name);
                }
                continue;
            }
            catch (InvalidInjectionException v2) {
                final String v1 = (v2.getInjectionInfo() != null) ? v2.getInjectionInfo().toString() : "Injection";
                throw new InvalidInterfaceMixinException(v-3, v1 + " is not supported in interface mixin");
            }
        }
    }
    
    @Override
    protected void applyInjections(final MixinTargetContext a1) {
    }
}
