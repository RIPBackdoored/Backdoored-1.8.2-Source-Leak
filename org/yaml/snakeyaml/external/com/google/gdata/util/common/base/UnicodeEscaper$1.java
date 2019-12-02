package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import java.io.*;

class UnicodeEscaper$1 implements Appendable {
    int pendingHighSurrogate = -1;
    char[] decodedChars = new char[2];
    final /* synthetic */ Appendable val$out;
    final /* synthetic */ UnicodeEscaper this$0;
    
    UnicodeEscaper$1(final UnicodeEscaper a1, final Appendable val$out) {
        this.this$0 = a1;
        this.val$out = val$out;
        super();
    }
    
    @Override
    public Appendable append(final CharSequence a1) throws IOException {
        return this.append(a1, 0, a1.length());
    }
    
    @Override
    public Appendable append(final CharSequence v-4, final int v-3, final int v-2) throws IOException {
        int nextEscapeIndex = v-3;
        if (nextEscapeIndex < v-2) {
            int v0 = nextEscapeIndex;
            if (this.pendingHighSurrogate != -1) {
                final char a1 = v-4.charAt(nextEscapeIndex++);
                if (!Character.isLowSurrogate(a1)) {
                    throw new IllegalArgumentException("Expected low surrogate character but got " + a1);
                }
                final char[] a2 = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, a1));
                if (a2 != null) {
                    this.outputChars(a2, a2.length);
                    ++v0;
                }
                else {
                    this.val$out.append((char)this.pendingHighSurrogate);
                }
                this.pendingHighSurrogate = -1;
            }
            while (true) {
                nextEscapeIndex = this.this$0.nextEscapeIndex(v-4, nextEscapeIndex, v-2);
                if (nextEscapeIndex > v0) {
                    this.val$out.append(v-4, v0, nextEscapeIndex);
                }
                if (nextEscapeIndex == v-2) {
                    break;
                }
                final int v2 = UnicodeEscaper.codePointAt(v-4, nextEscapeIndex, v-2);
                if (v2 < 0) {
                    this.pendingHighSurrogate = -v2;
                    break;
                }
                final char[] v3 = this.this$0.escape(v2);
                if (v3 != null) {
                    this.outputChars(v3, v3.length);
                }
                else {
                    final int a3 = Character.toChars(v2, this.decodedChars, 0);
                    this.outputChars(this.decodedChars, a3);
                }
                nextEscapeIndex = (v0 = nextEscapeIndex + (Character.isSupplementaryCodePoint(v2) ? 2 : 1));
            }
        }
        return this;
    }
    
    @Override
    public Appendable append(final char v0) throws IOException {
        if (this.pendingHighSurrogate != -1) {
            if (!Character.isLowSurrogate(v0)) {
                throw new IllegalArgumentException("Expected low surrogate character but got '" + v0 + "' with value " + (int)v0);
            }
            final char[] a1 = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, v0));
            if (a1 != null) {
                this.outputChars(a1, a1.length);
            }
            else {
                this.val$out.append((char)this.pendingHighSurrogate);
                this.val$out.append(v0);
            }
            this.pendingHighSurrogate = -1;
        }
        else if (Character.isHighSurrogate(v0)) {
            this.pendingHighSurrogate = v0;
        }
        else {
            if (Character.isLowSurrogate(v0)) {
                throw new IllegalArgumentException("Unexpected low surrogate character '" + v0 + "' with value " + (int)v0);
            }
            final char[] v = this.this$0.escape(v0);
            if (v != null) {
                this.outputChars(v, v.length);
            }
            else {
                this.val$out.append(v0);
            }
        }
        return this;
    }
    
    private void outputChars(final char[] v1, final int v2) throws IOException {
        for (int a1 = 0; a1 < v2; ++a1) {
            this.val$out.append(v1[a1]);
        }
    }
}