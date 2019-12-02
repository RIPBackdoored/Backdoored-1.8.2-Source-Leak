package com.google.api.client.http;

import com.google.api.client.repackaged.com.google.common.base.*;
import com.google.api.client.util.escape.*;
import java.util.*;
import com.google.api.client.util.*;

public class UriTemplate
{
    static final Map<Character, CompositeOutput> COMPOSITE_PREFIXES;
    private static final String COMPOSITE_NON_EXPLODE_JOINER = ",";
    
    public UriTemplate() {
        super();
    }
    
    static CompositeOutput getCompositeOutput(final String a1) {
        final CompositeOutput v1 = UriTemplate.COMPOSITE_PREFIXES.get(a1.charAt(0));
        return (v1 == null) ? CompositeOutput.SIMPLE : v1;
    }
    
    private static Map<String, Object> getMap(final Object v-2) {
        final Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (final Map.Entry<String, Object> v1 : Data.mapOf(v-2).entrySet()) {
            final Object a1 = v1.getValue();
            if (a1 != null && !Data.isNull(a1)) {
                map.put(v1.getKey(), a1);
            }
        }
        return map;
    }
    
    public static String expand(final String a4, final String v1, final Object v2, final boolean v3) {
        String v4 = null;
        if (v1.startsWith("/")) {
            final GenericUrl a5 = new GenericUrl(a4);
            a5.setRawPath(null);
            final String a6 = a5.build() + v1;
        }
        else if (v1.startsWith("http://") || v1.startsWith("https://")) {
            final String a7 = v1;
        }
        else {
            v4 = a4 + v1;
        }
        return expand(v4, v2, v3);
    }
    
    public static String expand(final String v-18, final Object v-17, final boolean v-16) {
        final Map<String, Object> map = getMap(v-17);
        final StringBuilder v-19 = new StringBuilder();
        int i = 0;
        final int length = v-18.length();
        while (i < length) {
            final int index = v-18.indexOf(123, i);
            if (index == -1) {
                if (i == 0 && !v-16) {
                    return v-18;
                }
                v-19.append(v-18.substring(i));
                break;
            }
            else {
                v-19.append(v-18.substring(i, index));
                final int index2 = v-18.indexOf(125, index + 2);
                i = index2 + 1;
                final String substring = v-18.substring(index + 1, index2);
                final CompositeOutput compositeOutput = getCompositeOutput(substring);
                final ListIterator<String> listIterator = Splitter.on(',').splitToList(substring).listIterator();
                boolean b = true;
                while (listIterator.hasNext()) {
                    final String s = listIterator.next();
                    final boolean endsWith = s.endsWith("*");
                    final int n = (listIterator.nextIndex() == 1) ? compositeOutput.getVarNameStartIndex() : 0;
                    int length2 = s.length();
                    if (endsWith) {
                        --length2;
                    }
                    final String substring2 = s.substring(n, length2);
                    Object v0 = map.remove(substring2);
                    if (v0 == null) {
                        continue;
                    }
                    if (!b) {
                        v-19.append(compositeOutput.getExplodeJoiner());
                    }
                    else {
                        v-19.append(compositeOutput.getOutputPrefix());
                        b = false;
                    }
                    if (v0 instanceof Iterator) {
                        final Iterator<?> a1 = (Iterator<?>)v0;
                        v0 = getListPropertyValue(substring2, a1, endsWith, compositeOutput);
                    }
                    else if (v0 instanceof Iterable || v0.getClass().isArray()) {
                        final Iterator<?> a2 = Types.iterableOf(v0).iterator();
                        v0 = getListPropertyValue(substring2, a2, endsWith, compositeOutput);
                    }
                    else if (v0.getClass().isEnum()) {
                        final String a3 = FieldInfo.of((Enum<?>)v0).getName();
                        if (a3 != null) {
                            if (compositeOutput.requiresVarAssignment()) {
                                v0 = String.format("%s=%s", substring2, v0);
                            }
                            v0 = CharEscapers.escapeUriPath(v0.toString());
                        }
                    }
                    else if (!Data.isValueOfPrimitiveType(v0)) {
                        final Map<String, Object> v2 = getMap(v0);
                        v0 = getMapPropertyValue(substring2, v2, endsWith, compositeOutput);
                    }
                    else {
                        if (compositeOutput.requiresVarAssignment()) {
                            v0 = String.format("%s=%s", substring2, v0);
                        }
                        if (compositeOutput.getReservedExpansion()) {
                            v0 = CharEscapers.escapeUriPathWithoutReserved(v0.toString());
                        }
                        else {
                            v0 = CharEscapers.escapeUriPath(v0.toString());
                        }
                    }
                    v-19.append(v0);
                }
            }
        }
        if (v-16) {
            GenericUrl.addQueryParams(map.entrySet(), v-19);
        }
        return v-19.toString();
    }
    
    private static String getListPropertyValue(final String a2, final Iterator<?> a3, final boolean a4, final CompositeOutput v1) {
        if (!a3.hasNext()) {
            return "";
        }
        final StringBuilder v2 = new StringBuilder();
        String v3 = null;
        if (a4) {
            final String a5 = v1.getExplodeJoiner();
        }
        else {
            v3 = ",";
            if (v1.requiresVarAssignment()) {
                v2.append(CharEscapers.escapeUriPath(a2));
                v2.append("=");
            }
        }
        while (a3.hasNext()) {
            if (a4 && v1.requiresVarAssignment()) {
                v2.append(CharEscapers.escapeUriPath(a2));
                v2.append("=");
            }
            v2.append(v1.getEncodedValue(a3.next().toString()));
            if (a3.hasNext()) {
                v2.append(v3);
            }
        }
        return v2.toString();
    }
    
    private static String getMapPropertyValue(final String v-9, final Map<String, Object> v-8, final boolean v-7, final CompositeOutput v-6) {
        if (v-8.isEmpty()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        String s = null;
        String s2 = null;
        if (v-7) {
            final String a1 = v-6.getExplodeJoiner();
            final String a2 = "=";
        }
        else {
            s = ",";
            s2 = ",";
            if (v-6.requiresVarAssignment()) {
                sb.append(CharEscapers.escapeUriPath(v-9));
                sb.append("=");
            }
        }
        final Iterator<Map.Entry<String, Object>> iterator = v-8.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, Object> a3 = iterator.next();
            final String a4 = v-6.getEncodedValue(a3.getKey());
            final String v1 = v-6.getEncodedValue(a3.getValue().toString());
            sb.append(a4);
            sb.append(s2);
            sb.append(v1);
            if (iterator.hasNext()) {
                sb.append(s);
            }
        }
        return sb.toString();
    }
    
    static {
        COMPOSITE_PREFIXES = new HashMap<Character, CompositeOutput>();
        CompositeOutput.values();
    }
    
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
}
