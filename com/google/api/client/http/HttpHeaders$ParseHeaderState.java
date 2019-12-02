package com.google.api.client.http;

import com.google.api.client.util.*;
import java.lang.reflect.*;
import java.util.*;

private static final class ParseHeaderState
{
    final ArrayValueMap arrayValueMap;
    final StringBuilder logger;
    final ClassInfo classInfo;
    final List<Type> context;
    
    public ParseHeaderState(final HttpHeaders a1, final StringBuilder a2) {
        super();
        final Class<? extends HttpHeaders> v1 = a1.getClass();
        this.context = Arrays.asList(v1);
        this.classInfo = ClassInfo.of(v1, true);
        this.logger = a2;
        this.arrayValueMap = new ArrayValueMap(a1);
    }
    
    void finish() {
        this.arrayValueMap.setValues();
    }
}
