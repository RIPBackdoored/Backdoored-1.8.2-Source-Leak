package org.reflections.vfs;

import java.io.*;

public class SystemFile implements Vfs.File
{
    private final SystemDir root;
    private final java.io.File file;
    
    public SystemFile(final SystemDir a1, final java.io.File a2) {
        super();
        this.root = a1;
        this.file = a2;
    }
    
    @Override
    public String getName() {
        return this.file.getName();
    }
    
    @Override
    public String getRelativePath() {
        final String v1 = this.file.getPath().replace("\\", "/");
        if (v1.startsWith(this.root.getPath())) {
            return v1.substring(this.root.getPath().length() + 1);
        }
        return null;
    }
    
    @Override
    public InputStream openInputStream() {
        try {
            return new FileInputStream(this.file);
        }
        catch (FileNotFoundException v1) {
            throw new RuntimeException(v1);
        }
    }
    
    @Override
    public String toString() {
        return this.file.toString();
    }
}
