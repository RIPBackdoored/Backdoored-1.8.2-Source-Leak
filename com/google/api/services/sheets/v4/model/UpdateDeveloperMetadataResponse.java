package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class UpdateDeveloperMetadataResponse extends GenericJson
{
    @Key
    private List<DeveloperMetadata> developerMetadata;
    
    public UpdateDeveloperMetadataResponse() {
        super();
    }
    
    public List<DeveloperMetadata> getDeveloperMetadata() {
        return this.developerMetadata;
    }
    
    public UpdateDeveloperMetadataResponse setDeveloperMetadata(final List<DeveloperMetadata> developerMetadata) {
        this.developerMetadata = developerMetadata;
        return this;
    }
    
    @Override
    public UpdateDeveloperMetadataResponse set(final String a1, final Object a2) {
        return (UpdateDeveloperMetadataResponse)super.set(a1, a2);
    }
    
    @Override
    public UpdateDeveloperMetadataResponse clone() {
        return (UpdateDeveloperMetadataResponse)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String s, final Object o) {
        return this.set(s, o);
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
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
