package org.json;

public class XMLParserConfiguration
{
    public static final XMLParserConfiguration ORIGINAL;
    public static final XMLParserConfiguration KEEP_STRINGS;
    public final boolean keepStrings;
    public final String cDataTagName;
    public final boolean convertNilAttributeToNull;
    
    public XMLParserConfiguration() {
        this(false, "content", false);
    }
    
    public XMLParserConfiguration(final boolean a1) {
        this(a1, "content", false);
    }
    
    public XMLParserConfiguration(final String a1) {
        this(false, a1, false);
    }
    
    public XMLParserConfiguration(final boolean a1, final String a2) {
        super();
        this.keepStrings = a1;
        this.cDataTagName = a2;
        this.convertNilAttributeToNull = false;
    }
    
    public XMLParserConfiguration(final boolean a1, final String a2, final boolean a3) {
        super();
        this.keepStrings = a1;
        this.cDataTagName = a2;
        this.convertNilAttributeToNull = a3;
    }
    
    static {
        ORIGINAL = new XMLParserConfiguration();
        KEEP_STRINGS = new XMLParserConfiguration(true);
    }
}
