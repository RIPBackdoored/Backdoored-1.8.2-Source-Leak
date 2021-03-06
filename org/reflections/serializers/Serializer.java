package org.reflections.serializers;

import org.reflections.*;
import java.io.*;

public interface Serializer
{
    Reflections read(final InputStream p0);
    
    File save(final Reflections p0, final String p1);
    
    String toString(final Reflections p0);
}
