package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class Append extends SheetsRequest<AppendValuesResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values/{range}:append";
    @Key
    private String spreadsheetId;
    @Key
    private String range;
    @Key
    private String responseValueRenderOption;
    @Key
    private String insertDataOption;
    @Key
    private String valueInputOption;
    @Key
    private String responseDateTimeRenderOption;
    @Key
    private Boolean includeValuesInResponse;
    final /* synthetic */ Values this$2;
    
    protected Append(final Values this$2, final String a1, final String a2, final ValueRange valueRange) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values/{range}:append", valueRange, AppendValuesResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
        this.range = Preconditions.checkNotNull(a2, (Object)"Required parameter range must be specified.");
    }
    
    @Override
    public Append set$Xgafv(final String s) {
        return (Append)super.set$Xgafv(s);
    }
    
    @Override
    public Append setAccessToken(final String accessToken) {
        return (Append)super.setAccessToken(accessToken);
    }
    
    @Override
    public Append setAlt(final String alt) {
        return (Append)super.setAlt(alt);
    }
    
    @Override
    public Append setCallback(final String callback) {
        return (Append)super.setCallback(callback);
    }
    
    @Override
    public Append setFields(final String fields) {
        return (Append)super.setFields(fields);
    }
    
    @Override
    public Append setKey(final String key) {
        return (Append)super.setKey(key);
    }
    
    @Override
    public Append setOauthToken(final String oauthToken) {
        return (Append)super.setOauthToken(oauthToken);
    }
    
    @Override
    public Append setPrettyPrint(final Boolean prettyPrint) {
        return (Append)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public Append setQuotaUser(final String quotaUser) {
        return (Append)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public Append setUploadType(final String uploadType) {
        return (Append)super.setUploadType(uploadType);
    }
    
    @Override
    public Append setUploadProtocol(final String uploadProtocol) {
        return (Append)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public Append setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    public String getRange() {
        return this.range;
    }
    
    public Append setRange(final String range) {
        this.range = range;
        return this;
    }
    
    public String getResponseValueRenderOption() {
        return this.responseValueRenderOption;
    }
    
    public Append setResponseValueRenderOption(final String responseValueRenderOption) {
        this.responseValueRenderOption = responseValueRenderOption;
        return this;
    }
    
    public String getInsertDataOption() {
        return this.insertDataOption;
    }
    
    public Append setInsertDataOption(final String insertDataOption) {
        this.insertDataOption = insertDataOption;
        return this;
    }
    
    public String getValueInputOption() {
        return this.valueInputOption;
    }
    
    public Append setValueInputOption(final String valueInputOption) {
        this.valueInputOption = valueInputOption;
        return this;
    }
    
    public String getResponseDateTimeRenderOption() {
        return this.responseDateTimeRenderOption;
    }
    
    public Append setResponseDateTimeRenderOption(final String responseDateTimeRenderOption) {
        this.responseDateTimeRenderOption = responseDateTimeRenderOption;
        return this;
    }
    
    public Boolean getIncludeValuesInResponse() {
        return this.includeValuesInResponse;
    }
    
    public Append setIncludeValuesInResponse(final Boolean includeValuesInResponse) {
        this.includeValuesInResponse = includeValuesInResponse;
        return this;
    }
    
    @Override
    public Append set(final String s, final Object o) {
        return (Append)super.set(s, o);
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
