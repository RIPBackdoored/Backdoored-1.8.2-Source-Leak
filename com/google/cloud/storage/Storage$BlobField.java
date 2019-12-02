package com.google.cloud.storage;

import com.google.cloud.*;
import java.util.*;
import com.google.common.collect.*;

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
