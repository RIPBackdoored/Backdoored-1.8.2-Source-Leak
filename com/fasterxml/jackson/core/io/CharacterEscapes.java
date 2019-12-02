package com.fasterxml.jackson.core.io;

import java.io.*;
import com.fasterxml.jackson.core.*;
import java.util.*;

public abstract class CharacterEscapes implements Serializable
{
    public static final int ESCAPE_NONE = 0;
    public static final int ESCAPE_STANDARD = -1;
    public static final int ESCAPE_CUSTOM = -2;
    
    public CharacterEscapes() {
        super();
    }
    
    public abstract int[] getEscapeCodesForAscii();
    
    public abstract SerializableString getEscapeSequence(final int p0);
    
    public static int[] standardAsciiEscapesForJSON() {
        final int[] v1 = CharTypes.get7BitOutputEscapes();
        return Arrays.copyOf(v1, v1.length);
    }
}
