package com.google.cloud.storage;

import com.google.api.services.storage.model.*;

@Deprecated
public static class NumNewerVersionsDeleteRule extends DeleteRule
{
    private static final long serialVersionUID = -1955554976528303894L;
    private final int numNewerVersions;
    
    public NumNewerVersionsDeleteRule(final int a1) {
        super(Type.NUM_NEWER_VERSIONS);
        this.numNewerVersions = a1;
    }
    
    public int getNumNewerVersions() {
        return this.numNewerVersions;
    }
    
    @Override
    void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
        a1.setNumNewerVersions(Integer.valueOf(this.numNewerVersions));
    }
}
