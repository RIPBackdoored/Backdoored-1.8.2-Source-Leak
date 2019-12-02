package org.yaml.snakeyaml;

import java.util.*;
import org.yaml.snakeyaml.serializer.*;

public class DumperOptions
{
    private ScalarStyle defaultStyle;
    private FlowStyle defaultFlowStyle;
    private boolean canonical;
    private boolean allowUnicode;
    private boolean allowReadOnlyProperties;
    private int indent;
    private int indicatorIndent;
    private int bestWidth;
    private boolean splitLines;
    private LineBreak lineBreak;
    private boolean explicitStart;
    private boolean explicitEnd;
    private TimeZone timeZone;
    private Version version;
    private Map<String, String> tags;
    private Boolean prettyFlow;
    private AnchorGenerator anchorGenerator;
    
    public DumperOptions() {
        super();
        this.defaultStyle = ScalarStyle.PLAIN;
        this.defaultFlowStyle = FlowStyle.AUTO;
        this.canonical = false;
        this.allowUnicode = true;
        this.allowReadOnlyProperties = false;
        this.indent = 2;
        this.indicatorIndent = 0;
        this.bestWidth = 80;
        this.splitLines = true;
        this.lineBreak = LineBreak.UNIX;
        this.explicitStart = false;
        this.explicitEnd = false;
        this.timeZone = null;
        this.version = null;
        this.tags = null;
        this.prettyFlow = false;
        this.anchorGenerator = new NumberAnchorGenerator(0);
    }
    
    public boolean isAllowUnicode() {
        return this.allowUnicode;
    }
    
    public ScalarStyle getDefaultScalarStyle() {
        return this.defaultStyle;
    }
    
    public int getIndent() {
        return this.indent;
    }
    
    public int getIndicatorIndent() {
        return this.indicatorIndent;
    }
    
    public Version getVersion() {
        return this.version;
    }
    
    public boolean isCanonical() {
        return this.canonical;
    }
    
    public boolean isPrettyFlow() {
        return this.prettyFlow;
    }
    
    public int getWidth() {
        return this.bestWidth;
    }
    
    public boolean getSplitLines() {
        return this.splitLines;
    }
    
    public LineBreak getLineBreak() {
        return this.lineBreak;
    }
    
    public void setDefaultFlowStyle(final FlowStyle defaultFlowStyle) {
        if (defaultFlowStyle == null) {
            throw new NullPointerException("Use FlowStyle enum.");
        }
        this.defaultFlowStyle = defaultFlowStyle;
    }
    
    public FlowStyle getDefaultFlowStyle() {
        return this.defaultFlowStyle;
    }
    
    public boolean isExplicitStart() {
        return this.explicitStart;
    }
    
    public boolean isExplicitEnd() {
        return this.explicitEnd;
    }
    
    public Map<String, String> getTags() {
        return this.tags;
    }
    
    public boolean isAllowReadOnlyProperties() {
        return this.allowReadOnlyProperties;
    }
    
    public TimeZone getTimeZone() {
        return this.timeZone;
    }
    
    public AnchorGenerator getAnchorGenerator() {
        return this.anchorGenerator;
    }
    
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
}
