package com.google.cloud.storage;

import com.google.api.services.storage.model.*;

@Deprecated
public static class AgeDeleteRule extends DeleteRule
{
    private static final long serialVersionUID = 5697166940712116380L;
    private final int daysToLive;
    
    public AgeDeleteRule(final int a1) {
        super(Type.AGE);
        this.daysToLive = a1;
    }
    
    public int getDaysToLive() {
        return this.daysToLive;
    }
    
    @Override
    void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
        a1.setAge(Integer.valueOf(this.daysToLive));
    }
}
