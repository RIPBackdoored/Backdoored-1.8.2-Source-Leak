package com.google.cloud.storage.spi.v1;

import com.google.api.services.storage.*;
import com.google.api.client.googleapis.batch.*;
import com.google.api.services.storage.model.*;
import java.io.*;
import com.google.api.client.http.*;
import io.opencensus.trace.*;
import io.opencensus.common.*;
import java.util.*;

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
        (this.batches = new LinkedList<BatchRequest>()).add(a1.batch(HttpStorageRpc.access$000(httpStorageRpc)));
    }
    
    @Override
    public void addDelete(final StorageObject a3, final Callback<Void> v1, final Map<Option, ?> v2) {
        try {
            if (this.currentBatchSize == 100) {
                this.batches.add(this.storage.batch());
                this.currentBatchSize = 0;
            }
            HttpStorageRpc.access$200(this.this$0, a3, (Map)v2).queue((BatchRequest)this.batches.getLast(), HttpStorageRpc.access$100((Callback)v1));
            ++this.currentBatchSize;
        }
        catch (IOException a4) {
            throw HttpStorageRpc.access$300(a4);
        }
    }
    
    @Override
    public void addPatch(final StorageObject a3, final Callback<StorageObject> v1, final Map<Option, ?> v2) {
        try {
            if (this.currentBatchSize == 100) {
                this.batches.add(this.storage.batch());
                this.currentBatchSize = 0;
            }
            HttpStorageRpc.access$400(this.this$0, a3, (Map)v2).queue((BatchRequest)this.batches.getLast(), HttpStorageRpc.access$100((Callback)v1));
            ++this.currentBatchSize;
        }
        catch (IOException a4) {
            throw HttpStorageRpc.access$300(a4);
        }
    }
    
    @Override
    public void addGet(final StorageObject a3, final Callback<StorageObject> v1, final Map<Option, ?> v2) {
        try {
            if (this.currentBatchSize == 100) {
                this.batches.add(this.storage.batch());
                this.currentBatchSize = 0;
            }
            HttpStorageRpc.access$500(this.this$0, a3, (Map)v2).queue((BatchRequest)this.batches.getLast(), HttpStorageRpc.access$100((Callback)v1));
            ++this.currentBatchSize;
        }
        catch (IOException a4) {
            throw HttpStorageRpc.access$300(a4);
        }
    }
    
    @Override
    public void submit() {
        final Span access$600 = HttpStorageRpc.access$600(this.this$0, HttpStorageRpcSpans.SPAN_NAME_BATCH_SUBMIT);
        final Scope withSpan = HttpStorageRpc.access$700(this.this$0).withSpan(access$600);
        try {
            access$600.putAttribute("batch size", AttributeValue.longAttributeValue((long)this.batches.size()));
            for (final BatchRequest v1 : this.batches) {
                access$600.addAnnotation("Execute batch request");
                v1.setBatchUrl(new GenericUrl(String.format("%s/batch/storage/v1", HttpStorageRpc.access$800(this.this$0).getHost())));
                v1.execute();
            }
        }
        catch (IOException v2) {
            access$600.setStatus(Status.UNKNOWN.withDescription(v2.getMessage()));
            throw HttpStorageRpc.access$300(v2);
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
