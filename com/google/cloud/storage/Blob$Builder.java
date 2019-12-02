package com.google.cloud.storage;

import java.util.*;

public static class Builder extends BlobInfo.Builder
{
    private final Storage storage;
    private final BuilderImpl infoBuilder;
    
    Builder(final Blob a1) {
        super();
        this.storage = a1.getStorage();
        this.infoBuilder = new BuilderImpl(a1);
    }
    
    @Override
    public Builder setBlobId(final BlobId a1) {
        this.infoBuilder.setBlobId(a1);
        return this;
    }
    
    @Override
    Builder setGeneratedId(final String a1) {
        this.infoBuilder.setGeneratedId(a1);
        return this;
    }
    
    @Override
    public Builder setContentType(final String a1) {
        this.infoBuilder.setContentType(a1);
        return this;
    }
    
    @Override
    public Builder setContentDisposition(final String a1) {
        this.infoBuilder.setContentDisposition(a1);
        return this;
    }
    
    @Override
    public Builder setContentLanguage(final String a1) {
        this.infoBuilder.setContentLanguage(a1);
        return this;
    }
    
    @Override
    public Builder setContentEncoding(final String a1) {
        this.infoBuilder.setContentEncoding(a1);
        return this;
    }
    
    @Override
    Builder setComponentCount(final Integer a1) {
        this.infoBuilder.setComponentCount(a1);
        return this;
    }
    
    @Override
    public Builder setCacheControl(final String a1) {
        this.infoBuilder.setCacheControl(a1);
        return this;
    }
    
    @Override
    public Builder setAcl(final List<Acl> a1) {
        this.infoBuilder.setAcl(a1);
        return this;
    }
    
    @Override
    Builder setOwner(final Acl.Entity a1) {
        this.infoBuilder.setOwner(a1);
        return this;
    }
    
    @Override
    Builder setSize(final Long a1) {
        this.infoBuilder.setSize(a1);
        return this;
    }
    
    @Override
    Builder setEtag(final String a1) {
        this.infoBuilder.setEtag(a1);
        return this;
    }
    
    @Override
    Builder setSelfLink(final String a1) {
        this.infoBuilder.setSelfLink(a1);
        return this;
    }
    
    @Override
    public Builder setMd5(final String a1) {
        this.infoBuilder.setMd5(a1);
        return this;
    }
    
    @Override
    public Builder setMd5FromHexString(final String a1) {
        this.infoBuilder.setMd5FromHexString(a1);
        return this;
    }
    
    @Override
    public Builder setCrc32c(final String a1) {
        this.infoBuilder.setCrc32c(a1);
        return this;
    }
    
    @Override
    public Builder setCrc32cFromHexString(final String a1) {
        this.infoBuilder.setCrc32cFromHexString(a1);
        return this;
    }
    
    @Override
    Builder setMediaLink(final String a1) {
        this.infoBuilder.setMediaLink(a1);
        return this;
    }
    
    @Override
    public Builder setMetadata(final Map<String, String> a1) {
        this.infoBuilder.setMetadata(a1);
        return this;
    }
    
    @Override
    public Builder setStorageClass(final StorageClass a1) {
        this.infoBuilder.setStorageClass(a1);
        return this;
    }
    
    @Override
    Builder setMetageneration(final Long a1) {
        this.infoBuilder.setMetageneration(a1);
        return this;
    }
    
    @Override
    Builder setDeleteTime(final Long a1) {
        this.infoBuilder.setDeleteTime(a1);
        return this;
    }
    
    @Override
    Builder setUpdateTime(final Long a1) {
        this.infoBuilder.setUpdateTime(a1);
        return this;
    }
    
    @Override
    Builder setCreateTime(final Long a1) {
        this.infoBuilder.setCreateTime(a1);
        return this;
    }
    
    @Override
    Builder setIsDirectory(final boolean a1) {
        this.infoBuilder.setIsDirectory(a1);
        return this;
    }
    
    @Override
    Builder setCustomerEncryption(final CustomerEncryption a1) {
        this.infoBuilder.setCustomerEncryption(a1);
        return this;
    }
    
    @Override
    Builder setKmsKeyName(final String a1) {
        this.infoBuilder.setKmsKeyName(a1);
        return this;
    }
    
    @Override
    public Builder setEventBasedHold(final Boolean a1) {
        this.infoBuilder.setEventBasedHold(a1);
        return this;
    }
    
    @Override
    public Builder setTemporaryHold(final Boolean a1) {
        this.infoBuilder.setTemporaryHold(a1);
        return this;
    }
    
    @Override
    Builder setRetentionExpirationTime(final Long a1) {
        this.infoBuilder.setRetentionExpirationTime(a1);
        return this;
    }
    
    @Override
    public Blob build() {
        return new Blob(this.storage, this.infoBuilder);
    }
    
    @Override
    public /* bridge */ BlobInfo build() {
        return this.build();
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setRetentionExpirationTime(final Long retentionExpirationTime) {
        return this.setRetentionExpirationTime(retentionExpirationTime);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setTemporaryHold(final Boolean temporaryHold) {
        return this.setTemporaryHold(temporaryHold);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setEventBasedHold(final Boolean eventBasedHold) {
        return this.setEventBasedHold(eventBasedHold);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setKmsKeyName(final String kmsKeyName) {
        return this.setKmsKeyName(kmsKeyName);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setCustomerEncryption(final CustomerEncryption customerEncryption) {
        return this.setCustomerEncryption(customerEncryption);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setIsDirectory(final boolean isDirectory) {
        return this.setIsDirectory(isDirectory);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setCreateTime(final Long createTime) {
        return this.setCreateTime(createTime);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setUpdateTime(final Long updateTime) {
        return this.setUpdateTime(updateTime);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setDeleteTime(final Long deleteTime) {
        return this.setDeleteTime(deleteTime);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setMetageneration(final Long metageneration) {
        return this.setMetageneration(metageneration);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setMetadata(final Map metadata) {
        return this.setMetadata(metadata);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setStorageClass(final StorageClass storageClass) {
        return this.setStorageClass(storageClass);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setMediaLink(final String mediaLink) {
        return this.setMediaLink(mediaLink);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setCrc32cFromHexString(final String crc32cFromHexString) {
        return this.setCrc32cFromHexString(crc32cFromHexString);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setCrc32c(final String crc32c) {
        return this.setCrc32c(crc32c);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setMd5FromHexString(final String md5FromHexString) {
        return this.setMd5FromHexString(md5FromHexString);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setMd5(final String md5) {
        return this.setMd5(md5);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setSelfLink(final String selfLink) {
        return this.setSelfLink(selfLink);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setEtag(final String etag) {
        return this.setEtag(etag);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setSize(final Long size) {
        return this.setSize(size);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setOwner(final Acl.Entity owner) {
        return this.setOwner(owner);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setAcl(final List acl) {
        return this.setAcl(acl);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setCacheControl(final String cacheControl) {
        return this.setCacheControl(cacheControl);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setComponentCount(final Integer componentCount) {
        return this.setComponentCount(componentCount);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setContentEncoding(final String contentEncoding) {
        return this.setContentEncoding(contentEncoding);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setContentLanguage(final String contentLanguage) {
        return this.setContentLanguage(contentLanguage);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setContentDisposition(final String contentDisposition) {
        return this.setContentDisposition(contentDisposition);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setContentType(final String contentType) {
        return this.setContentType(contentType);
    }
    
    @Override
    /* bridge */ BlobInfo.Builder setGeneratedId(final String generatedId) {
        return this.setGeneratedId(generatedId);
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder setBlobId(final BlobId blobId) {
        return this.setBlobId(blobId);
    }
}
