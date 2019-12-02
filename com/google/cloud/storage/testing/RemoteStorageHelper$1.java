package com.google.cloud.storage.testing;

import com.google.cloud.storage.*;
import com.google.api.gax.paging.*;
import java.util.*;

static final class RemoteStorageHelper$1 implements Runnable {
    final /* synthetic */ Storage val$storage;
    final /* synthetic */ long val$olderThan;
    
    RemoteStorageHelper$1(final Storage val$storage, final long val$olderThan) {
        this.val$storage = val$storage;
        this.val$olderThan = val$olderThan;
        super();
    }
    
    @Override
    public void run() {
        final Page<Bucket> list = this.val$storage.list(Storage.BucketListOption.prefix("gcloud-test-bucket-temp-"));
        for (final Bucket bucket : list.iterateAll()) {
            if (bucket.getCreateTime() < this.val$olderThan) {
                try {
                    for (final Blob v1 : bucket.list(Storage.BlobListOption.fields(Storage.BlobField.EVENT_BASED_HOLD, Storage.BlobField.TEMPORARY_HOLD)).iterateAll()) {
                        if (v1.getEventBasedHold() || v1.getTemporaryHold()) {
                            this.val$storage.update(v1.toBuilder().setTemporaryHold(false).setEventBasedHold(false).build());
                        }
                    }
                    RemoteStorageHelper.forceDelete(this.val$storage, bucket.getName());
                }
                catch (Exception ex) {}
            }
        }
    }
}