package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.util.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public class SourceMap
{
    private static final String DEFAULT_STRATUM = "Mixin";
    private static final String NEWLINE = "\n";
    private final String sourceFile;
    private final Map<String, Stratum> strata;
    private int nextLineOffset;
    private String defaultStratum;
    
    public SourceMap(final String a1) {
        super();
        this.strata = new LinkedHashMap<String, Stratum>();
        this.nextLineOffset = 1;
        this.defaultStratum = "Mixin";
        this.sourceFile = a1;
    }
    
    public String getSourceFile() {
        return this.sourceFile;
    }
    
    public String getPseudoGeneratedSourceFile() {
        return this.sourceFile.replace(".java", "$mixin.java");
    }
    
    public File addFile(final ClassNode a1) {
        return this.addFile(this.defaultStratum, a1);
    }
    
    public File addFile(final String a1, final ClassNode a2) {
        return this.addFile(a1, a2.sourceFile, a2.name + ".java", Bytecode.getMaxLineNumber(a2, 500, 50));
    }
    
    public File addFile(final String a1, final String a2, final int a3) {
        return this.addFile(this.defaultStratum, a1, a2, a3);
    }
    
    public File addFile(final String a1, final String a2, final String a3, final int a4) {
        Stratum v1 = this.strata.get(a1);
        if (v1 == null) {
            v1 = new Stratum(a1);
            this.strata.put(a1, v1);
        }
        final File v2 = v1.addFile(this.nextLineOffset, a4, a2, a3);
        this.nextLineOffset += a4;
        return v2;
    }
    
    @Override
    public String toString() {
        final StringBuilder v1 = new StringBuilder();
        this.appendTo(v1);
        return v1.toString();
    }
    
    private void appendTo(final StringBuilder v2) {
        v2.append("SMAP").append("\n");
        v2.append(this.getSourceFile()).append("\n");
        v2.append(this.defaultStratum).append("\n");
        for (final Stratum a1 : this.strata.values()) {
            a1.appendTo(v2);
        }
        v2.append("*E").append("\n");
    }
    
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
    
    static class Stratum
    {
        private static final String STRATUM_MARK = "*S";
        private static final String FILE_MARK = "*F";
        private static final String LINES_MARK = "*L";
        public final String name;
        private final Map<String, File> files;
        
        public Stratum(final String a1) {
            super();
            this.files = new LinkedHashMap<String, File>();
            this.name = a1;
        }
        
        public File addFile(final int a1, final int a2, final String a3, final String a4) {
            File v1 = this.files.get(a4);
            if (v1 == null) {
                v1 = new File(this.files.size() + 1, a1, a2, a3, a4);
                this.files.put(a4, v1);
            }
            return v1;
        }
        
        void appendTo(final StringBuilder v-1) {
            v-1.append("*S").append(" ").append(this.name).append("\n");
            v-1.append("*F").append("\n");
            for (final File a1 : this.files.values()) {
                a1.appendFile(v-1);
            }
            v-1.append("*L").append("\n");
            for (final File v1 : this.files.values()) {
                v1.appendLines(v-1);
            }
        }
    }
}
