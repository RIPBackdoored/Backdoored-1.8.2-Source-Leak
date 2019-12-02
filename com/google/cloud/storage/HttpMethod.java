package com.google.cloud.storage;

import com.google.api.core.*;
import com.google.cloud.*;

public final class HttpMethod extends StringEnumValue
{
    private static final long serialVersionUID = -1394461645628254471L;
    private static final ApiFunction<String, HttpMethod> CONSTRUCTOR;
    private static final StringEnumType<HttpMethod> type;
    public static final HttpMethod GET;
    public static final HttpMethod HEAD;
    public static final HttpMethod PUT;
    public static final HttpMethod POST;
    public static final HttpMethod DELETE;
    public static final HttpMethod OPTIONS;
    
    private HttpMethod(final String a1) {
        super(a1);
    }
    
    public static HttpMethod valueOfStrict(final String a1) {
        return (HttpMethod)HttpMethod.type.valueOfStrict(a1);
    }
    
    public static HttpMethod valueOf(final String a1) {
        return (HttpMethod)HttpMethod.type.valueOf(a1);
    }
    
    public static HttpMethod[] values() {
        return (HttpMethod[])HttpMethod.type.values();
    }
    
    HttpMethod(final String a1, final HttpMethod$1 a2) {
        this(a1);
    }
    
    static {
        CONSTRUCTOR = (ApiFunction)new ApiFunction<String, HttpMethod>() {
            HttpMethod$1() {
                super();
            }
            
            public HttpMethod apply(final String a1) {
                return new HttpMethod(a1, null);
            }
            
            public /* bridge */ Object apply(final Object o) {
                return this.apply((String)o);
            }
        };
        type = new StringEnumType((Class)HttpMethod.class, (ApiFunction)HttpMethod.CONSTRUCTOR);
        GET = (HttpMethod)HttpMethod.type.createAndRegister("GET");
        HEAD = (HttpMethod)HttpMethod.type.createAndRegister("HEAD");
        PUT = (HttpMethod)HttpMethod.type.createAndRegister("PUT");
        POST = (HttpMethod)HttpMethod.type.createAndRegister("POST");
        DELETE = (HttpMethod)HttpMethod.type.createAndRegister("DELETE");
        OPTIONS = (HttpMethod)HttpMethod.type.createAndRegister("OPTIONS");
    }
}
