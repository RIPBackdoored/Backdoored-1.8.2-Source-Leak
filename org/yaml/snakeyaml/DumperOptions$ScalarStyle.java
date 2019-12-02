package org.yaml.snakeyaml;

public enum ScalarStyle
{
    DOUBLE_QUOTED(Character.valueOf('\"')), 
    SINGLE_QUOTED(Character.valueOf('\'')), 
    LITERAL(Character.valueOf('|')), 
    FOLDED(Character.valueOf('>')), 
    PLAIN((Character)null);
    
    private Character styleChar;
    private static final /* synthetic */ ScalarStyle[] $VALUES;
    
    public static ScalarStyle[] values() {
        return ScalarStyle.$VALUES.clone();
    }
    
    public static ScalarStyle valueOf(final String name) {
        return Enum.valueOf(ScalarStyle.class, name);
    }
    
    private ScalarStyle(final Character style) {
        this.styleChar = style;
    }
    
    public Character getChar() {
        return this.styleChar;
    }
    
    @Override
    public String toString() {
        return "Scalar style: '" + this.styleChar + "'";
    }
    
    static {
        $VALUES = new ScalarStyle[] { ScalarStyle.DOUBLE_QUOTED, ScalarStyle.SINGLE_QUOTED, ScalarStyle.LITERAL, ScalarStyle.FOLDED, ScalarStyle.PLAIN };
    }
}
