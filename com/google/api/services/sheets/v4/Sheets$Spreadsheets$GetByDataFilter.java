package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class GetByDataFilter extends SheetsRequest<Spreadsheet>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}:getByDataFilter";
    @Key
    private String spreadsheetId;
    final /* synthetic */ Spreadsheets this$1;
    
    protected GetByDataFilter(final Spreadsheets this$1, final String a1, final GetSpreadsheetByDataFilterRequest getSpreadsheetByDataFilterRequest) {
        this.this$1 = this$1;
        super(this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}:getByDataFilter", getSpreadsheetByDataFilterRequest, Spreadsheet.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
    }
    
    @Override
    public GetByDataFilter set$Xgafv(final String s) {
        return (GetByDataFilter)super.set$Xgafv(s);
    }
    
    @Override
    public GetByDataFilter setAccessToken(final String accessToken) {
        return (GetByDataFilter)super.setAccessToken(accessToken);
    }
    
    @Override
    public GetByDataFilter setAlt(final String alt) {
        return (GetByDataFilter)super.setAlt(alt);
    }
    
    @Override
    public GetByDataFilter setCallback(final String callback) {
        return (GetByDataFilter)super.setCallback(callback);
    }
    
    @Override
    public GetByDataFilter setFields(final String fields) {
        return (GetByDataFilter)super.setFields(fields);
    }
    
    @Override
    public GetByDataFilter setKey(final String key) {
        return (GetByDataFilter)super.setKey(key);
    }
    
    @Override
    public GetByDataFilter setOauthToken(final String oauthToken) {
        return (GetByDataFilter)super.setOauthToken(oauthToken);
    }
    
    @Override
    public GetByDataFilter setPrettyPrint(final Boolean prettyPrint) {
        return (GetByDataFilter)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public GetByDataFilter setQuotaUser(final String quotaUser) {
        return (GetByDataFilter)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public GetByDataFilter setUploadType(final String uploadType) {
        return (GetByDataFilter)super.setUploadType(uploadType);
    }
    
    @Override
    public GetByDataFilter setUploadProtocol(final String uploadProtocol) {
        return (GetByDataFilter)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public GetByDataFilter setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public GetByDataFilter set(final String s, final Object o) {
        return (GetByDataFilter)super.set(s, o);
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
