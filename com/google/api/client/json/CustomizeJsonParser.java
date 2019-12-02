package com.google.api.client.json;

import com.google.api.client.util.*;
import java.lang.reflect.*;
import java.util.*;

@Beta
public class CustomizeJsonParser
{
    public CustomizeJsonParser() {
        super();
    }
    
    public boolean stopAt(final Object a1, final String a2) {
        return false;
    }
    
    public void handleUnrecognizedKey(final Object a1, final String a2) {
    }
    
    public Collection<Object> newInstanceForArray(final Object a1, final Field a2) {
        return null;
    }
    
    public Object newInstanceForObject(final Object a1, final Class<?> a2) {
        return null;
    }
}
