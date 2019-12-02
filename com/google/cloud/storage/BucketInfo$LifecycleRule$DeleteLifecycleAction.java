package com.google.cloud.storage;

public static class DeleteLifecycleAction extends LifecycleAction
{
    public static final String TYPE = "Delete";
    private static final long serialVersionUID = -2050986302222644873L;
    
    private DeleteLifecycleAction() {
        super();
    }
    
    @Override
    public String getActionType() {
        return "Delete";
    }
    
    DeleteLifecycleAction(final BucketInfo$1 a1) {
        this();
    }
}
