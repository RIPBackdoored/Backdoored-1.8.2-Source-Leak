package com.sun.jna;

import java.util.*;

static class AttachOptions extends Structure
{
    public static final List<String> FIELDS;
    public boolean daemon;
    public boolean detach;
    public String name;
    
    AttachOptions() {
        super();
        this.setStringEncoding("utf8");
    }
    
    @Override
    protected List<String> getFieldOrder() {
        return AttachOptions.FIELDS;
    }
    
    static {
        FIELDS = Structure.createFieldsOrder("daemon", "detach", "name");
    }
}
