package org.reflections.serializers;

import java.lang.reflect.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;

class JsonSerializer$1 implements JsonDeserializer<Multimap> {
    final /* synthetic */ JsonSerializer this$0;
    
    JsonSerializer$1(final JsonSerializer a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public Multimap deserialize(final JsonElement v1, final Type v2, final JsonDeserializationContext v3) throws JsonParseException {
        final SetMultimap<String, String> v4 = (SetMultimap<String, String>)Multimaps.newSetMultimap((Map)new HashMap(), (Supplier)new Supplier<Set<String>>() {
            final /* synthetic */ JsonSerializer$1 this$1;
            
            JsonSerializer$1$1() {
                this.this$1 = a1;
                super();
            }
            
            @Override
            public Set<String> get() {
                return (Set<String>)Sets.newHashSet();
            }
            
            @Override
            public /* bridge */ Object get() {
                return this.get();
            }
        });
        for (final Map.Entry<String, JsonElement> a2 : ((JsonObject)v1).entrySet()) {
            for (final JsonElement a3 : a2.getValue()) {
                v4.get((Object)a2.getKey()).add(a3.getAsString());
            }
        }
        return (Multimap)v4;
    }
    
    @Override
    public /* bridge */ Object deserialize(final JsonElement v1, final Type v2, final JsonDeserializationContext v3) throws JsonParseException {
        return this.deserialize(v1, v2, v3);
    }
}