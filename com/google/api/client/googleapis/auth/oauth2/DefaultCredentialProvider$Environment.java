package com.google.api.client.googleapis.auth.oauth2;

private enum Environment
{
    UNKNOWN, 
    ENVIRONMENT_VARIABLE, 
    WELL_KNOWN_FILE, 
    CLOUD_SHELL, 
    APP_ENGINE, 
    COMPUTE_ENGINE;
    
    private static final /* synthetic */ Environment[] $VALUES;
    
    public static Environment[] values() {
        return Environment.$VALUES.clone();
    }
    
    public static Environment valueOf(final String a1) {
        return Enum.valueOf(Environment.class, a1);
    }
    
    static {
        $VALUES = new Environment[] { Environment.UNKNOWN, Environment.ENVIRONMENT_VARIABLE, Environment.WELL_KNOWN_FILE, Environment.CLOUD_SHELL, Environment.APP_ENGINE, Environment.COMPUTE_ENGINE };
    }
}
