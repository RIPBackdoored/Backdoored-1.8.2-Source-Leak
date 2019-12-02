package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public class Method extends Member
{
    private final List<FrameData> frames;
    private boolean isAccessor;
    final /* synthetic */ ClassInfo this$0;
    
    public Method(final ClassInfo a1, final Member a2) {
        this.this$0 = a1;
        super(a2);
        this.frames = ((a2 instanceof Method) ? ((Method)a2).frames : null);
    }
    
    public Method(final ClassInfo a1, final MethodNode a2) {
        this(a1, a2, false);
        this.setUnique(Annotations.getVisible(a2, Unique.class) != null);
        this.isAccessor = (Annotations.getSingleVisible(a2, Accessor.class, Invoker.class) != null);
    }
    
    public Method(final ClassInfo a1, final MethodNode a2, final boolean a3) {
        this.this$0 = a1;
        super(Type.METHOD, a2.name, a2.desc, a2.access, a3);
        this.frames = this.gatherFrames(a2);
        this.setUnique(Annotations.getVisible(a2, Unique.class) != null);
        this.isAccessor = (Annotations.getSingleVisible(a2, Accessor.class, Invoker.class) != null);
    }
    
    public Method(final ClassInfo a1, final String a2, final String a3) {
        this.this$0 = a1;
        super(Type.METHOD, a2, a3, 1, false);
        this.frames = null;
    }
    
    public Method(final ClassInfo a1, final String a2, final String a3, final int a4) {
        this.this$0 = a1;
        super(Type.METHOD, a2, a3, a4, false);
        this.frames = null;
    }
    
    public Method(final ClassInfo a1, final String a2, final String a3, final int a4, final boolean a5) {
        this.this$0 = a1;
        super(Type.METHOD, a2, a3, a4, a5);
        this.frames = null;
    }
    
    private List<FrameData> gatherFrames(final MethodNode v-1) {
        final List<FrameData> v0 = new ArrayList<FrameData>();
        for (final AbstractInsnNode a1 : v-1.instructions) {
            if (a1 instanceof FrameNode) {
                v0.add(new FrameData(v-1.instructions.indexOf(a1), (FrameNode)a1));
            }
        }
        return v0;
    }
    
    public List<FrameData> getFrames() {
        return this.frames;
    }
    
    @Override
    public ClassInfo getOwner() {
        return this.this$0;
    }
    
    public boolean isAccessor() {
        return this.isAccessor;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof Method && super.equals(a1);
    }
    
    @Override
    public /* bridge */ String toString() {
        return super.toString();
    }
    
    @Override
    public /* bridge */ int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public /* bridge */ boolean equals(final String a1, final String a2) {
        return super.equals(a1, a2);
    }
    
    @Override
    public /* bridge */ String remapTo(final String a1) {
        return super.remapTo(a1);
    }
    
    @Override
    public /* bridge */ String renameTo(final String a1) {
        return super.renameTo(a1);
    }
    
    @Override
    public /* bridge */ int getAccess() {
        return super.getAccess();
    }
    
    @Override
    public /* bridge */ ClassInfo getImplementor() {
        return super.getImplementor();
    }
    
    @Override
    public /* bridge */ boolean matchesFlags(final int a1) {
        return super.matchesFlags(a1);
    }
    
    @Override
    public /* bridge */ void setDecoratedFinal(final boolean a1, final boolean a2) {
        super.setDecoratedFinal(a1, a2);
    }
    
    @Override
    public /* bridge */ boolean isDecoratedMutable() {
        return super.isDecoratedMutable();
    }
    
    @Override
    public /* bridge */ boolean isDecoratedFinal() {
        return super.isDecoratedFinal();
    }
    
    @Override
    public /* bridge */ void setUnique(final boolean unique) {
        super.setUnique(unique);
    }
    
    @Override
    public /* bridge */ boolean isUnique() {
        return super.isUnique();
    }
    
    @Override
    public /* bridge */ boolean isSynthetic() {
        return super.isSynthetic();
    }
    
    @Override
    public /* bridge */ boolean isFinal() {
        return super.isFinal();
    }
    
    @Override
    public /* bridge */ boolean isAbstract() {
        return super.isAbstract();
    }
    
    @Override
    public /* bridge */ boolean isStatic() {
        return super.isStatic();
    }
    
    @Override
    public /* bridge */ boolean isPrivate() {
        return super.isPrivate();
    }
    
    @Override
    public /* bridge */ boolean isRemapped() {
        return super.isRemapped();
    }
    
    @Override
    public /* bridge */ boolean isRenamed() {
        return super.isRenamed();
    }
    
    @Override
    public /* bridge */ boolean isInjected() {
        return super.isInjected();
    }
    
    @Override
    public /* bridge */ String getDesc() {
        return super.getDesc();
    }
    
    @Override
    public /* bridge */ String getOriginalDesc() {
        return super.getOriginalDesc();
    }
    
    @Override
    public /* bridge */ String getName() {
        return super.getName();
    }
    
    @Override
    public /* bridge */ String getOriginalName() {
        return super.getOriginalName();
    }
}
