package com.google.api.client.json.rpc2;

import com.google.api.client.util.*;

@Beta
public class JsonRpcRequest extends GenericData
{
    @Key
    private final String jsonrpc = "2.0";
    @Key
    private Object id;
    @Key
    private String method;
    @Key
    private Object params;
    
    public JsonRpcRequest() {
        super();
    }
    
    public String getVersion() {
        return "2.0";
    }
    
    public Object getId() {
        return this.id;
    }
    
    public void setId(final Object a1) {
        this.id = a1;
    }
    
    public String getMethod() {
        return this.method;
    }
    
    public void setMethod(final String a1) {
        this.method = a1;
    }
    
    public Object getParameters() {
        return this.params;
    }
    
    public void setParameters(final Object a1) {
        this.params = a1;
    }
    
    @Override
    public JsonRpcRequest set(final String a1, final Object a2) {
        return (JsonRpcRequest)super.set(a1, a2);
    }
    
    @Override
    public JsonRpcRequest clone() {
        return (JsonRpcRequest)super.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
