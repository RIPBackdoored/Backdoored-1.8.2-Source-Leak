package com.google.api.client.http;

import com.google.api.client.util.*;
import java.util.*;
import java.net.*;
import com.google.api.client.util.escape.*;

public class GenericUrl extends GenericData
{
    private static final Escaper URI_FRAGMENT_ESCAPER;
    private String scheme;
    private String host;
    private String userInfo;
    private int port;
    private List<String> pathParts;
    private String fragment;
    
    public GenericUrl() {
        super();
        this.port = -1;
    }
    
    public GenericUrl(final String a1) {
        this(parseURL(a1));
    }
    
    public GenericUrl(final URI a1) {
        this(a1.getScheme(), a1.getHost(), a1.getPort(), a1.getRawPath(), a1.getRawFragment(), a1.getRawQuery(), a1.getRawUserInfo());
    }
    
    public GenericUrl(final URL a1) {
        this(a1.getProtocol(), a1.getHost(), a1.getPort(), a1.getPath(), a1.getRef(), a1.getQuery(), a1.getUserInfo());
    }
    
    private GenericUrl(final String a1, final String a2, final int a3, final String a4, final String a5, final String a6, final String a7) {
        super();
        this.port = -1;
        this.scheme = a1.toLowerCase(Locale.US);
        this.host = a2;
        this.port = a3;
        this.pathParts = toPathParts(a4);
        this.fragment = ((a5 != null) ? CharEscapers.decodeUri(a5) : null);
        if (a6 != null) {
            UrlEncodedParser.parse(a6, this);
        }
        this.userInfo = ((a7 != null) ? CharEscapers.decodeUri(a7) : null);
    }
    
    @Override
    public int hashCode() {
        return this.build().hashCode();
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (!super.equals(a1) || !(a1 instanceof GenericUrl)) {
            return false;
        }
        final GenericUrl v1 = (GenericUrl)a1;
        return this.build().equals(v1.build());
    }
    
    @Override
    public String toString() {
        return this.build();
    }
    
    @Override
    public GenericUrl clone() {
        final GenericUrl v1 = (GenericUrl)super.clone();
        if (this.pathParts != null) {
            v1.pathParts = new ArrayList<String>(this.pathParts);
        }
        return v1;
    }
    
    @Override
    public GenericUrl set(final String a1, final Object a2) {
        return (GenericUrl)super.set(a1, a2);
    }
    
    public final String getScheme() {
        return this.scheme;
    }
    
    public final void setScheme(final String a1) {
        this.scheme = Preconditions.checkNotNull(a1);
    }
    
    public String getHost() {
        return this.host;
    }
    
    public final void setHost(final String a1) {
        this.host = Preconditions.checkNotNull(a1);
    }
    
    public final String getUserInfo() {
        return this.userInfo;
    }
    
    public final void setUserInfo(final String a1) {
        this.userInfo = a1;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public final void setPort(final int a1) {
        Preconditions.checkArgument(a1 >= -1, (Object)"expected port >= -1");
        this.port = a1;
    }
    
    public List<String> getPathParts() {
        return this.pathParts;
    }
    
    public void setPathParts(final List<String> a1) {
        this.pathParts = a1;
    }
    
    public String getFragment() {
        return this.fragment;
    }
    
    public final void setFragment(final String a1) {
        this.fragment = a1;
    }
    
    public final String build() {
        return this.buildAuthority() + this.buildRelativeUrl();
    }
    
    public final String buildAuthority() {
        final StringBuilder v1 = new StringBuilder();
        v1.append(Preconditions.checkNotNull(this.scheme));
        v1.append("://");
        if (this.userInfo != null) {
            v1.append(CharEscapers.escapeUriUserInfo(this.userInfo)).append('@');
        }
        v1.append(Preconditions.checkNotNull(this.host));
        final int v2 = this.port;
        if (v2 != -1) {
            v1.append(':').append(v2);
        }
        return v1.toString();
    }
    
    public final String buildRelativeUrl() {
        final StringBuilder v1 = new StringBuilder();
        if (this.pathParts != null) {
            this.appendRawPathFromParts(v1);
        }
        addQueryParams(this.entrySet(), v1);
        final String v2 = this.fragment;
        if (v2 != null) {
            v1.append('#').append(GenericUrl.URI_FRAGMENT_ESCAPER.escape(v2));
        }
        return v1.toString();
    }
    
    public final URI toURI() {
        return toURI(this.build());
    }
    
    public final URL toURL() {
        return parseURL(this.build());
    }
    
    public final URL toURL(final String v0) {
        try {
            final URL a1 = this.toURL();
            return new URL(a1, v0);
        }
        catch (MalformedURLException v) {
            throw new IllegalArgumentException(v);
        }
    }
    
    public Object getFirst(final String v-2) {
        final Object value = this.get(v-2);
        if (value instanceof Collection) {
            final Collection<Object> a1 = (Collection<Object>)value;
            final Iterator<Object> v1 = a1.iterator();
            return v1.hasNext() ? v1.next() : null;
        }
        return value;
    }
    
    public Collection<Object> getAll(final String v2) {
        final Object v3 = this.get(v2);
        if (v3 == null) {
            return Collections.emptySet();
        }
        if (v3 instanceof Collection) {
            final Collection<Object> a1 = (Collection<Object>)v3;
            return Collections.unmodifiableCollection((Collection<?>)a1);
        }
        return Collections.singleton(v3);
    }
    
    public String getRawPath() {
        final List<String> v1 = this.pathParts;
        if (v1 == null) {
            return null;
        }
        final StringBuilder v2 = new StringBuilder();
        this.appendRawPathFromParts(v2);
        return v2.toString();
    }
    
    public void setRawPath(final String a1) {
        this.pathParts = toPathParts(a1);
    }
    
    public void appendRawPath(final String v0) {
        if (v0 != null && v0.length() != 0) {
            final List<String> v = toPathParts(v0);
            if (this.pathParts == null || this.pathParts.isEmpty()) {
                this.pathParts = v;
            }
            else {
                final int a1 = this.pathParts.size();
                this.pathParts.set(a1 - 1, this.pathParts.get(a1 - 1) + v.get(0));
                this.pathParts.addAll(v.subList(1, v.size()));
            }
        }
    }
    
    public static List<String> toPathParts(final String v-3) {
        if (v-3 == null || v-3.length() == 0) {
            return null;
        }
        final List<String> list = new ArrayList<String>();
        int n = 0;
        boolean v0 = true;
        while (v0) {
            final int v2 = v-3.indexOf(47, n);
            v0 = (v2 != -1);
            String v3 = null;
            if (v0) {
                final String a1 = v-3.substring(n, v2);
            }
            else {
                v3 = v-3.substring(n);
            }
            list.add(CharEscapers.decodeUri(v3));
            n = v2 + 1;
        }
        return list;
    }
    
    private void appendRawPathFromParts(final StringBuilder v-1) {
        for (int v0 = this.pathParts.size(), v2 = 0; v2 < v0; ++v2) {
            final String a1 = this.pathParts.get(v2);
            if (v2 != 0) {
                v-1.append('/');
            }
            if (a1.length() != 0) {
                v-1.append(CharEscapers.escapeUriPath(a1));
            }
        }
    }
    
    static void addQueryParams(final Set<Map.Entry<String, Object>> v-5, final StringBuilder v-4) {
        boolean b = true;
        for (final Map.Entry<String, Object> entry : v-5) {
            final Object v0 = entry.getValue();
            if (v0 != null) {
                final String v2 = CharEscapers.escapeUriQuery(entry.getKey());
                if (v0 instanceof Collection) {
                    final Collection<?> a2 = (Collection<?>)v0;
                    for (final Object a3 : a2) {
                        b = appendParam(b, v-4, v2, a3);
                    }
                }
                else {
                    b = appendParam(b, v-4, v2, v0);
                }
            }
        }
    }
    
    private static boolean appendParam(boolean a1, final StringBuilder a2, final String a3, final Object a4) {
        if (a1) {
            a1 = false;
            a2.append('?');
        }
        else {
            a2.append('&');
        }
        a2.append(a3);
        final String v1 = CharEscapers.escapeUriQuery(a4.toString());
        if (v1.length() != 0) {
            a2.append('=').append(v1);
        }
        return a1;
    }
    
    private static URI toURI(final String v1) {
        try {
            return new URI(v1);
        }
        catch (URISyntaxException a1) {
            throw new IllegalArgumentException(a1);
        }
    }
    
    private static URL parseURL(final String v1) {
        try {
            return new URL(v1);
        }
        catch (MalformedURLException a1) {
            throw new IllegalArgumentException(a1);
        }
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
    
    static {
        URI_FRAGMENT_ESCAPER = new PercentEscaper("=&-_.!~*'()@:$,;/?:", false);
    }
}
