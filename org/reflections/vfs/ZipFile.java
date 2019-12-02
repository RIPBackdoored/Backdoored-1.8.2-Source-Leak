package org.reflections.vfs;

import java.util.zip.*;
import java.io.*;

public class ZipFile implements Vfs.File
{
    private final ZipDir root;
    private final ZipEntry entry;
    
    public ZipFile(final ZipDir a1, final ZipEntry a2) {
        super();
        this.root = a1;
        this.entry = a2;
    }
    
    @Override
    public String getName() {
        final String v1 = this.entry.getName();
        return v1.substring(v1.lastIndexOf("/") + 1);
    }
    
    @Override
    public String getRelativePath() {
        return this.entry.getName();
    }
    
    @Override
    public InputStream openInputStream() throws IOException {
        return this.root.jarFile.getInputStream(this.entry);
    }
    
    @Override
    public String toString() {
        return this.root.getPath() + "!" + java.io.File.separatorChar + this.entry.toString();
    }
}
