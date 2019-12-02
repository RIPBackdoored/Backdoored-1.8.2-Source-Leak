package com.google.api.client.json.jackson2;

static class InstanceHolder
{
    static final JacksonFactory INSTANCE;
    
    InstanceHolder() {
        super();
    }
    
    static {
        INSTANCE = new JacksonFactory();
    }
}
