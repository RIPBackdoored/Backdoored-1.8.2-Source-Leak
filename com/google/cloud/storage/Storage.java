package com.google.cloud.storage;

import com.google.api.gax.paging.*;
import java.net.*;
import java.util.concurrent.*;
import com.google.cloud.storage.spi.v1.*;
import java.security.*;
import com.google.common.io.*;
import com.google.cloud.*;
import java.io.*;
import com.google.auth.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public interface Storage extends Service<StorageOptions>
{
    Bucket create(final BucketInfo p0, final BucketTargetOption... p1);
    
    Blob create(final BlobInfo p0, final BlobTargetOption... p1);
    
    Blob create(final BlobInfo p0, final byte[] p1, final BlobTargetOption... p2);
    
    Blob create(final BlobInfo p0, final byte[] p1, final int p2, final int p3, final BlobTargetOption... p4);
    
    @Deprecated
    Blob create(final BlobInfo p0, final InputStream p1, final BlobWriteOption... p2);
    
    Bucket get(final String p0, final BucketGetOption... p1);
    
    Bucket lockRetentionPolicy(final BucketInfo p0, final BucketTargetOption... p1);
    
    Blob get(final String p0, final String p1, final BlobGetOption... p2);
    
    Blob get(final BlobId p0, final BlobGetOption... p1);
    
    Blob get(final BlobId p0);
    
    Page<Bucket> list(final BucketListOption... p0);
    
    Page<Blob> list(final String p0, final BlobListOption... p1);
    
    Bucket update(final BucketInfo p0, final BucketTargetOption... p1);
    
    Blob update(final BlobInfo p0, final BlobTargetOption... p1);
    
    Blob update(final BlobInfo p0);
    
    boolean delete(final String p0, final BucketSourceOption... p1);
    
    boolean delete(final String p0, final String p1, final BlobSourceOption... p2);
    
    boolean delete(final BlobId p0, final BlobSourceOption... p1);
    
    boolean delete(final BlobId p0);
    
    Blob compose(final ComposeRequest p0);
    
    CopyWriter copy(final CopyRequest p0);
    
    byte[] readAllBytes(final String p0, final String p1, final BlobSourceOption... p2);
    
    byte[] readAllBytes(final BlobId p0, final BlobSourceOption... p1);
    
    StorageBatch batch();
    
    ReadChannel reader(final String p0, final String p1, final BlobSourceOption... p2);
    
    ReadChannel reader(final BlobId p0, final BlobSourceOption... p1);
    
    WriteChannel writer(final BlobInfo p0, final BlobWriteOption... p1);
    
    WriteChannel writer(final URL p0);
    
    URL signUrl(final BlobInfo p0, final long p1, final TimeUnit p2, final SignUrlOption... p3);
    
    List<Blob> get(final BlobId... p0);
    
    List<Blob> get(final Iterable<BlobId> p0);
    
    List<Blob> update(final BlobInfo... p0);
    
    List<Blob> update(final Iterable<BlobInfo> p0);
    
    List<Boolean> delete(final BlobId... p0);
    
    List<Boolean> delete(final Iterable<BlobId> p0);
    
    Acl getAcl(final String p0, final Acl.Entity p1, final BucketSourceOption... p2);
    
    Acl getAcl(final String p0, final Acl.Entity p1);
    
    boolean deleteAcl(final String p0, final Acl.Entity p1, final BucketSourceOption... p2);
    
    boolean deleteAcl(final String p0, final Acl.Entity p1);
    
    Acl createAcl(final String p0, final Acl p1, final BucketSourceOption... p2);
    
    Acl createAcl(final String p0, final Acl p1);
    
    Acl updateAcl(final String p0, final Acl p1, final BucketSourceOption... p2);
    
    Acl updateAcl(final String p0, final Acl p1);
    
    List<Acl> listAcls(final String p0, final BucketSourceOption... p1);
    
    List<Acl> listAcls(final String p0);
    
    Acl getDefaultAcl(final String p0, final Acl.Entity p1);
    
    boolean deleteDefaultAcl(final String p0, final Acl.Entity p1);
    
    Acl createDefaultAcl(final String p0, final Acl p1);
    
    Acl updateDefaultAcl(final String p0, final Acl p1);
    
    List<Acl> listDefaultAcls(final String p0);
    
    Acl getAcl(final BlobId p0, final Acl.Entity p1);
    
    boolean deleteAcl(final BlobId p0, final Acl.Entity p1);
    
    Acl createAcl(final BlobId p0, final Acl p1);
    
    Acl updateAcl(final BlobId p0, final Acl p1);
    
    List<Acl> listAcls(final BlobId p0);
    
    HmacKey createHmacKey(final ServiceAccount p0, final CreateHmacKeyOption... p1);
    
    Page<HmacKey.HmacKeyMetadata> listHmacKeys(final ListHmacKeysOption... p0);
    
    HmacKey.HmacKeyMetadata getHmacKey(final String p0, final GetHmacKeyOption... p1);
    
    void deleteHmacKey(final HmacKey.HmacKeyMetadata p0, final DeleteHmacKeyOption... p1);
    
    HmacKey.HmacKeyMetadata updateHmacKeyState(final HmacKey.HmacKeyMetadata p0, final HmacKey.HmacKeyState p1, final UpdateHmacKeyOption... p2);
    
    Policy getIamPolicy(final String p0, final BucketSourceOption... p1);
    
    Policy setIamPolicy(final String p0, final Policy p1, final BucketSourceOption... p2);
    
    List<Boolean> testIamPermissions(final String p0, final List<String> p1, final BucketSourceOption... p2);
    
    ServiceAccount getServiceAccount(final String p0);
    
    public enum PredefinedAcl
    {
        AUTHENTICATED_READ("authenticatedRead"), 
        ALL_AUTHENTICATED_USERS("allAuthenticatedUsers"), 
        PRIVATE("private"), 
        PROJECT_PRIVATE("projectPrivate"), 
        PUBLIC_READ("publicRead"), 
        PUBLIC_READ_WRITE("publicReadWrite"), 
        BUCKET_OWNER_READ("bucketOwnerRead"), 
        BUCKET_OWNER_FULL_CONTROL("bucketOwnerFullControl");
        
        private final String entry;
        private static final /* synthetic */ PredefinedAcl[] $VALUES;
        
        public static PredefinedAcl[] values() {
            return PredefinedAcl.$VALUES.clone();
        }
        
        public static PredefinedAcl valueOf(final String a1) {
            return Enum.valueOf(PredefinedAcl.class, a1);
        }
        
        private PredefinedAcl(final String a1) {
            this.entry = a1;
        }
        
        String getEntry() {
            return this.entry;
        }
        
        static {
            $VALUES = new PredefinedAcl[] { PredefinedAcl.AUTHENTICATED_READ, PredefinedAcl.ALL_AUTHENTICATED_USERS, PredefinedAcl.PRIVATE, PredefinedAcl.PROJECT_PRIVATE, PredefinedAcl.PUBLIC_READ, PredefinedAcl.PUBLIC_READ_WRITE, PredefinedAcl.BUCKET_OWNER_READ, PredefinedAcl.BUCKET_OWNER_FULL_CONTROL };
        }
    }
    
    public enum BucketField implements FieldSelector
    {
        ID("id"), 
        SELF_LINK("selfLink"), 
        NAME("name"), 
        TIME_CREATED("timeCreated"), 
        METAGENERATION("metageneration"), 
        ACL("acl"), 
        DEFAULT_OBJECT_ACL("defaultObjectAcl"), 
        OWNER("owner"), 
        LABELS("labels"), 
        LOCATION("location"), 
        LOCATION_TYPE("locationType"), 
        WEBSITE("website"), 
        VERSIONING("versioning"), 
        CORS("cors"), 
        LIFECYCLE("lifecycle"), 
        STORAGE_CLASS("storageClass"), 
        ETAG("etag"), 
        ENCRYPTION("encryption"), 
        BILLING("billing"), 
        DEFAULT_EVENT_BASED_HOLD("defaultEventBasedHold"), 
        RETENTION_POLICY("retentionPolicy"), 
        IAMCONFIGURATION("iamConfiguration");
        
        static final List<? extends FieldSelector> REQUIRED_FIELDS;
        private final String selector;
        private static final /* synthetic */ BucketField[] $VALUES;
        
        public static BucketField[] values() {
            return BucketField.$VALUES.clone();
        }
        
        public static BucketField valueOf(final String a1) {
            return Enum.valueOf(BucketField.class, a1);
        }
        
        private BucketField(final String a1) {
            this.selector = a1;
        }
        
        public String getSelector() {
            return this.selector;
        }
        
        static {
            $VALUES = new BucketField[] { BucketField.ID, BucketField.SELF_LINK, BucketField.NAME, BucketField.TIME_CREATED, BucketField.METAGENERATION, BucketField.ACL, BucketField.DEFAULT_OBJECT_ACL, BucketField.OWNER, BucketField.LABELS, BucketField.LOCATION, BucketField.LOCATION_TYPE, BucketField.WEBSITE, BucketField.VERSIONING, BucketField.CORS, BucketField.LIFECYCLE, BucketField.STORAGE_CLASS, BucketField.ETAG, BucketField.ENCRYPTION, BucketField.BILLING, BucketField.DEFAULT_EVENT_BASED_HOLD, BucketField.RETENTION_POLICY, BucketField.IAMCONFIGURATION };
            REQUIRED_FIELDS = ImmutableList.of(BucketField.NAME);
        }
    }
    
    public enum BlobField implements FieldSelector
    {
        ACL("acl"), 
        BUCKET("bucket"), 
        CACHE_CONTROL("cacheControl"), 
        COMPONENT_COUNT("componentCount"), 
        CONTENT_DISPOSITION("contentDisposition"), 
        CONTENT_ENCODING("contentEncoding"), 
        CONTENT_LANGUAGE("contentLanguage"), 
        CONTENT_TYPE("contentType"), 
        CRC32C("crc32c"), 
        ETAG("etag"), 
        GENERATION("generation"), 
        ID("id"), 
        KIND("kind"), 
        MD5HASH("md5Hash"), 
        MEDIA_LINK("mediaLink"), 
        METADATA("metadata"), 
        METAGENERATION("metageneration"), 
        NAME("name"), 
        OWNER("owner"), 
        SELF_LINK("selfLink"), 
        SIZE("size"), 
        STORAGE_CLASS("storageClass"), 
        TIME_DELETED("timeDeleted"), 
        TIME_CREATED("timeCreated"), 
        KMS_KEY_NAME("kmsKeyName"), 
        EVENT_BASED_HOLD("eventBasedHold"), 
        TEMPORARY_HOLD("temporaryHold"), 
        RETENTION_EXPIRATION_TIME("retentionExpirationTime"), 
        UPDATED("updated");
        
        static final List<? extends FieldSelector> REQUIRED_FIELDS;
        private final String selector;
        private static final /* synthetic */ BlobField[] $VALUES;
        
        public static BlobField[] values() {
            return BlobField.$VALUES.clone();
        }
        
        public static BlobField valueOf(final String a1) {
            return Enum.valueOf(BlobField.class, a1);
        }
        
        private BlobField(final String a1) {
            this.selector = a1;
        }
        
        public String getSelector() {
            return this.selector;
        }
        
        static {
            $VALUES = new BlobField[] { BlobField.ACL, BlobField.BUCKET, BlobField.CACHE_CONTROL, BlobField.COMPONENT_COUNT, BlobField.CONTENT_DISPOSITION, BlobField.CONTENT_ENCODING, BlobField.CONTENT_LANGUAGE, BlobField.CONTENT_TYPE, BlobField.CRC32C, BlobField.ETAG, BlobField.GENERATION, BlobField.ID, BlobField.KIND, BlobField.MD5HASH, BlobField.MEDIA_LINK, BlobField.METADATA, BlobField.METAGENERATION, BlobField.NAME, BlobField.OWNER, BlobField.SELF_LINK, BlobField.SIZE, BlobField.STORAGE_CLASS, BlobField.TIME_DELETED, BlobField.TIME_CREATED, BlobField.KMS_KEY_NAME, BlobField.EVENT_BASED_HOLD, BlobField.TEMPORARY_HOLD, BlobField.RETENTION_EXPIRATION_TIME, BlobField.UPDATED };
            REQUIRED_FIELDS = ImmutableList.of(BlobField.BUCKET, BlobField.NAME);
        }
    }
    
    public static class BucketTargetOption extends Option
    {
        private static final long serialVersionUID = -5880204616982900975L;
        
        private BucketTargetOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        private BucketTargetOption(final StorageRpc.Option a1) {
            this(a1, null);
        }
        
        public static BucketTargetOption predefinedAcl(final PredefinedAcl a1) {
            return new BucketTargetOption(StorageRpc.Option.PREDEFINED_ACL, a1.getEntry());
        }
        
        public static BucketTargetOption predefinedDefaultObjectAcl(final PredefinedAcl a1) {
            return new BucketTargetOption(StorageRpc.Option.PREDEFINED_DEFAULT_OBJECT_ACL, a1.getEntry());
        }
        
        public static BucketTargetOption metagenerationMatch() {
            return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
        }
        
        public static BucketTargetOption metagenerationNotMatch() {
            return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
        }
        
        public static BucketTargetOption userProject(final String a1) {
            return new BucketTargetOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static BucketTargetOption projection(final String a1) {
            return new BucketTargetOption(StorageRpc.Option.PROJECTION, a1);
        }
    }
    
    public static class BucketSourceOption extends Option
    {
        private static final long serialVersionUID = 5185657617120212117L;
        
        private BucketSourceOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static BucketSourceOption metagenerationMatch(final long a1) {
            return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
        }
        
        public static BucketSourceOption metagenerationNotMatch(final long a1) {
            return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
        }
        
        public static BucketSourceOption userProject(final String a1) {
            return new BucketSourceOption(StorageRpc.Option.USER_PROJECT, a1);
        }
    }
    
    public static class ListHmacKeysOption extends Option
    {
        private ListHmacKeysOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static ListHmacKeysOption serviceAccount(final ServiceAccount a1) {
            return new ListHmacKeysOption(StorageRpc.Option.SERVICE_ACCOUNT_EMAIL, a1.getEmail());
        }
        
        public static ListHmacKeysOption maxResults(final long a1) {
            return new ListHmacKeysOption(StorageRpc.Option.MAX_RESULTS, a1);
        }
        
        public static ListHmacKeysOption pageToken(final String a1) {
            return new ListHmacKeysOption(StorageRpc.Option.PAGE_TOKEN, a1);
        }
        
        public static ListHmacKeysOption showDeletedKeys(final boolean a1) {
            return new ListHmacKeysOption(StorageRpc.Option.SHOW_DELETED_KEYS, a1);
        }
        
        public static ListHmacKeysOption userProject(final String a1) {
            return new ListHmacKeysOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static ListHmacKeysOption projectId(final String a1) {
            return new ListHmacKeysOption(StorageRpc.Option.PROJECT_ID, a1);
        }
    }
    
    public static class CreateHmacKeyOption extends Option
    {
        private CreateHmacKeyOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static CreateHmacKeyOption userProject(final String a1) {
            return new CreateHmacKeyOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static CreateHmacKeyOption projectId(final String a1) {
            return new CreateHmacKeyOption(StorageRpc.Option.PROJECT_ID, a1);
        }
    }
    
    public static class GetHmacKeyOption extends Option
    {
        private GetHmacKeyOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static GetHmacKeyOption userProject(final String a1) {
            return new GetHmacKeyOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static GetHmacKeyOption projectId(final String a1) {
            return new GetHmacKeyOption(StorageRpc.Option.PROJECT_ID, a1);
        }
    }
    
    public static class DeleteHmacKeyOption extends Option
    {
        private DeleteHmacKeyOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static DeleteHmacKeyOption userProject(final String a1) {
            return new DeleteHmacKeyOption(StorageRpc.Option.USER_PROJECT, a1);
        }
    }
    
    public static class UpdateHmacKeyOption extends Option
    {
        private UpdateHmacKeyOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static UpdateHmacKeyOption userProject(final String a1) {
            return new UpdateHmacKeyOption(StorageRpc.Option.USER_PROJECT, a1);
        }
    }
    
    public static class BucketGetOption extends Option
    {
        private static final long serialVersionUID = 1901844869484087395L;
        
        private BucketGetOption(final StorageRpc.Option a1, final long a2) {
            super(a1, a2);
        }
        
        private BucketGetOption(final StorageRpc.Option a1, final String a2) {
            super(a1, a2);
        }
        
        public static BucketGetOption metagenerationMatch(final long a1) {
            return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
        }
        
        public static BucketGetOption metagenerationNotMatch(final long a1) {
            return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
        }
        
        public static BucketGetOption userProject(final String a1) {
            return new BucketGetOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static BucketGetOption fields(final BucketField... a1) {
            return new BucketGetOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.selector((List)BucketField.REQUIRED_FIELDS, (FieldSelector[])a1));
        }
    }
    
    public static class BlobTargetOption extends Option
    {
        private static final long serialVersionUID = 214616862061934846L;
        
        private BlobTargetOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        private BlobTargetOption(final StorageRpc.Option a1) {
            this(a1, null);
        }
        
        public static BlobTargetOption predefinedAcl(final PredefinedAcl a1) {
            return new BlobTargetOption(StorageRpc.Option.PREDEFINED_ACL, a1.getEntry());
        }
        
        public static BlobTargetOption doesNotExist() {
            return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH, 0L);
        }
        
        public static BlobTargetOption generationMatch() {
            return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH);
        }
        
        public static BlobTargetOption generationNotMatch() {
            return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH);
        }
        
        public static BlobTargetOption metagenerationMatch() {
            return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
        }
        
        public static BlobTargetOption metagenerationNotMatch() {
            return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
        }
        
        public static BlobTargetOption disableGzipContent() {
            return new BlobTargetOption(StorageRpc.Option.IF_DISABLE_GZIP_CONTENT, true);
        }
        
        public static BlobTargetOption encryptionKey(final Key a1) {
            final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
            return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, v1);
        }
        
        public static BlobTargetOption userProject(final String a1) {
            return new BlobTargetOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static BlobTargetOption encryptionKey(final String a1) {
            return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, a1);
        }
        
        public static BlobTargetOption kmsKeyName(final String a1) {
            return new BlobTargetOption(StorageRpc.Option.KMS_KEY_NAME, a1);
        }
        
        static Tuple<BlobInfo, BlobTargetOption[]> convert(final BlobInfo a2, final BlobWriteOption... v1) {
            final BlobInfo.Builder v2 = a2.toBuilder().setCrc32c(null).setMd5(null);
            final List<BlobTargetOption> v3 = (List<BlobTargetOption>)Lists.newArrayListWithCapacity(v1.length);
            for (final BlobWriteOption a3 : v1) {
                switch (a3.option) {
                    case IF_CRC32C_MATCH: {
                        v2.setCrc32c(a2.getCrc32c());
                        break;
                    }
                    case IF_MD5_MATCH: {
                        v2.setMd5(a2.getMd5());
                        break;
                    }
                    default: {
                        v3.add(a3.toTargetOption());
                        break;
                    }
                }
            }
            return (Tuple<BlobInfo, BlobTargetOption[]>)Tuple.of((Object)v2.build(), (Object)v3.toArray(new BlobTargetOption[v3.size()]));
        }
        
        BlobTargetOption(final StorageRpc.Option a1, final Object a2, final Storage$1 a3) {
            this(a1, a2);
        }
    }
    
    public static class BlobWriteOption implements Serializable
    {
        private static final long serialVersionUID = -3880421670966224580L;
        private final Option option;
        private final Object value;
        
        BlobTargetOption toTargetOption() {
            return new BlobTargetOption(this.option.toRpcOption(), this.value);
        }
        
        private BlobWriteOption(final Option a1, final Object a2) {
            super();
            this.option = a1;
            this.value = a2;
        }
        
        private BlobWriteOption(final Option a1) {
            this(a1, null);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.option, this.value);
        }
        
        @Override
        public boolean equals(final Object a1) {
            if (a1 == null) {
                return false;
            }
            if (!(a1 instanceof BlobWriteOption)) {
                return false;
            }
            final BlobWriteOption v1 = (BlobWriteOption)a1;
            return this.option == v1.option && Objects.equals(this.value, v1.value);
        }
        
        public static BlobWriteOption predefinedAcl(final PredefinedAcl a1) {
            return new BlobWriteOption(Option.PREDEFINED_ACL, a1.getEntry());
        }
        
        public static BlobWriteOption doesNotExist() {
            return new BlobWriteOption(Option.IF_GENERATION_MATCH, 0L);
        }
        
        public static BlobWriteOption generationMatch() {
            return new BlobWriteOption(Option.IF_GENERATION_MATCH);
        }
        
        public static BlobWriteOption generationNotMatch() {
            return new BlobWriteOption(Option.IF_GENERATION_NOT_MATCH);
        }
        
        public static BlobWriteOption metagenerationMatch() {
            return new BlobWriteOption(Option.IF_METAGENERATION_MATCH);
        }
        
        public static BlobWriteOption metagenerationNotMatch() {
            return new BlobWriteOption(Option.IF_METAGENERATION_NOT_MATCH);
        }
        
        public static BlobWriteOption md5Match() {
            return new BlobWriteOption(Option.IF_MD5_MATCH, true);
        }
        
        public static BlobWriteOption crc32cMatch() {
            return new BlobWriteOption(Option.IF_CRC32C_MATCH, true);
        }
        
        public static BlobWriteOption encryptionKey(final Key a1) {
            final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
            return new BlobWriteOption(Option.CUSTOMER_SUPPLIED_KEY, v1);
        }
        
        public static BlobWriteOption encryptionKey(final String a1) {
            return new BlobWriteOption(Option.CUSTOMER_SUPPLIED_KEY, a1);
        }
        
        public static BlobWriteOption kmsKeyName(final String a1) {
            return new BlobWriteOption(Option.KMS_KEY_NAME, a1);
        }
        
        public static BlobWriteOption userProject(final String a1) {
            return new BlobWriteOption(Option.USER_PROJECT, a1);
        }
        
        static /* synthetic */ Option access$000(final BlobWriteOption a1) {
            return a1.option;
        }
        
        enum Option
        {
            PREDEFINED_ACL, 
            IF_GENERATION_MATCH, 
            IF_GENERATION_NOT_MATCH, 
            IF_METAGENERATION_MATCH, 
            IF_METAGENERATION_NOT_MATCH, 
            IF_MD5_MATCH, 
            IF_CRC32C_MATCH, 
            CUSTOMER_SUPPLIED_KEY, 
            KMS_KEY_NAME, 
            USER_PROJECT;
            
            private static final /* synthetic */ Option[] $VALUES;
            
            public static Option[] values() {
                return Option.$VALUES.clone();
            }
            
            public static Option valueOf(final String a1) {
                return Enum.valueOf(Option.class, a1);
            }
            
            StorageRpc.Option toRpcOption() {
                return StorageRpc.Option.valueOf(this.name());
            }
            
            static {
                $VALUES = new Option[] { Option.PREDEFINED_ACL, Option.IF_GENERATION_MATCH, Option.IF_GENERATION_NOT_MATCH, Option.IF_METAGENERATION_MATCH, Option.IF_METAGENERATION_NOT_MATCH, Option.IF_MD5_MATCH, Option.IF_CRC32C_MATCH, Option.CUSTOMER_SUPPLIED_KEY, Option.KMS_KEY_NAME, Option.USER_PROJECT };
            }
        }
    }
    
    public static class BlobSourceOption extends Option
    {
        private static final long serialVersionUID = -3712768261070182991L;
        
        private BlobSourceOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static BlobSourceOption generationMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, null);
        }
        
        public static BlobSourceOption generationMatch(final long a1) {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, a1);
        }
        
        public static BlobSourceOption generationNotMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, null);
        }
        
        public static BlobSourceOption generationNotMatch(final long a1) {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, a1);
        }
        
        public static BlobSourceOption metagenerationMatch(final long a1) {
            return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
        }
        
        public static BlobSourceOption metagenerationNotMatch(final long a1) {
            return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
        }
        
        public static BlobSourceOption decryptionKey(final Key a1) {
            final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
            return new BlobSourceOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, v1);
        }
        
        public static BlobSourceOption decryptionKey(final String a1) {
            return new BlobSourceOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, a1);
        }
        
        public static BlobSourceOption userProject(final String a1) {
            return new BlobSourceOption(StorageRpc.Option.USER_PROJECT, a1);
        }
    }
    
    public static class BlobGetOption extends Option
    {
        private static final long serialVersionUID = 803817709703661480L;
        
        private BlobGetOption(final StorageRpc.Option a1, final Long a2) {
            super(a1, a2);
        }
        
        private BlobGetOption(final StorageRpc.Option a1, final String a2) {
            super(a1, a2);
        }
        
        public static BlobGetOption generationMatch() {
            return new BlobGetOption(StorageRpc.Option.IF_GENERATION_MATCH, (Long)null);
        }
        
        public static BlobGetOption generationMatch(final long a1) {
            return new BlobGetOption(StorageRpc.Option.IF_GENERATION_MATCH, a1);
        }
        
        public static BlobGetOption generationNotMatch() {
            return new BlobGetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, (Long)null);
        }
        
        public static BlobGetOption generationNotMatch(final long a1) {
            return new BlobGetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, a1);
        }
        
        public static BlobGetOption metagenerationMatch(final long a1) {
            return new BlobGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
        }
        
        public static BlobGetOption metagenerationNotMatch(final long a1) {
            return new BlobGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
        }
        
        public static BlobGetOption fields(final BlobField... a1) {
            return new BlobGetOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.selector((List)BlobField.REQUIRED_FIELDS, (FieldSelector[])a1));
        }
        
        public static BlobGetOption userProject(final String a1) {
            return new BlobGetOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static BlobGetOption decryptionKey(final Key a1) {
            final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
            return new BlobGetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, v1);
        }
        
        public static BlobGetOption decryptionKey(final String a1) {
            return new BlobGetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, a1);
        }
    }
    
    public static class BucketListOption extends Option
    {
        private static final long serialVersionUID = 8754017079673290353L;
        
        private BucketListOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static BucketListOption pageSize(final long a1) {
            return new BucketListOption(StorageRpc.Option.MAX_RESULTS, a1);
        }
        
        public static BucketListOption pageToken(final String a1) {
            return new BucketListOption(StorageRpc.Option.PAGE_TOKEN, a1);
        }
        
        public static BucketListOption prefix(final String a1) {
            return new BucketListOption(StorageRpc.Option.PREFIX, a1);
        }
        
        public static BucketListOption userProject(final String a1) {
            return new BucketListOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static BucketListOption fields(final BucketField... a1) {
            return new BucketListOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.listSelector("items", (List)BucketField.REQUIRED_FIELDS, (FieldSelector[])a1));
        }
    }
    
    public static class BlobListOption extends Option
    {
        private static final String[] TOP_LEVEL_FIELDS;
        private static final long serialVersionUID = 9083383524788661294L;
        
        private BlobListOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        public static BlobListOption pageSize(final long a1) {
            return new BlobListOption(StorageRpc.Option.MAX_RESULTS, a1);
        }
        
        public static BlobListOption pageToken(final String a1) {
            return new BlobListOption(StorageRpc.Option.PAGE_TOKEN, a1);
        }
        
        public static BlobListOption prefix(final String a1) {
            return new BlobListOption(StorageRpc.Option.PREFIX, a1);
        }
        
        public static BlobListOption currentDirectory() {
            return new BlobListOption(StorageRpc.Option.DELIMITER, true);
        }
        
        public static BlobListOption userProject(final String a1) {
            return new BlobListOption(StorageRpc.Option.USER_PROJECT, a1);
        }
        
        public static BlobListOption versions(final boolean a1) {
            return new BlobListOption(StorageRpc.Option.VERSIONS, a1);
        }
        
        public static BlobListOption fields(final BlobField... a1) {
            return new BlobListOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.listSelector(BlobListOption.TOP_LEVEL_FIELDS, "items", (List)BlobField.REQUIRED_FIELDS, (FieldSelector[])a1, new String[0]));
        }
        
        static {
            TOP_LEVEL_FIELDS = new String[] { "prefixes" };
        }
    }
    
    public static class SignUrlOption implements Serializable
    {
        private static final long serialVersionUID = 7850569877451099267L;
        private final Option option;
        private final Object value;
        
        private SignUrlOption(final Option a1, final Object a2) {
            super();
            this.option = a1;
            this.value = a2;
        }
        
        Option getOption() {
            return this.option;
        }
        
        Object getValue() {
            return this.value;
        }
        
        public static SignUrlOption httpMethod(final HttpMethod a1) {
            return new SignUrlOption(Option.HTTP_METHOD, a1);
        }
        
        public static SignUrlOption withContentType() {
            return new SignUrlOption(Option.CONTENT_TYPE, true);
        }
        
        public static SignUrlOption withMd5() {
            return new SignUrlOption(Option.MD5, true);
        }
        
        public static SignUrlOption withExtHeaders(final Map<String, String> a1) {
            return new SignUrlOption(Option.EXT_HEADERS, a1);
        }
        
        public static SignUrlOption withV2Signature() {
            return new SignUrlOption(Option.SIGNATURE_VERSION, SignatureVersion.V2);
        }
        
        public static SignUrlOption withV4Signature() {
            return new SignUrlOption(Option.SIGNATURE_VERSION, SignatureVersion.V4);
        }
        
        public static SignUrlOption signWith(final ServiceAccountSigner a1) {
            return new SignUrlOption(Option.SERVICE_ACCOUNT_CRED, a1);
        }
        
        public static SignUrlOption withHostName(final String a1) {
            return new SignUrlOption(Option.HOST_NAME, a1);
        }
        
        enum Option
        {
            HTTP_METHOD, 
            CONTENT_TYPE, 
            MD5, 
            EXT_HEADERS, 
            SERVICE_ACCOUNT_CRED, 
            SIGNATURE_VERSION, 
            HOST_NAME;
            
            private static final /* synthetic */ Option[] $VALUES;
            
            public static Option[] values() {
                return Option.$VALUES.clone();
            }
            
            public static Option valueOf(final String a1) {
                return Enum.valueOf(Option.class, a1);
            }
            
            static {
                $VALUES = new Option[] { Option.HTTP_METHOD, Option.CONTENT_TYPE, Option.MD5, Option.EXT_HEADERS, Option.SERVICE_ACCOUNT_CRED, Option.SIGNATURE_VERSION, Option.HOST_NAME };
            }
        }
        
        enum SignatureVersion
        {
            V2, 
            V4;
            
            private static final /* synthetic */ SignatureVersion[] $VALUES;
            
            public static SignatureVersion[] values() {
                return SignatureVersion.$VALUES.clone();
            }
            
            public static SignatureVersion valueOf(final String a1) {
                return Enum.valueOf(SignatureVersion.class, a1);
            }
            
            static {
                $VALUES = new SignatureVersion[] { SignatureVersion.V2, SignatureVersion.V4 };
            }
        }
    }
    
    public static class ComposeRequest implements Serializable
    {
        private static final long serialVersionUID = -7385681353748590911L;
        private final List<SourceBlob> sourceBlobs;
        private final BlobInfo target;
        private final List<BlobTargetOption> targetOptions;
        
        private ComposeRequest(final Builder a1) {
            super();
            this.sourceBlobs = (List<SourceBlob>)ImmutableList.copyOf((Collection<?>)a1.sourceBlobs);
            this.target = a1.target;
            this.targetOptions = (List<BlobTargetOption>)ImmutableList.copyOf((Collection<?>)a1.targetOptions);
        }
        
        public List<SourceBlob> getSourceBlobs() {
            return this.sourceBlobs;
        }
        
        public BlobInfo getTarget() {
            return this.target;
        }
        
        public List<BlobTargetOption> getTargetOptions() {
            return this.targetOptions;
        }
        
        public static ComposeRequest of(final Iterable<String> a1, final BlobInfo a2) {
            return newBuilder().setTarget(a2).addSource(a1).build();
        }
        
        public static ComposeRequest of(final String a1, final Iterable<String> a2, final String a3) {
            return of(a2, BlobInfo.newBuilder(BlobId.of(a1, a3)).build());
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        ComposeRequest(final Builder a1, final Storage$1 a2) {
            this(a1);
        }
        
        public static class SourceBlob implements Serializable
        {
            private static final long serialVersionUID = 4094962795951990439L;
            final String name;
            final Long generation;
            
            SourceBlob(final String a1) {
                this(a1, null);
            }
            
            SourceBlob(final String a1, final Long a2) {
                super();
                this.name = a1;
                this.generation = a2;
            }
            
            public String getName() {
                return this.name;
            }
            
            public Long getGeneration() {
                return this.generation;
            }
        }
        
        public static class Builder
        {
            private final List<SourceBlob> sourceBlobs;
            private final Set<BlobTargetOption> targetOptions;
            private BlobInfo target;
            
            public Builder() {
                super();
                this.sourceBlobs = new LinkedList<SourceBlob>();
                this.targetOptions = new LinkedHashSet<BlobTargetOption>();
            }
            
            public Builder addSource(final Iterable<String> v2) {
                for (final String a1 : v2) {
                    this.sourceBlobs.add(new SourceBlob(a1));
                }
                return this;
            }
            
            public Builder addSource(final String... a1) {
                return this.addSource(Arrays.asList(a1));
            }
            
            public Builder addSource(final String a1, final long a2) {
                this.sourceBlobs.add(new SourceBlob(a1, a2));
                return this;
            }
            
            public Builder setTarget(final BlobInfo a1) {
                this.target = a1;
                return this;
            }
            
            public Builder setTargetOptions(final BlobTargetOption... a1) {
                Collections.addAll(this.targetOptions, a1);
                return this;
            }
            
            public Builder setTargetOptions(final Iterable<BlobTargetOption> a1) {
                Iterables.addAll(this.targetOptions, a1);
                return this;
            }
            
            public ComposeRequest build() {
                Preconditions.checkArgument(!this.sourceBlobs.isEmpty());
                Preconditions.checkNotNull(this.target);
                return new ComposeRequest(this);
            }
            
            static /* synthetic */ List access$300(final Builder a1) {
                return a1.sourceBlobs;
            }
            
            static /* synthetic */ BlobInfo access$400(final Builder a1) {
                return a1.target;
            }
            
            static /* synthetic */ Set access$500(final Builder a1) {
                return a1.targetOptions;
            }
        }
    }
    
    public static class CopyRequest implements Serializable
    {
        private static final long serialVersionUID = -4498650529476219937L;
        private final BlobId source;
        private final List<BlobSourceOption> sourceOptions;
        private final boolean overrideInfo;
        private final BlobInfo target;
        private final List<BlobTargetOption> targetOptions;
        private final Long megabytesCopiedPerChunk;
        
        private CopyRequest(final Builder a1) {
            super();
            this.source = Preconditions.checkNotNull(a1.source);
            this.sourceOptions = (List<BlobSourceOption>)ImmutableList.copyOf((Collection<?>)a1.sourceOptions);
            this.overrideInfo = a1.overrideInfo;
            this.target = Preconditions.checkNotNull(a1.target);
            this.targetOptions = (List<BlobTargetOption>)ImmutableList.copyOf((Collection<?>)a1.targetOptions);
            this.megabytesCopiedPerChunk = a1.megabytesCopiedPerChunk;
        }
        
        public BlobId getSource() {
            return this.source;
        }
        
        public List<BlobSourceOption> getSourceOptions() {
            return this.sourceOptions;
        }
        
        public BlobInfo getTarget() {
            return this.target;
        }
        
        public boolean overrideInfo() {
            return this.overrideInfo;
        }
        
        public List<BlobTargetOption> getTargetOptions() {
            return this.targetOptions;
        }
        
        public Long getMegabytesCopiedPerChunk() {
            return this.megabytesCopiedPerChunk;
        }
        
        public static CopyRequest of(final String a1, final String a2, final BlobInfo a3) {
            return newBuilder().setSource(a1, a2).setTarget(a3, new BlobTargetOption[0]).build();
        }
        
        public static CopyRequest of(final BlobId a1, final BlobInfo a2) {
            return newBuilder().setSource(a1).setTarget(a2, new BlobTargetOption[0]).build();
        }
        
        public static CopyRequest of(final String a1, final String a2, final String a3) {
            return newBuilder().setSource(a1, a2).setTarget(BlobId.of(a1, a3)).build();
        }
        
        public static CopyRequest of(final String a1, final String a2, final BlobId a3) {
            return newBuilder().setSource(a1, a2).setTarget(a3).build();
        }
        
        public static CopyRequest of(final BlobId a1, final String a2) {
            return newBuilder().setSource(a1).setTarget(BlobId.of(a1.getBucket(), a2)).build();
        }
        
        public static CopyRequest of(final BlobId a1, final BlobId a2) {
            return newBuilder().setSource(a1).setTarget(a2).build();
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        CopyRequest(final Builder a1, final Storage$1 a2) {
            this(a1);
        }
        
        public static class Builder
        {
            private final Set<BlobSourceOption> sourceOptions;
            private final Set<BlobTargetOption> targetOptions;
            private BlobId source;
            private boolean overrideInfo;
            private BlobInfo target;
            private Long megabytesCopiedPerChunk;
            
            public Builder() {
                super();
                this.sourceOptions = new LinkedHashSet<BlobSourceOption>();
                this.targetOptions = new LinkedHashSet<BlobTargetOption>();
            }
            
            public Builder setSource(final String a1, final String a2) {
                this.source = BlobId.of(a1, a2);
                return this;
            }
            
            public Builder setSource(final BlobId a1) {
                this.source = a1;
                return this;
            }
            
            public Builder setSourceOptions(final BlobSourceOption... a1) {
                Collections.addAll(this.sourceOptions, a1);
                return this;
            }
            
            public Builder setSourceOptions(final Iterable<BlobSourceOption> a1) {
                Iterables.addAll(this.sourceOptions, a1);
                return this;
            }
            
            public Builder setTarget(final BlobId a1) {
                this.overrideInfo = false;
                this.target = BlobInfo.newBuilder(a1).build();
                return this;
            }
            
            public Builder setTarget(final BlobId a1, final BlobTargetOption... a2) {
                this.overrideInfo = false;
                this.target = BlobInfo.newBuilder(a1).build();
                Collections.addAll(this.targetOptions, a2);
                return this;
            }
            
            public Builder setTarget(final BlobInfo a1, final BlobTargetOption... a2) {
                this.overrideInfo = true;
                this.target = Preconditions.checkNotNull(a1);
                Collections.addAll(this.targetOptions, a2);
                return this;
            }
            
            public Builder setTarget(final BlobInfo a1, final Iterable<BlobTargetOption> a2) {
                this.overrideInfo = true;
                this.target = Preconditions.checkNotNull(a1);
                Iterables.addAll(this.targetOptions, a2);
                return this;
            }
            
            public Builder setTarget(final BlobId a1, final Iterable<BlobTargetOption> a2) {
                this.overrideInfo = false;
                this.target = BlobInfo.newBuilder(a1).build();
                Iterables.addAll(this.targetOptions, a2);
                return this;
            }
            
            public Builder setMegabytesCopiedPerChunk(final Long a1) {
                this.megabytesCopiedPerChunk = a1;
                return this;
            }
            
            public CopyRequest build() {
                return new CopyRequest(this);
            }
            
            static /* synthetic */ BlobId access$700(final Builder a1) {
                return a1.source;
            }
            
            static /* synthetic */ Set access$800(final Builder a1) {
                return a1.sourceOptions;
            }
            
            static /* synthetic */ boolean access$900(final Builder a1) {
                return a1.overrideInfo;
            }
            
            static /* synthetic */ BlobInfo access$1000(final Builder a1) {
                return a1.target;
            }
            
            static /* synthetic */ Set access$1100(final Builder a1) {
                return a1.targetOptions;
            }
            
            static /* synthetic */ Long access$1200(final Builder a1) {
                return a1.megabytesCopiedPerChunk;
            }
        }
    }
}
