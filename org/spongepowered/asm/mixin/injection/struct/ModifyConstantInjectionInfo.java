package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.points.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.invoke.*;
import com.google.common.base.*;
import org.spongepowered.asm.mixin.injection.*;

public class ModifyConstantInjectionInfo extends InjectionInfo
{
    private static final String CONSTANT_ANNOTATION_CLASS;
    
    public ModifyConstantInjectionInfo(final MixinTargetContext a1, final MethodNode a2, final AnnotationNode a3) {
        super(a1, a2, a3, "constant");
    }
    
    @Override
    protected List<AnnotationNode> readInjectionPoints(final String v2) {
        List<AnnotationNode> v3 = super.readInjectionPoints(v2);
        if (v3.isEmpty()) {
            final AnnotationNode a1 = new AnnotationNode(ModifyConstantInjectionInfo.CONSTANT_ANNOTATION_CLASS);
            a1.visit("log", Boolean.TRUE);
            v3 = ImmutableList.of(a1);
        }
        return v3;
    }
    
    @Override
    protected void parseInjectionPoints(final List<AnnotationNode> v2) {
        final Type v3 = Type.getReturnType(this.method.desc);
        for (final AnnotationNode a1 : v2) {
            this.injectionPoints.add(new BeforeConstant(this.getContext(), a1, v3.getDescriptor()));
        }
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode a1) {
        return new ModifyConstantInjector(this);
    }
    
    @Override
    protected String getDescription() {
        return "Constant modifier method";
    }
    
    @Override
    public String getSliceId(final String a1) {
        return Strings.nullToEmpty(a1);
    }
    
    static {
        CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');
    }
}
