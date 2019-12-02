package org.yaml.snakeyaml;

public enum LineBreak
{
    WIN("\r\n"), 
    MAC("\r"), 
    UNIX("\n");
    
    private String lineBreak;
    private static final /* synthetic */ LineBreak[] $VALUES;
    
    public static LineBreak[] values() {
        return LineBreak.$VALUES.clone();
    }
    
    public static LineBreak valueOf(final String name) {
        return Enum.valueOf(LineBreak.class, name);
    }
    
    private LineBreak(final String lineBreak) {
        this.lineBreak = lineBreak;
    }
    
    public String getString() {
        return this.lineBreak;
    }
    
    @Override
    public String toString() {
        return "Line break: " + this.name();
    }
    
    static {
        $VALUES = new LineBreak[] { LineBreak.WIN, LineBreak.MAC, LineBreak.UNIX };
    }
}
