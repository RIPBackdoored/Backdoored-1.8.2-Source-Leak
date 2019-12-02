package org.reflections.vfs;

import java.net.*;

public interface UrlType
{
    boolean matches(final URL p0) throws Exception;
    
    Dir createDir(final URL p0) throws Exception;
}
