package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class Update extends SheetsRequest<UpdateValuesResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values/{range}";
    @Key
    private String spreadsheetId;
    @Key
    private String range;
    @Key
    private String responseValueRenderOption;
    @Key
    private String valueInputOption;
    @Key
    private String responseDateTimeRenderOption;
    @Key
    private Boolean includeValuesInResponse;
    final /* synthetic */ Values this$2;
    
    protected Update(final Values this$2, final String a1, final String a2, final ValueRange valueRange) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "PUT", "v4/spreadsheets/{spreadsheetId}/values/{range}", valueRange, UpdateValuesResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
        this.range = Preconditions.checkNotNull(a2, (Object)"Required parameter range must be specified.");
    }
    
    @Override
    public Update set$Xgafv(final String s) {
        return (Update)super.set$Xgafv(s);
    }
    
    @Override
    public Update setAccessToken(final String accessToken) {
        return (Update)super.setAccessToken(accessToken);
    }
    
    @Override
    public Update setAlt(final String alt) {
        return (Update)super.setAlt(alt);
    }
    
    @Override
    public Update setCallback(final String callback) {
        return (Update)super.setCallback(callback);
    }
    
    @Override
    public Update setFields(final String fields) {
        return (Update)super.setFields(fields);
    }
    
    @Override
    public Update setKey(final String key) {
        return (Update)super.setKey(key);
    }
    
    @Override
    public Update setOauthToken(final String oauthToken) {
        return (Update)super.setOauthToken(oauthToken);
    }
    
    @Override
    public Update setPrettyPrint(final Boolean prettyPrint) {
        return (Update)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public Update setQuotaUser(final String quotaUser) {
        return (Update)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public Update setUploadType(final String uploadType) {
        return (Update)super.setUploadType(uploadType);
    }
    
    @Override
    public Update setUploadProtocol(final String uploadProtocol) {
        return (Update)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public Update setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    public String getRange() {
        return this.range;
    }
    
    public Update setRange(final String range) {
        this.range = range;
        return this;
    }
    
    public String getResponseValueRenderOption() {
        return this.responseValueRenderOption;
    }
    
    public Update setResponseValueRenderOption(final String responseValueRenderOption) {
        this.responseValueRenderOption = responseValueRenderOption;
        return this;
    }
    
    public String getValueInputOption() {
        return this.valueInputOption;
    }
    
    public Update setValueInputOption(final String valueInputOption) {
        this.valueInputOption = valueInputOption;
        return this;
    }
    
    public String getResponseDateTimeRenderOption() {
        return this.responseDateTimeRenderOption;
    }
    
    public Update setResponseDateTimeRenderOption(final String responseDateTimeRenderOption) {
        this.responseDateTimeRenderOption = responseDateTimeRenderOption;
        return this;
    }
    
    public Boolean getIncludeValuesInResponse() {
        return this.includeValuesInResponse;
    }
    
    public Update setIncludeValuesInResponse(final Boolean includeValuesInResponse) {
        this.includeValuesInResponse = includeValuesInResponse;
        return this;
    }
    
    @Override
    public Update set(final String s, final Object o) {
        return (Update)super.set(s, o);
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
