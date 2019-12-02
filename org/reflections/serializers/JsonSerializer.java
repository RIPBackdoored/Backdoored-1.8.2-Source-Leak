package org.reflections.serializers;

import org.reflections.*;
import org.reflections.util.*;
import java.nio.charset.*;
import com.google.common.io.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;

public class JsonSerializer implements Serializer
{
    private Gson gson;
    
    public JsonSerializer() {
        super();
    }
    
    @Override
    public Reflections read(final InputStream a1) {
        return this.getGson().fromJson(new InputStreamReader(a1), Reflections.class);
    }
    
    @Override
    public File save(final Reflections v2, final String v3) {
        try {
            final File a1 = Utils.prepareFile(v3);
            Files.write((CharSequence)this.toString(v2), a1, Charset.defaultCharset());
            return a1;
        }
        catch (IOException a2) {
            throw new RuntimeException(a2);
        }
    }
    
    @Override
    public String toString(final Reflections a1) {
        return this.getGson().toJson(a1);
    }
    
    private Gson getGson() {
        if (this.gson == null) {
            this.gson = new GsonBuilder().registerTypeAdapter(Multimap.class, new com.google.gson.JsonSerializer<Multimap>() {
                final /* synthetic */ JsonSerializer this$0;
                
                JsonSerializer$2() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public JsonElement serialize(final Multimap a1, final Type a2, final JsonSerializationContext a3) {
                    return a3.serialize(a1.asMap());
                }
                
                @Override
                public /* bridge */ JsonElement serialize(final Object o, final Type a2, final JsonSerializationContext a3) {
                    return this.serialize((Multimap)o, a2, a3);
                }
            }).registerTypeAdapter(Multimap.class, new JsonDeserializer<Multimap>() {
                final /* synthetic */ JsonSerializer this$0;
                
                JsonSerializer$1() {
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
            }).setPrettyPrinting().create();
        }
        return this.gson;
    }
}
