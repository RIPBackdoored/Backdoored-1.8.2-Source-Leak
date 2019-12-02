package org.reflections.vfs;

import java.io.*;

public interface File
{
    String getName();
    
    String getRelativePath();
    
    InputStream openInputStream() throws IOException;
}
