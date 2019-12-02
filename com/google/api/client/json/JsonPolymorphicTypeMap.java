package com.google.api.client.json;

import java.lang.annotation.*;
import com.google.api.client.util.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Beta
public @interface JsonPolymorphicTypeMap {
    TypeDef[] typeDefinitions();
    
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeDef {
        String key();
        
        Class<?> ref();
    }
}
