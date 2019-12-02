package org.spongepowered.asm.mixin.struct;

import java.util.*;

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
