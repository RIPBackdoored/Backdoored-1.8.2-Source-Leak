package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class BatchUpdateByDataFilter extends SheetsRequest<BatchUpdateValuesByDataFilterResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values:batchUpdateByDataFilter";
    @Key
    private String spreadsheetId;
    final /* synthetic */ Values this$2;
    
    protected BatchUpdateByDataFilter(final Values this$2, final String a1, final BatchUpdateValuesByDataFilterRequest batchUpdateValuesByDataFilterRequest) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values:batchUpdateByDataFilter", batchUpdateValuesByDataFilterRequest, BatchUpdateValuesByDataFilterResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
    }
    
    @Override
    public BatchUpdateByDataFilter set$Xgafv(final String s) {
        return (BatchUpdateByDataFilter)super.set$Xgafv(s);
    }
    
    @Override
    public BatchUpdateByDataFilter setAccessToken(final String accessToken) {
        return (BatchUpdateByDataFilter)super.setAccessToken(accessToken);
    }
    
    @Override
    public BatchUpdateByDataFilter setAlt(final String alt) {
        return (BatchUpdateByDataFilter)super.setAlt(alt);
    }
    
    @Override
    public BatchUpdateByDataFilter setCallback(final String callback) {
        return (BatchUpdateByDataFilter)super.setCallback(callback);
    }
    
    @Override
    public BatchUpdateByDataFilter setFields(final String fields) {
        return (BatchUpdateByDataFilter)super.setFields(fields);
    }
    
    @Override
    public BatchUpdateByDataFilter setKey(final String key) {
        return (BatchUpdateByDataFilter)super.setKey(key);
    }
    
    @Override
    public BatchUpdateByDataFilter setOauthToken(final String oauthToken) {
        return (BatchUpdateByDataFilter)super.setOauthToken(oauthToken);
    }
    
    @Override
    public BatchUpdateByDataFilter setPrettyPrint(final Boolean prettyPrint) {
        return (BatchUpdateByDataFilter)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public BatchUpdateByDataFilter setQuotaUser(final String quotaUser) {
        return (BatchUpdateByDataFilter)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public BatchUpdateByDataFilter setUploadType(final String uploadType) {
        return (BatchUpdateByDataFilter)super.setUploadType(uploadType);
    }
    
    @Override
    public BatchUpdateByDataFilter setUploadProtocol(final String uploadProtocol) {
        return (BatchUpdateByDataFilter)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchUpdateByDataFilter setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public BatchUpdateByDataFilter set(final String s, final Object o) {
        return (BatchUpdateByDataFilter)super.set(s, o);
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
