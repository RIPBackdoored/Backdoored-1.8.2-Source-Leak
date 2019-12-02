package com.google.api.services.sheets.v4;

import com.google.api.client.googleapis.services.*;
import java.io.*;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.util.*;

public class SheetsOperations
{
    final /* synthetic */ Spreadsheets this$1;
    
    public SheetsOperations(final Spreadsheets this$1) {
        this.this$1 = this$1;
        super();
    }
    
    public CopyTo copyTo(final String s, final Integer n, final CopySheetToAnotherSpreadsheetRequest copySheetToAnotherSpreadsheetRequest) throws IOException {
        final CopyTo copyTo = new CopyTo(s, n, copySheetToAnotherSpreadsheetRequest);
        this.this$1.this$0.initialize(copyTo);
        return copyTo;
    }
    
    public class CopyTo extends SheetsRequest<SheetProperties>
    {
        private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/sheets/{sheetId}:copyTo";
        @Key
        private String spreadsheetId;
        @Key
        private Integer sheetId;
        final /* synthetic */ SheetsOperations this$2;
        
        protected CopyTo(final SheetsOperations this$2, final String a1, final Integer a2, final CopySheetToAnotherSpreadsheetRequest copySheetToAnotherSpreadsheetRequest) {
            this.this$2 = this$2;
            super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/sheets/{sheetId}:copyTo", copySheetToAnotherSpreadsheetRequest, SheetProperties.class);
            this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
            this.sheetId = Preconditions.checkNotNull(a2, (Object)"Required parameter sheetId must be specified.");
        }
        
        @Override
        public CopyTo set$Xgafv(final String s) {
            return (CopyTo)super.set$Xgafv(s);
        }
        
        @Override
        public CopyTo setAccessToken(final String accessToken) {
            return (CopyTo)super.setAccessToken(accessToken);
        }
        
        @Override
        public CopyTo setAlt(final String alt) {
            return (CopyTo)super.setAlt(alt);
        }
        
        @Override
        public CopyTo setCallback(final String callback) {
            return (CopyTo)super.setCallback(callback);
        }
        
        @Override
        public CopyTo setFields(final String fields) {
            return (CopyTo)super.setFields(fields);
        }
        
        @Override
        public CopyTo setKey(final String key) {
            return (CopyTo)super.setKey(key);
        }
        
        @Override
        public CopyTo setOauthToken(final String oauthToken) {
            return (CopyTo)super.setOauthToken(oauthToken);
        }
        
        @Override
        public CopyTo setPrettyPrint(final Boolean prettyPrint) {
            return (CopyTo)super.setPrettyPrint(prettyPrint);
        }
        
        @Override
        public CopyTo setQuotaUser(final String quotaUser) {
            return (CopyTo)super.setQuotaUser(quotaUser);
        }
        
        @Override
        public CopyTo setUploadType(final String uploadType) {
            return (CopyTo)super.setUploadType(uploadType);
        }
        
        @Override
        public CopyTo setUploadProtocol(final String uploadProtocol) {
            return (CopyTo)super.setUploadProtocol(uploadProtocol);
        }
        
        public String getSpreadsheetId() {
            return this.spreadsheetId;
        }
        
        public CopyTo setSpreadsheetId(final String spreadsheetId) {
            this.spreadsheetId = spreadsheetId;
            return this;
        }
        
        public Integer getSheetId() {
            return this.sheetId;
        }
        
        public CopyTo setSheetId(final Integer sheetId) {
            this.sheetId = sheetId;
            return this;
        }
        
        @Override
        public CopyTo set(final String s, final Object o) {
            return (CopyTo)super.set(s, o);
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
}
