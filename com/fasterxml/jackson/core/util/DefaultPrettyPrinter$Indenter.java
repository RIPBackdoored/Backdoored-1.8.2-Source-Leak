package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.*;
import java.io.*;

public interface Indenter
{
    void writeIndentation(final JsonGenerator p0, final int p1) throws IOException;
    
    boolean isInline();
}
