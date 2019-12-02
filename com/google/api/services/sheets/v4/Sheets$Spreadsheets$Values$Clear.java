package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class Clear extends SheetsRequest<ClearValuesResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values/{range}:clear";
    @Key
    private String spreadsheetId;
    @Key
    private String range;
    final /* synthetic */ Values this$2;
    
    protected Clear(final Values this$2, final String a1, final String a2, final ClearValuesRequest clearValuesRequest) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values/{range}:clear", clearValuesRequest, ClearValuesResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
        this.range = Preconditions.checkNotNull(a2, (Object)"Required parameter range must be specified.");
    }
    
    @Override
    public Clear set$Xgafv(final String s) {
        return (Clear)super.set$Xgafv(s);
    }
    
    @Override
    public Clear setAccessToken(final String accessToken) {
        return (Clear)super.setAccessToken(accessToken);
    }
    
    @Override
    public Clear setAlt(final String alt) {
        return (Clear)super.setAlt(alt);
    }
    
    @Override
    public Clear setCallback(final String callback) {
        return (Clear)super.setCallback(callback);
    }
    
    @Override
    public Clear setFields(final String fields) {
        return (Clear)super.setFields(fields);
    }
    
    @Override
    public Clear setKey(final String key) {
        return (Clear)super.setKey(key);
    }
    
    @Override
    public Clear setOauthToken(final String oauthToken) {
        return (Clear)super.setOauthToken(oauthToken);
    }
    
    @Override
    public Clear setPrettyPrint(final Boolean prettyPrint) {
        return (Clear)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public Clear setQuotaUser(final String quotaUser) {
        return (Clear)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public Clear setUploadType(final String uploadType) {
        return (Clear)super.setUploadType(uploadType);
    }
    
    @Override
    public Clear setUploadProtocol(final String uploadProtocol) {
        return (Clear)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public Clear setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    public String getRange() {
        return this.range;
    }
    
    public Clear setRange(final String range) {
        this.range = range;
        return this;
    }
    
    @Override
    public Clear set(final String s, final Object o) {
        return (Clear)super.set(s, o);
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
