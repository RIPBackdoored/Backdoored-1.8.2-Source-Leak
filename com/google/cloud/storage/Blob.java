package com.google.cloud.storage;

import com.google.api.services.storage.model.*;
import java.nio.file.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.api.gax.retrying.*;
import com.google.common.base.*;
import com.google.cloud.*;
import java.util.concurrent.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.security.*;
import com.google.common.io.*;

public class Blob extends BlobInfo
{
    private static final long serialVersionUID = -6806832496717441434L;
    private final StorageOptions options;
    private transient Storage storage;
    static final Function<Tuple<Storage, StorageObject>, Blob> BLOB_FROM_PB_FUNCTION;
    private static final int DEFAULT_CHUNK_SIZE = 2097152;
    
    public void downloadTo(final Path v2, final BlobSourceOption... v3) {
        try (final OutputStream a1 = Files.newOutputStream(v2, new OpenOption[0])) {
            this.downloadTo(a1, v3);
        }
        catch (IOException a2) {
            throw new StorageException(a2);
        }
    }
    
    public void downloadTo(final OutputStream a1, final BlobSourceOption... a2) {
        final CountingOutputStream v1 = new CountingOutputStream(a1);
        final StorageRpc v2 = this.options.getStorageRpcV1();
        final Map<StorageRpc.Option, ?> v3 = StorageImpl.optionMap(this.getBlobId(), (Option[])a2);
        RetryHelper.runWithRetries((Callable)Executors.callable(new Runnable() {
            final /* synthetic */ StorageRpc val$storageRpc;
            final /* synthetic */ Map val$requestOptions;
            final /* synthetic */ CountingOutputStream val$countingOutputStream;
            final /* synthetic */ Blob this$0;
            
            Blob$2() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void run() {
                v2.read(this.this$0.getBlobId().toPb(), v3, v1.getCount(), (OutputStream)v1);
            }
        }), this.options.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, this.options.getClock());
    }
    
    public void downloadTo(final Path a1) {
        this.downloadTo(a1, new BlobSourceOption[0]);
    }
    
    Blob(final Storage a1, final BuilderImpl a2) {
        super(a2);
        this.storage = Preconditions.checkNotNull(a1);
        this.options = (StorageOptions)a1.getOptions();
    }
    
    public boolean exists(final BlobSourceOption... a1) {
        final int v1 = a1.length;
        final Storage.BlobGetOption[] v2 = Arrays.copyOf(BlobSourceOption.toGetOptions(this, a1), v1 + 1);
        v2[v1] = Storage.BlobGetOption.fields(new Storage.BlobField[0]);
        return this.storage.get(this.getBlobId(), v2) != null;
    }
    
    public byte[] getContent(final BlobSourceOption... a1) {
        return this.storage.readAllBytes(this.getBlobId(), BlobSourceOption.toSourceOptions(this, a1));
    }
    
    public Blob reload(final BlobSourceOption... a1) {
        return this.storage.get(this.getBlobId(), BlobSourceOption.toGetOptions(this, a1));
    }
    
    public Blob update(final Storage.BlobTargetOption... a1) {
        return this.storage.update(this, a1);
    }
    
    public boolean delete(final BlobSourceOption... a1) {
        return this.storage.delete(this.getBlobId(), BlobSourceOption.toSourceOptions(this, a1));
    }
    
    public CopyWriter copyTo(final BlobId a1, final BlobSourceOption... a2) {
        final Storage.CopyRequest v1 = Storage.CopyRequest.newBuilder().setSource(this.getBucket(), this.getName()).setSourceOptions(BlobSourceOption.toSourceOptions(this, a2)).setTarget(a1).build();
        return this.storage.copy(v1);
    }
    
    public CopyWriter copyTo(final String a1, final BlobSourceOption... a2) {
        return this.copyTo(a1, this.getName(), a2);
    }
    
    public CopyWriter copyTo(final String a1, final String a2, final BlobSourceOption... a3) {
        return this.copyTo(BlobId.of(a1, a2), a3);
    }
    
    public ReadChannel reader(final BlobSourceOption... a1) {
        return this.storage.reader(this.getBlobId(), BlobSourceOption.toSourceOptions(this, a1));
    }
    
    public WriteChannel writer(final Storage.BlobWriteOption... a1) {
        return this.storage.writer(this, a1);
    }
    
    public URL signUrl(final long a1, final TimeUnit a2, final Storage.SignUrlOption... a3) {
        return this.storage.signUrl(this, a1, a2, a3);
    }
    
    public Acl getAcl(final Acl.Entity a1) {
        return this.storage.getAcl(this.getBlobId(), a1);
    }
    
    public boolean deleteAcl(final Acl.Entity a1) {
        return this.storage.deleteAcl(this.getBlobId(), a1);
    }
    
    public Acl createAcl(final Acl a1) {
        return this.storage.createAcl(this.getBlobId(), a1);
    }
    
    public Acl updateAcl(final Acl a1) {
        return this.storage.updateAcl(this.getBlobId(), a1);
    }
    
    public List<Acl> listAcls() {
        return this.storage.listAcls(this.getBlobId());
    }
    
    public Storage getStorage() {
        return this.storage;
    }
    
    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }
    
    @Override
    public final boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (a1 == null || !a1.getClass().equals(Blob.class)) {
            return false;
        }
        final Blob v1 = (Blob)a1;
        return Objects.equals(this.toPb(), v1.toPb()) && Objects.equals(this.options, v1.options);
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), this.options);
    }
    
    private void readObject(final ObjectInputStream a1) throws IOException, ClassNotFoundException {
        a1.defaultReadObject();
        this.storage = (Storage)this.options.getService();
    }
    
    static Blob fromPb(final Storage a1, final StorageObject a2) {
        final BlobInfo v1 = BlobInfo.fromPb(a2);
        return new Blob(a1, new BuilderImpl(v1));
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static {
        BLOB_FROM_PB_FUNCTION = new Function<Tuple<Storage, StorageObject>, Blob>() {
            Blob$1() {
                super();
            }
            
            @Override
            public Blob apply(final Tuple<Storage, StorageObject> a1) {
                return Blob.fromPb((Storage)a1.x(), (StorageObject)a1.y());
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Tuple<Storage, StorageObject>)o);
            }
        };
    }
    
    public static class BlobSourceOption extends Option
    {
        private static final long serialVersionUID = 214616862061934846L;
        
        private BlobSourceOption(final StorageRpc.Option a1) {
            super(a1, null);
        }
        
        private BlobSourceOption(final StorageRpc.Option a1, final Object a2) {
            super(a1, a2);
        }
        
        private Storage.BlobSourceOption toSourceOptions(final BlobInfo a1) {
            switch (this.getRpcOption()) {
                case IF_GENERATION_MATCH: {
                    return Storage.BlobSourceOption.generationMatch(a1.getGeneration());
                }
                case IF_GENERATION_NOT_MATCH: {
                    return Storage.BlobSourceOption.generationNotMatch(a1.getGeneration());
                }
                case IF_METAGENERATION_MATCH: {
                    return Storage.BlobSourceOption.metagenerationMatch(a1.getMetageneration());
                }
                case IF_METAGENERATION_NOT_MATCH: {
                    return Storage.BlobSourceOption.metagenerationNotMatch(a1.getMetageneration());
                }
                case CUSTOMER_SUPPLIED_KEY: {
                    return Storage.BlobSourceOption.decryptionKey((String)this.getValue());
                }
                case USER_PROJECT: {
                    return Storage.BlobSourceOption.userProject((String)this.getValue());
                }
                default: {
                    throw new AssertionError((Object)"Unexpected enum value");
                }
            }
        }
        
        private Storage.BlobGetOption toGetOption(final BlobInfo a1) {
            switch (this.getRpcOption()) {
                case IF_GENERATION_MATCH: {
                    return Storage.BlobGetOption.generationMatch(a1.getGeneration());
                }
                case IF_GENERATION_NOT_MATCH: {
                    return Storage.BlobGetOption.generationNotMatch(a1.getGeneration());
                }
                case IF_METAGENERATION_MATCH: {
                    return Storage.BlobGetOption.metagenerationMatch(a1.getMetageneration());
                }
                case IF_METAGENERATION_NOT_MATCH: {
                    return Storage.BlobGetOption.metagenerationNotMatch(a1.getMetageneration());
                }
                case USER_PROJECT: {
                    return Storage.BlobGetOption.userProject((String)this.getValue());
                }
                case CUSTOMER_SUPPLIED_KEY: {
                    return Storage.BlobGetOption.decryptionKey((String)this.getValue());
                }
                default: {
                    throw new AssertionError((Object)"Unexpected enum value");
                }
            }
        }
        
        public static BlobSourceOption generationMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH);
        }
        
        public static BlobSourceOption generationNotMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH);
        }
        
        public static BlobSourceOption metagenerationMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
        }
        
        public static BlobSourceOption metagenerationNotMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
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
        
        static Storage.BlobSourceOption[] toSourceOptions(final BlobInfo a2, final BlobSourceOption... v1) {
            final Storage.BlobSourceOption[] v2 = new Storage.BlobSourceOption[v1.length];
            int v3 = 0;
            for (final BlobSourceOption a3 : v1) {
                v2[v3++] = a3.toSourceOptions(a2);
            }
            return v2;
        }
        
        static Storage.BlobGetOption[] toGetOptions(final BlobInfo a2, final BlobSourceOption... v1) {
            final Storage.BlobGetOption[] v2 = new Storage.BlobGetOption[v1.length];
            int v3 = 0;
            for (final BlobSourceOption a3 : v1) {
                v2[v3++] = a3.toGetOption(a2);
            }
            return v2;
        }
    }
    
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
}
