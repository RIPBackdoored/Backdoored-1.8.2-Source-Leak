package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class SearchDeveloperMetadataResponse extends GenericJson
{
    @Key
    private List<MatchedDeveloperMetadata> matchedDeveloperMetadata;
    
    public SearchDeveloperMetadataResponse() {
        super();
    }
    
    public List<MatchedDeveloperMetadata> getMatchedDeveloperMetadata() {
        return this.matchedDeveloperMetadata;
    }
    
    public SearchDeveloperMetadataResponse setMatchedDeveloperMetadata(final List<MatchedDeveloperMetadata> matchedDeveloperMetadata) {
        this.matchedDeveloperMetadata = matchedDeveloperMetadata;
        return this;
    }
    
    @Override
    public SearchDeveloperMetadataResponse set(final String a1, final Object a2) {
        return (SearchDeveloperMetadataResponse)super.set(a1, a2);
    }
    
    @Override
    public SearchDeveloperMetadataResponse clone() {
        return (SearchDeveloperMetadataResponse)super.clone();
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
    
    static {
        Data.nullOf(MatchedDeveloperMetadata.class);
    }
}
