package com.google.api.client.repackaged.com.google.common.base;

import java.util.regex.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class Splitter
{
    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final int limit;
    
    private Splitter(final Strategy a1) {
        this(a1, false, CharMatcher.none(), 0);
    }
    
    private Splitter(final Strategy a1, final boolean a2, final CharMatcher a3, final int a4) {
        super();
        this.strategy = a1;
        this.omitEmptyStrings = a2;
        this.trimmer = a3;
        this.limit = a4;
    }
    
    public static Splitter on(final char a1) {
        return on(CharMatcher.is(a1));
    }
    
    public static Splitter on(final CharMatcher a1) {
        Preconditions.checkNotNull(a1);
        return new Splitter(new Strategy() {
            final /* synthetic */ CharMatcher val$separatorMatcher;
            
            Splitter$1() {
                super();
            }
            
            @Override
            public SplittingIterator iterator(final Splitter a1, final CharSequence a2) {
                return new SplittingIterator(a1, a2) {
                    final /* synthetic */ Splitter$1 this$0;
                    
                    Splitter$1$1(final Splitter a1, final CharSequence a2) {
                        this.this$0 = this$0;
                        super(a1, a2);
                    }
                    
                    @Override
                    int separatorStart(final int a1) {
                        return a1.indexIn(this.toSplit, a1);
                    }
                    
                    @Override
                    int separatorEnd(final int a1) {
                        return a1 + 1;
                    }
                };
            }
            
            @Override
            public /* bridge */ Iterator iterator(final Splitter a1, final CharSequence a2) {
                return this.iterator(a1, a2);
            }
        });
    }
    
    public static Splitter on(final String a1) {
        Preconditions.checkArgument(a1.length() != 0, (Object)"The separator may not be the empty string.");
        if (a1.length() == 1) {
            return on(a1.charAt(0));
        }
        return new Splitter(new Strategy() {
            final /* synthetic */ String val$separator;
            
            Splitter$2() {
                super();
            }
            
            @Override
            public SplittingIterator iterator(final Splitter a1, final CharSequence a2) {
                return new SplittingIterator(a1, a2) {
                    final /* synthetic */ Splitter$2 this$0;
                    
                    Splitter$2$1(final Splitter a1, final CharSequence a2) {
                        this.this$0 = this$0;
                        super(a1, a2);
                    }
                    
                    public int separatorStart(final int v-1) {
                        final int v0 = a1.length();
                        int v2 = v-1;
                        final int v3 = this.toSplit.length() - v0;
                    Label_0026:
                        while (v2 <= v3) {
                            for (int a1 = 0; a1 < v0; ++a1) {
                                if (this.toSplit.charAt(a1 + v2) != a1.charAt(a1)) {
                                    ++v2;
                                    continue Label_0026;
                                }
                            }
                            return v2;
                        }
                        return -1;
                    }
                    
                    public int separatorEnd(final int a1) {
                        return a1 + a1.length();
                    }
                };
            }
            
            @Override
            public /* bridge */ Iterator iterator(final Splitter a1, final CharSequence a2) {
                return this.iterator(a1, a2);
            }
        });
    }
    
    @GwtIncompatible
    public static Splitter on(final Pattern a1) {
        return on(new JdkPattern(a1));
    }
    
    private static Splitter on(final CommonPattern a1) {
        Preconditions.checkArgument(!a1.matcher("").matches(), "The pattern may not match the empty string: %s", a1);
        return new Splitter(new Strategy() {
            final /* synthetic */ CommonPattern val$separatorPattern;
            
            Splitter$3() {
                super();
            }
            
            @Override
            public SplittingIterator iterator(final Splitter a1, final CharSequence a2) {
                final CommonMatcher v1 = a1.matcher(a2);
                return new SplittingIterator(a1, a2) {
                    final /* synthetic */ CommonMatcher val$matcher;
                    final /* synthetic */ Splitter$3 this$0;
                    
                    Splitter$3$1(final Splitter a1, final CharSequence a2) {
                        this.this$0 = this$0;
                        super(a1, a2);
                    }
                    
                    public int separatorStart(final int a1) {
                        return v1.find(a1) ? v1.start() : -1;
                    }
                    
                    public int separatorEnd(final int a1) {
                        return v1.end();
                    }
                };
            }
            
            @Override
            public /* bridge */ Iterator iterator(final Splitter a1, final CharSequence a2) {
                return this.iterator(a1, a2);
            }
        });
    }
    
    @GwtIncompatible
    public static Splitter onPattern(final String a1) {
        return on(Platform.compilePattern(a1));
    }
    
    public static Splitter fixedLength(final int a1) {
        Preconditions.checkArgument(a1 > 0, (Object)"The length may not be less than 1");
        return new Splitter(new Strategy() {
            final /* synthetic */ int val$length;
            
            Splitter$4() {
                super();
            }
            
            @Override
            public SplittingIterator iterator(final Splitter a1, final CharSequence a2) {
                return new SplittingIterator(a1, a2) {
                    final /* synthetic */ Splitter$4 this$0;
                    
                    Splitter$4$1(final Splitter a1, final CharSequence a2) {
                        this.this$0 = this$0;
                        super(a1, a2);
                    }
                    
                    public int separatorStart(final int a1) {
                        final int v1 = a1 + a1;
                        return (v1 < this.toSplit.length()) ? v1 : -1;
                    }
                    
                    public int separatorEnd(final int a1) {
                        return a1;
                    }
                };
            }
            
            @Override
            public /* bridge */ Iterator iterator(final Splitter a1, final CharSequence a2) {
                return this.iterator(a1, a2);
            }
        });
    }
    
    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }
    
    public Splitter limit(final int a1) {
        Preconditions.checkArgument(a1 > 0, "must be greater than zero: %s", a1);
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, a1);
    }
    
    public Splitter trimResults() {
        return this.trimResults(CharMatcher.whitespace());
    }
    
    public Splitter trimResults(final CharMatcher a1) {
        Preconditions.checkNotNull(a1);
        return new Splitter(this.strategy, this.omitEmptyStrings, a1, this.limit);
    }
    
    public Iterable<String> split(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        return new Iterable<String>() {
            final /* synthetic */ CharSequence val$sequence;
            final /* synthetic */ Splitter this$0;
            
            Splitter$5() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public Iterator<String> iterator() {
                return Splitter.this.splittingIterator(a1);
            }
            
            @Override
            public String toString() {
                return Joiner.on(", ").appendTo(new StringBuilder().append('['), (Iterable<?>)this).append(']').toString();
            }
        };
    }
    
    private Iterator<String> splittingIterator(final CharSequence a1) {
        return this.strategy.iterator(this, a1);
    }
    
    @Beta
    public List<String> splitToList(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        final Iterator<String> v1 = this.splittingIterator(a1);
        final List<String> v2 = new ArrayList<String>();
        while (v1.hasNext()) {
            v2.add(v1.next());
        }
        return Collections.unmodifiableList((List<? extends String>)v2);
    }
    
    @Beta
    public MapSplitter withKeyValueSeparator(final String a1) {
        return this.withKeyValueSeparator(on(a1));
    }
    
    @Beta
    public MapSplitter withKeyValueSeparator(final char a1) {
        return this.withKeyValueSeparator(on(a1));
    }
    
    @Beta
    public MapSplitter withKeyValueSeparator(final Splitter a1) {
        return new MapSplitter(this, a1);
    }
    
    static /* bridge */ Iterator access$000(final Splitter a1, final CharSequence a2) {
        return a1.splittingIterator(a2);
    }
    
    static /* synthetic */ CharMatcher access$200(final Splitter a1) {
        return a1.trimmer;
    }
    
    static /* synthetic */ boolean access$300(final Splitter a1) {
        return a1.omitEmptyStrings;
    }
    
    static /* synthetic */ int access$400(final Splitter a1) {
        return a1.limit;
    }
    
    @Beta
    public static final class MapSplitter
    {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter outerSplitter;
        private final Splitter entrySplitter;
        
        private MapSplitter(final Splitter a1, final Splitter a2) {
            super();
            this.outerSplitter = a1;
            this.entrySplitter = Preconditions.checkNotNull(a2);
        }
        
        public Map<String, String> split(final CharSequence v-4) {
            final Map<String, String> map = new LinkedHashMap<String, String>();
            for (final String s : this.outerSplitter.split(v-4)) {
                final Iterator<String> a1 = this.entrySplitter.splittingIterator(s);
                Preconditions.checkArgument(a1.hasNext(), "Chunk [%s] is not a valid entry", s);
                final String v1 = a1.next();
                Preconditions.checkArgument(!map.containsKey(v1), "Duplicate key [%s] found.", v1);
                Preconditions.checkArgument(a1.hasNext(), "Chunk [%s] is not a valid entry", s);
                final String v2 = a1.next();
                map.put(v1, v2);
                Preconditions.checkArgument(!a1.hasNext(), "Chunk [%s] is not a valid entry", s);
            }
            return Collections.unmodifiableMap((Map<? extends String, ? extends String>)map);
        }
        
        MapSplitter(final Splitter a1, final Splitter a2, final Splitter$1 a3) {
            this(a1, a2);
        }
    }
    
    private abstract static class SplittingIterator extends AbstractIterator<String>
    {
        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyStrings;
        int offset;
        int limit;
        
        abstract int separatorStart(final int p0);
        
        abstract int separatorEnd(final int p0);
        
        protected SplittingIterator(final Splitter a1, final CharSequence a2) {
            super();
            this.offset = 0;
            this.trimmer = a1.trimmer;
            this.omitEmptyStrings = a1.omitEmptyStrings;
            this.limit = a1.limit;
            this.toSplit = a2;
        }
        
        @Override
        protected String computeNext() {
            int n = this.offset;
            while (this.offset != -1) {
                int n2 = n;
                final int v0 = this.separatorStart(this.offset);
                int v2;
                if (v0 == -1) {
                    v2 = this.toSplit.length();
                    this.offset = -1;
                }
                else {
                    v2 = v0;
                    this.offset = this.separatorEnd(v0);
                }
                if (this.offset == n) {
                    ++this.offset;
                    if (this.offset < this.toSplit.length()) {
                        continue;
                    }
                    this.offset = -1;
                }
                else {
                    while (n2 < v2 && this.trimmer.matches(this.toSplit.charAt(n2))) {
                        ++n2;
                    }
                    while (v2 > n2 && this.trimmer.matches(this.toSplit.charAt(v2 - 1))) {
                        --v2;
                    }
                    if (!this.omitEmptyStrings || n2 != v2) {
                        if (this.limit == 1) {
                            v2 = this.toSplit.length();
                            this.offset = -1;
                            while (v2 > n2 && this.trimmer.matches(this.toSplit.charAt(v2 - 1))) {
                                --v2;
                            }
                        }
                        else {
                            --this.limit;
                        }
                        return this.toSplit.subSequence(n2, v2).toString();
                    }
                    n = this.offset;
                }
            }
            return this.endOfData();
        }
        
        @Override
        protected /* bridge */ Object computeNext() {
            return this.computeNext();
        }
    }
    
    private interface Strategy
    {
        Iterator<String> iterator(final Splitter p0, final CharSequence p1);
    }
}
