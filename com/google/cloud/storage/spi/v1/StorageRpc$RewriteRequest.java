package com.google.cloud.storage.spi.v1;

import com.google.api.services.storage.model.*;
import java.util.*;

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
