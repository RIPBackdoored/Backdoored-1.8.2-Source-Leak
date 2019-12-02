package javassist.bytecode.analysis;

import java.util.*;

class IntQueue
{
    private Entry head;
    private Entry tail;
    
    IntQueue() {
        super();
    }
    
    void add(final int a1) {
        final Entry v1 = new Entry(a1);
        if (this.tail != null) {
            this.tail.next = v1;
        }
        this.tail = v1;
        if (this.head == null) {
            this.head = v1;
        }
    }
    
    boolean isEmpty() {
        return this.head == null;
    }
    
    int take() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        final int v1 = this.head.value;
        this.head = this.head.next;
        if (this.head == null) {
            this.tail = null;
        }
        return v1;
    }
    
    private static class Entry
    {
        private Entry next;
        private int value;
        
        private Entry(final int a1) {
            super();
            this.value = a1;
        }
        
        Entry(final int a1, final IntQueue$1 a2) {
            this(a1);
        }
        
        static /* synthetic */ Entry access$102(final Entry a1, final Entry a2) {
            return a1.next = a2;
        }
        
        static /* synthetic */ int access$200(final Entry a1) {
            return a1.value;
        }
        
        static /* synthetic */ Entry access$100(final Entry a1) {
            return a1.next;
        }
    }
}
