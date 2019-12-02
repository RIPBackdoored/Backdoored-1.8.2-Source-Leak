package com.google.api.services.sheets.v4;

import com.google.api.services.sheets.v4.model.*;
import java.util.*;
import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.util.*;

public class BatchGet extends SheetsRequest<BatchGetValuesResponse>
{
    private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values:batchGet";
    @Key
    private String spreadsheetId;
    @Key
    private String valueRenderOption;
    @Key
    private String dateTimeRenderOption;
    @Key
    private List<String> ranges;
    @Key
    private String majorDimension;
    final /* synthetic */ Values this$2;
    
    protected BatchGet(final Values this$2, final String a1) {
        this.this$2 = this$2;
        super(this$2.this$1.this$0, "GET", "v4/spreadsheets/{spreadsheetId}/values:batchGet", (Object)null, BatchGetValuesResponse.class);
        this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
    }
    
    public HttpResponse executeUsingHead() throws IOException {
        return super.executeUsingHead();
    }
    
    public HttpRequest buildHttpRequestUsingHead() throws IOException {
        return super.buildHttpRequestUsingHead();
    }
    
    @Override
    public BatchGet set$Xgafv(final String s) {
        return (BatchGet)super.set$Xgafv(s);
    }
    
    @Override
    public BatchGet setAccessToken(final String accessToken) {
        return (BatchGet)super.setAccessToken(accessToken);
    }
    
    @Override
    public BatchGet setAlt(final String alt) {
        return (BatchGet)super.setAlt(alt);
    }
    
    @Override
    public BatchGet setCallback(final String callback) {
        return (BatchGet)super.setCallback(callback);
    }
    
    @Override
    public BatchGet setFields(final String fields) {
        return (BatchGet)super.setFields(fields);
    }
    
    @Override
    public BatchGet setKey(final String key) {
        return (BatchGet)super.setKey(key);
    }
    
    @Override
    public BatchGet setOauthToken(final String oauthToken) {
        return (BatchGet)super.setOauthToken(oauthToken);
    }
    
    @Override
    public BatchGet setPrettyPrint(final Boolean prettyPrint) {
        return (BatchGet)super.setPrettyPrint(prettyPrint);
    }
    
    @Override
    public BatchGet setQuotaUser(final String quotaUser) {
        return (BatchGet)super.setQuotaUser(quotaUser);
    }
    
    @Override
    public BatchGet setUploadType(final String uploadType) {
        return (BatchGet)super.setUploadType(uploadType);
    }
    
    @Override
    public BatchGet setUploadProtocol(final String uploadProtocol) {
        return (BatchGet)super.setUploadProtocol(uploadProtocol);
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchGet setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    public String getValueRenderOption() {
        return this.valueRenderOption;
    }
    
    public BatchGet setValueRenderOption(final String valueRenderOption) {
        this.valueRenderOption = valueRenderOption;
        return this;
    }
    
    public String getDateTimeRenderOption() {
        return this.dateTimeRenderOption;
    }
    
    public BatchGet setDateTimeRenderOption(final String dateTimeRenderOption) {
        this.dateTimeRenderOption = dateTimeRenderOption;
        return this;
    }
    
    public List<String> getRanges() {
        return this.ranges;
    }
    
    public BatchGet setRanges(final List<String> ranges) {
        this.ranges = ranges;
        return this;
    }
    
    public String getMajorDimension() {
        return this.majorDimension;
    }
    
    public BatchGet setMajorDimension(final String majorDimension) {
        this.majorDimension = majorDimension;
        return this;
    }
    
    @Override
    public BatchGet set(final String s, final Object o) {
        return (BatchGet)super.set(s, o);
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
