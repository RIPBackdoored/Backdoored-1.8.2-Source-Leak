package com.google.api.services.sheets.v4;

import com.google.api.client.json.*;
import java.io.*;
import com.google.api.client.googleapis.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.util.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.googleapis.services.*;

public class Sheets extends AbstractGoogleJsonClient
{
    public static final String DEFAULT_ROOT_URL = "https://sheets.googleapis.com/";
    public static final String DEFAULT_SERVICE_PATH = "";
    public static final String DEFAULT_BATCH_PATH = "batch";
    public static final String DEFAULT_BASE_URL = "https://sheets.googleapis.com/";
    
    public Sheets(final HttpTransport httpTransport, final JsonFactory jsonFactory, final HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(httpTransport, jsonFactory, httpRequestInitializer));
    }
    
    Sheets(final Builder a1) {
        super(a1);
    }
    
    @Override
    protected void initialize(final AbstractGoogleClientRequest<?> a1) throws IOException {
        super.initialize(a1);
    }
    
    public Spreadsheets spreadsheets() {
        return new Spreadsheets();
    }
    
    static {
        Preconditions.checkState(GoogleUtils.MAJOR_VERSION == 1 && GoogleUtils.MINOR_VERSION >= 15, "You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.25.0 of the Google Sheets API library.", GoogleUtils.VERSION);
    }
    
    public class Spreadsheets
    {
        final /* synthetic */ Sheets this$0;
        
        public Spreadsheets(final Sheets this$0) {
            this.this$0 = this$0;
            super();
        }
        
        public BatchUpdate batchUpdate(final String s, final BatchUpdateSpreadsheetRequest batchUpdateSpreadsheetRequest) throws IOException {
            final BatchUpdate batchUpdate = new BatchUpdate(s, batchUpdateSpreadsheetRequest);
            this.this$0.initialize(batchUpdate);
            return batchUpdate;
        }
        
        public Create create(final Spreadsheet spreadsheet) throws IOException {
            final Create create = new Create(spreadsheet);
            this.this$0.initialize(create);
            return create;
        }
        
        public Get get(final String s) throws IOException {
            final Get get = new Get(s);
            this.this$0.initialize(get);
            return get;
        }
        
        public GetByDataFilter getByDataFilter(final String s, final GetSpreadsheetByDataFilterRequest getSpreadsheetByDataFilterRequest) throws IOException {
            final GetByDataFilter getByDataFilter = new GetByDataFilter(s, getSpreadsheetByDataFilterRequest);
            this.this$0.initialize(getByDataFilter);
            return getByDataFilter;
        }
        
        public DeveloperMetadata developerMetadata() {
            return new DeveloperMetadata();
        }
        
        public SheetsOperations sheets() {
            return new SheetsOperations();
        }
        
        public Values values() {
            return new Values();
        }
        
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
        
        public class Get extends SheetsRequest<Spreadsheet>
        {
            private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}";
            @Key
            private String spreadsheetId;
            @Key
            private List<String> ranges;
            @Key
            private Boolean includeGridData;
            final /* synthetic */ Spreadsheets this$1;
            
            protected Get(final Spreadsheets this$1, final String a1) {
                this.this$1 = this$1;
                super(this$1.this$0, "GET", "v4/spreadsheets/{spreadsheetId}", (Object)null, Spreadsheet.class);
                this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
            }
            
            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }
            
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }
            
            @Override
            public Get set$Xgafv(final String s) {
                return (Get)super.set$Xgafv(s);
            }
            
            @Override
            public Get setAccessToken(final String accessToken) {
                return (Get)super.setAccessToken(accessToken);
            }
            
            @Override
            public Get setAlt(final String alt) {
                return (Get)super.setAlt(alt);
            }
            
            @Override
            public Get setCallback(final String callback) {
                return (Get)super.setCallback(callback);
            }
            
            @Override
            public Get setFields(final String fields) {
                return (Get)super.setFields(fields);
            }
            
            @Override
            public Get setKey(final String key) {
                return (Get)super.setKey(key);
            }
            
            @Override
            public Get setOauthToken(final String oauthToken) {
                return (Get)super.setOauthToken(oauthToken);
            }
            
            @Override
            public Get setPrettyPrint(final Boolean prettyPrint) {
                return (Get)super.setPrettyPrint(prettyPrint);
            }
            
            @Override
            public Get setQuotaUser(final String quotaUser) {
                return (Get)super.setQuotaUser(quotaUser);
            }
            
            @Override
            public Get setUploadType(final String uploadType) {
                return (Get)super.setUploadType(uploadType);
            }
            
            @Override
            public Get setUploadProtocol(final String uploadProtocol) {
                return (Get)super.setUploadProtocol(uploadProtocol);
            }
            
            public String getSpreadsheetId() {
                return this.spreadsheetId;
            }
            
            public Get setSpreadsheetId(final String spreadsheetId) {
                this.spreadsheetId = spreadsheetId;
                return this;
            }
            
            public List<String> getRanges() {
                return this.ranges;
            }
            
            public Get setRanges(final List<String> ranges) {
                this.ranges = ranges;
                return this;
            }
            
            public Boolean getIncludeGridData() {
                return this.includeGridData;
            }
            
            public Get setIncludeGridData(final Boolean includeGridData) {
                this.includeGridData = includeGridData;
                return this;
            }
            
            @Override
            public Get set(final String s, final Object o) {
                return (Get)super.set(s, o);
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
        
        public class DeveloperMetadata
        {
            final /* synthetic */ Spreadsheets this$1;
            
            public DeveloperMetadata(final Spreadsheets this$1) {
                this.this$1 = this$1;
                super();
            }
            
            public Get get(final String s, final Integer n) throws IOException {
                final Get get = new Get(s, n);
                this.this$1.this$0.initialize(get);
                return get;
            }
            
            public Search search(final String s, final SearchDeveloperMetadataRequest searchDeveloperMetadataRequest) throws IOException {
                final Search search = new Search(s, searchDeveloperMetadataRequest);
                this.this$1.this$0.initialize(search);
                return search;
            }
            
            public class Get extends SheetsRequest<com.google.api.services.sheets.v4.model.DeveloperMetadata>
            {
                private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/developerMetadata/{metadataId}";
                @Key
                private String spreadsheetId;
                @Key
                private Integer metadataId;
                final /* synthetic */ DeveloperMetadata this$2;
                
                protected Get(final DeveloperMetadata this$2, final String a1, final Integer a2) {
                    this.this$2 = this$2;
                    super(this$2.this$1.this$0, "GET", "v4/spreadsheets/{spreadsheetId}/developerMetadata/{metadataId}", (Object)null, com.google.api.services.sheets.v4.model.DeveloperMetadata.class);
                    this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
                    this.metadataId = Preconditions.checkNotNull(a2, (Object)"Required parameter metadataId must be specified.");
                }
                
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }
                
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }
                
                @Override
                public Get set$Xgafv(final String s) {
                    return (Get)super.set$Xgafv(s);
                }
                
                @Override
                public Get setAccessToken(final String accessToken) {
                    return (Get)super.setAccessToken(accessToken);
                }
                
                @Override
                public Get setAlt(final String alt) {
                    return (Get)super.setAlt(alt);
                }
                
                @Override
                public Get setCallback(final String callback) {
                    return (Get)super.setCallback(callback);
                }
                
                @Override
                public Get setFields(final String fields) {
                    return (Get)super.setFields(fields);
                }
                
                @Override
                public Get setKey(final String key) {
                    return (Get)super.setKey(key);
                }
                
                @Override
                public Get setOauthToken(final String oauthToken) {
                    return (Get)super.setOauthToken(oauthToken);
                }
                
                @Override
                public Get setPrettyPrint(final Boolean prettyPrint) {
                    return (Get)super.setPrettyPrint(prettyPrint);
                }
                
                @Override
                public Get setQuotaUser(final String quotaUser) {
                    return (Get)super.setQuotaUser(quotaUser);
                }
                
                @Override
                public Get setUploadType(final String uploadType) {
                    return (Get)super.setUploadType(uploadType);
                }
                
                @Override
                public Get setUploadProtocol(final String uploadProtocol) {
                    return (Get)super.setUploadProtocol(uploadProtocol);
                }
                
                public String getSpreadsheetId() {
                    return this.spreadsheetId;
                }
                
                public Get setSpreadsheetId(final String spreadsheetId) {
                    this.spreadsheetId = spreadsheetId;
                    return this;
                }
                
                public Integer getMetadataId() {
                    return this.metadataId;
                }
                
                public Get setMetadataId(final Integer metadataId) {
                    this.metadataId = metadataId;
                    return this;
                }
                
                @Override
                public Get set(final String s, final Object o) {
                    return (Get)super.set(s, o);
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
            
            public class Search extends SheetsRequest<SearchDeveloperMetadataResponse>
            {
                private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/developerMetadata:search";
                @Key
                private String spreadsheetId;
                final /* synthetic */ DeveloperMetadata this$2;
                
                protected Search(final DeveloperMetadata this$2, final String a1, final SearchDeveloperMetadataRequest searchDeveloperMetadataRequest) {
                    this.this$2 = this$2;
                    super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/developerMetadata:search", searchDeveloperMetadataRequest, SearchDeveloperMetadataResponse.class);
                    this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
                }
                
                @Override
                public Search set$Xgafv(final String s) {
                    return (Search)super.set$Xgafv(s);
                }
                
                @Override
                public Search setAccessToken(final String accessToken) {
                    return (Search)super.setAccessToken(accessToken);
                }
                
                @Override
                public Search setAlt(final String alt) {
                    return (Search)super.setAlt(alt);
                }
                
                @Override
                public Search setCallback(final String callback) {
                    return (Search)super.setCallback(callback);
                }
                
                @Override
                public Search setFields(final String fields) {
                    return (Search)super.setFields(fields);
                }
                
                @Override
                public Search setKey(final String key) {
                    return (Search)super.setKey(key);
                }
                
                @Override
                public Search setOauthToken(final String oauthToken) {
                    return (Search)super.setOauthToken(oauthToken);
                }
                
                @Override
                public Search setPrettyPrint(final Boolean prettyPrint) {
                    return (Search)super.setPrettyPrint(prettyPrint);
                }
                
                @Override
                public Search setQuotaUser(final String quotaUser) {
                    return (Search)super.setQuotaUser(quotaUser);
                }
                
                @Override
                public Search setUploadType(final String uploadType) {
                    return (Search)super.setUploadType(uploadType);
                }
                
                @Override
                public Search setUploadProtocol(final String uploadProtocol) {
                    return (Search)super.setUploadProtocol(uploadProtocol);
                }
                
                public String getSpreadsheetId() {
                    return this.spreadsheetId;
                }
                
                public Search setSpreadsheetId(final String spreadsheetId) {
                    this.spreadsheetId = spreadsheetId;
                    return this;
                }
                
                @Override
                public Search set(final String s, final Object o) {
                    return (Search)super.set(s, o);
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
        
        public class Values
        {
            final /* synthetic */ Spreadsheets this$1;
            
            public Values(final Spreadsheets this$1) {
                this.this$1 = this$1;
                super();
            }
            
            public Append append(final String s, final String s2, final ValueRange valueRange) throws IOException {
                final Append append = new Append(s, s2, valueRange);
                this.this$1.this$0.initialize(append);
                return append;
            }
            
            public BatchClear batchClear(final String s, final BatchClearValuesRequest batchClearValuesRequest) throws IOException {
                final BatchClear batchClear = new BatchClear(s, batchClearValuesRequest);
                this.this$1.this$0.initialize(batchClear);
                return batchClear;
            }
            
            public BatchClearByDataFilter batchClearByDataFilter(final String s, final BatchClearValuesByDataFilterRequest batchClearValuesByDataFilterRequest) throws IOException {
                final BatchClearByDataFilter batchClearByDataFilter = new BatchClearByDataFilter(s, batchClearValuesByDataFilterRequest);
                this.this$1.this$0.initialize(batchClearByDataFilter);
                return batchClearByDataFilter;
            }
            
            public BatchGet batchGet(final String s) throws IOException {
                final BatchGet batchGet = new BatchGet(s);
                this.this$1.this$0.initialize(batchGet);
                return batchGet;
            }
            
            public BatchGetByDataFilter batchGetByDataFilter(final String s, final BatchGetValuesByDataFilterRequest batchGetValuesByDataFilterRequest) throws IOException {
                final BatchGetByDataFilter batchGetByDataFilter = new BatchGetByDataFilter(s, batchGetValuesByDataFilterRequest);
                this.this$1.this$0.initialize(batchGetByDataFilter);
                return batchGetByDataFilter;
            }
            
            public BatchUpdate batchUpdate(final String s, final BatchUpdateValuesRequest batchUpdateValuesRequest) throws IOException {
                final BatchUpdate batchUpdate = new BatchUpdate(s, batchUpdateValuesRequest);
                this.this$1.this$0.initialize(batchUpdate);
                return batchUpdate;
            }
            
            public BatchUpdateByDataFilter batchUpdateByDataFilter(final String s, final BatchUpdateValuesByDataFilterRequest batchUpdateValuesByDataFilterRequest) throws IOException {
                final BatchUpdateByDataFilter batchUpdateByDataFilter = new BatchUpdateByDataFilter(s, batchUpdateValuesByDataFilterRequest);
                this.this$1.this$0.initialize(batchUpdateByDataFilter);
                return batchUpdateByDataFilter;
            }
            
            public Clear clear(final String s, final String s2, final ClearValuesRequest clearValuesRequest) throws IOException {
                final Clear clear = new Clear(s, s2, clearValuesRequest);
                this.this$1.this$0.initialize(clear);
                return clear;
            }
            
            public Get get(final String s, final String s2) throws IOException {
                final Get get = new Get(s, s2);
                this.this$1.this$0.initialize(get);
                return get;
            }
            
            public Update update(final String s, final String s2, final ValueRange valueRange) throws IOException {
                final Update update = new Update(s, s2, valueRange);
                this.this$1.this$0.initialize(update);
                return update;
            }
            
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
            
            public class BatchClear extends SheetsRequest<BatchClearValuesResponse>
            {
                private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values:batchClear";
                @Key
                private String spreadsheetId;
                final /* synthetic */ Values this$2;
                
                protected BatchClear(final Values this$2, final String a1, final BatchClearValuesRequest batchClearValuesRequest) {
                    this.this$2 = this$2;
                    super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values:batchClear", batchClearValuesRequest, BatchClearValuesResponse.class);
                    this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
                }
                
                @Override
                public BatchClear set$Xgafv(final String s) {
                    return (BatchClear)super.set$Xgafv(s);
                }
                
                @Override
                public BatchClear setAccessToken(final String accessToken) {
                    return (BatchClear)super.setAccessToken(accessToken);
                }
                
                @Override
                public BatchClear setAlt(final String alt) {
                    return (BatchClear)super.setAlt(alt);
                }
                
                @Override
                public BatchClear setCallback(final String callback) {
                    return (BatchClear)super.setCallback(callback);
                }
                
                @Override
                public BatchClear setFields(final String fields) {
                    return (BatchClear)super.setFields(fields);
                }
                
                @Override
                public BatchClear setKey(final String key) {
                    return (BatchClear)super.setKey(key);
                }
                
                @Override
                public BatchClear setOauthToken(final String oauthToken) {
                    return (BatchClear)super.setOauthToken(oauthToken);
                }
                
                @Override
                public BatchClear setPrettyPrint(final Boolean prettyPrint) {
                    return (BatchClear)super.setPrettyPrint(prettyPrint);
                }
                
                @Override
                public BatchClear setQuotaUser(final String quotaUser) {
                    return (BatchClear)super.setQuotaUser(quotaUser);
                }
                
                @Override
                public BatchClear setUploadType(final String uploadType) {
                    return (BatchClear)super.setUploadType(uploadType);
                }
                
                @Override
                public BatchClear setUploadProtocol(final String uploadProtocol) {
                    return (BatchClear)super.setUploadProtocol(uploadProtocol);
                }
                
                public String getSpreadsheetId() {
                    return this.spreadsheetId;
                }
                
                public BatchClear setSpreadsheetId(final String spreadsheetId) {
                    this.spreadsheetId = spreadsheetId;
                    return this;
                }
                
                @Override
                public BatchClear set(final String s, final Object o) {
                    return (BatchClear)super.set(s, o);
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
            
            public class BatchUpdate extends SheetsRequest<BatchUpdateValuesResponse>
            {
                private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values:batchUpdate";
                @Key
                private String spreadsheetId;
                final /* synthetic */ Values this$2;
                
                protected BatchUpdate(final Values this$2, final String a1, final BatchUpdateValuesRequest batchUpdateValuesRequest) {
                    this.this$2 = this$2;
                    super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values:batchUpdate", batchUpdateValuesRequest, BatchUpdateValuesResponse.class);
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
            
            public class BatchUpdateByDataFilter extends SheetsRequest<BatchUpdateValuesByDataFilterResponse>
            {
                private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values:batchUpdateByDataFilter";
                @Key
                private String spreadsheetId;
                final /* synthetic */ Values this$2;
                
                protected BatchUpdateByDataFilter(final Values this$2, final String a1, final BatchUpdateValuesByDataFilterRequest batchUpdateValuesByDataFilterRequest) {
                    this.this$2 = this$2;
                    super(this$2.this$1.this$0, "POST", "v4/spreadsheets/{spreadsheetId}/values:batchUpdateByDataFilter", batchUpdateValuesByDataFilterRequest, BatchUpdateValuesByDataFilterResponse.class);
                    this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
                }
                
                @Override
                public BatchUpdateByDataFilter set$Xgafv(final String s) {
                    return (BatchUpdateByDataFilter)super.set$Xgafv(s);
                }
                
                @Override
                public BatchUpdateByDataFilter setAccessToken(final String accessToken) {
                    return (BatchUpdateByDataFilter)super.setAccessToken(accessToken);
                }
                
                @Override
                public BatchUpdateByDataFilter setAlt(final String alt) {
                    return (BatchUpdateByDataFilter)super.setAlt(alt);
                }
                
                @Override
                public BatchUpdateByDataFilter setCallback(final String callback) {
                    return (BatchUpdateByDataFilter)super.setCallback(callback);
                }
                
                @Override
                public BatchUpdateByDataFilter setFields(final String fields) {
                    return (BatchUpdateByDataFilter)super.setFields(fields);
                }
                
                @Override
                public BatchUpdateByDataFilter setKey(final String key) {
                    return (BatchUpdateByDataFilter)super.setKey(key);
                }
                
                @Override
                public BatchUpdateByDataFilter setOauthToken(final String oauthToken) {
                    return (BatchUpdateByDataFilter)super.setOauthToken(oauthToken);
                }
                
                @Override
                public BatchUpdateByDataFilter setPrettyPrint(final Boolean prettyPrint) {
                    return (BatchUpdateByDataFilter)super.setPrettyPrint(prettyPrint);
                }
                
                @Override
                public BatchUpdateByDataFilter setQuotaUser(final String quotaUser) {
                    return (BatchUpdateByDataFilter)super.setQuotaUser(quotaUser);
                }
                
                @Override
                public BatchUpdateByDataFilter setUploadType(final String uploadType) {
                    return (BatchUpdateByDataFilter)super.setUploadType(uploadType);
                }
                
                @Override
                public BatchUpdateByDataFilter setUploadProtocol(final String uploadProtocol) {
                    return (BatchUpdateByDataFilter)super.setUploadProtocol(uploadProtocol);
                }
                
                public String getSpreadsheetId() {
                    return this.spreadsheetId;
                }
                
                public BatchUpdateByDataFilter setSpreadsheetId(final String spreadsheetId) {
                    this.spreadsheetId = spreadsheetId;
                    return this;
                }
                
                @Override
                public BatchUpdateByDataFilter set(final String s, final Object o) {
                    return (BatchUpdateByDataFilter)super.set(s, o);
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
            
            public class Get extends SheetsRequest<ValueRange>
            {
                private static final String REST_PATH = "v4/spreadsheets/{spreadsheetId}/values/{range}";
                @Key
                private String spreadsheetId;
                @Key
                private String range;
                @Key
                private String valueRenderOption;
                @Key
                private String dateTimeRenderOption;
                @Key
                private String majorDimension;
                final /* synthetic */ Values this$2;
                
                protected Get(final Values this$2, final String a1, final String a2) {
                    this.this$2 = this$2;
                    super(this$2.this$1.this$0, "GET", "v4/spreadsheets/{spreadsheetId}/values/{range}", (Object)null, ValueRange.class);
                    this.spreadsheetId = Preconditions.checkNotNull(a1, (Object)"Required parameter spreadsheetId must be specified.");
                    this.range = Preconditions.checkNotNull(a2, (Object)"Required parameter range must be specified.");
                }
                
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }
                
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }
                
                @Override
                public Get set$Xgafv(final String s) {
                    return (Get)super.set$Xgafv(s);
                }
                
                @Override
                public Get setAccessToken(final String accessToken) {
                    return (Get)super.setAccessToken(accessToken);
                }
                
                @Override
                public Get setAlt(final String alt) {
                    return (Get)super.setAlt(alt);
                }
                
                @Override
                public Get setCallback(final String callback) {
                    return (Get)super.setCallback(callback);
                }
                
                @Override
                public Get setFields(final String fields) {
                    return (Get)super.setFields(fields);
                }
                
                @Override
                public Get setKey(final String key) {
                    return (Get)super.setKey(key);
                }
                
                @Override
                public Get setOauthToken(final String oauthToken) {
                    return (Get)super.setOauthToken(oauthToken);
                }
                
                @Override
                public Get setPrettyPrint(final Boolean prettyPrint) {
                    return (Get)super.setPrettyPrint(prettyPrint);
                }
                
                @Override
                public Get setQuotaUser(final String quotaUser) {
                    return (Get)super.setQuotaUser(quotaUser);
                }
                
                @Override
                public Get setUploadType(final String uploadType) {
                    return (Get)super.setUploadType(uploadType);
                }
                
                @Override
                public Get setUploadProtocol(final String uploadProtocol) {
                    return (Get)super.setUploadProtocol(uploadProtocol);
                }
                
                public String getSpreadsheetId() {
                    return this.spreadsheetId;
                }
                
                public Get setSpreadsheetId(final String spreadsheetId) {
                    this.spreadsheetId = spreadsheetId;
                    return this;
                }
                
                public String getRange() {
                    return this.range;
                }
                
                public Get setRange(final String range) {
                    this.range = range;
                    return this;
                }
                
                public String getValueRenderOption() {
                    return this.valueRenderOption;
                }
                
                public Get setValueRenderOption(final String valueRenderOption) {
                    this.valueRenderOption = valueRenderOption;
                    return this;
                }
                
                public String getDateTimeRenderOption() {
                    return this.dateTimeRenderOption;
                }
                
                public Get setDateTimeRenderOption(final String dateTimeRenderOption) {
                    this.dateTimeRenderOption = dateTimeRenderOption;
                    return this;
                }
                
                public String getMajorDimension() {
                    return this.majorDimension;
                }
                
                public Get setMajorDimension(final String majorDimension) {
                    this.majorDimension = majorDimension;
                    return this;
                }
                
                @Override
                public Get set(final String s, final Object o) {
                    return (Get)super.set(s, o);
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
        }
    }
    
    public static final class Builder extends AbstractGoogleJsonClient.Builder
    {
        public Builder(final HttpTransport a1, final JsonFactory a2, final HttpRequestInitializer a3) {
            super(a1, a2, "https://sheets.googleapis.com/", "", a3, false);
            this.setBatchPath("batch");
        }
        
        @Override
        public Sheets build() {
            return new Sheets(this);
        }
        
        @Override
        public Builder setRootUrl(final String rootUrl) {
            return (Builder)super.setRootUrl(rootUrl);
        }
        
        @Override
        public Builder setServicePath(final String servicePath) {
            return (Builder)super.setServicePath(servicePath);
        }
        
        @Override
        public Builder setBatchPath(final String batchPath) {
            return (Builder)super.setBatchPath(batchPath);
        }
        
        @Override
        public Builder setHttpRequestInitializer(final HttpRequestInitializer httpRequestInitializer) {
            return (Builder)super.setHttpRequestInitializer(httpRequestInitializer);
        }
        
        @Override
        public Builder setApplicationName(final String applicationName) {
            return (Builder)super.setApplicationName(applicationName);
        }
        
        @Override
        public Builder setSuppressPatternChecks(final boolean suppressPatternChecks) {
            return (Builder)super.setSuppressPatternChecks(suppressPatternChecks);
        }
        
        @Override
        public Builder setSuppressRequiredParameterChecks(final boolean suppressRequiredParameterChecks) {
            return (Builder)super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
        }
        
        @Override
        public Builder setSuppressAllChecks(final boolean suppressAllChecks) {
            return (Builder)super.setSuppressAllChecks(suppressAllChecks);
        }
        
        public Builder setSheetsRequestInitializer(final SheetsRequestInitializer googleClientRequestInitializer) {
            return (Builder)super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }
        
        @Override
        public Builder setGoogleClientRequestInitializer(final GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder)super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient.Builder setSuppressAllChecks(final boolean suppressAllChecks) {
            return this.setSuppressAllChecks(suppressAllChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient.Builder setSuppressRequiredParameterChecks(final boolean suppressRequiredParameterChecks) {
            return this.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient.Builder setSuppressPatternChecks(final boolean suppressPatternChecks) {
            return this.setSuppressPatternChecks(suppressPatternChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient.Builder setApplicationName(final String applicationName) {
            return this.setApplicationName(applicationName);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient.Builder setHttpRequestInitializer(final HttpRequestInitializer httpRequestInitializer) {
            return this.setHttpRequestInitializer(httpRequestInitializer);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient.Builder setGoogleClientRequestInitializer(final GoogleClientRequestInitializer googleClientRequestInitializer) {
            return this.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient.Builder setServicePath(final String servicePath) {
            return this.setServicePath(servicePath);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient.Builder setRootUrl(final String rootUrl) {
            return this.setRootUrl(rootUrl);
        }
        
        @Override
        public /* bridge */ AbstractGoogleJsonClient build() {
            return this.build();
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setSuppressAllChecks(final boolean suppressAllChecks) {
            return this.setSuppressAllChecks(suppressAllChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setSuppressRequiredParameterChecks(final boolean suppressRequiredParameterChecks) {
            return this.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setSuppressPatternChecks(final boolean suppressPatternChecks) {
            return this.setSuppressPatternChecks(suppressPatternChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setApplicationName(final String applicationName) {
            return this.setApplicationName(applicationName);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setHttpRequestInitializer(final HttpRequestInitializer httpRequestInitializer) {
            return this.setHttpRequestInitializer(httpRequestInitializer);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setGoogleClientRequestInitializer(final GoogleClientRequestInitializer googleClientRequestInitializer) {
            return this.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setBatchPath(final String batchPath) {
            return this.setBatchPath(batchPath);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setServicePath(final String servicePath) {
            return this.setServicePath(servicePath);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setRootUrl(final String rootUrl) {
            return this.setRootUrl(rootUrl);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient build() {
            return this.build();
        }
    }
}
