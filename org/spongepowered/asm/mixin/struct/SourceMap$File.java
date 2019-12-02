package org.spongepowered.asm.mixin.struct;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public static class File
{
    public final int id;
    public final int lineOffset;
    public final int size;
    public final String sourceFileName;
    public final String sourceFilePath;
    
    public File(final int a1, final int a2, final int a3, final String a4) {
        this(a1, a2, a3, a4, null);
    }
    
    public File(final int a1, final int a2, final int a3, final String a4, final String a5) {
        super();
        this.id = a1;
        this.lineOffset = a2;
        this.size = a3;
        this.sourceFileName = a4;
        this.sourceFilePath = a5;
    }
    
    public void applyOffset(final ClassNode v2) {
        for (final MethodNode a1 : v2.methods) {
            this.applyOffset(a1);
        }
    }
    
    public void applyOffset(final MethodNode v0) {
        for (final AbstractInsnNode a1 : v0.instructions) {
            if (a1 instanceof LineNumberNode) {
                final LineNumberNode lineNumberNode = (LineNumberNode)a1;
                lineNumberNode.line += this.lineOffset - 1;
            }
        }
    }
    
    void appendFile(final StringBuilder a1) {
        if (this.sourceFilePath != null) {
            a1.append("+ ").append(this.id).append(" ").append(this.sourceFileName).append("\n");
            a1.append(this.sourceFilePath).append("\n");
        }
        else {
            a1.append(this.id).append(" ").append(this.sourceFileName).append("\n");
        }
    }
    
    public void appendLines(final StringBuilder a1) {
        a1.append("1#").append(this.id).append(",").append(this.size).append(":").append(this.lineOffset).append("\n");
    }
}
