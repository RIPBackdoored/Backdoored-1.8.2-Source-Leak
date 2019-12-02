package com.google.cloud.storage;

import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;

@Deprecated
public static class CreatedBeforeDeleteRule extends DeleteRule
{
    private static final long serialVersionUID = 881692650279195867L;
    private final long timeMillis;
    
    public CreatedBeforeDeleteRule(final long a1) {
        super(Type.CREATE_BEFORE);
        this.timeMillis = a1;
    }
    
    public long getTimeMillis() {
        return this.timeMillis;
    }
    
    @Override
    void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
        a1.setCreatedBefore(new DateTime(true, this.timeMillis, 0));
    }
}
