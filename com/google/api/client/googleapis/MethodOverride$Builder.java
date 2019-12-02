package com.google.api.client.googleapis;

public static final class Builder
{
    private boolean overrideAllMethods;
    
    public Builder() {
        super();
    }
    
    public MethodOverride build() {
        return new MethodOverride(this.overrideAllMethods);
    }
    
    public boolean getOverrideAllMethods() {
        return this.overrideAllMethods;
    }
    
    public Builder setOverrideAllMethods(final boolean a1) {
        this.overrideAllMethods = a1;
        return this;
    }
}
