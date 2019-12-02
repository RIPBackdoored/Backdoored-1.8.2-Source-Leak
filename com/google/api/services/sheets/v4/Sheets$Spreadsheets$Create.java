package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class Create extends SheetsRequest<Spreadsheet>
{
    private static final String REST_PATH = "v4/spreadsheets";
    final /* synthetic */ Spreadsheets this$1;
    
    protected Create(final Spreadsheets this$1, final Spreadsheet spreadsheet) {
        this.this$1 = this$1;
        super(this$1.this$0, "POST", "v4/spreadsheets", spreadsheet, Spreadsheet.class);
    }
    
    @Override
    public Create set$Xgafv(final String s) {
        return (Create)super.set$Xgafv(s);
    }
    
    @Override
    public Create setAccessToken(final String accessToken) {
        return (Create)super.setAccessToken(accessToken);
    }
    
    @Override
    public Create setAlt(final String alt) {
        return (Create)super.setAlt(alt);
    }
    
    @Override
    public Create setCallback(final String callback) {
        return (Create)super.setCallback(callback);
    }
    
    @Override
    public Create setFields(final String fields) {
        return (Create)super.setFields(fields);
    }
    
    @Override
    public Create setKey(final String key) {
        return (Create)super.setKey(key);
    }
    
    @Override
    public Create setOauthToken(final String oauthToken) {
        return (Create)super.setOauthToken(oauthToken);
    }
    
    @Override
    public Create setPrettyPrint(final Boolean prettyPrint) {
        return (Create)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public Create setQuotaUser(final String quotaUser) {
        return (Create)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public Create setUploadType(final String uploadType) {
        return (Create)super.setUploadType(uploadType);
    }
    
    @Override
    public Create setUploadProtocol(final String uploadProtocol) {
        return (Create)super.setUploadProtocol(uploadProtocol);
    }
    
    @Override
    public Create set(final String s, final Object o) {
        return (Create)super.set(s, o);
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
