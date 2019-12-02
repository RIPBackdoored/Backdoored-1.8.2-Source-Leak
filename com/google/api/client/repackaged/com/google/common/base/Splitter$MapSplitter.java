package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;

@Beta
public static final class MapSplitter
{
    private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
    private final Splitter outerSplitter;
    private final Splitter entrySplitter;
    
    private MapSplitter(final Splitter a1, final Splitter a2) {
        super();
        this.outerSplitter = a1;
        this.entrySplitter = Preconditions.checkNotNull(a2);
    }
    
    public Map<String, String> split(final CharSequence v-4) {
        final Map<String, String> map = new LinkedHashMap<String, String>();
        for (final String s : this.outerSplitter.split(v-4)) {
            final Iterator<String> a1 = (Iterator<String>)Splitter.access$000(this.entrySplitter, (CharSequence)s);
            Preconditions.checkArgument(a1.hasNext(), "Chunk [%s] is not a valid entry", s);
            final String v1 = a1.next();
            Preconditions.checkArgument(!map.containsKey(v1), "Duplicate key [%s] found.", v1);
            Preconditions.checkArgument(a1.hasNext(), "Chunk [%s] is not a valid entry", s);
            final String v2 = a1.next();
            map.put(v1, v2);
            Preconditions.checkArgument(!a1.hasNext(), "Chunk [%s] is not a valid entry", s);
        }
        return Collections.unmodifiableMap((Map<? extends String, ? extends String>)map);
    }
    
    MapSplitter(final Splitter a1, final Splitter a2, final Splitter$1 a3) {
        this(a1, a2);
    }
}
