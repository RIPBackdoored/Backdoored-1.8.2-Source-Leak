package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class BatchClearByDataFilter extends SheetsRequest<BatchClearValuesByDataFilterResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values:batchClearByDataFilter";
    @Key
    private String spreadsheetId;
    final /* synthetic */ Values this$2;
    
    protected BatchClearByDataFilter(final Values this$2, final String a1, final BatchClearValuesByDataFilterRequest batchClearValuesByDataFilterRequest) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values:batchClearByDataFilter", batchClearValuesByDataFilterRequest, BatchClearValuesByDataFilterResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
    }
    
    @Override
    public BatchClearByDataFilter set$Xgafv(final String s) {
        return (BatchClearByDataFilter)super.set$Xgafv(s);
    }
    
    @Override
    public BatchClearByDataFilter setAccessToken(final String accessToken) {
        return (BatchClearByDataFilter)super.setAccessToken(accessToken);
    }
    
    @Override
    public BatchClearByDataFilter setAlt(final String alt) {
        return (BatchClearByDataFilter)super.setAlt(alt);
    }
    
    @Override
    public BatchClearByDataFilter setCallback(final String callback) {
        return (BatchClearByDataFilter)super.setCallback(callback);
    }
    
    @Override
    public BatchClearByDataFilter setFields(final String fields) {
        return (BatchClearByDataFilter)super.setFields(fields);
    }
    
    @Override
    public BatchClearByDataFilter setKey(final String key) {
        return (BatchClearByDataFilter)super.setKey(key);
    }
    
    @Override
    public BatchClearByDataFilter setOauthToken(final String oauthToken) {
        return (BatchClearByDataFilter)super.setOauthToken(oauthToken);
    }
    
    @Override
    public BatchClearByDataFilter setPrettyPrint(final Boolean prettyPrint) {
        return (BatchClearByDataFilter)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public BatchClearByDataFilter setQuotaUser(final String quotaUser) {
        return (BatchClearByDataFilter)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public BatchClearByDataFilter setUploadType(final String uploadType) {
        return (BatchClearByDataFilter)super.setUploadType(uploadType);
    }
    
    @Override
    public BatchClearByDataFilter setUploadProtocol(final String uploadProtocol) {
        return (BatchClearByDataFilter)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchClearByDataFilter setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public BatchClearByDataFilter set(final String s, final Object o) {
        return (BatchClearByDataFilter)super.set(s, o);
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
