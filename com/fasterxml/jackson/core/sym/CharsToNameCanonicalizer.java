package com.fasterxml.jackson.core.sym;

import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.util.*;

public final class CharsToNameCanonicalizer
{
    public static final int HASH_MULT = 33;
    private static final int DEFAULT_T_SIZE = 64;
    private static final int MAX_T_SIZE = 65536;
    static final int MAX_ENTRIES_FOR_REUSE = 12000;
    static final int MAX_COLL_CHAIN_LENGTH = 100;
    private final CharsToNameCanonicalizer _parent;
    private final AtomicReference<TableInfo> _tableInfo;
    private final int _seed;
    private final int _flags;
    private boolean _canonicalize;
    private String[] _symbols;
    private Bucket[] _buckets;
    private int _size;
    private int _sizeThreshold;
    private int _indexMask;
    private int _longestCollisionList;
    private boolean _hashShared;
    private BitSet _overflows;
    
    private CharsToNameCanonicalizer(final int a1) {
        super();
        this._parent = null;
        this._seed = a1;
        this._canonicalize = true;
        this._flags = -1;
        this._hashShared = false;
        this._longestCollisionList = 0;
        this._tableInfo = new AtomicReference<TableInfo>(TableInfo.createInitial(64));
    }
    
    private CharsToNameCanonicalizer(final CharsToNameCanonicalizer a1, final int a2, final int a3, final TableInfo a4) {
        super();
        this._parent = a1;
        this._seed = a3;
        this._tableInfo = null;
        this._flags = a2;
        this._canonicalize = JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(a2);
        this._symbols = a4.symbols;
        this._buckets = a4.buckets;
        this._size = a4.size;
        this._longestCollisionList = a4.longestCollisionList;
        final int v1 = this._symbols.length;
        this._sizeThreshold = _thresholdSize(v1);
        this._indexMask = v1 - 1;
        this._hashShared = true;
    }
    
    private static int _thresholdSize(final int a1) {
        return a1 - (a1 >> 2);
    }
    
    public static CharsToNameCanonicalizer createRoot() {
        final long v1 = System.currentTimeMillis();
        final int v2 = (int)v1 + (int)(v1 >>> 32) | 0x1;
        return createRoot(v2);
    }
    
    protected static CharsToNameCanonicalizer createRoot(final int a1) {
        return new CharsToNameCanonicalizer(a1);
    }
    
    public CharsToNameCanonicalizer makeChild(final int a1) {
        return new CharsToNameCanonicalizer(this, a1, this._seed, this._tableInfo.get());
    }
    
    public void release() {
        if (!this.maybeDirty()) {
            return;
        }
        if (this._parent != null && this._canonicalize) {
            this._parent.mergeChild(new TableInfo(this));
            this._hashShared = true;
        }
    }
    
    private void mergeChild(TableInfo a1) {
        final int v1 = a1.size;
        final TableInfo v2 = this._tableInfo.get();
        if (v1 == v2.size) {
            return;
        }
        if (v1 > 12000) {
            a1 = TableInfo.createInitial(64);
        }
        this._tableInfo.compareAndSet(v2, a1);
    }
    
    public int size() {
        if (this._tableInfo != null) {
            return this._tableInfo.get().size;
        }
        return this._size;
    }
    
    public int bucketCount() {
        return this._symbols.length;
    }
    
    public boolean maybeDirty() {
        return !this._hashShared;
    }
    
    public int hashSeed() {
        return this._seed;
    }
    
    public int collisionCount() {
        int n = 0;
        for (final Bucket v2 : this._buckets) {
            if (v2 != null) {
                n += v2.length;
            }
        }
        return n;
    }
    
    public int maxCollisionLength() {
        return this._longestCollisionList;
    }
    
    public String findSymbol(final char[] a4, final int v1, final int v2, final int v3) {
        if (v2 < 1) {
            return "";
        }
        if (!this._canonicalize) {
            return new String(a4, v1, v2);
        }
        final int v4 = this._hashToIndex(v3);
        String v5 = this._symbols[v4];
        if (v5 != null) {
            if (v5.length() == v2) {
                int a5 = 0;
                while (v5.charAt(a5) == a4[v1 + a5]) {
                    if (++a5 == v2) {
                        return v5;
                    }
                }
            }
            final Bucket a6 = this._buckets[v4 >> 1];
            if (a6 != null) {
                v5 = a6.has(a4, v1, v2);
                if (v5 != null) {
                    return v5;
                }
                v5 = this._findSymbol2(a4, v1, v2, a6.next);
                if (v5 != null) {
                    return v5;
                }
            }
        }
        return this._addSymbol(a4, v1, v2, v3, v4);
    }
    
    private String _findSymbol2(final char[] a3, final int a4, final int v1, Bucket v2) {
        while (v2 != null) {
            final String a5 = v2.has(a3, a4, v1);
            if (a5 != null) {
                return a5;
            }
            v2 = v2.next;
        }
        return null;
    }
    
    private String _addSymbol(final char[] a5, final int v1, final int v2, final int v3, int v4) {
        if (this._hashShared) {
            this.copyArrays();
            this._hashShared = false;
        }
        else if (this._size >= this._sizeThreshold) {
            this.rehash();
            v4 = this._hashToIndex(this.calcHash(a5, v1, v2));
        }
        String v5 = new String(a5, v1, v2);
        if (JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(this._flags)) {
            v5 = InternCache.instance.intern(v5);
        }
        ++this._size;
        if (this._symbols[v4] == null) {
            this._symbols[v4] = v5;
        }
        else {
            final int a6 = v4 >> 1;
            final Bucket a7 = new Bucket(v5, this._buckets[a6]);
            final int a8 = a7.length;
            if (a8 > 100) {
                this._handleSpillOverflow(a6, a7);
            }
            else {
                this._buckets[a6] = a7;
                this._longestCollisionList = Math.max(a8, this._longestCollisionList);
            }
        }
        return v5;
    }
    
    private void _handleSpillOverflow(final int a1, final Bucket a2) {
        if (this._overflows == null) {
            (this._overflows = new BitSet()).set(a1);
        }
        else if (this._overflows.get(a1)) {
            if (JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(this._flags)) {
                this.reportTooManyCollisions(100);
            }
            this._canonicalize = false;
        }
        else {
            this._overflows.set(a1);
        }
        this._symbols[a1 + a1] = a2.symbol;
        this._buckets[a1] = null;
        this._size -= a2.length;
        this._longestCollisionList = -1;
    }
    
    public int _hashToIndex(int a1) {
        a1 += a1 >>> 15;
        a1 ^= a1 << 7;
        a1 += a1 >>> 3;
        return a1 & this._indexMask;
    }
    
    public int calcHash(final char[] v1, final int v2, final int v3) {
        int v4 = this._seed;
        for (int a1 = v2, a2 = v2 + v3; a1 < a2; ++a1) {
            v4 = v4 * 33 + v1[a1];
        }
        return (v4 == 0) ? 1 : v4;
    }
    
    public int calcHash(final String v2) {
        final int v3 = v2.length();
        int v4 = this._seed;
        for (int a1 = 0; a1 < v3; ++a1) {
            v4 = v4 * 33 + v2.charAt(a1);
        }
        return (v4 == 0) ? 1 : v4;
    }
    
    private void copyArrays() {
        final String[] v1 = this._symbols;
        this._symbols = Arrays.copyOf(v1, v1.length);
        final Bucket[] v2 = this._buckets;
        this._buckets = Arrays.copyOf(v2, v2.length);
    }
    
    private void rehash() {
        int length = this._symbols.length;
        final int a1 = length + length;
        if (a1 > 65536) {
            this._size = 0;
            this._canonicalize = false;
            this._symbols = new String[64];
            this._buckets = new Bucket[32];
            this._indexMask = 63;
            this._hashShared = false;
            return;
        }
        final String[] symbols = this._symbols;
        final Bucket[] buckets = this._buckets;
        this._symbols = new String[a1];
        this._buckets = new Bucket[a1 >> 1];
        this._indexMask = a1 - 1;
        this._sizeThreshold = _thresholdSize(a1);
        int n = 0;
        int longestCollisionList = 0;
        for (final String s : symbols) {
            if (s != null) {
                ++n;
                final int v0 = this._hashToIndex(this.calcHash(s));
                if (this._symbols[v0] == null) {
                    this._symbols[v0] = s;
                }
                else {
                    final int v2 = v0 >> 1;
                    final Bucket v3 = new Bucket(s, this._buckets[v2]);
                    this._buckets[v2] = v3;
                    longestCollisionList = Math.max(longestCollisionList, v3.length);
                }
            }
        }
        length >>= 1;
        for (Bucket next : buckets) {
            while (next != null) {
                ++n;
                final String v4 = next.symbol;
                final int v2 = this._hashToIndex(this.calcHash(v4));
                if (this._symbols[v2] == null) {
                    this._symbols[v2] = v4;
                }
                else {
                    final int v5 = v2 >> 1;
                    final Bucket v6 = new Bucket(v4, this._buckets[v5]);
                    this._buckets[v5] = v6;
                    longestCollisionList = Math.max(longestCollisionList, v6.length);
                }
                next = next.next;
            }
        }
        this._longestCollisionList = longestCollisionList;
        this._overflows = null;
        if (n != this._size) {
            throw new IllegalStateException(String.format("Internal error on SymbolTable.rehash(): had %d entries; now have %d", this._size, n));
        }
    }
    
    protected void reportTooManyCollisions(final int a1) {
        throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + a1 + " -- suspect a DoS attack based on hash collisions");
    }
    
    static /* synthetic */ int access$000(final CharsToNameCanonicalizer a1) {
        return a1._size;
    }
    
    static /* synthetic */ int access$100(final CharsToNameCanonicalizer a1) {
        return a1._longestCollisionList;
    }
    
    static /* synthetic */ String[] access$200(final CharsToNameCanonicalizer a1) {
        return a1._symbols;
    }
    
    static /* synthetic */ Bucket[] access$300(final CharsToNameCanonicalizer a1) {
        return a1._buckets;
    }
    
    static final class Bucket
    {
        public final String symbol;
        public final Bucket next;
        public final int length;
        
        public Bucket(final String a1, final Bucket a2) {
            super();
            this.symbol = a1;
            this.next = a2;
            this.length = ((a2 == null) ? 1 : (a2.length + 1));
        }
        
        public String has(final char[] a1, final int a2, final int a3) {
            if (this.symbol.length() != a3) {
                return null;
            }
            int v1 = 0;
            while (this.symbol.charAt(v1) == a1[a2 + v1]) {
                if (++v1 >= a3) {
                    return this.symbol;
                }
            }
            return null;
        }
    }
    
    private static final class TableInfo
    {
        final int size;
        final int longestCollisionList;
        final String[] symbols;
        final Bucket[] buckets;
        
        public TableInfo(final int a1, final int a2, final String[] a3, final Bucket[] a4) {
            super();
            this.size = a1;
            this.longestCollisionList = a2;
            this.symbols = a3;
            this.buckets = a4;
        }
        
        public TableInfo(final CharsToNameCanonicalizer a1) {
            super();
            this.size = a1._size;
            this.longestCollisionList = a1._longestCollisionList;
            this.symbols = a1._symbols;
            this.buckets = a1._buckets;
        }
        
        public static TableInfo createInitial(final int a1) {
            return new TableInfo(0, 0, new String[a1], new Bucket[a1 >> 1]);
        }
    }
}
