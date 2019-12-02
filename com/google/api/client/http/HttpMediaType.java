package com.google.api.client.http;

import com.google.api.client.util.*;
import java.util.regex.*;
import java.util.*;
import java.nio.charset.*;

public final class HttpMediaType
{
    private static final Pattern TYPE_REGEX;
    private static final Pattern TOKEN_REGEX;
    private static final Pattern FULL_MEDIA_TYPE_REGEX;
    private static final Pattern PARAMETER_REGEX;
    private String type;
    private String subType;
    private final SortedMap<String, String> parameters;
    private String cachedBuildResult;
    
    public HttpMediaType(final String a1, final String a2) {
        super();
        this.type = "application";
        this.subType = "octet-stream";
        this.parameters = new TreeMap<String, String>();
        this.setType(a1);
        this.setSubType(a2);
    }
    
    public HttpMediaType(final String a1) {
        super();
        this.type = "application";
        this.subType = "octet-stream";
        this.parameters = new TreeMap<String, String>();
        this.fromString(a1);
    }
    
    public HttpMediaType setType(final String a1) {
        Preconditions.checkArgument(HttpMediaType.TYPE_REGEX.matcher(a1).matches(), (Object)"Type contains reserved characters");
        this.type = a1;
        this.cachedBuildResult = null;
        return this;
    }
    
    public String getType() {
        return this.type;
    }
    
    public HttpMediaType setSubType(final String a1) {
        Preconditions.checkArgument(HttpMediaType.TYPE_REGEX.matcher(a1).matches(), (Object)"Subtype contains reserved characters");
        this.subType = a1;
        this.cachedBuildResult = null;
        return this;
    }
    
    public String getSubType() {
        return this.subType;
    }
    
    private HttpMediaType fromString(final String v-3) {
        Matcher matcher = HttpMediaType.FULL_MEDIA_TYPE_REGEX.matcher(v-3);
        Preconditions.checkArgument(matcher.matches(), (Object)"Type must be in the 'maintype/subtype; parameter=value' format");
        this.setType(matcher.group(1));
        this.setSubType(matcher.group(2));
        final String group = matcher.group(3);
        if (group != null) {
            matcher = HttpMediaType.PARAMETER_REGEX.matcher(group);
            while (matcher.find()) {
                final String a1 = matcher.group(1);
                String v1 = matcher.group(3);
                if (v1 == null) {
                    v1 = matcher.group(2);
                }
                this.setParameter(a1, v1);
            }
        }
        return this;
    }
    
    public HttpMediaType setParameter(final String a1, final String a2) {
        if (a2 == null) {
            this.removeParameter(a1);
            return this;
        }
        Preconditions.checkArgument(HttpMediaType.TOKEN_REGEX.matcher(a1).matches(), (Object)"Name contains reserved characters");
        this.cachedBuildResult = null;
        this.parameters.put(a1.toLowerCase(Locale.US), a2);
        return this;
    }
    
    public String getParameter(final String a1) {
        return this.parameters.get(a1.toLowerCase(Locale.US));
    }
    
    public HttpMediaType removeParameter(final String a1) {
        this.cachedBuildResult = null;
        this.parameters.remove(a1.toLowerCase(Locale.US));
        return this;
    }
    
    public void clearParameters() {
        this.cachedBuildResult = null;
        this.parameters.clear();
    }
    
    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends String>)this.parameters);
    }
    
    static boolean matchesToken(final String a1) {
        return HttpMediaType.TOKEN_REGEX.matcher(a1).matches();
    }
    
    private static String quoteString(final String a1) {
        String v1 = a1.replace("\\", "\\\\");
        v1 = v1.replace("\"", "\\\"");
        return "\"" + v1 + "\"";
    }
    
    public String build() {
        if (this.cachedBuildResult != null) {
            return this.cachedBuildResult;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(this.type);
        sb.append('/');
        sb.append(this.subType);
        if (this.parameters != null) {
            for (final Map.Entry<String, String> v0 : this.parameters.entrySet()) {
                final String v2 = v0.getValue();
                sb.append("; ");
                sb.append(v0.getKey());
                sb.append("=");
                sb.append(matchesToken(v2) ? v2 : quoteString(v2));
            }
        }
        return this.cachedBuildResult = sb.toString();
    }
    
    @Override
    public String toString() {
        return this.build();
    }
    
    public boolean equalsIgnoreParameters(final HttpMediaType a1) {
        return a1 != null && this.getType().equalsIgnoreCase(a1.getType()) && this.getSubType().equalsIgnoreCase(a1.getSubType());
    }
    
    public static boolean equalsIgnoreParameters(final String a1, final String a2) {
        return (a1 == null && a2 == null) || (a1 != null && a2 != null && new HttpMediaType(a1).equalsIgnoreParameters(new HttpMediaType(a2)));
    }
    
    public HttpMediaType setCharsetParameter(final Charset a1) {
        this.setParameter("charset", (a1 == null) ? null : a1.name());
        return this;
    }
    
    public Charset getCharsetParameter() {
        final String v1 = this.getParameter("charset");
        return (v1 == null) ? null : Charset.forName(v1);
    }
    
    @Override
    public int hashCode() {
        return this.build().hashCode();
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (!(a1 instanceof HttpMediaType)) {
            return false;
        }
        final HttpMediaType v1 = (HttpMediaType)a1;
        return this.equalsIgnoreParameters(v1) && this.parameters.equals(v1.parameters);
    }
    
    static {
        TYPE_REGEX = Pattern.compile("[\\w!#$&.+\\-\\^_]+|[*]");
        TOKEN_REGEX = Pattern.compile("[\\p{ASCII}&&[^\\p{Cntrl} ;/=\\[\\]\\(\\)\\<\\>\\@\\,\\:\\\"\\?\\=]]+");
        final String v1 = "[^\\s/=;\"]+";
        final String v2 = ";.*";
        FULL_MEDIA_TYPE_REGEX = Pattern.compile("\\s*(" + v1 + ")/(" + v1 + ")\\s*(" + v2 + ")?", 32);
        final String v3 = "\"([^\"]*)\"";
        final String v4 = "[^\\s;\"]*";
        final String v5 = v3 + "|" + v4;
        PARAMETER_REGEX = Pattern.compile("\\s*;\\s*(" + v1 + ")=(" + v5 + ")");
    }
}
