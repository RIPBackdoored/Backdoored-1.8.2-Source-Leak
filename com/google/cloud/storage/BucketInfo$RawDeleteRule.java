package com.google.cloud.storage;

import com.google.api.services.storage.model.*;
import java.io.*;
import com.google.api.client.json.jackson2.*;

static class RawDeleteRule extends DeleteRule
{
    private static final long serialVersionUID = -7166938278642301933L;
    private transient Bucket.Lifecycle.Rule rule;
    
    RawDeleteRule(final Bucket.Lifecycle.Rule a1) {
        super(Type.UNKNOWN);
        this.rule = a1;
    }
    
    @Override
    void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
        throw new UnsupportedOperationException();
    }
    
    private void writeObject(final ObjectOutputStream a1) throws IOException {
        a1.defaultWriteObject();
        a1.writeUTF(this.rule.toString());
    }
    
    private void readObject(final ObjectInputStream a1) throws IOException, ClassNotFoundException {
        a1.defaultReadObject();
        this.rule = new JacksonFactory().fromString(a1.readUTF(), Bucket.Lifecycle.Rule.class);
    }
    
    @Override
    Bucket.Lifecycle.Rule toPb() {
        return this.rule;
    }
}
