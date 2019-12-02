package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class BatchUpdate extends SheetsRequest<BatchUpdateSpreadsheetResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}:batchUpdate";
    @Key
    private String spreadsheetId;
    final /* synthetic */ Spreadsheets this$1;
    
    protected BatchUpdate(final Spreadsheets this$1, final String a1, final BatchUpdateSpreadsheetRequest batchUpdateSpreadsheetRequest) {
        this.this$1 = this$1;
        super(this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}:batchUpdate", batchUpdateSpreadsheetRequest, BatchUpdateSpreadsheetResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
    }
    
    @Override
    public BatchUpdate set$Xgafv(final String s) {
        return (BatchUpdate)super.set$Xgafv(s);
    }
    
    @Override
    public BatchUpdate setAccessToken(final String accessToken) {
        return (BatchUpdate)super.setAccessToken(accessToken);
    }
    
    @Override
    public BatchUpdate setAlt(final String alt) {
        return (BatchUpdate)super.setAlt(alt);
    }
    
    @Override
    public BatchUpdate setCallback(final String callback) {
        return (BatchUpdate)super.setCallback(callback);
    }
    
    @Override
    public BatchUpdate setFields(final String fields) {
        return (BatchUpdate)super.setFields(fields);
    }
    
    @Override
    public BatchUpdate setKey(final String key) {
        return (BatchUpdate)super.setKey(key);
    }
    
    @Override
    public BatchUpdate setOauthToken(final String oauthToken) {
        return (BatchUpdate)super.setOauthToken(oauthToken);
    }
    
    @Override
    public BatchUpdate setPrettyPrint(final Boolean prettyPrint) {
        return (BatchUpdate)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public BatchUpdate setQuotaUser(final String quotaUser) {
        return (BatchUpdate)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public BatchUpdate setUploadType(final String uploadType) {
        return (BatchUpdate)super.setUploadType(uploadType);
    }
    
    @Override
    public BatchUpdate setUploadProtocol(final String uploadProtocol) {
        return (BatchUpdate)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchUpdate setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public BatchUpdate set(final String s, final Object o) {
        return (BatchUpdate)super.set(s, o);
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
