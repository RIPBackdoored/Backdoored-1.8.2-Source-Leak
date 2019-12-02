package com.google.api.client.util;

public final class Objects
{
    public static boolean equal(final Object a1, final Object a2) {
        return com.google.api.client.repackaged.com.google.common.base.Objects.equal(a1, a2);
    }
    
    public static ToStringHelper toStringHelper(final Object a1) {
        return new ToStringHelper(a1.getClass().getSimpleName());
    }
    
    private Objects() {
        super();
    }
    
    public static final class ToStringHelper
    {
        private final String className;
        private ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;
        
        ToStringHelper(final String a1) {
            super();
            this.holderHead = new ValueHolder();
            this.holderTail = this.holderHead;
            this.className = a1;
        }
        
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }
        
        public ToStringHelper add(final String a1, final Object a2) {
            return this.addHolder(a1, a2);
        }
        
        @Override
        public String toString() {
            final boolean omitNullValues = this.omitNullValues;
            String s = "";
            final StringBuilder v0 = new StringBuilder(32).append(this.className).append('{');
            for (ValueHolder v2 = this.holderHead.next; v2 != null; v2 = v2.next) {
                if (!omitNullValues || v2.value != null) {
                    v0.append(s);
                    s = ", ";
                    if (v2.name != null) {
                        v0.append(v2.name).append('=');
                    }
                    v0.append(v2.value);
                }
            }
            return v0.append('}').toString();
        }
        
        private ValueHolder addHolder() {
            final ValueHolder v1 = new ValueHolder();
            final ValueHolder holderTail = this.holderTail;
            final ValueHolder valueHolder = v1;
            holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return v1;
        }
        
        private ToStringHelper addHolder(final String a1, final Object a2) {
            final ValueHolder v1 = this.addHolder();
            v1.value = a2;
            v1.name = Preconditions.checkNotNull(a1);
            return this;
        }
        
        private static final class ValueHolder
        {
            String name;
            Object value;
            ValueHolder next;
            
            private ValueHolder() {
                super();
            }
            
            ValueHolder(final Objects$1 a1) {
                this();
            }
        }
    }
}
