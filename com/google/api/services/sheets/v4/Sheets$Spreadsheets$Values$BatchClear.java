package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class BatchClear extends SheetsRequest<BatchClearValuesResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values:batchClear";
    @Key
    private String spreadsheetId;
    final /* synthetic */ Values this$2;
    
    protected BatchClear(final Values this$2, final String a1, final BatchClearValuesRequest batchClearValuesRequest) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values:batchClear", batchClearValuesRequest, BatchClearValuesResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
    }
    
    @Override
    public BatchClear set$Xgafv(final String s) {
        return (BatchClear)super.set$Xgafv(s);
    }
    
    @Override
    public BatchClear setAccessToken(final String accessToken) {
        return (BatchClear)super.setAccessToken(accessToken);
    }
    
    @Override
    public BatchClear setAlt(final String alt) {
        return (BatchClear)super.setAlt(alt);
    }
    
    @Override
    public BatchClear setCallback(final String callback) {
        return (BatchClear)super.setCallback(callback);
    }
    
    @Override
    public BatchClear setFields(final String fields) {
        return (BatchClear)super.setFields(fields);
    }
    
    @Override
    public BatchClear setKey(final String key) {
        return (BatchClear)super.setKey(key);
    }
    
    @Override
    public BatchClear setOauthToken(final String oauthToken) {
        return (BatchClear)super.setOauthToken(oauthToken);
    }
    
    @Override
    public BatchClear setPrettyPrint(final Boolean prettyPrint) {
        return (BatchClear)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public BatchClear setQuotaUser(final String quotaUser) {
        return (BatchClear)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public BatchClear setUploadType(final String uploadType) {
        return (BatchClear)super.setUploadType(uploadType);
    }
    
    @Override
    public BatchClear setUploadProtocol(final String uploadProtocol) {
        return (BatchClear)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchClear setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public BatchClear set(final String s, final Object o) {
        return (BatchClear)super.set(s, o);
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
