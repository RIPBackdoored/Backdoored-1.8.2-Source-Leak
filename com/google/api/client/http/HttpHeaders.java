package com.google.api.client.http;

import java.util.logging.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.api.client.util.*;
import java.util.*;

public class HttpHeaders extends GenericData
{
    @Key("Accept")
    private List<String> accept;
    @Key("Accept-Encoding")
    private List<String> acceptEncoding;
    @Key("Authorization")
    private List<String> authorization;
    @Key("Cache-Control")
    private List<String> cacheControl;
    @Key("Content-Encoding")
    private List<String> contentEncoding;
    @Key("Content-Length")
    private List<Long> contentLength;
    @Key("Content-MD5")
    private List<String> contentMD5;
    @Key("Content-Range")
    private List<String> contentRange;
    @Key("Content-Type")
    private List<String> contentType;
    @Key("Cookie")
    private List<String> cookie;
    @Key("Date")
    private List<String> date;
    @Key("ETag")
    private List<String> etag;
    @Key("Expires")
    private List<String> expires;
    @Key("If-Modified-Since")
    private List<String> ifModifiedSince;
    @Key("If-Match")
    private List<String> ifMatch;
    @Key("If-None-Match")
    private List<String> ifNoneMatch;
    @Key("If-Unmodified-Since")
    private List<String> ifUnmodifiedSince;
    @Key("If-Range")
    private List<String> ifRange;
    @Key("Last-Modified")
    private List<String> lastModified;
    @Key("Location")
    private List<String> location;
    @Key("MIME-Version")
    private List<String> mimeVersion;
    @Key("Range")
    private List<String> range;
    @Key("Retry-After")
    private List<String> retryAfter;
    @Key("User-Agent")
    private List<String> userAgent;
    @Key("WWW-Authenticate")
    private List<String> authenticate;
    @Key("Age")
    private List<Long> age;
    
    public HttpHeaders() {
        super(EnumSet.of(Flags.IGNORE_CASE));
        this.acceptEncoding = new ArrayList<String>(Collections.singleton("gzip"));
    }
    
    @Override
    public HttpHeaders clone() {
        return (HttpHeaders)super.clone();
    }
    
    @Override
    public HttpHeaders set(final String a1, final Object a2) {
        return (HttpHeaders)super.set(a1, a2);
    }
    
    public final String getAccept() {
        return this.getFirstHeaderValue(this.accept);
    }
    
    public HttpHeaders setAccept(final String a1) {
        this.accept = this.getAsList(a1);
        return this;
    }
    
    public final String getAcceptEncoding() {
        return this.getFirstHeaderValue(this.acceptEncoding);
    }
    
    public HttpHeaders setAcceptEncoding(final String a1) {
        this.acceptEncoding = this.getAsList(a1);
        return this;
    }
    
    public final String getAuthorization() {
        return this.getFirstHeaderValue(this.authorization);
    }
    
    public final List<String> getAuthorizationAsList() {
        return this.authorization;
    }
    
    public HttpHeaders setAuthorization(final String a1) {
        return this.setAuthorization(this.getAsList(a1));
    }
    
    public HttpHeaders setAuthorization(final List<String> a1) {
        this.authorization = a1;
        return this;
    }
    
    public final String getCacheControl() {
        return this.getFirstHeaderValue(this.cacheControl);
    }
    
    public HttpHeaders setCacheControl(final String a1) {
        this.cacheControl = this.getAsList(a1);
        return this;
    }
    
    public final String getContentEncoding() {
        return this.getFirstHeaderValue(this.contentEncoding);
    }
    
    public HttpHeaders setContentEncoding(final String a1) {
        this.contentEncoding = this.getAsList(a1);
        return this;
    }
    
    public final Long getContentLength() {
        return this.getFirstHeaderValue(this.contentLength);
    }
    
    public HttpHeaders setContentLength(final Long a1) {
        this.contentLength = this.getAsList(a1);
        return this;
    }
    
    public final String getContentMD5() {
        return this.getFirstHeaderValue(this.contentMD5);
    }
    
    public HttpHeaders setContentMD5(final String a1) {
        this.contentMD5 = this.getAsList(a1);
        return this;
    }
    
    public final String getContentRange() {
        return this.getFirstHeaderValue(this.contentRange);
    }
    
    public HttpHeaders setContentRange(final String a1) {
        this.contentRange = this.getAsList(a1);
        return this;
    }
    
    public final String getContentType() {
        return this.getFirstHeaderValue(this.contentType);
    }
    
    public HttpHeaders setContentType(final String a1) {
        this.contentType = this.getAsList(a1);
        return this;
    }
    
    public final String getCookie() {
        return this.getFirstHeaderValue(this.cookie);
    }
    
    public HttpHeaders setCookie(final String a1) {
        this.cookie = this.getAsList(a1);
        return this;
    }
    
    public final String getDate() {
        return this.getFirstHeaderValue(this.date);
    }
    
    public HttpHeaders setDate(final String a1) {
        this.date = this.getAsList(a1);
        return this;
    }
    
    public final String getETag() {
        return this.getFirstHeaderValue(this.etag);
    }
    
    public HttpHeaders setETag(final String a1) {
        this.etag = this.getAsList(a1);
        return this;
    }
    
    public final String getExpires() {
        return this.getFirstHeaderValue(this.expires);
    }
    
    public HttpHeaders setExpires(final String a1) {
        this.expires = this.getAsList(a1);
        return this;
    }
    
    public final String getIfModifiedSince() {
        return this.getFirstHeaderValue(this.ifModifiedSince);
    }
    
    public HttpHeaders setIfModifiedSince(final String a1) {
        this.ifModifiedSince = this.getAsList(a1);
        return this;
    }
    
    public final String getIfMatch() {
        return this.getFirstHeaderValue(this.ifMatch);
    }
    
    public HttpHeaders setIfMatch(final String a1) {
        this.ifMatch = this.getAsList(a1);
        return this;
    }
    
    public final String getIfNoneMatch() {
        return this.getFirstHeaderValue(this.ifNoneMatch);
    }
    
    public HttpHeaders setIfNoneMatch(final String a1) {
        this.ifNoneMatch = this.getAsList(a1);
        return this;
    }
    
    public final String getIfUnmodifiedSince() {
        return this.getFirstHeaderValue(this.ifUnmodifiedSince);
    }
    
    public HttpHeaders setIfUnmodifiedSince(final String a1) {
        this.ifUnmodifiedSince = this.getAsList(a1);
        return this;
    }
    
    public final String getIfRange() {
        return this.getFirstHeaderValue(this.ifRange);
    }
    
    public HttpHeaders setIfRange(final String a1) {
        this.ifRange = this.getAsList(a1);
        return this;
    }
    
    public final String getLastModified() {
        return this.getFirstHeaderValue(this.lastModified);
    }
    
    public HttpHeaders setLastModified(final String a1) {
        this.lastModified = this.getAsList(a1);
        return this;
    }
    
    public final String getLocation() {
        return this.getFirstHeaderValue(this.location);
    }
    
    public HttpHeaders setLocation(final String a1) {
        this.location = this.getAsList(a1);
        return this;
    }
    
    public final String getMimeVersion() {
        return this.getFirstHeaderValue(this.mimeVersion);
    }
    
    public HttpHeaders setMimeVersion(final String a1) {
        this.mimeVersion = this.getAsList(a1);
        return this;
    }
    
    public final String getRange() {
        return this.getFirstHeaderValue(this.range);
    }
    
    public HttpHeaders setRange(final String a1) {
        this.range = this.getAsList(a1);
        return this;
    }
    
    public final String getRetryAfter() {
        return this.getFirstHeaderValue(this.retryAfter);
    }
    
    public HttpHeaders setRetryAfter(final String a1) {
        this.retryAfter = this.getAsList(a1);
        return this;
    }
    
    public final String getUserAgent() {
        return this.getFirstHeaderValue(this.userAgent);
    }
    
    public HttpHeaders setUserAgent(final String a1) {
        this.userAgent = this.getAsList(a1);
        return this;
    }
    
    public final String getAuthenticate() {
        return this.getFirstHeaderValue(this.authenticate);
    }
    
    public final List<String> getAuthenticateAsList() {
        return this.authenticate;
    }
    
    public HttpHeaders setAuthenticate(final String a1) {
        this.authenticate = this.getAsList(a1);
        return this;
    }
    
    public final Long getAge() {
        return this.getFirstHeaderValue(this.age);
    }
    
    public HttpHeaders setAge(final Long a1) {
        this.age = this.getAsList(a1);
        return this;
    }
    
    public HttpHeaders setBasicAuthentication(final String a1, final String a2) {
        final String v1 = Preconditions.checkNotNull(a1) + ":" + Preconditions.checkNotNull(a2);
        final String v2 = Base64.encodeBase64String(StringUtils.getBytesUtf8(v1));
        return this.setAuthorization("Basic " + v2);
    }
    
    private static void addHeader(final Logger a1, final StringBuilder a2, final StringBuilder a3, final LowLevelHttpRequest a4, final String a5, final Object a6, final Writer a7) throws IOException {
        if (a6 == null || Data.isNull(a6)) {
            return;
        }
        String v2;
        final String v1 = v2 = toStringValue(a6);
        if (("Authorization".equalsIgnoreCase(a5) || "Cookie".equalsIgnoreCase(a5)) && (a1 == null || !a1.isLoggable(Level.ALL))) {
            v2 = "<Not Logged>";
        }
        if (a2 != null) {
            a2.append(a5).append(": ");
            a2.append(v2);
            a2.append(StringUtils.LINE_SEPARATOR);
        }
        if (a3 != null) {
            a3.append(" -H '").append(a5).append(": ").append(v2).append("'");
        }
        if (a4 != null) {
            a4.addHeader(a5, v1);
        }
        if (a7 != null) {
            a7.write(a5);
            a7.write(": ");
            a7.write(v1);
            a7.write("\r\n");
        }
    }
    
    private static String toStringValue(final Object a1) {
        return (a1 instanceof Enum) ? FieldInfo.of((Enum<?>)a1).getName() : a1.toString();
    }
    
    static void serializeHeaders(final HttpHeaders a1, final StringBuilder a2, final StringBuilder a3, final Logger a4, final LowLevelHttpRequest a5) throws IOException {
        serializeHeaders(a1, a2, a3, a4, a5, null);
    }
    
    public static void serializeHeadersForMultipartRequests(final HttpHeaders a1, final StringBuilder a2, final Logger a3, final Writer a4) throws IOException {
        serializeHeaders(a1, a2, null, a3, null, a4);
    }
    
    static void serializeHeaders(final HttpHeaders v-7, final StringBuilder v-6, final StringBuilder v-5, final Logger v-4, final LowLevelHttpRequest v-3, final Writer v-2) throws IOException {
        final HashSet<String> set = new HashSet<String>();
        for (final Map.Entry<String, Object> v1 : v-7.entrySet()) {
            final String a5 = v1.getKey();
            Preconditions.checkArgument(set.add(a5), "multiple headers of the same name (headers are case insensitive): %s", a5);
            final Object a6 = v1.getValue();
            if (a6 != null) {
                String a7 = a5;
                final FieldInfo a8 = v-7.getClassInfo().getFieldInfo(a5);
                if (a8 != null) {
                    a7 = a8.getName();
                }
                final Class<?> a9 = a6.getClass();
                if (a6 instanceof Iterable || a9.isArray()) {
                    for (final Object a10 : Types.iterableOf(a6)) {
                        addHeader(v-4, v-6, v-5, v-3, a7, a10, v-2);
                    }
                }
                else {
                    addHeader(v-4, v-6, v-5, v-3, a7, a6, v-2);
                }
            }
        }
        if (v-2 != null) {
            v-2.flush();
        }
    }
    
    public final void fromHttpResponse(final LowLevelHttpResponse v1, final StringBuilder v2) throws IOException {
        this.clear();
        final ParseHeaderState v3 = new ParseHeaderState(this, v2);
        for (int v4 = v1.getHeaderCount(), a1 = 0; a1 < v4; ++a1) {
            this.parseHeader(v1.getHeaderName(a1), v1.getHeaderValue(a1), v3);
        }
        v3.finish();
    }
    
    private <T> T getFirstHeaderValue(final List<T> a1) {
        return (a1 == null) ? null : a1.get(0);
    }
    
    private <T> List<T> getAsList(final T a1) {
        if (a1 == null) {
            return null;
        }
        final List<T> v1 = new ArrayList<T>();
        v1.add(a1);
        return v1;
    }
    
    public String getFirstHeaderStringValue(final String v2) {
        final Object v3 = this.get(v2.toLowerCase(Locale.US));
        if (v3 == null) {
            return null;
        }
        final Class<?> v4 = v3.getClass();
        if (v3 instanceof Iterable || v4.isArray()) {
            final Iterator<Object> iterator = Types.iterableOf(v3).iterator();
            if (iterator.hasNext()) {
                final Object a1 = iterator.next();
                return toStringValue(a1);
            }
        }
        return toStringValue(v3);
    }
    
    public List<String> getHeaderStringValues(final String v-2) {
        final Object value = this.get(v-2.toLowerCase(Locale.US));
        if (value == null) {
            return Collections.emptyList();
        }
        final Class<?> v0 = value.getClass();
        if (value instanceof Iterable || v0.isArray()) {
            final List<String> v2 = new ArrayList<String>();
            for (final Object a1 : Types.iterableOf(value)) {
                v2.add(toStringValue(a1));
            }
            return Collections.unmodifiableList((List<? extends String>)v2);
        }
        return Collections.singletonList(toStringValue(value));
    }
    
    public final void fromHttpHeaders(final HttpHeaders v0) {
        try {
            final ParseHeaderState a1 = new ParseHeaderState(this, null);
            serializeHeaders(v0, null, null, null, new HeaderParsingFakeLevelHttpRequest(this, a1));
            a1.finish();
        }
        catch (IOException v) {
            throw Throwables.propagate(v);
        }
    }
    
    void parseHeader(final String v-7, final String v-6, final ParseHeaderState v-5) {
        final List<Type> context = v-5.context;
        final ClassInfo classInfo = v-5.classInfo;
        final ArrayValueMap arrayValueMap = v-5.arrayValueMap;
        final StringBuilder logger = v-5.logger;
        if (logger != null) {
            logger.append(v-7 + ": " + v-6).append(StringUtils.LINE_SEPARATOR);
        }
        final FieldInfo v0 = classInfo.getFieldInfo(v-7);
        if (v0 != null) {
            final Type v2 = Data.resolveWildcardTypeOrTypeVariable(context, v0.getGenericType());
            if (Types.isArray(v2)) {
                final Class<?> a1 = Types.getRawArrayComponentType(context, Types.getArrayComponentType(v2));
                arrayValueMap.put(v0.getField(), a1, parseValue(a1, context, v-6));
            }
            else if (Types.isAssignableToOrFrom(Types.getRawArrayComponentType(context, v2), Iterable.class)) {
                Collection<Object> a2 = (Collection<Object>)v0.getValue(this);
                if (a2 == null) {
                    a2 = Data.newCollectionInstance(v2);
                    v0.setValue(this, a2);
                }
                final Type a3 = (v2 == Object.class) ? null : Types.getIterableParameter(v2);
                a2.add(parseValue(a3, context, v-6));
            }
            else {
                v0.setValue(this, parseValue(v2, context, v-6));
            }
        }
        else {
            ArrayList<String> v3 = (ArrayList<String>)this.get(v-7);
            if (v3 == null) {
                v3 = new ArrayList<String>();
                this.set(v-7, v3);
            }
            v3.add(v-6);
        }
    }
    
    private static Object parseValue(final Type a1, final List<Type> a2, final String a3) {
        final Type v1 = Data.resolveWildcardTypeOrTypeVariable(a2, a1);
        return Data.parsePrimitiveValue(v1, a3);
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    private static class HeaderParsingFakeLevelHttpRequest extends LowLevelHttpRequest
    {
        private final HttpHeaders target;
        private final ParseHeaderState state;
        
        HeaderParsingFakeLevelHttpRequest(final HttpHeaders a1, final ParseHeaderState a2) {
            super();
            this.target = a1;
            this.state = a2;
        }
        
        @Override
        public void addHeader(final String a1, final String a2) {
            this.target.parseHeader(a1, a2, this.state);
        }
        
        @Override
        public LowLevelHttpResponse execute() throws IOException {
            throw new UnsupportedOperationException();
        }
    }
    
    private static final class ParseHeaderState
    {
        final ArrayValueMap arrayValueMap;
        final StringBuilder logger;
        final ClassInfo classInfo;
        final List<Type> context;
        
        public ParseHeaderState(final HttpHeaders a1, final StringBuilder a2) {
            super();
            final Class<? extends HttpHeaders> v1 = a1.getClass();
            this.context = Arrays.asList(v1);
            this.classInfo = ClassInfo.of(v1, true);
            this.logger = a2;
            this.arrayValueMap = new ArrayValueMap(a1);
        }
        
        void finish() {
            this.arrayValueMap.setValues();
        }
    }
}
