package com.google.cloud.storage.testing;

import java.util.concurrent.*;
import com.google.common.base.*;
import com.google.api.gax.paging.*;
import com.google.cloud.storage.*;
import java.util.*;

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
