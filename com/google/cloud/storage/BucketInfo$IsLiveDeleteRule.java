package com.google.cloud.storage;

import com.google.api.services.storage.model.*;

@Deprecated
public static class IsLiveDeleteRule extends DeleteRule
{
    private static final long serialVersionUID = -3502994563121313364L;
    private final boolean isLive;
    
    public IsLiveDeleteRule(final boolean a1) {
        super(Type.IS_LIVE);
        this.isLive = a1;
    }
    
    public boolean isLive() {
        return this.isLive;
    }
    
    @Override
    void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
        a1.setIsLive(Boolean.valueOf(this.isLive));
    }
}
