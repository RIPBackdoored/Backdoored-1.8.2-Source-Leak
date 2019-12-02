package com.google.cloud.storage.testing;

import com.google.api.gax.paging.*;
import java.util.concurrent.*;
import com.google.auth.oauth2.*;
import com.google.auth.*;
import com.google.cloud.*;
import java.util.logging.*;
import java.io.*;
import com.google.cloud.http.*;
import com.google.api.gax.retrying.*;
import org.threeten.bp.*;
import com.google.common.base.*;
import com.google.cloud.storage.*;
import java.util.*;

public class RemoteStorageHelper
{
    private static final Logger log;
    private static final String BUCKET_NAME_PREFIX = "gcloud-test-bucket-temp-";
    private final StorageOptions options;
    
    private RemoteStorageHelper(final StorageOptions a1) {
        super();
        this.options = a1;
    }
    
    public StorageOptions getOptions() {
        return this.options;
    }
    
    public static void cleanBuckets(final Storage a2, final long a3, final long v1) {
        final Runnable v2 = new Runnable() {
            final /* synthetic */ Storage val$storage;
            final /* synthetic */ long val$olderThan;
            
            RemoteStorageHelper$1() {
                super();
            }
            
            @Override
            public void run() {
                final Page<Bucket> list = a2.list(Storage.BucketListOption.prefix("gcloud-test-bucket-temp-"));
                for (final Bucket bucket : list.iterateAll()) {
                    if (bucket.getCreateTime() < a3) {
                        try {
                            for (final Blob v1 : bucket.list(Storage.BlobListOption.fields(Storage.BlobField.EVENT_BASED_HOLD, Storage.BlobField.TEMPORARY_HOLD)).iterateAll()) {
                                if (v1.getEventBasedHold() || v1.getTemporaryHold()) {
                                    a2.update(v1.toBuilder().setTemporaryHold(false).setEventBasedHold(false).build());
                                }
                            }
                            RemoteStorageHelper.forceDelete(a2, bucket.getName());
                        }
                        catch (Exception ex) {}
                    }
                }
            }
        };
        final Thread v3 = new Thread(v2);
        v3.start();
        try {
            v3.join(v1);
        }
        catch (InterruptedException a4) {
            RemoteStorageHelper.log.info("cleanBuckets interrupted");
        }
    }
    
    public static Boolean forceDelete(final Storage a1, final String a2, final long a3, final TimeUnit a4) throws InterruptedException, ExecutionException {
        return forceDelete(a1, a2, a3, a4, "");
    }
    
    public static Boolean forceDelete(final Storage a2, final String a3, final long a4, final TimeUnit a5, final String v1) throws InterruptedException, ExecutionException {
        final ExecutorService v2 = Executors.newSingleThreadExecutor();
        final Future<Boolean> v3 = v2.submit((Callable<Boolean>)new DeleteBucketTask(a2, a3, v1));
        try {
            return v3.get(a4, a5);
        }
        catch (TimeoutException a6) {
            return false;
        }
        finally {
            v2.shutdown();
        }
    }
    
    public static void forceDelete(final Storage a1, final String a2) {
        new DeleteBucketTask(a1, a2).call();
    }
    
    public static String generateBucketName() {
        return "gcloud-test-bucket-temp-" + UUID.randomUUID().toString();
    }
    
    public static RemoteStorageHelper create(final String v-1, final InputStream v0) throws StorageHelperException {
        try {
            HttpTransportOptions a1 = StorageOptions.getDefaultHttpTransportOptions();
            a1 = a1.toBuilder().setConnectTimeout(60000).setReadTimeout(60000).build();
            final StorageOptions a2 = ((StorageOptions.Builder)((StorageOptions.Builder)((StorageOptions.Builder)StorageOptions.newBuilder().setCredentials((Credentials)GoogleCredentials.fromStream(v0))).setProjectId(v-1)).setRetrySettings(retrySettings())).setTransportOptions((TransportOptions)a1).build();
            return new RemoteStorageHelper(a2);
        }
        catch (IOException v) {
            if (RemoteStorageHelper.log.isLoggable(Level.WARNING)) {
                RemoteStorageHelper.log.log(Level.WARNING, v.getMessage());
            }
            throw StorageHelperException.translate(v);
        }
    }
    
    public static RemoteStorageHelper create() throws StorageHelperException {
        HttpTransportOptions v1 = StorageOptions.getDefaultHttpTransportOptions();
        v1 = v1.toBuilder().setConnectTimeout(60000).setReadTimeout(60000).build();
        final StorageOptions v2 = ((StorageOptions.Builder)StorageOptions.newBuilder().setRetrySettings(retrySettings())).setTransportOptions((TransportOptions)v1).build();
        return new RemoteStorageHelper(v2);
    }
    
    private static RetrySettings retrySettings() {
        return RetrySettings.newBuilder().setMaxAttempts(10).setMaxRetryDelay(Duration.ofMillis(30000L)).setTotalTimeout(Duration.ofMillis(120000L)).setInitialRetryDelay(Duration.ofMillis(250L)).setRetryDelayMultiplier(1.0).setInitialRpcTimeout(Duration.ofMillis(120000L)).setRpcTimeoutMultiplier(1.0).setMaxRpcTimeout(Duration.ofMillis(120000L)).build();
    }
    
    static {
        log = Logger.getLogger(RemoteStorageHelper.class.getName());
    }
    
    private static class DeleteBucketTask implements Callable<Boolean>
    {
        private final Storage storage;
        private final String bucket;
        private final String userProject;
        
        public DeleteBucketTask(final Storage a1, final String a2) {
            super();
            this.storage = a1;
            this.bucket = a2;
            this.userProject = "";
        }
        
        public DeleteBucketTask(final Storage a1, final String a2, final String a3) {
            super();
            this.storage = a1;
            this.bucket = a2;
            this.userProject = a3;
        }
        
        @Override
        public Boolean call() {
            while (true) {
                final ArrayList<BlobId> v0 = new ArrayList<BlobId>();
                Page<Blob> v2;
                if (Strings.isNullOrEmpty(this.userProject)) {
                    v2 = this.storage.list(this.bucket, Storage.BlobListOption.versions(true));
                }
                else {
                    v2 = this.storage.list(this.bucket, Storage.BlobListOption.versions(true), Storage.BlobListOption.userProject(this.userProject));
                }
                for (final BlobInfo v3 : v2.getValues()) {
                    v0.add(v3.getBlobId());
                }
                if (!v0.isEmpty()) {
                    final List<Boolean> v4 = this.storage.delete(v0);
                    if (!Strings.isNullOrEmpty(this.userProject)) {
                        for (int v5 = 0; v5 < v4.size(); ++v5) {
                            if (!v4.get(v5)) {
                                this.storage.delete(this.bucket, v0.get(v5).getName(), Storage.BlobSourceOption.userProject(this.userProject));
                            }
                        }
                    }
                }
                try {
                    if (Strings.isNullOrEmpty(this.userProject)) {
                        this.storage.delete(this.bucket, new Storage.BucketSourceOption[0]);
                    }
                    else {
                        this.storage.delete(this.bucket, Storage.BucketSourceOption.userProject(this.userProject));
                    }
                    return true;
                }
                catch (StorageException v6) {
                    if (v6.getCode() == 409) {
                        try {
                            Thread.sleep(500L);
                            continue;
                        }
                        catch (InterruptedException v7) {
                            Thread.currentThread().interrupt();
                            throw v6;
                        }
                        throw v6;
                        continue;
                    }
                    throw v6;
                }
            }
        }
        
        @Override
        public /* bridge */ Object call() throws Exception {
            return this.call();
        }
    }
    
    public static class StorageHelperException extends RuntimeException
    {
        private static final long serialVersionUID = -7756074894502258736L;
        
        public StorageHelperException(final String a1) {
            super(a1);
        }
        
        public StorageHelperException(final String a1, final Throwable a2) {
            super(a1, a2);
        }
        
        public static StorageHelperException translate(final Exception a1) {
            return new StorageHelperException(a1.getMessage(), a1);
        }
    }
}
