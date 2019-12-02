package com.google.cloud.storage;

import java.io.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.net.*;

public final class Cors implements Serializable
{
    private static final long serialVersionUID = -8637770919343335655L;
    static final Function<Bucket.Cors, Cors> FROM_PB_FUNCTION;
    static final Function<Cors, Bucket.Cors> TO_PB_FUNCTION;
    private final Integer maxAgeSeconds;
    private final ImmutableList<HttpMethod> methods;
    private final ImmutableList<Origin> origins;
    private final ImmutableList<String> responseHeaders;
    
    private Cors(final Builder a1) {
        super();
        this.maxAgeSeconds = a1.maxAgeSeconds;
        this.methods = a1.methods;
        this.origins = a1.origins;
        this.responseHeaders = a1.responseHeaders;
    }
    
    public Integer getMaxAgeSeconds() {
        return this.maxAgeSeconds;
    }
    
    public List<HttpMethod> getMethods() {
        return this.methods;
    }
    
    public List<Origin> getOrigins() {
        return this.origins;
    }
    
    public List<String> getResponseHeaders() {
        return this.responseHeaders;
    }
    
    public Builder toBuilder() {
        return newBuilder().setMaxAgeSeconds(this.maxAgeSeconds).setMethods(this.methods).setOrigins(this.origins).setResponseHeaders(this.responseHeaders);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.maxAgeSeconds, this.methods, this.origins, this.responseHeaders);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (!(a1 instanceof Cors)) {
            return false;
        }
        final Cors v1 = (Cors)a1;
        return Objects.equals(this.maxAgeSeconds, v1.maxAgeSeconds) && Objects.equals(this.methods, v1.methods) && Objects.equals(this.origins, v1.origins) && Objects.equals(this.responseHeaders, v1.responseHeaders);
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    Bucket.Cors toPb() {
        final Bucket.Cors v1 = new Bucket.Cors();
        v1.setMaxAgeSeconds(this.maxAgeSeconds);
        v1.setResponseHeader((List)this.responseHeaders);
        if (this.methods != null) {
            v1.setMethod((List)Lists.newArrayList(Iterables.transform((Iterable<HttpMethod>)this.methods, (Function<? super HttpMethod, ?>)Functions.toStringFunction())));
        }
        if (this.origins != null) {
            v1.setOrigin((List)Lists.newArrayList(Iterables.transform((Iterable<Origin>)this.origins, (Function<? super Origin, ?>)Functions.toStringFunction())));
        }
        return v1;
    }
    
    static Cors fromPb(final Bucket.Cors a1) {
        final Builder v1 = newBuilder().setMaxAgeSeconds(a1.getMaxAgeSeconds());
        if (a1.getMethod() != null) {
            v1.setMethods(Iterables.transform((Iterable<Object>)a1.getMethod(), (Function<? super Object, ? extends HttpMethod>)new Function<String, HttpMethod>() {
                Cors$3() {
                    super();
                }
                
                @Override
                public HttpMethod apply(final String a1) {
                    return HttpMethod.valueOf(a1.toUpperCase());
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            }));
        }
        if (a1.getOrigin() != null) {
            v1.setOrigins(Iterables.transform((Iterable<Object>)a1.getOrigin(), (Function<? super Object, ? extends Origin>)new Function<String, Origin>() {
                Cors$4() {
                    super();
                }
                
                @Override
                public Origin apply(final String a1) {
                    return Origin.of(a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            }));
        }
        v1.setResponseHeaders(a1.getResponseHeader());
        return v1.build();
    }
    
    Cors(final Builder a1, final Cors$1 a2) {
        this(a1);
    }
    
    static {
        FROM_PB_FUNCTION = new Function<Bucket.Cors, Cors>() {
            Cors$1() {
                super();
            }
            
            @Override
            public Cors apply(final Bucket.Cors a1) {
                return Cors.fromPb(a1);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Bucket.Cors)o);
            }
        };
        TO_PB_FUNCTION = new Function<Cors, Bucket.Cors>() {
            Cors$2() {
                super();
            }
            
            @Override
            public Bucket.Cors apply(final Cors a1) {
                return a1.toPb();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Cors)o);
            }
        };
    }
    
    public static final class Origin implements Serializable
    {
        private static final long serialVersionUID = -4447958124895577993L;
        private static final String ANY_URI = "*";
        private final String value;
        private static final Origin ANY;
        
        private Origin(final String a1) {
            super();
            this.value = Preconditions.checkNotNull(a1);
        }
        
        public static Origin any() {
            return Origin.ANY;
        }
        
        public static Origin of(final String a2, final String a3, final int v1) {
            try {
                return of(new URI(a2, null, a3, v1, null, null, null).toString());
            }
            catch (URISyntaxException a4) {
                throw new IllegalArgumentException(a4);
            }
        }
        
        public static Origin of(final String a1) {
            if ("*".equals(a1)) {
                return any();
            }
            return new Origin(a1);
        }
        
        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
        
        @Override
        public boolean equals(final Object a1) {
            return a1 instanceof Origin && this.value.equals(((Origin)a1).value);
        }
        
        @Override
        public String toString() {
            return this.getValue();
        }
        
        public String getValue() {
            return this.value;
        }
        
        static {
            ANY = new Origin("*");
        }
    }
    
    public static final class Builder
    {
        private Integer maxAgeSeconds;
        private ImmutableList<HttpMethod> methods;
        private ImmutableList<Origin> origins;
        private ImmutableList<String> responseHeaders;
        
        private Builder() {
            super();
        }
        
        public Builder setMaxAgeSeconds(final Integer a1) {
            this.maxAgeSeconds = a1;
            return this;
        }
        
        public Builder setMethods(final Iterable<HttpMethod> a1) {
            this.methods = ((a1 != null) ? ImmutableList.copyOf((Iterable<? extends HttpMethod>)a1) : null);
            return this;
        }
        
        public Builder setOrigins(final Iterable<Origin> a1) {
            this.origins = ((a1 != null) ? ImmutableList.copyOf((Iterable<? extends Origin>)a1) : null);
            return this;
        }
        
        public Builder setResponseHeaders(final Iterable<String> a1) {
            this.responseHeaders = ((a1 != null) ? ImmutableList.copyOf((Iterable<? extends String>)a1) : null);
            return this;
        }
        
        public Cors build() {
            return new Cors(this, null);
        }
        
        static /* synthetic */ Integer access$100(final Builder a1) {
            return a1.maxAgeSeconds;
        }
        
        static /* synthetic */ ImmutableList access$200(final Builder a1) {
            return a1.methods;
        }
        
        static /* synthetic */ ImmutableList access$300(final Builder a1) {
            return a1.origins;
        }
        
        static /* synthetic */ ImmutableList access$400(final Builder a1) {
            return a1.responseHeaders;
        }
        
        Builder(final Cors$1 a1) {
            this();
        }
    }
}
