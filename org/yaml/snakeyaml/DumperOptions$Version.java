package org.yaml.snakeyaml;

public enum Version
{
    V1_0(new Integer[] { 1, 0 }), 
    V1_1(new Integer[] { 1, 1 });
    
    private Integer[] version;
    private static final /* synthetic */ Version[] $VALUES;
    
    public static Version[] values() {
        return Version.$VALUES.clone();
    }
    
    public static Version valueOf(final String name) {
        return Enum.valueOf(Version.class, name);
    }
    
    private Version(final Integer[] version) {
        this.version = version;
    }
    
    public int major() {
        return this.version[0];
    }
    
    public String getRepresentation() {
        return this.version[0] + "." + this.version[1];
    }
    
    @Override
    public String toString() {
        return "Version: " + this.getRepresentation();
    }
    
    static {
        $VALUES = new Version[] { Version.V1_0, Version.V1_1 };
    }
}
