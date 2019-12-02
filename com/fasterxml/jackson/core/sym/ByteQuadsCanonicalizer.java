package com.fasterxml.jackson.core.sym;

import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.util.*;

public final class ByteQuadsCanonicalizer
{
    private static final int DEFAULT_T_SIZE = 64;
    private static final int MAX_T_SIZE = 65536;
    private static final int MIN_HASH_SIZE = 16;
    static final int MAX_ENTRIES_FOR_REUSE = 6000;
    private final ByteQuadsCanonicalizer _parent;
    private final AtomicReference<TableInfo> _tableInfo;
    private final int _seed;
    private boolean _intern;
    private final boolean _failOnDoS;
    private int[] _hashArea;
    private int _hashSize;
    private int _secondaryStart;
    private int _tertiaryStart;
    private int _tertiaryShift;
    private int _count;
    private String[] _names;
    private int _spilloverEnd;
    private int _longNameOffset;
    private transient boolean _needRehash;
    private boolean _hashShared;
    private static final int MULT = 33;
    private static final int MULT2 = 65599;
    private static final int MULT3 = 31;
    
    private ByteQuadsCanonicalizer(int a3, final boolean a4, final int v1, final boolean v2) {
        super();
        this._parent = null;
        this._seed = v1;
        this._intern = a4;
        this._failOnDoS = v2;
        if (a3 < 16) {
            a3 = 16;
        }
        else if ((a3 & a3 - 1) != 0x0) {
            int a5;
            for (a5 = 16; a5 < a3; a5 += a5) {}
            a3 = a5;
        }
        this._tableInfo = new AtomicReference<TableInfo>(TableInfo.createInitial(a3));
    }
    
    private ByteQuadsCanonicalizer(final ByteQuadsCanonicalizer a1, final boolean a2, final int a3, final boolean a4, final TableInfo a5) {
        super();
        this._parent = a1;
        this._seed = a3;
        this._intern = a2;
        this._failOnDoS = a4;
        this._tableInfo = null;
        this._count = a5.count;
        this._hashSize = a5.size;
        this._secondaryStart = this._hashSize << 2;
        this._tertiaryStart = this._secondaryStart + (this._secondaryStart >> 1);
        this._tertiaryShift = a5.tertiaryShift;
        this._hashArea = a5.mainHash;
        this._names = a5.names;
        this._spilloverEnd = a5.spilloverEnd;
        this._longNameOffset = a5.longNameOffset;
        this._needRehash = false;
        this._hashShared = true;
    }
    
    public static ByteQuadsCanonicalizer createRoot() {
        final long v1 = System.currentTimeMillis();
        final int v2 = (int)v1 + (int)(v1 >>> 32) | 0x1;
        return createRoot(v2);
    }
    
    protected static ByteQuadsCanonicalizer createRoot(final int a1) {
        return new ByteQuadsCanonicalizer(64, true, a1, true);
    }
    
    public ByteQuadsCanonicalizer makeChild(final int a1) {
        return new ByteQuadsCanonicalizer(this, JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(a1), this._seed, JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(a1), this._tableInfo.get());
    }
    
    public void release() {
        if (this._parent != null && this.maybeDirty()) {
            this._parent.mergeChild(new TableInfo(this));
            this._hashShared = true;
        }
    }
    
    private void mergeChild(TableInfo a1) {
        final int v1 = a1.count;
        final TableInfo v2 = this._tableInfo.get();
        if (v1 == v2.count) {
            return;
        }
        if (v1 > 6000) {
            a1 = TableInfo.createInitial(64);
        }
        this._tableInfo.compareAndSet(v2, a1);
    }
    
    public int size() {
        if (this._tableInfo != null) {
            return this._tableInfo.get().count;
        }
        return this._count;
    }
    
    public int bucketCount() {
        return this._hashSize;
    }
    
    public boolean maybeDirty() {
        return !this._hashShared;
    }
    
    public int hashSeed() {
        return this._seed;
    }
    
    public int primaryCount() {
        int v0 = 0;
        for (int v2 = 3, v3 = this._secondaryStart; v2 < v3; v2 += 4) {
            if (this._hashArea[v2] != 0) {
                ++v0;
            }
        }
        return v0;
    }
    
    public int secondaryCount() {
        int n = 0;
        for (int v0 = this._secondaryStart + 3, v2 = this._tertiaryStart; v0 < v2; v0 += 4) {
            if (this._hashArea[v0] != 0) {
                ++n;
            }
        }
        return n;
    }
    
    public int tertiaryCount() {
        int n = 0;
        for (int v0 = this._tertiaryStart + 3, v2 = v0 + this._hashSize; v0 < v2; v0 += 4) {
            if (this._hashArea[v0] != 0) {
                ++n;
            }
        }
        return n;
    }
    
    public int spilloverCount() {
        return this._spilloverEnd - this._spilloverStart() >> 2;
    }
    
    public int totalCount() {
        int v0 = 0;
        for (int v2 = 3, v3 = this._hashSize << 3; v2 < v3; v2 += 4) {
            if (this._hashArea[v2] != 0) {
                ++v0;
            }
        }
        return v0;
    }
    
    @Override
    public String toString() {
        final int v1 = this.primaryCount();
        final int v2 = this.secondaryCount();
        final int v3 = this.tertiaryCount();
        final int v4 = this.spilloverCount();
        final int v5 = this.totalCount();
        return String.format("[%s: size=%d, hashSize=%d, %d/%d/%d/%d pri/sec/ter/spill (=%s), total:%d]", this.getClass().getName(), this._count, this._hashSize, v1, v2, v3, v4, v1 + v2 + v3 + v4, v5);
    }
    
    public String findName(final int a1) {
        final int v1 = this._calcOffset(this.calcHash(a1));
        final int[] v2 = this._hashArea;
        int v3 = v2[v1 + 3];
        if (v3 == 1) {
            if (v2[v1] == a1) {
                return this._names[v1 >> 2];
            }
        }
        else if (v3 == 0) {
            return null;
        }
        final int v4 = this._secondaryStart + (v1 >> 3 << 2);
        v3 = v2[v4 + 3];
        if (v3 == 1) {
            if (v2[v4] == a1) {
                return this._names[v4 >> 2];
            }
        }
        else if (v3 == 0) {
            return null;
        }
        return this._findSecondary(v1, a1);
    }
    
    public String findName(final int a1, final int a2) {
        final int v1 = this._calcOffset(this.calcHash(a1, a2));
        final int[] v2 = this._hashArea;
        int v3 = v2[v1 + 3];
        if (v3 == 2) {
            if (a1 == v2[v1] && a2 == v2[v1 + 1]) {
                return this._names[v1 >> 2];
            }
        }
        else if (v3 == 0) {
            return null;
        }
        final int v4 = this._secondaryStart + (v1 >> 3 << 2);
        v3 = v2[v4 + 3];
        if (v3 == 2) {
            if (a1 == v2[v4] && a2 == v2[v4 + 1]) {
                return this._names[v4 >> 2];
            }
        }
        else if (v3 == 0) {
            return null;
        }
        return this._findSecondary(v1, a1, a2);
    }
    
    public String findName(final int a1, final int a2, final int a3) {
        final int v1 = this._calcOffset(this.calcHash(a1, a2, a3));
        final int[] v2 = this._hashArea;
        int v3 = v2[v1 + 3];
        if (v3 == 3) {
            if (a1 == v2[v1] && v2[v1 + 1] == a2 && v2[v1 + 2] == a3) {
                return this._names[v1 >> 2];
            }
        }
        else if (v3 == 0) {
            return null;
        }
        final int v4 = this._secondaryStart + (v1 >> 3 << 2);
        v3 = v2[v4 + 3];
        if (v3 == 3) {
            if (a1 == v2[v4] && v2[v4 + 1] == a2 && v2[v4 + 2] == a3) {
                return this._names[v4 >> 2];
            }
        }
        else if (v3 == 0) {
            return null;
        }
        return this._findSecondary(v1, a1, a2, a3);
    }
    
    public String findName(final int[] a1, final int a2) {
        if (a2 < 4) {
            switch (a2) {
                case 3: {
                    return this.findName(a1[0], a1[1], a1[2]);
                }
                case 2: {
                    return this.findName(a1[0], a1[1]);
                }
                case 1: {
                    return this.findName(a1[0]);
                }
                default: {
                    return "";
                }
            }
        }
        else {
            final int v1 = this.calcHash(a1, a2);
            final int v2 = this._calcOffset(v1);
            final int[] v3 = this._hashArea;
            final int v4 = v3[v2 + 3];
            if (v1 == v3[v2] && v4 == a2 && this._verifyLongName(a1, a2, v3[v2 + 1])) {
                return this._names[v2 >> 2];
            }
            if (v4 == 0) {
                return null;
            }
            final int v5 = this._secondaryStart + (v2 >> 3 << 2);
            final int v6 = v3[v5 + 3];
            if (v1 == v3[v5] && v6 == a2 && this._verifyLongName(a1, a2, v3[v5 + 1])) {
                return this._names[v5 >> 2];
            }
            return this._findSecondary(v2, v1, a1, a2);
        }
    }
    
    private final int _calcOffset(final int a1) {
        final int v1 = a1 & this._hashSize - 1;
        return v1 << 2;
    }
    
    private String _findSecondary(final int v2, final int v3) {
        int v4 = this._tertiaryStart + (v2 >> this._tertiaryShift + 2 << this._tertiaryShift);
        final int[] v5 = this._hashArea;
        final int v6 = 1 << this._tertiaryShift;
        for (int a2 = v4 + v6; v4 < a2; v4 += 4) {
            final int a3 = v5[v4 + 3];
            if (v3 == v5[v4] && 1 == a3) {
                return this._names[v4 >> 2];
            }
            if (a3 == 0) {
                return null;
            }
        }
        for (v4 = this._spilloverStart(); v4 < this._spilloverEnd; v4 += 4) {
            if (v3 == v5[v4] && 1 == v5[v4 + 3]) {
                return this._names[v4 >> 2];
            }
        }
        return null;
    }
    
    private String _findSecondary(final int v1, final int v2, final int v3) {
        int v4 = this._tertiaryStart + (v1 >> this._tertiaryShift + 2 << this._tertiaryShift);
        final int[] v5 = this._hashArea;
        final int v6 = 1 << this._tertiaryShift;
        for (int a2 = v4 + v6; v4 < a2; v4 += 4) {
            final int a3 = v5[v4 + 3];
            if (v2 == v5[v4] && v3 == v5[v4 + 1] && 2 == a3) {
                return this._names[v4 >> 2];
            }
            if (a3 == 0) {
                return null;
            }
        }
        for (v4 = this._spilloverStart(); v4 < this._spilloverEnd; v4 += 4) {
            if (v2 == v5[v4] && v3 == v5[v4 + 1] && 2 == v5[v4 + 3]) {
                return this._names[v4 >> 2];
            }
        }
        return null;
    }
    
    private String _findSecondary(final int a4, final int v1, final int v2, final int v3) {
        int v4 = this._tertiaryStart + (a4 >> this._tertiaryShift + 2 << this._tertiaryShift);
        final int[] v5 = this._hashArea;
        final int v6 = 1 << this._tertiaryShift;
        for (int a5 = v4 + v6; v4 < a5; v4 += 4) {
            final int a6 = v5[v4 + 3];
            if (v1 == v5[v4] && v2 == v5[v4 + 1] && v3 == v5[v4 + 2] && 3 == a6) {
                return this._names[v4 >> 2];
            }
            if (a6 == 0) {
                return null;
            }
        }
        for (v4 = this._spilloverStart(); v4 < this._spilloverEnd; v4 += 4) {
            if (v1 == v5[v4] && v2 == v5[v4 + 1] && v3 == v5[v4 + 2] && 3 == v5[v4 + 3]) {
                return this._names[v4 >> 2];
            }
        }
        return null;
    }
    
    private String _findSecondary(final int a4, final int v1, final int[] v2, final int v3) {
        int v4 = this._tertiaryStart + (a4 >> this._tertiaryShift + 2 << this._tertiaryShift);
        final int[] v5 = this._hashArea;
        final int v6 = 1 << this._tertiaryShift;
        for (int a5 = v4 + v6; v4 < a5; v4 += 4) {
            final int a6 = v5[v4 + 3];
            if (v1 == v5[v4] && v3 == a6 && this._verifyLongName(v2, v3, v5[v4 + 1])) {
                return this._names[v4 >> 2];
            }
            if (a6 == 0) {
                return null;
            }
        }
        for (v4 = this._spilloverStart(); v4 < this._spilloverEnd; v4 += 4) {
            if (v1 == v5[v4] && v3 == v5[v4 + 3] && this._verifyLongName(v2, v3, v5[v4 + 1])) {
                return this._names[v4 >> 2];
            }
        }
        return null;
    }
    
    private boolean _verifyLongName(final int[] a1, final int a2, int a3) {
        final int[] v1 = this._hashArea;
        int v2 = 0;
        switch (a2) {
            default: {
                return this._verifyLongName2(a1, a2, a3);
            }
            case 8: {
                if (a1[v2++] != v1[a3++]) {
                    return false;
                }
            }
            case 7: {
                if (a1[v2++] != v1[a3++]) {
                    return false;
                }
            }
            case 6: {
                if (a1[v2++] != v1[a3++]) {
                    return false;
                }
            }
            case 5: {
                if (a1[v2++] != v1[a3++]) {
                    return false;
                }
                return a1[v2++] == v1[a3++] && a1[v2++] == v1[a3++] && a1[v2++] == v1[a3++] && a1[v2++] == v1[a3++];
            }
            case 4: {
                return a1[v2++] == v1[a3++] && a1[v2++] == v1[a3++] && a1[v2++] == v1[a3++] && a1[v2++] == v1[a3++];
            }
        }
    }
    
    private boolean _verifyLongName2(final int[] a1, final int a2, int a3) {
        int v1 = 0;
        while (a1[v1++] == this._hashArea[a3++]) {
            if (v1 >= a2) {
                return true;
            }
        }
        return false;
    }
    
    public String addName(String a1, final int a2) {
        this._verifySharing();
        if (this._intern) {
            a1 = InternCache.instance.intern(a1);
        }
        final int v1 = this._findOffsetForAdd(this.calcHash(a2));
        this._hashArea[v1] = a2;
        this._hashArea[v1 + 3] = 1;
        this._names[v1 >> 2] = a1;
        ++this._count;
        this._verifyNeedForRehash();
        return a1;
    }
    
    public String addName(String a1, final int a2, final int a3) {
        this._verifySharing();
        if (this._intern) {
            a1 = InternCache.instance.intern(a1);
        }
        final int v1 = (a3 == 0) ? this.calcHash(a2) : this.calcHash(a2, a3);
        final int v2 = this._findOffsetForAdd(v1);
        this._hashArea[v2] = a2;
        this._hashArea[v2 + 1] = a3;
        this._hashArea[v2 + 3] = 2;
        this._names[v2 >> 2] = a1;
        ++this._count;
        this._verifyNeedForRehash();
        return a1;
    }
    
    public String addName(String a1, final int a2, final int a3, final int a4) {
        this._verifySharing();
        if (this._intern) {
            a1 = InternCache.instance.intern(a1);
        }
        final int v1 = this._findOffsetForAdd(this.calcHash(a2, a3, a4));
        this._hashArea[v1] = a2;
        this._hashArea[v1 + 1] = a3;
        this._hashArea[v1 + 2] = a4;
        this._hashArea[v1 + 3] = 3;
        this._names[v1 >> 2] = a1;
        ++this._count;
        this._verifyNeedForRehash();
        return a1;
    }
    
    public String addName(String v1, final int[] v2, final int v3) {
        this._verifySharing();
        if (this._intern) {
            v1 = InternCache.instance.intern(v1);
        }
        int v4 = 0;
        switch (v3) {
            case 1: {
                v4 = this._findOffsetForAdd(this.calcHash(v2[0]));
                this._hashArea[v4] = v2[0];
                this._hashArea[v4 + 3] = 1;
                break;
            }
            case 2: {
                v4 = this._findOffsetForAdd(this.calcHash(v2[0], v2[1]));
                this._hashArea[v4] = v2[0];
                this._hashArea[v4 + 1] = v2[1];
                this._hashArea[v4 + 3] = 2;
                break;
            }
            case 3: {
                v4 = this._findOffsetForAdd(this.calcHash(v2[0], v2[1], v2[2]));
                this._hashArea[v4] = v2[0];
                this._hashArea[v4 + 1] = v2[1];
                this._hashArea[v4 + 2] = v2[2];
                this._hashArea[v4 + 3] = 3;
                break;
            }
            default: {
                final int a1 = this.calcHash(v2, v3);
                v4 = this._findOffsetForAdd(a1);
                this._hashArea[v4] = a1;
                final int a2 = this._appendLongName(v2, v3);
                this._hashArea[v4 + 1] = a2;
                this._hashArea[v4 + 3] = v3;
                break;
            }
        }
        this._names[v4 >> 2] = v1;
        ++this._count;
        this._verifyNeedForRehash();
        return v1;
    }
    
    private void _verifyNeedForRehash() {
        if (this._count > this._hashSize >> 1) {
            final int v1 = this._spilloverEnd - this._spilloverStart() >> 2;
            if (v1 > 1 + this._count >> 7 || this._count > this._hashSize * 0.8) {
                this._needRehash = true;
            }
        }
    }
    
    private void _verifySharing() {
        if (this._hashShared) {
            this._hashArea = Arrays.copyOf(this._hashArea, this._hashArea.length);
            this._names = Arrays.copyOf(this._names, this._names.length);
            this._hashShared = false;
            this._verifyNeedForRehash();
        }
        if (this._needRehash) {
            this.rehash();
        }
    }
    
    private int _findOffsetForAdd(final int v2) {
        int v3 = this._calcOffset(v2);
        final int[] v4 = this._hashArea;
        if (v4[v3 + 3] == 0) {
            return v3;
        }
        int v5 = this._secondaryStart + (v3 >> 3 << 2);
        if (v4[v5 + 3] == 0) {
            return v5;
        }
        v5 = this._tertiaryStart + (v3 >> this._tertiaryShift + 2 << this._tertiaryShift);
        final int v6 = 1 << this._tertiaryShift;
        for (int a1 = v5 + v6; v5 < a1; v5 += 4) {
            if (v4[v5 + 3] == 0) {
                return v5;
            }
        }
        v3 = this._spilloverEnd;
        this._spilloverEnd += 4;
        final int v7 = this._hashSize << 3;
        if (this._spilloverEnd >= v7) {
            if (this._failOnDoS) {
                this._reportTooManyCollisions();
            }
            this._needRehash = true;
        }
        return v3;
    }
    
    private int _appendLongName(final int[] v-4, final int v-3) {
        final int longNameOffset = this._longNameOffset;
        if (longNameOffset + v-3 > this._hashArea.length) {
            final int a1 = longNameOffset + v-3 - this._hashArea.length;
            final int a2 = Math.min(4096, this._hashSize);
            final int v1 = this._hashArea.length + Math.max(a1, a2);
            this._hashArea = Arrays.copyOf(this._hashArea, v1);
        }
        System.arraycopy(v-4, 0, this._hashArea, longNameOffset, v-3);
        this._longNameOffset += v-3;
        return longNameOffset;
    }
    
    public int calcHash(final int a1) {
        int v1 = a1 ^ this._seed;
        v1 += v1 >>> 16;
        v1 ^= v1 << 3;
        v1 += v1 >>> 12;
        return v1;
    }
    
    public int calcHash(final int a1, final int a2) {
        int v1 = a1;
        v1 += v1 >>> 15;
        v1 ^= v1 >>> 9;
        v1 += a2 * 33;
        v1 ^= this._seed;
        v1 += v1 >>> 16;
        v1 ^= v1 >>> 4;
        v1 += v1 << 3;
        return v1;
    }
    
    public int calcHash(final int a1, final int a2, final int a3) {
        int v1 = a1 ^ this._seed;
        v1 += v1 >>> 9;
        v1 *= 31;
        v1 += a2;
        v1 *= 33;
        v1 += v1 >>> 15;
        v1 ^= a3;
        v1 += v1 >>> 4;
        v1 += v1 >>> 15;
        v1 ^= v1 << 9;
        return v1;
    }
    
    public int calcHash(final int[] v2, final int v3) {
        if (v3 < 4) {
            throw new IllegalArgumentException();
        }
        int v4 = v2[0] ^ this._seed;
        v4 += v4 >>> 9;
        v4 += v2[1];
        v4 += v4 >>> 15;
        v4 *= 33;
        v4 ^= v2[2];
        v4 += v4 >>> 4;
        for (int a2 = 3; a2 < v3; ++a2) {
            int a3 = v2[a2];
            a3 ^= a3 >> 21;
            v4 += a3;
        }
        v4 *= 65599;
        v4 += v4 >>> 19;
        v4 ^= v4 << 5;
        return v4;
    }
    
    private void rehash() {
        this._needRehash = false;
        this._hashShared = false;
        final int[] hashArea = this._hashArea;
        final String[] names = this._names;
        final int hashSize = this._hashSize;
        final int count = this._count;
        final int n = hashSize + hashSize;
        final int spilloverEnd = this._spilloverEnd;
        if (n > 65536) {
            this.nukeSymbols(true);
            return;
        }
        this._hashArea = new int[hashArea.length + (hashSize << 3)];
        this._hashSize = n;
        this._secondaryStart = n << 2;
        this._tertiaryStart = this._secondaryStart + (this._secondaryStart >> 1);
        this._tertiaryShift = _calcTertiaryShift(n);
        this._names = new String[names.length << 1];
        this.nukeSymbols(false);
        int n2 = 0;
        int[] array = new int[16];
        for (int i = 0, n3 = spilloverEnd; i < n3; i += 4) {
            final int v3 = hashArea[i + 3];
            if (v3 != 0) {
                ++n2;
                final String v0 = names[i >> 2];
                switch (v3) {
                    case 1: {
                        array[0] = hashArea[i];
                        this.addName(v0, array, 1);
                        break;
                    }
                    case 2: {
                        array[0] = hashArea[i];
                        array[1] = hashArea[i + 1];
                        this.addName(v0, array, 2);
                        break;
                    }
                    case 3: {
                        array[0] = hashArea[i];
                        array[1] = hashArea[i + 1];
                        array[2] = hashArea[i + 2];
                        this.addName(v0, array, 3);
                        break;
                    }
                    default: {
                        if (v3 > array.length) {
                            array = new int[v3];
                        }
                        final int v2 = hashArea[i + 1];
                        System.arraycopy(hashArea, v2, array, 0, v3);
                        this.addName(v0, array, v3);
                        break;
                    }
                }
            }
        }
        if (n2 != count) {
            throw new IllegalStateException("Failed rehash(): old count=" + count + ", copyCount=" + n2);
        }
    }
    
    private void nukeSymbols(final boolean a1) {
        this._count = 0;
        this._spilloverEnd = this._spilloverStart();
        this._longNameOffset = this._hashSize << 3;
        if (a1) {
            Arrays.fill(this._hashArea, 0);
            Arrays.fill(this._names, null);
        }
    }
    
    private final int _spilloverStart() {
        final int v1 = this._hashSize;
        return (v1 << 3) - v1;
    }
    
    protected void _reportTooManyCollisions() {
        if (this._hashSize <= 1024) {
            return;
        }
        throw new IllegalStateException("Spill-over slots in symbol table with " + this._count + " entries, hash area of " + this._hashSize + " slots is now full (all " + (this._hashSize >> 3) + " slots -- suspect a DoS attack based on hash collisions." + " You can disable the check via `JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW`");
    }
    
    static int _calcTertiaryShift(final int a1) {
        final int v1 = a1 >> 2;
        if (v1 < 64) {
            return 4;
        }
        if (v1 <= 256) {
            return 5;
        }
        if (v1 <= 1024) {
            return 6;
        }
        return 7;
    }
    
    static /* synthetic */ int access$000(final ByteQuadsCanonicalizer a1) {
        return a1._hashSize;
    }
    
    static /* synthetic */ int access$100(final ByteQuadsCanonicalizer a1) {
        return a1._count;
    }
    
    static /* synthetic */ int access$200(final ByteQuadsCanonicalizer a1) {
        return a1._tertiaryShift;
    }
    
    static /* synthetic */ int[] access$300(final ByteQuadsCanonicalizer a1) {
        return a1._hashArea;
    }
    
    static /* synthetic */ String[] access$400(final ByteQuadsCanonicalizer a1) {
        return a1._names;
    }
    
    static /* synthetic */ int access$500(final ByteQuadsCanonicalizer a1) {
        return a1._spilloverEnd;
    }
    
    static /* synthetic */ int access$600(final ByteQuadsCanonicalizer a1) {
        return a1._longNameOffset;
    }
    
    private static final class TableInfo
    {
        public final int size;
        public final int count;
        public final int tertiaryShift;
        public final int[] mainHash;
        public final String[] names;
        public final int spilloverEnd;
        public final int longNameOffset;
        
        public TableInfo(final int a1, final int a2, final int a3, final int[] a4, final String[] a5, final int a6, final int a7) {
            super();
            this.size = a1;
            this.count = a2;
            this.tertiaryShift = a3;
            this.mainHash = a4;
            this.names = a5;
            this.spilloverEnd = a6;
            this.longNameOffset = a7;
        }
        
        public TableInfo(final ByteQuadsCanonicalizer a1) {
            super();
            this.size = a1._hashSize;
            this.count = a1._count;
            this.tertiaryShift = a1._tertiaryShift;
            this.mainHash = a1._hashArea;
            this.names = a1._names;
            this.spilloverEnd = a1._spilloverEnd;
            this.longNameOffset = a1._longNameOffset;
        }
        
        public static TableInfo createInitial(final int a1) {
            final int v1 = a1 << 3;
            final int v2 = ByteQuadsCanonicalizer._calcTertiaryShift(a1);
            return new TableInfo(a1, 0, v2, new int[v1], new String[a1 << 1], v1 - a1, v1);
        }
    }
}
