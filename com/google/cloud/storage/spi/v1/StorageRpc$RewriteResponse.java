package com.google.cloud.storage.spi.v1;

import com.google.api.services.storage.model.*;
import java.util.*;

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
