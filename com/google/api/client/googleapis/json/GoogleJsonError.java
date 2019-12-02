package com.google.api.client.googleapis.json;

import com.google.api.client.http.*;
import com.google.api.client.json.*;
import java.util.*;
import java.io.*;
import com.google.api.client.util.*;

public class GoogleJsonError extends GenericJson
{
    @Key
    private List<ErrorInfo> errors;
    @Key
    private int code;
    @Key
    private String message;
    
    public GoogleJsonError() {
        super();
    }
    
    public static GoogleJsonError parse(final JsonFactory a1, final HttpResponse a2) throws IOException {
        final JsonObjectParser v1 = new JsonObjectParser.Builder(a1).setWrapperKeys(Collections.singleton("error")).build();
        return v1.parseAndClose(a2.getContent(), a2.getContentCharset(), GoogleJsonError.class);
    }
    
    public final List<ErrorInfo> getErrors() {
        return this.errors;
    }
    
    public final void setErrors(final List<ErrorInfo> a1) {
        this.errors = a1;
    }
    
    public final int getCode() {
        return this.code;
    }
    
    public final void setCode(final int a1) {
        this.code = a1;
    }
    
    public final String getMessage() {
        return this.message;
    }
    
    public final void setMessage(final String a1) {
        this.message = a1;
    }
    
    @Override
    public GoogleJsonError set(final String a1, final Object a2) {
        return (GoogleJsonError)super.set(a1, a2);
    }
    
    @Override
    public GoogleJsonError clone() {
        return (GoogleJsonError)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
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
    
    static {
        Data.nullOf(ErrorInfo.class);
    }
    
    public static class ErrorInfo extends GenericJson
    {
        @Key
        private String domain;
        @Key
        private String reason;
        @Key
        private String message;
        @Key
        private String location;
        @Key
        private String locationType;
        
        public ErrorInfo() {
            super();
        }
        
        public final String getDomain() {
            return this.domain;
        }
        
        public final void setDomain(final String a1) {
            this.domain = a1;
        }
        
        public final String getReason() {
            return this.reason;
        }
        
        public final void setReason(final String a1) {
            this.reason = a1;
        }
        
        public final String getMessage() {
            return this.message;
        }
        
        public final void setMessage(final String a1) {
            this.message = a1;
        }
        
        public final String getLocation() {
            return this.location;
        }
        
        public final void setLocation(final String a1) {
            this.location = a1;
        }
        
        public final String getLocationType() {
            return this.locationType;
        }
        
        public final void setLocationType(final String a1) {
            this.locationType = a1;
        }
        
        @Override
        public ErrorInfo set(final String a1, final Object a2) {
            return (ErrorInfo)super.set(a1, a2);
        }
        
        @Override
        public ErrorInfo clone() {
            return (ErrorInfo)super.clone();
        }
        
        @Override
        public /* bridge */ GenericJson set(final String a1, final Object a2) {
            return this.set(a1, a2);
        }
        
        @Override
        public /* bridge */ GenericJson clone() {
            return this.clone();
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
}
