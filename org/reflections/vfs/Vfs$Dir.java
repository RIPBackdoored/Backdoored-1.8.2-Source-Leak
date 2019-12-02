package org.reflections.vfs;

public interface Dir
{
    String getPath();
    
    Iterable<File> getFiles();
    
    void close();
}
