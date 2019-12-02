package com.google.cloud.storage.spi.v1;

import com.google.api.core.*;
import com.google.cloud.*;
import java.io.*;
import com.google.api.services.storage.model.*;
import java.util.*;

@InternalApi
public interface StorageRpc extends ServiceRpc
{
    Bucket create(final Bucket p0, final Map<Option, ?> p1);
    
    StorageObject create(final StorageObject p0, final InputStream p1, final Map<Option, ?> p2);
    
    Tuple<String, Iterable<Bucket>> list(final Map<Option, ?> p0);
    
    Tuple<String, Iterable<StorageObject>> list(final String p0, final Map<Option, ?> p1);
    
    Bucket get(final Bucket p0, final Map<Option, ?> p1);
    
    StorageObject get(final StorageObject p0, final Map<Option, ?> p1);
    
    Bucket patch(final Bucket p0, final Map<Option, ?> p1);
    
    StorageObject patch(final StorageObject p0, final Map<Option, ?> p1);
    
    boolean delete(final Bucket p0, final Map<Option, ?> p1);
    
    boolean delete(final StorageObject p0, final Map<Option, ?> p1);
    
    RpcBatch createBatch();
    
    StorageObject compose(final Iterable<StorageObject> p0, final StorageObject p1, final Map<Option, ?> p2);
    
    byte[] load(final StorageObject p0, final Map<Option, ?> p1);
    
    Tuple<String, byte[]> read(final StorageObject p0, final Map<Option, ?> p1, final long p2, final int p3);
    
    long read(final StorageObject p0, final Map<Option, ?> p1, final long p2, final OutputStream p3);
    
    String open(final StorageObject p0, final Map<Option, ?> p1);
    
    String open(final String p0);
    
    void write(final String p0, final byte[] p1, final int p2, final long p3, final int p4, final boolean p5);
    
    RewriteResponse openRewrite(final RewriteRequest p0);
    
    RewriteResponse continueRewrite(final RewriteResponse p0);
    
    BucketAccessControl getAcl(final String p0, final String p1, final Map<Option, ?> p2);
    
    boolean deleteAcl(final String p0, final String p1, final Map<Option, ?> p2);
    
    BucketAccessControl createAcl(final BucketAccessControl p0, final Map<Option, ?> p1);
    
    BucketAccessControl patchAcl(final BucketAccessControl p0, final Map<Option, ?> p1);
    
    List<BucketAccessControl> listAcls(final String p0, final Map<Option, ?> p1);
    
    ObjectAccessControl getDefaultAcl(final String p0, final String p1);
    
    boolean deleteDefaultAcl(final String p0, final String p1);
    
    ObjectAccessControl createDefaultAcl(final ObjectAccessControl p0);
    
    ObjectAccessControl patchDefaultAcl(final ObjectAccessControl p0);
    
    List<ObjectAccessControl> listDefaultAcls(final String p0);
    
    ObjectAccessControl getAcl(final String p0, final String p1, final Long p2, final String p3);
    
    boolean deleteAcl(final String p0, final String p1, final Long p2, final String p3);
    
    ObjectAccessControl createAcl(final ObjectAccessControl p0);
    
    ObjectAccessControl patchAcl(final ObjectAccessControl p0);
    
    List<ObjectAccessControl> listAcls(final String p0, final String p1, final Long p2);
    
    HmacKey createHmacKey(final String p0, final Map<Option, ?> p1);
    
    Tuple<String, Iterable<HmacKeyMetadata>> listHmacKeys(final Map<Option, ?> p0);
    
    HmacKeyMetadata updateHmacKey(final HmacKeyMetadata p0, final Map<Option, ?> p1);
    
    HmacKeyMetadata getHmacKey(final String p0, final Map<Option, ?> p1);
    
    void deleteHmacKey(final HmacKeyMetadata p0, final Map<Option, ?> p1);
    
    Policy getIamPolicy(final String p0, final Map<Option, ?> p1);
    
    Policy setIamPolicy(final String p0, final Policy p1, final Map<Option, ?> p2);
    
    TestIamPermissionsResponse testIamPermissions(final String p0, final List<String> p1, final Map<Option, ?> p2);
    
    boolean deleteNotification(final String p0, final String p1);
    
    List<Notification> listNotifications(final String p0);
    
    Notification createNotification(final String p0, final Notification p1);
    
    Bucket lockRetentionPolicy(final Bucket p0, final Map<Option, ?> p1);
    
    ServiceAccount getServiceAccount(final String p0);
    
    public enum Option
    {
        PREDEFINED_ACL("predefinedAcl"), 
        PREDEFINED_DEFAULT_OBJECT_ACL("predefinedDefaultObjectAcl"), 
        IF_METAGENERATION_MATCH("ifMetagenerationMatch"), 
        IF_METAGENERATION_NOT_MATCH("ifMetagenerationNotMatch"), 
        IF_GENERATION_MATCH("ifGenerationMatch"), 
        IF_GENERATION_NOT_MATCH("ifGenerationNotMatch"), 
        IF_SOURCE_METAGENERATION_MATCH("ifSourceMetagenerationMatch"), 
        IF_SOURCE_METAGENERATION_NOT_MATCH("ifSourceMetagenerationNotMatch"), 
        IF_SOURCE_GENERATION_MATCH("ifSourceGenerationMatch"), 
        IF_SOURCE_GENERATION_NOT_MATCH("ifSourceGenerationNotMatch"), 
        IF_DISABLE_GZIP_CONTENT("disableGzipContent"), 
        PREFIX("prefix"), 
        PROJECT_ID("projectId"), 
        PROJECTION("projection"), 
        MAX_RESULTS("maxResults"), 
        PAGE_TOKEN("pageToken"), 
        DELIMITER("delimiter"), 
        VERSIONS("versions"), 
        FIELDS("fields"), 
        CUSTOMER_SUPPLIED_KEY("customerSuppliedKey"), 
        USER_PROJECT("userProject"), 
        KMS_KEY_NAME("kmsKeyName"), 
        SERVICE_ACCOUNT_EMAIL("serviceAccount"), 
        SHOW_DELETED_KEYS("showDeletedKeys");
        
        private final String value;
        private static final /* synthetic */ Option[] $VALUES;
        
        public static Option[] values() {
            return Option.$VALUES.clone();
        }
        
        public static Option valueOf(final String a1) {
            return Enum.valueOf(Option.class, a1);
        }
        
        private Option(final String a1) {
            this.value = a1;
        }
        
        public String value() {
            return this.value;
        }
        
         <T> T get(final Map<Option, ?> a1) {
            return (T)a1.get(this);
        }
        
        String getString(final Map<Option, ?> a1) {
            return this.get(a1);
        }
        
        Long getLong(final Map<Option, ?> a1) {
            return this.get(a1);
        }
        
        Boolean getBoolean(final Map<Option, ?> a1) {
            return this.get(a1);
        }
        
        static {
            $VALUES = new Option[] { Option.PREDEFINED_ACL, Option.PREDEFINED_DEFAULT_OBJECT_ACL, Option.IF_METAGENERATION_MATCH, Option.IF_METAGENERATION_NOT_MATCH, Option.IF_GENERATION_MATCH, Option.IF_GENERATION_NOT_MATCH, Option.IF_SOURCE_METAGENERATION_MATCH, Option.IF_SOURCE_METAGENERATION_NOT_MATCH, Option.IF_SOURCE_GENERATION_MATCH, Option.IF_SOURCE_GENERATION_NOT_MATCH, Option.IF_DISABLE_GZIP_CONTENT, Option.PREFIX, Option.PROJECT_ID, Option.PROJECTION, Option.MAX_RESULTS, Option.PAGE_TOKEN, Option.DELIMITER, Option.VERSIONS, Option.FIELDS, Option.CUSTOMER_SUPPLIED_KEY, Option.USER_PROJECT, Option.KMS_KEY_NAME, Option.SERVICE_ACCOUNT_EMAIL, Option.SHOW_DELETED_KEYS };
        }
    }
    
    public static class RewriteRequest
    {
        public final StorageObject source;
        public final Map<Option, ?> sourceOptions;
        public final boolean overrideInfo;
        public final StorageObject target;
        public final Map<Option, ?> targetOptions;
        public final Long megabytesRewrittenPerCall;
        
        public RewriteRequest(final StorageObject a1, final Map<Option, ?> a2, final boolean a3, final StorageObject a4, final Map<Option, ?> a5, final Long a6) {
            super();
            this.source = a1;
            this.sourceOptions = a2;
            this.overrideInfo = a3;
            this.target = a4;
            this.targetOptions = a5;
            this.megabytesRewrittenPerCall = a6;
        }
        
        @Override
        public boolean equals(final Object a1) {
            if (a1 == null) {
                return false;
            }
            if (!(a1 instanceof RewriteRequest)) {
                return false;
            }
            final RewriteRequest v1 = (RewriteRequest)a1;
            return Objects.equals(this.source, v1.source) && Objects.equals(this.sourceOptions, v1.sourceOptions) && Objects.equals(this.overrideInfo, v1.overrideInfo) && Objects.equals(this.target, v1.target) && Objects.equals(this.targetOptions, v1.targetOptions) && Objects.equals(this.megabytesRewrittenPerCall, v1.megabytesRewrittenPerCall);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.source, this.sourceOptions, this.overrideInfo, this.target, this.targetOptions, this.megabytesRewrittenPerCall);
        }
    }
    
    public static class RewriteResponse
    {
        public final RewriteRequest rewriteRequest;
        public final StorageObject result;
        public final long blobSize;
        public final boolean isDone;
        public final String rewriteToken;
        public final long totalBytesRewritten;
        
        public RewriteResponse(final RewriteRequest a1, final StorageObject a2, final long a3, final boolean a4, final String a5, final long a6) {
            super();
            this.rewriteRequest = a1;
            this.result = a2;
            this.blobSize = a3;
            this.isDone = a4;
            this.rewriteToken = a5;
            this.totalBytesRewritten = a6;
        }
        
        @Override
        public boolean equals(final Object a1) {
            if (a1 == null) {
                return false;
            }
            if (!(a1 instanceof RewriteResponse)) {
                return false;
            }
            final RewriteResponse v1 = (RewriteResponse)a1;
            return Objects.equals(this.rewriteRequest, v1.rewriteRequest) && Objects.equals(this.result, v1.result) && Objects.equals(this.rewriteToken, v1.rewriteToken) && this.blobSize == v1.blobSize && Objects.equals(this.isDone, v1.isDone) && this.totalBytesRewritten == v1.totalBytesRewritten;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.rewriteRequest, this.result, this.blobSize, this.isDone, this.rewriteToken, this.totalBytesRewritten);
        }
    }
}
