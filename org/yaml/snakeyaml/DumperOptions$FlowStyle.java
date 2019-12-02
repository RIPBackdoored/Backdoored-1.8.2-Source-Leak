package org.yaml.snakeyaml;

public enum FlowStyle
{
    FLOW(Boolean.TRUE), 
    BLOCK(Boolean.FALSE), 
    AUTO((Boolean)null);
    
    private Boolean styleBoolean;
    private static final /* synthetic */ FlowStyle[] $VALUES;
    
    public static FlowStyle[] values() {
        return FlowStyle.$VALUES.clone();
    }
    
    public static FlowStyle valueOf(final String name) {
        return Enum.valueOf(FlowStyle.class, name);
    }
    
    private FlowStyle(final Boolean flowStyle) {
        this.styleBoolean = flowStyle;
    }
    
    public Boolean getStyleBoolean() {
        return this.styleBoolean;
    }
    
    @Override
    public String toString() {
        return "Flow style: '" + this.styleBoolean + "'";
    }
    
    static {
        $VALUES = new FlowStyle[] { FlowStyle.FLOW, FlowStyle.BLOCK, FlowStyle.AUTO };
    }
}
