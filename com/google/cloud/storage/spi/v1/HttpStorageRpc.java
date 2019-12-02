package com.google.cloud.storage.spi.v1;

import com.google.api.services.storage.*;
import com.google.cloud.http.*;
import com.google.api.client.json.jackson2.*;
import com.google.api.client.json.*;
import com.google.api.client.googleapis.batch.json.*;
import com.google.api.client.googleapis.json.*;
import com.google.cloud.storage.*;
import com.google.common.io.*;
import com.google.common.hash.*;
import io.opencensus.common.*;
import com.google.cloud.*;
import com.google.common.collect.*;
import java.math.*;
import java.io.*;
import com.google.common.base.*;
import com.google.api.client.http.json.*;
import com.google.api.client.http.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.api.client.googleapis.batch.*;
import io.opencensus.trace.*;

public class HttpStorageRpc implements StorageRpc
{
    public static final String DEFAULT_PROJECTION = "full";
    public static final String NO_ACL_PROJECTION = "noAcl";
    private static final String ENCRYPTION_KEY_PREFIX = "x-goog-encryption-";
    private static final String SOURCE_ENCRYPTION_KEY_PREFIX = "x-goog-copy-source-encryption-";
    private static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    private final StorageOptions options;
    private final Storage storage;
    private final Tracer tracer;
    private final CensusHttpModule censusHttpModule;
    private final HttpRequestInitializer batchRequestInitializer;
    private static final long MEGABYTE = 1048576L;
    
    public HttpStorageRpc(final StorageOptions a1) {
        super();
        this.tracer = Tracing.getTracer();
        final HttpTransportOptions v1 = (HttpTransportOptions)a1.getTransportOptions();
        final HttpTransport v2 = v1.getHttpTransportFactory().create();
        HttpRequestInitializer v3 = v1.getHttpRequestInitializer((ServiceOptions)a1);
        this.options = a1;
        this.censusHttpModule = new CensusHttpModule(this.tracer, true);
        v3 = this.censusHttpModule.getHttpRequestInitializer(v3);
        this.batchRequestInitializer = this.censusHttpModule.getHttpRequestInitializer((HttpRequestInitializer)null);
        HttpStorageRpcSpans.registerAllSpanNamesForCollection();
        this.storage = new Storage.Builder(v2, (JsonFactory)new JacksonFactory(), v3).setRootUrl(a1.getHost()).setApplicationName(a1.getApplicationName()).build();
    }
    
    private static <T> JsonBatchCallback<T> toJsonCallback(final RpcBatch.Callback<T> a1) {
        return new JsonBatchCallback<T>() {
            final /* synthetic */ RpcBatch.Callback val$callback;
            
            HttpStorageRpc$1() {
                super();
            }
            
            @Override
            public void onSuccess(final T a1, final HttpHeaders a2) throws IOException {
                a1.onSuccess(a1);
            }
            
            @Override
            public void onFailure(final GoogleJsonError a1, final HttpHeaders a2) throws IOException {
                a1.onFailure(a1);
            }
        };
    }
    
    private static StorageException translate(final IOException a1) {
        return new StorageException(a1);
    }
    
    private static StorageException translate(final GoogleJsonError a1) {
        return new StorageException(a1);
    }
    
    private static void setEncryptionHeaders(final HttpHeaders a3, final String v1, final Map<Option, ?> v2) {
        final String v3 = Option.CUSTOMER_SUPPLIED_KEY.getString(v2);
        if (v3 != null) {
            final BaseEncoding a4 = BaseEncoding.base64();
            final HashFunction a5 = Hashing.sha256();
            a3.set(v1 + "algorithm", "AES256");
            a3.set(v1 + "key", v3);
            a3.set(v1 + "key-sha256", a4.encode(a5.hashBytes(a4.decode((CharSequence)v3)).asBytes()));
        }
    }
    
    private Span startSpan(final String a1) {
        return this.tracer.spanBuilder(a1).setRecordEvents(this.censusHttpModule.isRecordEvents()).startSpan();
    }
    
    @Override
    public Bucket create(final Bucket v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_BUCKET);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (Bucket)this.storage.buckets().insert(this.options.getProjectId(), v1).setProjection("full").setPredefinedAcl(Option.PREDEFINED_ACL.getString(v2)).setPredefinedDefaultObjectAcl(Option.PREDEFINED_DEFAULT_OBJECT_ACL.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public StorageObject create(final StorageObject v2, final InputStream v3, final Map<Option, ?> v4) {
        final Span v5 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT);
        final Scope v6 = this.tracer.withSpan(v5);
        try {
            final Storage.Objects.Insert a1 = this.storage.objects().insert(v2.getBucket(), v2, (AbstractInputStreamContent)new InputStreamContent(v2.getContentType(), v3));
            a1.getMediaHttpUploader().setDirectUploadEnabled(true);
            final Boolean a2 = Option.IF_DISABLE_GZIP_CONTENT.getBoolean(v4);
            if (a2 != null) {
                a1.setDisableGZipContent((boolean)a2);
            }
            setEncryptionHeaders(a1.getRequestHeaders(), "x-goog-encryption-", v4);
            return (StorageObject)a1.setProjection("full").setPredefinedAcl(Option.PREDEFINED_ACL.getString(v4)).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(v4)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(v4)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(v4)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(v4)).setUserProject(Option.USER_PROJECT.getString(v4)).setKmsKeyName(Option.KMS_KEY_NAME.getString(v4)).execute();
        }
        catch (IOException a3) {
            v5.setStatus(Status.UNKNOWN.withDescription(a3.getMessage()));
            throw translate(a3);
        }
        finally {
            v6.close();
            v5.end();
        }
    }
    
    @Override
    public Tuple<String, Iterable<Bucket>> list(final Map<Option, ?> v-2) {
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_BUCKETS);
        final Scope v0 = this.tracer.withSpan(startSpan);
        try {
            final Buckets a1 = (Buckets)this.storage.buckets().list(this.options.getProjectId()).setProjection("full").setPrefix(Option.PREFIX.getString(v-2)).setMaxResults(Option.MAX_RESULTS.getLong(v-2)).setPageToken(Option.PAGE_TOKEN.getString(v-2)).setFields(Option.FIELDS.getString(v-2)).setUserProject(Option.USER_PROJECT.getString(v-2)).execute();
            return (Tuple<String, Iterable<Bucket>>)Tuple.of((Object)a1.getNextPageToken(), (Object)a1.getItems());
        }
        catch (IOException v2) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(v2.getMessage()));
            throw translate(v2);
        }
        finally {
            v0.close();
            startSpan.end();
        }
    }
    
    @Override
    public Tuple<String, Iterable<StorageObject>> list(final String v-3, final Map<Option, ?> v-2) {
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECTS);
        final Scope v0 = this.tracer.withSpan(startSpan);
        try {
            final Objects a1 = (Objects)this.storage.objects().list(v-3).setProjection("full").setVersions(Option.VERSIONS.getBoolean(v-2)).setDelimiter(Option.DELIMITER.getString(v-2)).setPrefix(Option.PREFIX.getString(v-2)).setMaxResults(Option.MAX_RESULTS.getLong(v-2)).setPageToken(Option.PAGE_TOKEN.getString(v-2)).setFields(Option.FIELDS.getString(v-2)).setUserProject(Option.USER_PROJECT.getString(v-2)).execute();
            final Iterable<StorageObject> a2 = Iterables.concat((Iterable<? extends StorageObject>)MoreObjects.firstNonNull(a1.getItems(), ImmutableList.of()), (Iterable<? extends StorageObject>)((a1.getPrefixes() != null) ? Lists.transform((List<Object>)a1.getPrefixes(), (Function<? super Object, ?>)objectFromPrefix(v-3)) : ImmutableList.of()));
            return (Tuple<String, Iterable<StorageObject>>)Tuple.of((Object)a1.getNextPageToken(), (Object)a2);
        }
        catch (IOException v2) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(v2.getMessage()));
            throw translate(v2);
        }
        finally {
            v0.close();
            startSpan.end();
        }
    }
    
    private static Function<String, StorageObject> objectFromPrefix(final String a1) {
        return new Function<String, StorageObject>() {
            final /* synthetic */ String val$bucket;
            
            HttpStorageRpc$2() {
                super();
            }
            
            @Override
            public StorageObject apply(final String a1) {
                return new StorageObject().set("isDirectory", (Object)true).setBucket(a1).setName(a1).setSize(BigInteger.ZERO);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((String)o);
            }
        };
    }
    
    @Override
    public Bucket get(final Bucket v2, final Map<Option, ?> v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            return (Bucket)this.storage.buckets().get(v2.getName()).setProjection("full").setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(v3)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(v3)).setFields(Option.FIELDS.getString(v3)).setUserProject(Option.USER_PROJECT.getString(v3)).execute();
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return null;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    private Storage.Objects.Get getCall(final StorageObject a1, final Map<Option, ?> a2) throws IOException {
        final Storage.Objects.Get v1 = this.storage.objects().get(a1.getBucket(), a1.getName());
        setEncryptionHeaders(v1.getRequestHeaders(), "x-goog-encryption-", a2);
        return v1.setGeneration(a1.getGeneration()).setProjection("full").setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(a2)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(a2)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(a2)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(a2)).setFields(Option.FIELDS.getString(a2)).setUserProject(Option.USER_PROJECT.getString(a2));
    }
    
    @Override
    public StorageObject get(final StorageObject v2, final Map<Option, ?> v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            return (StorageObject)this.getCall(v2, v3).execute();
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return null;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public Bucket patch(final Bucket v2, final Map<Option, ?> v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_BUCKET);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            String a1 = Option.PROJECTION.getString(v3);
            if (v2.getIamConfiguration() != null && v2.getIamConfiguration().getBucketPolicyOnly() != null && v2.getIamConfiguration().getBucketPolicyOnly().getEnabled() != null && v2.getIamConfiguration().getBucketPolicyOnly().getEnabled()) {
                v2.setDefaultObjectAcl((List)null);
                v2.setAcl((List)null);
                if (a1 == null) {
                    a1 = "noAcl";
                }
            }
            return (Bucket)this.storage.buckets().patch(v2.getName(), v2).setProjection((a1 == null) ? "full" : a1).setPredefinedAcl(Option.PREDEFINED_ACL.getString(v3)).setPredefinedDefaultObjectAcl(Option.PREDEFINED_DEFAULT_OBJECT_ACL.getString(v3)).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(v3)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(v3)).setUserProject(Option.USER_PROJECT.getString(v3)).execute();
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            throw translate(a2);
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    private Storage.Objects.Patch patchCall(final StorageObject a1, final Map<Option, ?> a2) throws IOException {
        return this.storage.objects().patch(a1.getBucket(), a1.getName(), a1).setProjection("full").setPredefinedAcl(Option.PREDEFINED_ACL.getString(a2)).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(a2)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(a2)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(a2)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(a2)).setUserProject(Option.USER_PROJECT.getString(a2));
    }
    
    @Override
    public StorageObject patch(final StorageObject v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (StorageObject)this.patchCall(v1, v2).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public boolean delete(final Bucket v2, final Map<Option, ?> v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_BUCKET);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            this.storage.buckets().delete(v2.getName()).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(v3)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(v3)).setUserProject(Option.USER_PROJECT.getString(v3)).execute();
            return true;
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return false;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    private Storage.Objects.Delete deleteCall(final StorageObject a1, final Map<Option, ?> a2) throws IOException {
        return this.storage.objects().delete(a1.getBucket(), a1.getName()).setGeneration(a1.getGeneration()).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(a2)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(a2)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(a2)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(a2)).setUserProject(Option.USER_PROJECT.getString(a2));
    }
    
    @Override
    public boolean delete(final StorageObject v2, final Map<Option, ?> v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            this.deleteCall(v2, v3).execute();
            return true;
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return false;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public StorageObject compose(final Iterable<StorageObject> v-6, final StorageObject v-5, final Map<Option, ?> v-4) {
        final ComposeRequest composeRequest = new ComposeRequest();
        composeRequest.setDestination(v-5);
        final List<ComposeRequest.SourceObjects> sourceObjects = new ArrayList<ComposeRequest.SourceObjects>();
        for (final StorageObject a3 : v-6) {
            final ComposeRequest.SourceObjects a4 = new ComposeRequest.SourceObjects();
            a4.setName(a3.getName());
            final Long a5 = a3.getGeneration();
            if (a5 != null) {
                a4.setGeneration(a5);
                a4.setObjectPreconditions(new ComposeRequest.SourceObjects.ObjectPreconditions().setIfGenerationMatch(a5));
            }
            sourceObjects.add(a4);
        }
        composeRequest.setSourceObjects((List)sourceObjects);
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_COMPOSE);
        final Scope v0 = this.tracer.withSpan(startSpan);
        try {
            return (StorageObject)this.storage.objects().compose(v-5.getBucket(), v-5.getName(), composeRequest).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(v-4)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(v-4)).setUserProject(Option.USER_PROJECT.getString(v-4)).execute();
        }
        catch (IOException v2) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(v2.getMessage()));
            throw translate(v2);
        }
        finally {
            v0.close();
            startSpan.end();
        }
    }
    
    @Override
    public byte[] load(final StorageObject v-3, final Map<Option, ?> v-2) {
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LOAD);
        final Scope v0 = this.tracer.withSpan(startSpan);
        try {
            final Storage.Objects.Get a1 = this.storage.objects().get(v-3.getBucket(), v-3.getName()).setGeneration(v-3.getGeneration()).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(v-2)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(v-2)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(v-2)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(v-2)).setUserProject(Option.USER_PROJECT.getString(v-2));
            setEncryptionHeaders(a1.getRequestHeaders(), "x-goog-encryption-", v-2);
            final ByteArrayOutputStream a2 = new ByteArrayOutputStream();
            a1.executeMedia().download(a2);
            return a2.toByteArray();
        }
        catch (IOException v2) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(v2.getMessage()));
            throw translate(v2);
        }
        finally {
            v0.close();
            startSpan.end();
        }
    }
    
    @Override
    public RpcBatch createBatch() {
        return new DefaultRpcBatch(this, this.storage);
    }
    
    private Storage.Objects.Get createReadRequest(final StorageObject a1, final Map<Option, ?> a2) throws IOException {
        final Storage.Objects.Get v1 = this.storage.objects().get(a1.getBucket(), a1.getName()).setGeneration(a1.getGeneration()).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(a2)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(a2)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(a2)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(a2)).setUserProject(Option.USER_PROJECT.getString(a2));
        setEncryptionHeaders(v1.getRequestHeaders(), "x-goog-encryption-", a2);
        v1.setReturnRawInputStream(true);
        return v1;
    }
    
    @Override
    public long read(final StorageObject v1, final Map<Option, ?> v2, final long v3, final OutputStream v5) {
        final Span v6 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_READ);
        final Scope v7 = this.tracer.withSpan(v6);
        try {
            final Storage.Objects.Get a1 = this.createReadRequest(v1, v2);
            a1.getMediaHttpDownloader().setBytesDownloaded(v3);
            a1.getMediaHttpDownloader().setDirectDownloadEnabled(true);
            a1.executeMediaAndDownloadTo(v5);
            return a1.getMediaHttpDownloader().getNumBytesDownloaded();
        }
        catch (IOException a2) {
            v6.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 416) {
                return 0L;
            }
            throw a3;
        }
        finally {
            v7.close();
            v6.end();
        }
    }
    
    @Override
    public Tuple<String, byte[]> read(final StorageObject v-10, final Map<Option, ?> v-9, final long v-8, final int v-6) {
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_READ);
        final Scope withSpan = this.tracer.withSpan(startSpan);
        try {
            Preconditions.checkArgument(v-8 >= 0L, "Position should be non-negative, is %d", v-8);
            final Storage.Objects.Get a1 = this.createReadRequest(v-10, v-9);
            final StringBuilder a2 = new StringBuilder();
            a2.append("bytes=").append(v-8).append("-").append(v-8 + v-6 - 1L);
            final HttpHeaders a3 = a1.getRequestHeaders();
            a3.setRange(a2.toString());
            final ByteArrayOutputStream a4 = new ByteArrayOutputStream(v-6);
            a1.executeMedia().download(a4);
            final String v1 = a1.getLastResponseHeaders().getETag();
            return (Tuple<String, byte[]>)Tuple.of((Object)v1, (Object)a4.toByteArray());
        }
        catch (IOException a5) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(a5.getMessage()));
            final StorageException translate = translate(a5);
            if (translate.getCode() == 416) {
                return (Tuple<String, byte[]>)Tuple.of((Object)null, (Object)new byte[0]);
            }
            throw translate;
        }
        finally {
            withSpan.close();
            startSpan.end();
        }
    }
    
    @Override
    public void write(final String v-8, final byte[] v-7, final int v-6, final long v-5, final int v-3, final boolean v-2) {
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_WRITE);
        final Scope v0 = this.tracer.withSpan(startSpan);
        try {
            if (v-3 == 0 && !v-2) {
                return;
            }
            final GenericUrl v2 = new GenericUrl(v-8);
            final HttpRequest v3 = this.storage.getRequestFactory().buildPutRequest(v2, new ByteArrayContent(null, v-7, v-6, v-3));
            final long v4 = v-5 + v-3;
            final StringBuilder v5 = new StringBuilder("bytes ");
            if (v-3 == 0) {
                v5.append('*');
            }
            else {
                v5.append(v-5).append('-').append(v4 - 1L);
            }
            v5.append('/');
            if (v-2) {
                v5.append(v4);
            }
            else {
                v5.append('*');
            }
            v3.getHeaders().setContentRange(v5.toString());
            IOException v6 = null;
            HttpResponse v7 = null;
            try {
                v7 = v3.execute();
                final int a1 = v7.getStatusCode();
                final String a2 = v7.getStatusMessage();
            }
            catch (HttpResponseException a3) {
                v6 = a3;
                final int a4 = a3.getStatusCode();
                final String a5 = a3.getStatusMessage();
            }
            finally {
                if (v7 != null) {
                    v7.disconnect();
                }
            }
            final int v8;
            if ((!v-2 && v8 != 308) || (v-2 && v8 != 200 && v8 != 201)) {
                if (v6 != null) {
                    throw v6;
                }
                final GoogleJsonError a6 = new GoogleJsonError();
                a6.setCode(v8);
                final String v9;
                a6.setMessage(v9);
                throw translate(a6);
            }
        }
        catch (IOException v10) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(v10.getMessage()));
            throw translate(v10);
        }
        finally {
            v0.close();
            startSpan.end();
        }
    }
    
    @Override
    public String open(final StorageObject v-14, final Map<Option, ?> v-13) {
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_OPEN);
        final Scope withSpan = this.tracer.withSpan(startSpan);
        try {
            final Storage.Objects.Insert insert = this.storage.objects().insert(v-14.getBucket(), v-14);
            GenericUrl url = insert.buildHttpRequest().getUrl();
            final String scheme = url.getScheme();
            final String host = url.getHost();
            int port = url.getPort();
            port = ((port > 0) ? port : url.toURL().getDefaultPort());
            final String string = "/upload" + url.getRawPath();
            url = new GenericUrl(scheme + "://" + host + ":" + port + string);
            url.set("uploadType", "resumable");
            url.set("name", v-14.getName());
            for (final Option a2 : v-13.keySet()) {
                final Object a3 = a2.get(v-13);
                if (a3 != null) {
                    url.set(a2.value(), a3.toString());
                }
            }
            final JsonFactory jsonFactory = this.storage.getJsonFactory();
            final HttpRequestFactory requestFactory = this.storage.getRequestFactory();
            final HttpRequest buildPostRequest = requestFactory.buildPostRequest(url, new JsonHttpContent(jsonFactory, v-14));
            final HttpHeaders headers = buildPostRequest.getHeaders();
            headers.set("X-Upload-Content-Type", MoreObjects.firstNonNull(v-14.getContentType(), "application/octet-stream"));
            final String v0 = Option.CUSTOMER_SUPPLIED_KEY.getString(v-13);
            if (v0 != null) {
                final BaseEncoding v2 = BaseEncoding.base64();
                final HashFunction v3 = Hashing.sha256();
                headers.set("x-goog-encryption-algorithm", "AES256");
                headers.set("x-goog-encryption-key", v0);
                headers.set("x-goog-encryption-key-sha256", v2.encode(v3.hashBytes(v2.decode((CharSequence)v0)).asBytes()));
            }
            final HttpResponse v4 = buildPostRequest.execute();
            if (v4.getStatusCode() != 200) {
                final GoogleJsonError v5 = new GoogleJsonError();
                v5.setCode(v4.getStatusCode());
                v5.setMessage(v4.getStatusMessage());
                throw translate(v5);
            }
            return v4.getHeaders().getLocation();
        }
        catch (IOException a4) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(a4.getMessage()));
            throw translate(a4);
        }
        finally {
            withSpan.close();
            startSpan.end();
        }
    }
    
    @Override
    public String open(final String v-2) {
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_OPEN);
        final Scope v0 = this.tracer.withSpan(startSpan);
        try {
            final GenericUrl v2 = new GenericUrl(v-2);
            v2.set("uploadType", "resumable");
            final String v3 = "";
            final byte[] v4 = new byte[v3.length()];
            final HttpRequestFactory v5 = this.storage.getRequestFactory();
            final HttpRequest v6 = v5.buildPostRequest(v2, new ByteArrayContent("", v4, 0, v4.length));
            final HttpHeaders v7 = v6.getHeaders();
            v7.set("X-Upload-Content-Type", "");
            v7.set("x-goog-resumable", "start");
            final HttpResponse v8 = v6.execute();
            if (v8.getStatusCode() != 201) {
                final GoogleJsonError a1 = new GoogleJsonError();
                a1.setCode(v8.getStatusCode());
                a1.setMessage(v8.getStatusMessage());
                throw translate(a1);
            }
            return v8.getHeaders().getLocation();
        }
        catch (IOException v9) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(v9.getMessage()));
            throw translate(v9);
        }
        finally {
            v0.close();
            startSpan.end();
        }
    }
    
    @Override
    public RewriteResponse openRewrite(final RewriteRequest a1) {
        final Span v1 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_OPEN_REWRITE);
        final Scope v2 = this.tracer.withSpan(v1);
        try {
            return this.rewrite(a1, null);
        }
        finally {
            v2.close();
            v1.end();
        }
    }
    
    @Override
    public RewriteResponse continueRewrite(final RewriteResponse a1) {
        final Span v1 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CONTINUE_REWRITE);
        final Scope v2 = this.tracer.withSpan(v1);
        try {
            return this.rewrite(a1.rewriteRequest, a1.rewriteToken);
        }
        finally {
            v2.close();
            v1.end();
        }
    }
    
    private RewriteResponse rewrite(final RewriteRequest v-3, final String v-2) {
        try {
            String a1 = Option.USER_PROJECT.getString(v-3.sourceOptions);
            if (a1 == null) {
                a1 = Option.USER_PROJECT.getString(v-3.targetOptions);
            }
            final Long a2 = (v-3.megabytesRewrittenPerCall != null) ? Long.valueOf(v-3.megabytesRewrittenPerCall * 1048576L) : null;
            final Storage.Objects.Rewrite v1 = this.storage.objects().rewrite(v-3.source.getBucket(), v-3.source.getName(), v-3.target.getBucket(), v-3.target.getName(), v-3.overrideInfo ? v-3.target : null).setSourceGeneration(v-3.source.getGeneration()).setRewriteToken(v-2).setMaxBytesRewrittenPerCall(a2).setProjection("full").setIfSourceMetagenerationMatch(Option.IF_SOURCE_METAGENERATION_MATCH.getLong(v-3.sourceOptions)).setIfSourceMetagenerationNotMatch(Option.IF_SOURCE_METAGENERATION_NOT_MATCH.getLong(v-3.sourceOptions)).setIfSourceGenerationMatch(Option.IF_SOURCE_GENERATION_MATCH.getLong(v-3.sourceOptions)).setIfSourceGenerationNotMatch(Option.IF_SOURCE_GENERATION_NOT_MATCH.getLong(v-3.sourceOptions)).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(v-3.targetOptions)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(v-3.targetOptions)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(v-3.targetOptions)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(v-3.targetOptions)).setDestinationPredefinedAcl(Option.PREDEFINED_ACL.getString(v-3.targetOptions)).setUserProject(a1).setDestinationKmsKeyName(Option.KMS_KEY_NAME.getString(v-3.targetOptions));
            final HttpHeaders v2 = v1.getRequestHeaders();
            setEncryptionHeaders(v2, "x-goog-copy-source-encryption-", v-3.sourceOptions);
            setEncryptionHeaders(v2, "x-goog-encryption-", v-3.targetOptions);
            final com.google.api.services.storage.model.RewriteResponse v3 = (com.google.api.services.storage.model.RewriteResponse)v1.execute();
            return new RewriteResponse(v-3, v3.getResource(), v3.getObjectSize(), v3.getDone(), v3.getRewriteToken(), v3.getTotalBytesRewritten());
        }
        catch (IOException a3) {
            this.tracer.getCurrentSpan().setStatus(Status.UNKNOWN.withDescription(a3.getMessage()));
            throw translate(a3);
        }
    }
    
    @Override
    public BucketAccessControl getAcl(final String v1, final String v2, final Map<Option, ?> v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET_ACL);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            return (BucketAccessControl)this.storage.bucketAccessControls().get(v1, v2).setUserProject(Option.USER_PROJECT.getString(v3)).execute();
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return null;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public boolean deleteAcl(final String v1, final String v2, final Map<Option, ?> v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_BUCKET_ACL);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            this.storage.bucketAccessControls().delete(v1, v2).setUserProject(Option.USER_PROJECT.getString(v3)).execute();
            return true;
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return false;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public BucketAccessControl createAcl(final BucketAccessControl v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_BUCKET_ACL);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (BucketAccessControl)this.storage.bucketAccessControls().insert(v1.getBucket(), v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public BucketAccessControl patchAcl(final BucketAccessControl v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_BUCKET_ACL);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (BucketAccessControl)this.storage.bucketAccessControls().patch(v1.getBucket(), v1.getEntity(), v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public List<BucketAccessControl> listAcls(final String v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_BUCKET_ACLS);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (List<BucketAccessControl>)((BucketAccessControls)this.storage.bucketAccessControls().list(v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute()).getItems();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public ObjectAccessControl getDefaultAcl(final String v2, final String v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT_DEFAULT_ACL);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            return (ObjectAccessControl)this.storage.defaultObjectAccessControls().get(v2, v3).execute();
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return null;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public boolean deleteDefaultAcl(final String v2, final String v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT_DEFAULT_ACL);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            this.storage.defaultObjectAccessControls().delete(v2, v3).execute();
            return true;
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return false;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public ObjectAccessControl createDefaultAcl(final ObjectAccessControl v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT_DEFAULT_ACL);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (ObjectAccessControl)this.storage.defaultObjectAccessControls().insert(v2.getBucket(), v2).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public ObjectAccessControl patchDefaultAcl(final ObjectAccessControl v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT_DEFAULT_ACL);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (ObjectAccessControl)this.storage.defaultObjectAccessControls().patch(v2.getBucket(), v2.getEntity(), v2).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public List<ObjectAccessControl> listDefaultAcls(final String v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECT_DEFAULT_ACLS);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (List<ObjectAccessControl>)((ObjectAccessControls)this.storage.defaultObjectAccessControls().list(v2).execute()).getItems();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public ObjectAccessControl getAcl(final String a4, final String v1, final Long v2, final String v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT_ACL);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            return (ObjectAccessControl)this.storage.objectAccessControls().get(a4, v1, v3).setGeneration(v2).execute();
        }
        catch (IOException a5) {
            v4.setStatus(Status.UNKNOWN.withDescription(a5.getMessage()));
            final StorageException a6 = translate(a5);
            if (a6.getCode() == 404) {
                return null;
            }
            throw a6;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public boolean deleteAcl(final String a4, final String v1, final Long v2, final String v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT_ACL);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            this.storage.objectAccessControls().delete(a4, v1, v3).setGeneration(v2).execute();
            return true;
        }
        catch (IOException a5) {
            v4.setStatus(Status.UNKNOWN.withDescription(a5.getMessage()));
            final StorageException a6 = translate(a5);
            if (a6.getCode() == 404) {
                return false;
            }
            throw a6;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public ObjectAccessControl createAcl(final ObjectAccessControl v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT_ACL);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (ObjectAccessControl)this.storage.objectAccessControls().insert(v2.getBucket(), v2.getObject(), v2).setGeneration(v2.getGeneration()).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public ObjectAccessControl patchAcl(final ObjectAccessControl v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT_ACL);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (ObjectAccessControl)this.storage.objectAccessControls().patch(v2.getBucket(), v2.getObject(), v2.getEntity(), v2).setGeneration(v2.getGeneration()).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public List<ObjectAccessControl> listAcls(final String a3, final String v1, final Long v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECT_ACLS);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (List<ObjectAccessControl>)((ObjectAccessControls)this.storage.objectAccessControls().list(a3, v1).setGeneration(v2).execute()).getItems();
        }
        catch (IOException a4) {
            v3.setStatus(Status.UNKNOWN.withDescription(a4.getMessage()));
            throw translate(a4);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public HmacKey createHmacKey(final String v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_HMAC_KEY);
        final Scope v4 = this.tracer.withSpan(v3);
        String v5 = Option.PROJECT_ID.getString(v2);
        if (v5 == null) {
            v5 = this.options.getProjectId();
        }
        try {
            return (HmacKey)this.storage.projects().hmacKeys().create(v5, v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public Tuple<String, Iterable<HmacKeyMetadata>> listHmacKeys(final Map<Option, ?> v-3) {
        final Span startSpan = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_HMAC_KEYS);
        final Scope withSpan = this.tracer.withSpan(startSpan);
        String v0 = Option.PROJECT_ID.getString(v-3);
        if (v0 == null) {
            v0 = this.options.getProjectId();
        }
        try {
            final HmacKeysMetadata a1 = (HmacKeysMetadata)this.storage.projects().hmacKeys().list(v0).setServiceAccountEmail(Option.SERVICE_ACCOUNT_EMAIL.getString(v-3)).setPageToken(Option.PAGE_TOKEN.getString(v-3)).setMaxResults(Option.MAX_RESULTS.getLong(v-3)).setShowDeletedKeys(Option.SHOW_DELETED_KEYS.getBoolean(v-3)).execute();
            return (Tuple<String, Iterable<HmacKeyMetadata>>)Tuple.of((Object)a1.getNextPageToken(), (Object)a1.getItems());
        }
        catch (IOException v2) {
            startSpan.setStatus(Status.UNKNOWN.withDescription(v2.getMessage()));
            throw translate(v2);
        }
        finally {
            withSpan.close();
            startSpan.end();
        }
    }
    
    @Override
    public HmacKeyMetadata getHmacKey(final String v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_HMAC_KEY);
        final Scope v4 = this.tracer.withSpan(v3);
        String v5 = Option.PROJECT_ID.getString(v2);
        if (v5 == null) {
            v5 = this.options.getProjectId();
        }
        try {
            return (HmacKeyMetadata)this.storage.projects().hmacKeys().get(v5, v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public HmacKeyMetadata updateHmacKey(final HmacKeyMetadata v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_UPDATE_HMAC_KEY);
        final Scope v4 = this.tracer.withSpan(v3);
        String v5 = v1.getProjectId();
        if (v5 == null) {
            v5 = this.options.getProjectId();
        }
        try {
            return (HmacKeyMetadata)this.storage.projects().hmacKeys().update(v5, v1.getAccessId(), v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public void deleteHmacKey(final HmacKeyMetadata v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_HMAC_KEY);
        final Scope v4 = this.tracer.withSpan(v3);
        String v5 = v1.getProjectId();
        if (v5 == null) {
            v5 = this.options.getProjectId();
        }
        try {
            this.storage.projects().hmacKeys().delete(v5, v1.getAccessId()).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public Policy getIamPolicy(final String v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET_IAM_POLICY);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (Policy)this.storage.buckets().getIamPolicy(v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public Policy setIamPolicy(final String a3, final Policy v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_SET_BUCKET_IAM_POLICY);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (Policy)this.storage.buckets().setIamPolicy(a3, v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a4) {
            v3.setStatus(Status.UNKNOWN.withDescription(a4.getMessage()));
            throw translate(a4);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public TestIamPermissionsResponse testIamPermissions(final String a3, final List<String> v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_TEST_BUCKET_IAM_PERMISSIONS);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (TestIamPermissionsResponse)this.storage.buckets().testIamPermissions(a3, (List)v1).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a4) {
            v3.setStatus(Status.UNKNOWN.withDescription(a4.getMessage()));
            throw translate(a4);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public boolean deleteNotification(final String v2, final String v3) {
        final Span v4 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_NOTIFICATION);
        final Scope v5 = this.tracer.withSpan(v4);
        try {
            this.storage.notifications().delete(v2, v3).execute();
            return true;
        }
        catch (IOException a2) {
            v4.setStatus(Status.UNKNOWN.withDescription(a2.getMessage()));
            final StorageException a3 = translate(a2);
            if (a3.getCode() == 404) {
                return false;
            }
            throw a3;
        }
        finally {
            v5.close();
            v4.end();
        }
    }
    
    @Override
    public List<Notification> listNotifications(final String v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_NOTIFICATIONS);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (List<Notification>)((Notifications)this.storage.notifications().list(v2).execute()).getItems();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public Notification createNotification(final String v1, final Notification v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_NOTIFICATION);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (Notification)this.storage.notifications().insert(v1, v2).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public Bucket lockRetentionPolicy(final Bucket v1, final Map<Option, ?> v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_LOCK_RETENTION_POLICY);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (Bucket)this.storage.buckets().lockRetentionPolicy(v1.getName(), Option.IF_METAGENERATION_MATCH.getLong(v2)).setUserProject(Option.USER_PROJECT.getString(v2)).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    @Override
    public ServiceAccount getServiceAccount(final String v2) {
        final Span v3 = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_SERVICE_ACCOUNT);
        final Scope v4 = this.tracer.withSpan(v3);
        try {
            return (ServiceAccount)this.storage.projects().serviceAccount().get(v2).execute();
        }
        catch (IOException a1) {
            v3.setStatus(Status.UNKNOWN.withDescription(a1.getMessage()));
            throw translate(a1);
        }
        finally {
            v4.close();
            v3.end();
        }
    }
    
    static /* synthetic */ HttpRequestInitializer access$000(final HttpStorageRpc a1) {
        return a1.batchRequestInitializer;
    }
    
    static /* bridge */ JsonBatchCallback access$100(final RpcBatch.Callback a1) {
        return toJsonCallback((RpcBatch.Callback<Object>)a1);
    }
    
    static /* bridge */ Storage.Objects.Delete access$200(final HttpStorageRpc a1, final StorageObject a2, final Map a3) throws IOException {
        return a1.deleteCall(a2, a3);
    }
    
    static /* bridge */ StorageException access$300(final IOException a1) {
        return translate(a1);
    }
    
    static /* bridge */ Storage.Objects.Patch access$400(final HttpStorageRpc a1, final StorageObject a2, final Map a3) throws IOException {
        return a1.patchCall(a2, a3);
    }
    
    static /* bridge */ Storage.Objects.Get access$500(final HttpStorageRpc a1, final StorageObject a2, final Map a3) throws IOException {
        return a1.getCall(a2, a3);
    }
    
    static /* bridge */ Span access$600(final HttpStorageRpc a1, final String a2) {
        return a1.startSpan(a2);
    }
    
    static /* synthetic */ Tracer access$700(final HttpStorageRpc a1) {
        return a1.tracer;
    }
    
    static /* synthetic */ StorageOptions access$800(final HttpStorageRpc a1) {
        return a1.options;
    }
    
    private class DefaultRpcBatch implements RpcBatch
    {
        private static final int MAX_BATCH_SIZE = 100;
        private final Storage storage;
        private final LinkedList<BatchRequest> batches;
        private int currentBatchSize;
        final /* synthetic */ HttpStorageRpc this$0;
        
        private DefaultRpcBatch(final HttpStorageRpc httpStorageRpc, final Storage a1) {
            this.this$0 = httpStorageRpc;
            super();
            this.storage = a1;
            (this.batches = new LinkedList<BatchRequest>()).add(a1.batch(httpStorageRpc.batchRequestInitializer));
        }
        
        @Override
        public void addDelete(final StorageObject a3, final Callback<Void> v1, final Map<Option, ?> v2) {
            try {
                if (this.currentBatchSize == 100) {
                    this.batches.add(this.storage.batch());
                    this.currentBatchSize = 0;
                }
                HttpStorageRpc.this.deleteCall(a3, v2).queue((BatchRequest)this.batches.getLast(), toJsonCallback((RpcBatch.Callback<Object>)v1));
                ++this.currentBatchSize;
            }
            catch (IOException a4) {
                throw translate(a4);
            }
        }
        
        @Override
        public void addPatch(final StorageObject a3, final Callback<StorageObject> v1, final Map<Option, ?> v2) {
            try {
                if (this.currentBatchSize == 100) {
                    this.batches.add(this.storage.batch());
                    this.currentBatchSize = 0;
                }
                HttpStorageRpc.this.patchCall(a3, v2).queue((BatchRequest)this.batches.getLast(), toJsonCallback((RpcBatch.Callback<Object>)v1));
                ++this.currentBatchSize;
            }
            catch (IOException a4) {
                throw translate(a4);
            }
        }
        
        @Override
        public void addGet(final StorageObject a3, final Callback<StorageObject> v1, final Map<Option, ?> v2) {
            try {
                if (this.currentBatchSize == 100) {
                    this.batches.add(this.storage.batch());
                    this.currentBatchSize = 0;
                }
                HttpStorageRpc.this.getCall(a3, v2).queue((BatchRequest)this.batches.getLast(), toJsonCallback((RpcBatch.Callback<Object>)v1));
                ++this.currentBatchSize;
            }
            catch (IOException a4) {
                throw translate(a4);
            }
        }
        
        @Override
        public void submit() {
            final Span access$600 = HttpStorageRpc.this.startSpan(HttpStorageRpcSpans.SPAN_NAME_BATCH_SUBMIT);
            final Scope withSpan = this.this$0.tracer.withSpan(access$600);
            try {
                access$600.putAttribute("batch size", AttributeValue.longAttributeValue((long)this.batches.size()));
                for (final BatchRequest v1 : this.batches) {
                    access$600.addAnnotation("Execute batch request");
                    v1.setBatchUrl(new GenericUrl(String.format("%s/batch/storage/v1", this.this$0.options.getHost())));
                    v1.execute();
                }
            }
            catch (IOException v2) {
                access$600.setStatus(Status.UNKNOWN.withDescription(v2.getMessage()));
                throw translate(v2);
            }
            finally {
                withSpan.close();
                access$600.end();
            }
        }
        
        DefaultRpcBatch(final HttpStorageRpc a1, final Storage a2, final HttpStorageRpc$1 a3) {
            this(a1, a2);
        }
    }
}
