package com.google.api.client.repackaged.com.google.common.base;

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
        this.trimmer = Splitter.access$200(a1);
        this.omitEmptyStrings = Splitter.access$300(a1);
        this.limit = Splitter.access$400(a1);
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
