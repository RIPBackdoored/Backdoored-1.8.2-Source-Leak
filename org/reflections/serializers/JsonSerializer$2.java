package org.reflections.serializers;

import com.google.common.collect.*;
import java.lang.reflect.*;
import com.google.gson.*;

class JsonSerializer$2 implements JsonSerializer<Multimap> {
    final /* synthetic */ JsonSerializer this$0;
    
    JsonSerializer$2(final JsonSerializer a1) {
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
}