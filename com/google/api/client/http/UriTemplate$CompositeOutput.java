package com.google.api.client.http;

import com.google.api.client.util.*;
import com.google.api.client.util.escape.*;

private enum CompositeOutput
{
    PLUS(Character.valueOf('+'), "", ",", false, true), 
    HASH(Character.valueOf('#'), "#", ",", false, true), 
    DOT(Character.valueOf('.'), ".", ".", false, false), 
    FORWARD_SLASH(Character.valueOf('/'), "/", "/", false, false), 
    SEMI_COLON(Character.valueOf(';'), ";", ";", true, false), 
    QUERY(Character.valueOf('?'), "?", "&", true, false), 
    AMP(Character.valueOf('&'), "&", "&", true, false), 
    SIMPLE((Character)null, "", ",", false, false);
    
    private final Character propertyPrefix;
    private final String outputPrefix;
    private final String explodeJoiner;
    private final boolean requiresVarAssignment;
    private final boolean reservedExpansion;
    private static final /* synthetic */ CompositeOutput[] $VALUES;
    
    public static CompositeOutput[] values() {
        return CompositeOutput.$VALUES.clone();
    }
    
    public static CompositeOutput valueOf(final String a1) {
        return Enum.valueOf(CompositeOutput.class, a1);
    }
    
    private CompositeOutput(final Character a1, final String a2, final String a3, final boolean a4, final boolean a5) {
        this.propertyPrefix = a1;
        this.outputPrefix = Preconditions.checkNotNull(a2);
        this.explodeJoiner = Preconditions.checkNotNull(a3);
        this.requiresVarAssignment = a4;
        this.reservedExpansion = a5;
        if (a1 != null) {
            UriTemplate.COMPOSITE_PREFIXES.put(a1, this);
        }
    }
    
    String getOutputPrefix() {
        return this.outputPrefix;
    }
    
    String getExplodeJoiner() {
        return this.explodeJoiner;
    }
    
    boolean requiresVarAssignment() {
        return this.requiresVarAssignment;
    }
    
    int getVarNameStartIndex() {
        return (this.propertyPrefix != null) ? 1 : 0;
    }
    
    String getEncodedValue(final String v2) {
        String v3 = null;
        if (this.reservedExpansion) {
            final String a1 = CharEscapers.escapeUriPath(v2);
        }
        else {
            v3 = CharEscapers.escapeUri(v2);
        }
        return v3;
    }
    
    boolean getReservedExpansion() {
        return this.reservedExpansion;
    }
    
    static {
        $VALUES = new CompositeOutput[] { CompositeOutput.PLUS, CompositeOutput.HASH, CompositeOutput.DOT, CompositeOutput.FORWARD_SLASH, CompositeOutput.SEMI_COLON, CompositeOutput.QUERY, CompositeOutput.AMP, CompositeOutput.SIMPLE };
    }
}
