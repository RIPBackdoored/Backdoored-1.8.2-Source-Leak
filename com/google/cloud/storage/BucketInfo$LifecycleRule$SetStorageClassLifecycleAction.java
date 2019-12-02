package com.google.cloud.storage;

public static class SetStorageClassLifecycleAction extends LifecycleAction
{
    public static final String TYPE = "SetStorageClass";
    private static final long serialVersionUID = -62615467186000899L;
    private final StorageClass storageClass;
    
    private SetStorageClassLifecycleAction(final StorageClass a1) {
        super();
        this.storageClass = a1;
    }
    
    @Override
    public String getActionType() {
        return "SetStorageClass";
    }
    
    StorageClass getStorageClass() {
        return this.storageClass;
    }
    
    SetStorageClassLifecycleAction(final StorageClass a1, final BucketInfo$1 a2) {
        this(a1);
    }
}
