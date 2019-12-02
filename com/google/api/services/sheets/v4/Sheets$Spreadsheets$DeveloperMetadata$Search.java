package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class Search extends SheetsRequest<SearchDeveloperMetadataResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/developerMetadata:search";
    @Key
    private String spreadsheetId;
    final /* synthetic */ DeveloperMetadata this$2;
    
    protected Search(final DeveloperMetadata this$2, final String a1, final SearchDeveloperMetadataRequest searchDeveloperMetadataRequest) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/developerMetadata:search", searchDeveloperMetadataRequest, SearchDeveloperMetadataResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
    }
    
    @Override
    public Search set$Xgafv(final String s) {
        return (Search)super.set$Xgafv(s);
    }
    
    @Override
    public Search setAccessToken(final String accessToken) {
        return (Search)super.setAccessToken(accessToken);
    }
    
    @Override
    public Search setAlt(final String alt) {
        return (Search)super.setAlt(alt);
    }
    
    @Override
    public Search setCallback(final String callback) {
        return (Search)super.setCallback(callback);
    }
    
    @Override
    public Search setFields(final String fields) {
        return (Search)super.setFields(fields);
    }
    
    @Override
    public Search setKey(final String key) {
        return (Search)super.setKey(key);
    }
    
    @Override
    public Search setOauthToken(final String oauthToken) {
        return (Search)super.setOauthToken(oauthToken);
    }
    
    @Override
    public Search setPrettyPrint(final Boolean prettyPrint) {
        return (Search)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public Search setQuotaUser(final String quotaUser) {
        return (Search)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public Search setUploadType(final String uploadType) {
        return (Search)super.setUploadType(uploadType);
    }
    
    @Override
    public Search setUploadProtocol(final String uploadProtocol) {
        return (Search)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public Search setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public Search set(final String s, final Object o) {
        return (Search)super.set(s, o);
    }
    
    @Override
    public /* bridge */ SheetsRequest set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ SheetsRequest setUploadProtocol(final String uploadProtocol) {
        return this.setUploadProtocol(uploadProtocol);
    }
    
    @Override
    public /* bridge */ SheetsRequest setUploadType(final String uploadType) {
        return this.setUploadType(uploadType);
    }
    
    @Override
    public /* bridge */ SheetsRequest setQuotaUser(final String quotaUser) {
        return this.setQuotaUser(quotaUser);
    }
    
    @Override
    public /* bridge */ SheetsRequest setPrettyPrint(final Boolean prettyPrint) {
        return this.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public /* bridge */ SheetsRequest setOauthToken(final String oauthToken) {
        return this.setOauthToken(oauthToken);
    }
    
    @Override
    public /* bridge */ SheetsRequest setKey(final String key) {
        return this.setKey(key);
    }
    
    @Override
    public /* bridge */ SheetsRequest setFields(final String fields) {
        return this.setFields(fields);
    }
    
    @Override
    public /* bridge */ SheetsRequest setCallback(final String callback) {
        return this.setCallback(callback);
    }
    
    @Override
    public /* bridge */ SheetsRequest setAlt(final String alt) {
        return this.setAlt(alt);
    }
    
    @Override
    public /* bridge */ SheetsRequest setAccessToken(final String accessToken) {
        return this.setAccessToken(accessToken);
    }
    
    @Override
    public /* bridge */ SheetsRequest set$Xgafv(final String s) {
        return this.set$Xgafv(s);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClientRequest set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
}
