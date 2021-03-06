package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class BatchGetByDataFilter extends SheetsRequest<BatchGetValuesByDataFilterResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values:batchGetByDataFilter";
    @Key
    private String spreadsheetId;
    final /* synthetic */ Values this$2;
    
    protected BatchGetByDataFilter(final Values this$2, final String a1, final BatchGetValuesByDataFilterRequest batchGetValuesByDataFilterRequest) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values:batchGetByDataFilter", batchGetValuesByDataFilterRequest, BatchGetValuesByDataFilterResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
    }
    
    @Override
    public BatchGetByDataFilter set$Xgafv(final String s) {
        return (BatchGetByDataFilter)super.set$Xgafv(s);
    }
    
    @Override
    public BatchGetByDataFilter setAccessToken(final String accessToken) {
        return (BatchGetByDataFilter)super.setAccessToken(accessToken);
    }
    
    @Override
    public BatchGetByDataFilter setAlt(final String alt) {
        return (BatchGetByDataFilter)super.setAlt(alt);
    }
    
    @Override
    public BatchGetByDataFilter setCallback(final String callback) {
        return (BatchGetByDataFilter)super.setCallback(callback);
    }
    
    @Override
    public BatchGetByDataFilter setFields(final String fields) {
        return (BatchGetByDataFilter)super.setFields(fields);
    }
    
    @Override
    public BatchGetByDataFilter setKey(final String key) {
        return (BatchGetByDataFilter)super.setKey(key);
    }
    
    @Override
    public BatchGetByDataFilter setOauthToken(final String oauthToken) {
        return (BatchGetByDataFilter)super.setOauthToken(oauthToken);
    }
    
    @Override
    public BatchGetByDataFilter setPrettyPrint(final Boolean prettyPrint) {
        return (BatchGetByDataFilter)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public BatchGetByDataFilter setQuotaUser(final String quotaUser) {
        return (BatchGetByDataFilter)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public BatchGetByDataFilter setUploadType(final String uploadType) {
        return (BatchGetByDataFilter)super.setUploadType(uploadType);
    }
    
    @Override
    public BatchGetByDataFilter setUploadProtocol(final String uploadProtocol) {
        return (BatchGetByDataFilter)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchGetByDataFilter setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public BatchGetByDataFilter set(final String s, final Object o) {
        return (BatchGetByDataFilter)super.set(s, o);
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
