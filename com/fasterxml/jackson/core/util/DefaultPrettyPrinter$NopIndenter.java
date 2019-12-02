package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.*;
import java.io.*;

public static class NopIndenter implements Indenter, Serializable
{
    public static final NopIndenter instance;
    
    public NopIndenter() {
        super();
    }
    
    @Override
    public void writeIndentation(final JsonGenerator a1, final int a2) throws IOException {
    }
    
    @Override
    public boolean isInline() {
        return true;
    }
    
    static {
        instance = new NopIndenter();
    }
}
