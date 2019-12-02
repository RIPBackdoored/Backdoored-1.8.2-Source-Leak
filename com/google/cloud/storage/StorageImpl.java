package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.api.gax.retrying.*;
import com.google.common.io.*;
import com.google.common.hash.*;
import com.google.common.primitives.*;
import com.google.api.gax.paging.*;
import java.util.concurrent.*;
import com.google.auth.*;
import com.google.common.base.*;
import com.google.common.net.*;
import java.nio.charset.*;
import java.net.*;
import java.io.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.cloud.*;

final class StorageImpl extends BaseService<StorageOptions> implements Storage
{
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final String EMPTY_BYTE_ARRAY_MD5 = "1B2M2Y8AsgTpgAmY7PhCfg==";
    private static final String EMPTY_BYTE_ARRAY_CRC32C = "AAAAAA==";
    private static final String PATH_DELIMITER = "/";
    private static final String STORAGE_XML_HOST_NAME = "https://storage.googleapis.com";
    private static final Function<Tuple<Storage, Boolean>, Boolean> DELETE_FUNCTION;
    private final StorageRpc storageRpc;
    
    StorageImpl(final StorageOptions a1) {
        super((ServiceOptions)a1);
        this.storageRpc = a1.getStorageRpcV1();
    }
    
    public Bucket create(final BucketInfo v1, final BucketTargetOption... v2) {
        final com.google.api.services.storage.model.Bucket v3 = v1.toPb();
        final Map<StorageRpc.Option, ?> v4 = optionMap(v1, (Option[])v2);
        try {
            return Bucket.fromPb(this, (com.google.api.services.storage.model.Bucket)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Bucket>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$2() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Bucket call() {
                    return this.this$0.storageRpc.create(v3, v4);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public Blob create(final BlobInfo a1, final BlobTargetOption... a2) {
        final BlobInfo v1 = a1.toBuilder().setMd5("1B2M2Y8AsgTpgAmY7PhCfg==").setCrc32c("AAAAAA==").build();
        return this.internalCreate(v1, StorageImpl.EMPTY_BYTE_ARRAY, a2);
    }
    
    public Blob create(final BlobInfo a1, byte[] a2, final BlobTargetOption... a3) {
        a2 = MoreObjects.firstNonNull(a2, StorageImpl.EMPTY_BYTE_ARRAY);
        final BlobInfo v1 = a1.toBuilder().setMd5(BaseEncoding.base64().encode(Hashing.md5().hashBytes(a2).asBytes())).setCrc32c(BaseEncoding.base64().encode(Ints.toByteArray(Hashing.crc32c().hashBytes(a2).asInt()))).build();
        return this.internalCreate(v1, a2, a3);
    }
    
    public Blob create(final BlobInfo a1, byte[] a2, final int a3, final int a4, final BlobTargetOption... a5) {
        a2 = MoreObjects.firstNonNull(a2, StorageImpl.EMPTY_BYTE_ARRAY);
        final byte[] v1 = Arrays.copyOfRange(a2, a3, a3 + a4);
        final BlobInfo v2 = a1.toBuilder().setMd5(BaseEncoding.base64().encode(Hashing.md5().hashBytes(v1).asBytes())).setCrc32c(BaseEncoding.base64().encode(Ints.toByteArray(Hashing.crc32c().hashBytes(v1).asInt()))).build();
        return this.internalCreate(v2, v1, a5);
    }
    
    @Deprecated
    public Blob create(final BlobInfo a1, final InputStream a2, final BlobWriteOption... a3) {
        final Tuple<BlobInfo, BlobTargetOption[]> v1 = BlobTargetOption.convert(a1, a3);
        final StorageObject v2 = ((BlobInfo)v1.x()).toPb();
        final Map<StorageRpc.Option, ?> v3 = optionMap((BlobInfo)v1.x(), (Option[])v1.y());
        final InputStream v4 = MoreObjects.firstNonNull(a2, new ByteArrayInputStream(StorageImpl.EMPTY_BYTE_ARRAY));
        return Blob.fromPb(this, this.storageRpc.create(v2, v4, v3));
    }
    
    private Blob internalCreate(final BlobInfo a3, final byte[] v1, final BlobTargetOption... v2) {
        Preconditions.checkNotNull(v1);
        final StorageObject v3 = a3.toPb();
        final Map<StorageRpc.Option, ?> v4 = optionMap(a3, (Option[])v2);
        try {
            return Blob.fromPb(this, (StorageObject)RetryHelper.runWithRetries((Callable)new Callable<StorageObject>() {
                final /* synthetic */ StorageObject val$blobPb;
                final /* synthetic */ byte[] val$content;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$3() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public StorageObject call() {
                    return this.this$0.storageRpc.create(v3, new ByteArrayInputStream(v1), v4);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a4) {
            throw StorageException.translateAndThrow(a4);
        }
    }
    
    public Bucket get(final String v2, final BucketGetOption... v3) {
        final com.google.api.services.storage.model.Bucket v4 = BucketInfo.of(v2).toPb();
        final Map<StorageRpc.Option, ?> v5 = optionMap((Option[])v3);
        try {
            final com.google.api.services.storage.model.Bucket a1 = (com.google.api.services.storage.model.Bucket)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Bucket>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$4() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Bucket call() {
                    return this.this$0.storageRpc.get(v4, v5);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (a1 == null) ? null : Bucket.fromPb(this, a1);
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public Blob get(final String a1, final String a2, final BlobGetOption... a3) {
        return this.get(BlobId.of(a1, a2), a3);
    }
    
    public Blob get(final BlobId v2, final BlobGetOption... v3) {
        final StorageObject v4 = v2.toPb();
        final Map<StorageRpc.Option, ?> v5 = optionMap(v2, (Option[])v3);
        try {
            final StorageObject a1 = (StorageObject)RetryHelper.runWithRetries((Callable)new Callable<StorageObject>() {
                final /* synthetic */ StorageObject val$storedObject;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$5() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public StorageObject call() {
                    return this.this$0.storageRpc.get(v4, v5);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (a1 == null) ? null : Blob.fromPb(this, a1);
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public Blob get(final BlobId a1) {
        return this.get(a1, new BlobGetOption[0]);
    }
    
    public Page<Bucket> list(final BucketListOption... a1) {
        return listBuckets((StorageOptions)this.getOptions(), optionMap((Option[])a1));
    }
    
    public Page<Blob> list(final String a1, final BlobListOption... a2) {
        return listBlobs(a1, (StorageOptions)this.getOptions(), optionMap((Option[])a2));
    }
    
    private static Page<Bucket> listBuckets(final StorageOptions v-3, final Map<StorageRpc.Option, ?> v-2) {
        try {
            final Tuple<String, Iterable<com.google.api.services.storage.model.Bucket>> a1 = (Tuple<String, Iterable<com.google.api.services.storage.model.Bucket>>)RetryHelper.runWithRetries((Callable)new Callable<Tuple<String, Iterable<com.google.api.services.storage.model.Bucket>>>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                final /* synthetic */ Map val$optionsMap;
                
                StorageImpl$6() {
                    super();
                }
                
                @Override
                public Tuple<String, Iterable<com.google.api.services.storage.model.Bucket>> call() {
                    return v-3.getStorageRpcV1().list(v-2);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, v-3.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, v-3.getClock());
            final String a2 = (String)a1.x();
            final Iterable<Bucket> v1 = (Iterable<Bucket>)((a1.y() == null) ? ImmutableList.of() : Iterables.transform((Iterable<Object>)a1.y(), (Function<? super Object, ?>)new Function<com.google.api.services.storage.model.Bucket, Bucket>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                
                StorageImpl$7() {
                    super();
                }
                
                @Override
                public Bucket apply(final com.google.api.services.storage.model.Bucket a1) {
                    return Bucket.fromPb((Storage)v-3.getService(), a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((com.google.api.services.storage.model.Bucket)o);
                }
            }));
            return (Page<Bucket>)new PageImpl((PageImpl.NextPageFetcher)new BucketPageFetcher(v-3, a2, v-2), a2, (Iterable)v1);
        }
        catch (RetryHelper.RetryHelperException a3) {
            throw StorageException.translateAndThrow(a3);
        }
    }
    
    private static Page<Blob> listBlobs(final String v-2, final StorageOptions v-1, final Map<StorageRpc.Option, ?> v0) {
        try {
            final Tuple<String, Iterable<StorageObject>> a1 = (Tuple<String, Iterable<StorageObject>>)RetryHelper.runWithRetries((Callable)new Callable<Tuple<String, Iterable<StorageObject>>>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Map val$optionsMap;
                
                StorageImpl$8() {
                    super();
                }
                
                @Override
                public Tuple<String, Iterable<StorageObject>> call() {
                    return v-1.getStorageRpcV1().list(v-2, v0);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, v-1.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, v-1.getClock());
            final String a2 = (String)a1.x();
            final Iterable<Blob> a3 = (Iterable<Blob>)((a1.y() == null) ? ImmutableList.of() : Iterables.transform((Iterable<Object>)a1.y(), (Function<? super Object, ?>)new Function<StorageObject, Blob>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                
                StorageImpl$9() {
                    super();
                }
                
                @Override
                public Blob apply(final StorageObject a1) {
                    return Blob.fromPb((Storage)v-1.getService(), a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((StorageObject)o);
                }
            }));
            return (Page<Blob>)new PageImpl((PageImpl.NextPageFetcher)new BlobPageFetcher(v-2, v-1, a2, v0), a2, (Iterable)a3);
        }
        catch (RetryHelper.RetryHelperException v) {
            throw StorageException.translateAndThrow(v);
        }
    }
    
    public Bucket update(final BucketInfo v1, final BucketTargetOption... v2) {
        final com.google.api.services.storage.model.Bucket v3 = v1.toPb();
        final Map<StorageRpc.Option, ?> v4 = optionMap(v1, (Option[])v2);
        try {
            return Bucket.fromPb(this, (com.google.api.services.storage.model.Bucket)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Bucket>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$10() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Bucket call() {
                    return this.this$0.storageRpc.patch(v3, v4);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public Blob update(final BlobInfo v1, final BlobTargetOption... v2) {
        final StorageObject v3 = v1.toPb();
        final Map<StorageRpc.Option, ?> v4 = optionMap(v1, (Option[])v2);
        try {
            return Blob.fromPb(this, (StorageObject)RetryHelper.runWithRetries((Callable)new Callable<StorageObject>() {
                final /* synthetic */ StorageObject val$storageObject;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$11() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public StorageObject call() {
                    return this.this$0.storageRpc.patch(v3, v4);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public Blob update(final BlobInfo a1) {
        return this.update(a1, new BlobTargetOption[0]);
    }
    
    public boolean delete(final String v1, final BucketSourceOption... v2) {
        final com.google.api.services.storage.model.Bucket v3 = BucketInfo.of(v1).toPb();
        final Map<StorageRpc.Option, ?> v4 = optionMap((Option[])v2);
        try {
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$12() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.delete(v3, v4);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public boolean delete(final String a1, final String a2, final BlobSourceOption... a3) {
        return this.delete(BlobId.of(a1, a2), a3);
    }
    
    public boolean delete(final BlobId v1, final BlobSourceOption... v2) {
        final StorageObject v3 = v1.toPb();
        final Map<StorageRpc.Option, ?> v4 = optionMap(v1, (Option[])v2);
        try {
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ StorageObject val$storageObject;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$13() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.delete(v3, v4);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public boolean delete(final BlobId a1) {
        return this.delete(a1, new BlobSourceOption[0]);
    }
    
    public Blob compose(final ComposeRequest v-3) {
        final List<StorageObject> arrayListWithCapacity = (List<StorageObject>)Lists.newArrayListWithCapacity(v-3.getSourceBlobs().size());
        for (final ComposeRequest.SourceBlob a1 : v-3.getSourceBlobs()) {
            arrayListWithCapacity.add(BlobInfo.newBuilder(BlobId.of(v-3.getTarget().getBucket(), a1.getName(), a1.getGeneration())).build().toPb());
        }
        final StorageObject pb = v-3.getTarget().toPb();
        final Map<StorageRpc.Option, ?> v0 = optionMap(v-3.getTarget().getGeneration(), v-3.getTarget().getMetageneration(), v-3.getTargetOptions());
        try {
            return Blob.fromPb(this, (StorageObject)RetryHelper.runWithRetries((Callable)new Callable<StorageObject>() {
                final /* synthetic */ List val$sources;
                final /* synthetic */ StorageObject val$target;
                final /* synthetic */ Map val$targetOptions;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$14() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public StorageObject call() {
                    return this.this$0.storageRpc.compose(arrayListWithCapacity, pb, v0);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException v2) {
            throw StorageException.translateAndThrow(v2);
        }
    }
    
    public CopyWriter copy(final CopyRequest v-4) {
        final StorageObject pb = v-4.getSource().toPb();
        final Map<StorageRpc.Option, ?> optionMap = optionMap(v-4.getSource().getGeneration(), null, v-4.getSourceOptions(), true);
        final StorageObject pb2 = v-4.getTarget().toPb();
        final Map<StorageRpc.Option, ?> v0 = optionMap(v-4.getTarget().getGeneration(), v-4.getTarget().getMetageneration(), v-4.getTargetOptions());
        try {
            final StorageRpc.RewriteResponse a1 = (StorageRpc.RewriteResponse)RetryHelper.runWithRetries((Callable)new Callable<StorageRpc.RewriteResponse>() {
                final /* synthetic */ StorageObject val$source;
                final /* synthetic */ Map val$sourceOptions;
                final /* synthetic */ CopyRequest val$copyRequest;
                final /* synthetic */ StorageObject val$targetObject;
                final /* synthetic */ Map val$targetOptions;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$15() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public StorageRpc.RewriteResponse call() {
                    return this.this$0.storageRpc.openRewrite(new StorageRpc.RewriteRequest(pb, optionMap, v-4.overrideInfo(), pb2, v0, v-4.getMegabytesCopiedPerChunk()));
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return new CopyWriter((StorageOptions)this.getOptions(), a1);
        }
        catch (RetryHelper.RetryHelperException v2) {
            throw StorageException.translateAndThrow(v2);
        }
    }
    
    public byte[] readAllBytes(final String a1, final String a2, final BlobSourceOption... a3) {
        return this.readAllBytes(BlobId.of(a1, a2), a3);
    }
    
    public byte[] readAllBytes(final BlobId v1, final BlobSourceOption... v2) {
        final StorageObject v3 = v1.toPb();
        final Map<StorageRpc.Option, ?> v4 = optionMap(v1, (Option[])v2);
        try {
            return (byte[])RetryHelper.runWithRetries((Callable)new Callable<byte[]>() {
                final /* synthetic */ StorageObject val$storageObject;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$16() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public byte[] call() {
                    return this.this$0.storageRpc.load(v3, v4);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public StorageBatch batch() {
        return new StorageBatch((StorageOptions)this.getOptions());
    }
    
    public ReadChannel reader(final String a1, final String a2, final BlobSourceOption... a3) {
        final Map<StorageRpc.Option, ?> v1 = optionMap((Option[])a3);
        return (ReadChannel)new BlobReadChannel((StorageOptions)this.getOptions(), BlobId.of(a1, a2), v1);
    }
    
    public ReadChannel reader(final BlobId a1, final BlobSourceOption... a2) {
        final Map<StorageRpc.Option, ?> v1 = optionMap(a1, (Option[])a2);
        return (ReadChannel)new BlobReadChannel((StorageOptions)this.getOptions(), a1, v1);
    }
    
    public BlobWriteChannel writer(final BlobInfo a1, final BlobWriteOption... a2) {
        final Tuple<BlobInfo, BlobTargetOption[]> v1 = BlobTargetOption.convert(a1, a2);
        return this.writer((BlobInfo)v1.x(), (BlobTargetOption[])v1.y());
    }
    
    public BlobWriteChannel writer(final URL a1) {
        return new BlobWriteChannel((StorageOptions)this.getOptions(), a1);
    }
    
    private BlobWriteChannel writer(final BlobInfo a1, final BlobTargetOption... a2) {
        final Map<StorageRpc.Option, ?> v1 = optionMap(a1, (Option[])a2);
        return new BlobWriteChannel((StorageOptions)this.getOptions(), a1, v1);
    }
    
    public URL signUrl(final BlobInfo v-19, final long v-18, final TimeUnit v-16, final SignUrlOption... v-15) {
        final EnumMap<SignUrlOption.Option, Object> enumMap = (EnumMap<SignUrlOption.Option, Object>)Maps.newEnumMap((Class)SignUrlOption.Option.class);
        for (final SignUrlOption a1 : v-15) {
            enumMap.put(a1.getOption(), a1.getValue());
        }
        final boolean equals = SignUrlOption.SignatureVersion.V4.equals(enumMap.get(SignUrlOption.Option.SIGNATURE_VERSION));
        ServiceAccountSigner serviceAccountSigner = enumMap.get(SignUrlOption.Option.SERVICE_ACCOUNT_CRED);
        if (serviceAccountSigner == null) {
            Preconditions.checkState(((StorageOptions)this.getOptions()).getCredentials() instanceof ServiceAccountSigner, "Signing key was not provided and could not be derived");
            serviceAccountSigner = (ServiceAccountSigner)((StorageOptions)this.getOptions()).getCredentials();
        }
        final long a5 = equals ? TimeUnit.SECONDS.convert(v-16.toMillis(v-18), TimeUnit.MILLISECONDS) : TimeUnit.SECONDS.convert(((StorageOptions)this.getOptions()).getClock().millisTime() + v-16.toMillis(v-18), TimeUnit.MILLISECONDS);
        final String s = (enumMap.get(SignUrlOption.Option.HOST_NAME) != null) ? enumMap.get(SignUrlOption.Option.HOST_NAME) : "https://storage.googleapis.com";
        final String trim = CharMatcher.anyOf((CharSequence)"/").trimFrom((CharSequence)v-19.getBucket());
        String replace = "";
        if (!Strings.isNullOrEmpty(v-19.getName())) {
            replace = UrlEscapers.urlFragmentEscaper().escape(v-19.getName()).replace("?", "%3F").replace(";", "%3B");
        }
        final String constructResourceUriPath = this.constructResourceUriPath(trim, replace);
        final URI create = URI.create(constructResourceUriPath);
        try {
            final SignatureInfo buildSignatureInfo = this.buildSignatureInfo(enumMap, v-19, a5, create, serviceAccountSigner.getAccount());
            final String constructUnsignedPayload = buildSignatureInfo.constructUnsignedPayload();
            final byte[] sign = serviceAccountSigner.sign(constructUnsignedPayload.getBytes(StandardCharsets.UTF_8));
            final StringBuilder sb = new StringBuilder();
            sb.append(s).append(create);
            if (equals) {
                final BaseEncoding a2 = BaseEncoding.base16().lowerCase();
                final String a3 = URLEncoder.encode(a2.encode(sign), StandardCharsets.UTF_8.name());
                sb.append("?");
                sb.append(buildSignatureInfo.constructV4QueryString());
                sb.append("&X-Goog-Signature=").append(a3);
            }
            else {
                final BaseEncoding a4 = BaseEncoding.base64();
                final String v1 = URLEncoder.encode(a4.encode(sign), StandardCharsets.UTF_8.name());
                sb.append("?");
                sb.append("GoogleAccessId=").append(serviceAccountSigner.getAccount());
                sb.append("&Expires=").append(a5);
                sb.append("&Signature=").append(v1);
            }
            return new URL(sb.toString());
        }
        catch (MalformedURLException | UnsupportedEncodingException ex3) {
            final IOException ex2;
            final IOException ex = ex2;
            throw new IllegalStateException(ex);
        }
    }
    
    private String constructResourceUriPath(final String a1, final String a2) {
        final StringBuilder v1 = new StringBuilder();
        v1.append("/").append(a1);
        if (Strings.isNullOrEmpty(a2)) {
            return v1.toString();
        }
        if (!a2.startsWith("/")) {
            v1.append("/");
        }
        v1.append(a2);
        return v1.toString();
    }
    
    private SignatureInfo buildSignatureInfo(final Map<SignUrlOption.Option, Object> a1, final BlobInfo a2, final long a3, final URI a4, final String a5) {
        final HttpMethod v1 = a1.containsKey(SignUrlOption.Option.HTTP_METHOD) ? a1.get(SignUrlOption.Option.HTTP_METHOD) : HttpMethod.GET;
        final SignatureInfo.Builder v2 = new SignatureInfo.Builder(v1, a3, a4);
        if (MoreObjects.firstNonNull(a1.get(SignUrlOption.Option.MD5), false)) {
            Preconditions.checkArgument(a2.getMd5() != null, (Object)"Blob is missing a value for md5");
            v2.setContentMd5(a2.getMd5());
        }
        if (MoreObjects.firstNonNull(a1.get(SignUrlOption.Option.CONTENT_TYPE), false)) {
            Preconditions.checkArgument(a2.getContentType() != null, (Object)"Blob is missing a value for content-type");
            v2.setContentType(a2.getContentType());
        }
        v2.setSignatureVersion(a1.get(SignUrlOption.Option.SIGNATURE_VERSION));
        v2.setAccountEmail(a5);
        v2.setTimestamp(((StorageOptions)this.getOptions()).getClock().millisTime());
        final Map<String, String> v3 = a1.containsKey(SignUrlOption.Option.EXT_HEADERS) ? a1.get(SignUrlOption.Option.EXT_HEADERS) : Collections.emptyMap();
        return v2.setCanonicalizedExtensionHeaders(v3).build();
    }
    
    public List<Blob> get(final BlobId... a1) {
        return this.get(Arrays.asList(a1));
    }
    
    public List<Blob> get(final Iterable<BlobId> v2) {
        final StorageBatch v3 = this.batch();
        final List<Blob> v4 = (List<Blob>)Lists.newArrayList();
        for (final BlobId a1 : v2) {
            v3.get(a1, new BlobGetOption[0]).notify((BatchResult.Callback)new BatchResult.Callback<Blob, StorageException>() {
                final /* synthetic */ List val$results;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$17() {
                    this.this$0 = a1;
                    super();
                }
                
                public void success(final Blob a1) {
                    v4.add(a1);
                }
                
                public void error(final StorageException a1) {
                    v4.add(null);
                }
                
                public /* bridge */ void error(final Object o) {
                    this.error((StorageException)o);
                }
                
                public /* bridge */ void success(final Object o) {
                    this.success((Blob)o);
                }
            });
        }
        v3.submit();
        return Collections.unmodifiableList((List<? extends Blob>)v4);
    }
    
    public List<Blob> update(final BlobInfo... a1) {
        return this.update(Arrays.asList(a1));
    }
    
    public List<Blob> update(final Iterable<BlobInfo> v2) {
        final StorageBatch v3 = this.batch();
        final List<Blob> v4 = (List<Blob>)Lists.newArrayList();
        for (final BlobInfo a1 : v2) {
            v3.update(a1, new BlobTargetOption[0]).notify((BatchResult.Callback)new BatchResult.Callback<Blob, StorageException>() {
                final /* synthetic */ List val$results;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$18() {
                    this.this$0 = a1;
                    super();
                }
                
                public void success(final Blob a1) {
                    v4.add(a1);
                }
                
                public void error(final StorageException a1) {
                    v4.add(null);
                }
                
                public /* bridge */ void error(final Object o) {
                    this.error((StorageException)o);
                }
                
                public /* bridge */ void success(final Object o) {
                    this.success((Blob)o);
                }
            });
        }
        v3.submit();
        return Collections.unmodifiableList((List<? extends Blob>)v4);
    }
    
    public List<Boolean> delete(final BlobId... a1) {
        return this.delete(Arrays.asList(a1));
    }
    
    public List<Boolean> delete(final Iterable<BlobId> v2) {
        final StorageBatch v3 = this.batch();
        final List<Boolean> v4 = (List<Boolean>)Lists.newArrayList();
        for (final BlobId a1 : v2) {
            v3.delete(a1, new BlobSourceOption[0]).notify((BatchResult.Callback)new BatchResult.Callback<Boolean, StorageException>() {
                final /* synthetic */ List val$results;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$19() {
                    this.this$0 = a1;
                    super();
                }
                
                public void success(final Boolean a1) {
                    v4.add(a1);
                }
                
                public void error(final StorageException a1) {
                    v4.add(Boolean.FALSE);
                }
                
                public /* bridge */ void error(final Object o) {
                    this.error((StorageException)o);
                }
                
                public /* bridge */ void success(final Object o) {
                    this.success((Boolean)o);
                }
            });
        }
        v3.submit();
        return Collections.unmodifiableList((List<? extends Boolean>)v4);
    }
    
    public Acl getAcl(final String v2, final Acl.Entity v3, final BucketSourceOption... v4) {
        try {
            final Map<StorageRpc.Option, ?> a1 = optionMap((Option[])v4);
            final BucketAccessControl a2 = (BucketAccessControl)RetryHelper.runWithRetries((Callable)new Callable<BucketAccessControl>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$20() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public BucketAccessControl call() {
                    return this.this$0.storageRpc.getAcl(v2, v3.toPb(), a1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (a2 == null) ? null : Acl.fromPb(a2);
        }
        catch (RetryHelper.RetryHelperException a3) {
            throw StorageException.translateAndThrow(a3);
        }
    }
    
    public Acl getAcl(final String a1, final Acl.Entity a2) {
        return this.getAcl(a1, a2, new BucketSourceOption[0]);
    }
    
    public boolean deleteAcl(final String v1, final Acl.Entity v2, final BucketSourceOption... v3) {
        try {
            final Map<StorageRpc.Option, ?> a1 = optionMap((Option[])v3);
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$21() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.deleteAcl(v1, v2.toPb(), a1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public boolean deleteAcl(final String a1, final Acl.Entity a2) {
        return this.deleteAcl(a1, a2, new BucketSourceOption[0]);
    }
    
    public Acl createAcl(final String v1, final Acl v2, final BucketSourceOption... v3) {
        final BucketAccessControl v4 = v2.toBucketPb().setBucket(v1);
        try {
            final Map<StorageRpc.Option, ?> a1 = optionMap((Option[])v3);
            return Acl.fromPb((BucketAccessControl)RetryHelper.runWithRetries((Callable)new Callable<BucketAccessControl>() {
                final /* synthetic */ BucketAccessControl val$aclPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$22() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public BucketAccessControl call() {
                    return this.this$0.storageRpc.createAcl(v4, a1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public Acl createAcl(final String a1, final Acl a2) {
        return this.createAcl(a1, a2, new BucketSourceOption[0]);
    }
    
    public Acl updateAcl(final String v1, final Acl v2, final BucketSourceOption... v3) {
        final BucketAccessControl v4 = v2.toBucketPb().setBucket(v1);
        try {
            final Map<StorageRpc.Option, ?> a1 = optionMap((Option[])v3);
            return Acl.fromPb((BucketAccessControl)RetryHelper.runWithRetries((Callable)new Callable<BucketAccessControl>() {
                final /* synthetic */ BucketAccessControl val$aclPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$23() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public BucketAccessControl call() {
                    return this.this$0.storageRpc.patchAcl(v4, a1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public Acl updateAcl(final String a1, final Acl a2) {
        return this.updateAcl(a1, a2, new BucketSourceOption[0]);
    }
    
    public List<Acl> listAcls(final String v-1, final BucketSourceOption... v0) {
        try {
            final Map<StorageRpc.Option, ?> a1 = optionMap((Option[])v0);
            final List<BucketAccessControl> a2 = (List<BucketAccessControl>)RetryHelper.runWithRetries((Callable)new Callable<List<BucketAccessControl>>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$24() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public List<BucketAccessControl> call() {
                    return this.this$0.storageRpc.listAcls(v-1, a1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return Lists.transform(a2, (Function<? super BucketAccessControl, ? extends Acl>)Acl.FROM_BUCKET_PB_FUNCTION);
        }
        catch (RetryHelper.RetryHelperException v) {
            throw StorageException.translateAndThrow(v);
        }
    }
    
    public List<Acl> listAcls(final String a1) {
        return this.listAcls(a1, new BucketSourceOption[0]);
    }
    
    public Acl getDefaultAcl(final String v2, final Acl.Entity v3) {
        try {
            final ObjectAccessControl a1 = (ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$25() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.getDefaultAcl(v2, v3.toPb());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (a1 == null) ? null : Acl.fromPb(a1);
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public boolean deleteDefaultAcl(final String v1, final Acl.Entity v2) {
        try {
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$26() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.deleteDefaultAcl(v1, v2.toPb());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public Acl createDefaultAcl(final String v1, final Acl v2) {
        final ObjectAccessControl v3 = v2.toObjectPb().setBucket(v1);
        try {
            return Acl.fromPb((ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ ObjectAccessControl val$aclPb;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$27() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.createDefaultAcl(v3);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public Acl updateDefaultAcl(final String v1, final Acl v2) {
        final ObjectAccessControl v3 = v2.toObjectPb().setBucket(v1);
        try {
            return Acl.fromPb((ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ ObjectAccessControl val$aclPb;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$28() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.patchDefaultAcl(v3);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public List<Acl> listDefaultAcls(final String v0) {
        try {
            final List<ObjectAccessControl> a1 = (List<ObjectAccessControl>)RetryHelper.runWithRetries((Callable)new Callable<List<ObjectAccessControl>>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$29() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public List<ObjectAccessControl> call() {
                    return this.this$0.storageRpc.listDefaultAcls(v0);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return Lists.transform(a1, (Function<? super ObjectAccessControl, ? extends Acl>)Acl.FROM_OBJECT_PB_FUNCTION);
        }
        catch (RetryHelper.RetryHelperException v) {
            throw StorageException.translateAndThrow(v);
        }
    }
    
    public Acl getAcl(final BlobId v2, final Acl.Entity v3) {
        try {
            final ObjectAccessControl a1 = (ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ BlobId val$blob;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$30() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.getAcl(v2.getBucket(), v2.getName(), v2.getGeneration(), v3.toPb());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (a1 == null) ? null : Acl.fromPb(a1);
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public boolean deleteAcl(final BlobId v1, final Acl.Entity v2) {
        try {
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ BlobId val$blob;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$31() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.deleteAcl(v1.getBucket(), v1.getName(), v1.getGeneration(), v2.toPb());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public Acl createAcl(final BlobId v1, final Acl v2) {
        final ObjectAccessControl v3 = v2.toObjectPb().setBucket(v1.getBucket()).setObject(v1.getName()).setGeneration(v1.getGeneration());
        try {
            return Acl.fromPb((ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ ObjectAccessControl val$aclPb;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$32() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.createAcl(v3);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public Acl updateAcl(final BlobId v1, final Acl v2) {
        final ObjectAccessControl v3 = v2.toObjectPb().setBucket(v1.getBucket()).setObject(v1.getName()).setGeneration(v1.getGeneration());
        try {
            return Acl.fromPb((ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ ObjectAccessControl val$aclPb;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$33() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.patchAcl(v3);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public List<Acl> listAcls(final BlobId v0) {
        try {
            final List<ObjectAccessControl> a1 = (List<ObjectAccessControl>)RetryHelper.runWithRetries((Callable)new Callable<List<ObjectAccessControl>>() {
                final /* synthetic */ BlobId val$blob;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$34() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public List<ObjectAccessControl> call() {
                    return this.this$0.storageRpc.listAcls(v0.getBucket(), v0.getName(), v0.getGeneration());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return Lists.transform(a1, (Function<? super ObjectAccessControl, ? extends Acl>)Acl.FROM_OBJECT_PB_FUNCTION);
        }
        catch (RetryHelper.RetryHelperException v) {
            throw StorageException.translateAndThrow(v);
        }
    }
    
    public HmacKey createHmacKey(final ServiceAccount v1, final CreateHmacKeyOption... v2) {
        try {
            return HmacKey.fromPb((com.google.api.services.storage.model.HmacKey)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.HmacKey>() {
                final /* synthetic */ ServiceAccount val$serviceAccount;
                final /* synthetic */ CreateHmacKeyOption[] val$options;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$35() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.HmacKey call() {
                    return this.this$0.storageRpc.createHmacKey(v1.getEmail(), optionMap((Option[])v2));
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public Page<HmacKey.HmacKeyMetadata> listHmacKeys(final ListHmacKeysOption... a1) {
        return listHmacKeys((StorageOptions)this.getOptions(), optionMap((Option[])a1));
    }
    
    public HmacKey.HmacKeyMetadata getHmacKey(final String v1, final GetHmacKeyOption... v2) {
        try {
            return HmacKey.HmacKeyMetadata.fromPb((HmacKeyMetadata)RetryHelper.runWithRetries((Callable)new Callable<HmacKeyMetadata>() {
                final /* synthetic */ String val$accessId;
                final /* synthetic */ GetHmacKeyOption[] val$options;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$36() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public HmacKeyMetadata call() {
                    return this.this$0.storageRpc.getHmacKey(v1, optionMap((Option[])v2));
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    private HmacKey.HmacKeyMetadata updateHmacKey(final HmacKey.HmacKeyMetadata v1, final UpdateHmacKeyOption... v2) {
        try {
            return HmacKey.HmacKeyMetadata.fromPb((HmacKeyMetadata)RetryHelper.runWithRetries((Callable)new Callable<HmacKeyMetadata>() {
                final /* synthetic */ HmacKey.HmacKeyMetadata val$hmacKeyMetadata;
                final /* synthetic */ UpdateHmacKeyOption[] val$options;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$37() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public HmacKeyMetadata call() {
                    return this.this$0.storageRpc.updateHmacKey(v1.toPb(), optionMap((Option[])v2));
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public HmacKey.HmacKeyMetadata updateHmacKeyState(final HmacKey.HmacKeyMetadata a1, final HmacKey.HmacKeyState a2, final UpdateHmacKeyOption... a3) {
        final HmacKey.HmacKeyMetadata v1 = HmacKey.HmacKeyMetadata.newBuilder(a1.getServiceAccount()).setProjectId(a1.getProjectId()).setAccessId(a1.getAccessId()).setState(a2).build();
        return this.updateHmacKey(v1, a3);
    }
    
    public void deleteHmacKey(final HmacKey.HmacKeyMetadata v1, final DeleteHmacKeyOption... v2) {
        try {
            RetryHelper.runWithRetries((Callable)new Callable<Void>() {
                final /* synthetic */ HmacKey.HmacKeyMetadata val$metadata;
                final /* synthetic */ DeleteHmacKeyOption[] val$options;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$38() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Void call() {
                    this.this$0.storageRpc.deleteHmacKey(v1.toPb(), optionMap((Option[])v2));
                    return null;
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    private static Page<HmacKey.HmacKeyMetadata> listHmacKeys(final StorageOptions v-3, final Map<StorageRpc.Option, ?> v-2) {
        try {
            final Tuple<String, Iterable<HmacKeyMetadata>> a1 = (Tuple<String, Iterable<HmacKeyMetadata>>)RetryHelper.runWithRetries((Callable)new Callable<Tuple<String, Iterable<HmacKeyMetadata>>>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                final /* synthetic */ Map val$options;
                
                StorageImpl$39() {
                    super();
                }
                
                @Override
                public Tuple<String, Iterable<HmacKeyMetadata>> call() {
                    return v-3.getStorageRpcV1().listHmacKeys(v-2);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, v-3.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, v-3.getClock());
            final String a2 = (String)a1.x();
            final Iterable<HmacKey.HmacKeyMetadata> v1 = (Iterable<HmacKey.HmacKeyMetadata>)((a1.y() == null) ? ImmutableList.of() : Iterables.transform((Iterable<Object>)a1.y(), (Function<? super Object, ?>)new Function<HmacKeyMetadata, HmacKey.HmacKeyMetadata>() {
                StorageImpl$40() {
                    super();
                }
                
                @Override
                public HmacKey.HmacKeyMetadata apply(final HmacKeyMetadata a1) {
                    return HmacKey.HmacKeyMetadata.fromPb(a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((HmacKeyMetadata)o);
                }
            }));
            return (Page<HmacKey.HmacKeyMetadata>)new PageImpl((PageImpl.NextPageFetcher)new HmacKeyMetadataPageFetcher(v-3, v-2), a2, (Iterable)v1);
        }
        catch (RetryHelper.RetryHelperException a3) {
            throw StorageException.translateAndThrow(a3);
        }
    }
    
    public Policy getIamPolicy(final String v2, final BucketSourceOption... v3) {
        try {
            final Map<StorageRpc.Option, ?> a1 = optionMap((Option[])v3);
            return PolicyHelper.convertFromApiPolicy((com.google.api.services.storage.model.Policy)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Policy>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$41() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Policy call() {
                    return this.this$0.storageRpc.getIamPolicy(v2, a1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public Policy setIamPolicy(final String v1, final Policy v2, final BucketSourceOption... v3) {
        try {
            final Map<StorageRpc.Option, ?> a1 = optionMap((Option[])v3);
            return PolicyHelper.convertFromApiPolicy((com.google.api.services.storage.model.Policy)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Policy>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Policy val$policy;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$42() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Policy call() {
                    return this.this$0.storageRpc.setIamPolicy(v1, PolicyHelper.convertToApiPolicy(v2), a1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a2) {
            throw StorageException.translateAndThrow(a2);
        }
    }
    
    public List<Boolean> testIamPermissions(final String v-2, final List<String> v-1, final BucketSourceOption... v0) {
        try {
            final Map<StorageRpc.Option, ?> a1 = optionMap((Option[])v0);
            final TestIamPermissionsResponse a2 = (TestIamPermissionsResponse)RetryHelper.runWithRetries((Callable)new Callable<TestIamPermissionsResponse>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ List val$permissions;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$43() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public TestIamPermissionsResponse call() {
                    return this.this$0.storageRpc.testIamPermissions(v-2, v-1, a1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            final Set<String> a3 = (Set<String>)((a2.getPermissions() != null) ? ImmutableSet.copyOf((Collection<?>)a2.getPermissions()) : ImmutableSet.of());
            return Lists.transform(v-1, (Function<? super String, ? extends Boolean>)new Function<String, Boolean>() {
                final /* synthetic */ Set val$heldPermissions;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$44() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Boolean apply(final String a1) {
                    return a3.contains(a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            });
        }
        catch (RetryHelper.RetryHelperException v) {
            throw StorageException.translateAndThrow(v);
        }
    }
    
    public Bucket lockRetentionPolicy(final BucketInfo v1, final BucketTargetOption... v2) {
        final com.google.api.services.storage.model.Bucket v3 = v1.toPb();
        final Map<StorageRpc.Option, ?> v4 = optionMap(v1, (Option[])v2);
        try {
            return Bucket.fromPb(this, (com.google.api.services.storage.model.Bucket)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Bucket>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$45() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Bucket call() {
                    return this.this$0.storageRpc.lockRetentionPolicy(v3, v4);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    public ServiceAccount getServiceAccount(final String v0) {
        try {
            final com.google.api.services.storage.model.ServiceAccount a1 = (com.google.api.services.storage.model.ServiceAccount)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.ServiceAccount>() {
                final /* synthetic */ String val$projectId;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$46() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.ServiceAccount call() {
                    return this.this$0.storageRpc.getServiceAccount(v0);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (a1 == null) ? null : ServiceAccount.fromPb(a1);
        }
        catch (RetryHelper.RetryHelperException v) {
            throw StorageException.translateAndThrow(v);
        }
    }
    
    private static <T> void addToOptionMap(final StorageRpc.Option a1, final T a2, final Map<StorageRpc.Option, Object> a3) {
        addToOptionMap(a1, a1, a2, a3);
    }
    
    private static <T> void addToOptionMap(final StorageRpc.Option a2, final StorageRpc.Option a3, final T a4, final Map<StorageRpc.Option, Object> v1) {
        if (v1.containsKey(a2)) {
            T a5 = (T)v1.remove(a2);
            Preconditions.checkArgument(a5 != null || a4 != null, (Object)("Option " + a2.value() + " is missing a value"));
            a5 = MoreObjects.firstNonNull(a5, a4);
            v1.put(a3, a5);
        }
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final Long a1, final Long a2, final Iterable<? extends Option> a3) {
        return optionMap(a1, a2, a3, false);
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final Long a3, final Long a4, final Iterable<? extends Option> v1, final boolean v2) {
        final Map<StorageRpc.Option, Object> v3 = (Map<StorageRpc.Option, Object>)Maps.newEnumMap((Class)StorageRpc.Option.class);
        for (final Option a5 : v1) {
            final Object a6 = v3.put(a5.getRpcOption(), a5.getValue());
            Preconditions.checkArgument(a6 == null, "Duplicate option %s", (Object)a5);
        }
        final Boolean v4 = v3.remove(StorageRpc.Option.DELIMITER);
        if (Boolean.TRUE.equals(v4)) {
            v3.put(StorageRpc.Option.DELIMITER, "/");
        }
        if (v2) {
            addToOptionMap(StorageRpc.Option.IF_GENERATION_MATCH, StorageRpc.Option.IF_SOURCE_GENERATION_MATCH, a3, v3);
            addToOptionMap(StorageRpc.Option.IF_GENERATION_NOT_MATCH, StorageRpc.Option.IF_SOURCE_GENERATION_NOT_MATCH, a3, v3);
            addToOptionMap(StorageRpc.Option.IF_METAGENERATION_MATCH, StorageRpc.Option.IF_SOURCE_METAGENERATION_MATCH, a4, v3);
            addToOptionMap(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, StorageRpc.Option.IF_SOURCE_METAGENERATION_NOT_MATCH, a4, v3);
        }
        else {
            addToOptionMap(StorageRpc.Option.IF_GENERATION_MATCH, a3, v3);
            addToOptionMap(StorageRpc.Option.IF_GENERATION_NOT_MATCH, a3, v3);
            addToOptionMap(StorageRpc.Option.IF_METAGENERATION_MATCH, a4, v3);
            addToOptionMap(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a4, v3);
        }
        return (Map<StorageRpc.Option, ?>)ImmutableMap.copyOf((Map)v3);
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final Option... a1) {
        return optionMap(null, null, Arrays.asList(a1));
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final Long a1, final Long a2, final Option... a3) {
        return optionMap(a1, a2, Arrays.asList(a3));
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final BucketInfo a1, final Option... a2) {
        return optionMap(null, a1.getMetageneration(), a2);
    }
    
    static Map<StorageRpc.Option, ?> optionMap(final BlobInfo a1, final Option... a2) {
        return optionMap(a1.getGeneration(), a1.getMetageneration(), a2);
    }
    
    static Map<StorageRpc.Option, ?> optionMap(final BlobId a1, final Option... a2) {
        return optionMap(a1.getGeneration(), null, a2);
    }
    
    public /* bridge */ WriteChannel writer(final URL a1) {
        return (WriteChannel)this.writer(a1);
    }
    
    public /* bridge */ WriteChannel writer(final BlobInfo a1, final BlobWriteOption[] a2) {
        return (WriteChannel)this.writer(a1, a2);
    }
    
    static /* synthetic */ StorageRpc access$000(final StorageImpl a1) {
        return a1.storageRpc;
    }
    
    static /* bridge */ Page access$100(final StorageOptions a1, final Map a2) {
        return listBuckets(a1, a2);
    }
    
    static /* bridge */ Page access$200(final String a1, final StorageOptions a2, final Map a3) {
        return listBlobs(a1, a2, a3);
    }
    
    static /* bridge */ Page access$300(final StorageOptions a1, final Map a2) {
        return listHmacKeys(a1, a2);
    }
    
    static /* bridge */ Map access$400(final Option[] a1) {
        return optionMap(a1);
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
        DELETE_FUNCTION = new Function<Tuple<Storage, Boolean>, Boolean>() {
            StorageImpl$1() {
                super();
            }
            
            @Override
            public Boolean apply(final Tuple<Storage, Boolean> a1) {
                return (Boolean)a1.y();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Tuple<Storage, Boolean>)o);
            }
        };
    }
    
    private static class BucketPageFetcher implements PageImpl.NextPageFetcher<Bucket>
    {
        private static final long serialVersionUID = 5850406828803613729L;
        private final Map<StorageRpc.Option, ?> requestOptions;
        private final StorageOptions serviceOptions;
        
        BucketPageFetcher(final StorageOptions a1, final String a2, final Map<StorageRpc.Option, ?> a3) {
            super();
            this.requestOptions = (Map<StorageRpc.Option, ?>)PageImpl.nextRequestOptions((Object)StorageRpc.Option.PAGE_TOKEN, a2, (Map)a3);
            this.serviceOptions = a1;
        }
        
        public Page<Bucket> getNextPage() {
            return listBuckets(this.serviceOptions, this.requestOptions);
        }
    }
    
    private static class BlobPageFetcher implements PageImpl.NextPageFetcher<Blob>
    {
        private static final long serialVersionUID = 81807334445874098L;
        private final Map<StorageRpc.Option, ?> requestOptions;
        private final StorageOptions serviceOptions;
        private final String bucket;
        
        BlobPageFetcher(final String a1, final StorageOptions a2, final String a3, final Map<StorageRpc.Option, ?> a4) {
            super();
            this.requestOptions = (Map<StorageRpc.Option, ?>)PageImpl.nextRequestOptions((Object)StorageRpc.Option.PAGE_TOKEN, a3, (Map)a4);
            this.serviceOptions = a2;
            this.bucket = a1;
        }
        
        public Page<Blob> getNextPage() {
            return listBlobs(this.bucket, this.serviceOptions, this.requestOptions);
        }
    }
    
    private static class HmacKeyMetadataPageFetcher implements PageImpl.NextPageFetcher<HmacKey.HmacKeyMetadata>
    {
        private static final long serialVersionUID = 308012320541700881L;
        private final StorageOptions serviceOptions;
        private final Map<StorageRpc.Option, ?> options;
        
        HmacKeyMetadataPageFetcher(final StorageOptions a1, final Map<StorageRpc.Option, ?> a2) {
            super();
            this.serviceOptions = a1;
            this.options = a2;
        }
        
        public Page<HmacKey.HmacKeyMetadata> getNextPage() {
            return listHmacKeys(this.serviceOptions, this.options);
        }
    }
}
