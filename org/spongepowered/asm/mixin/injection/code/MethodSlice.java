package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import org.apache.logging.log4j.*;
import java.util.*;

public final class MethodSlice
{
    private static final Logger logger;
    private final ISliceContext owner;
    private final String id;
    private final InjectionPoint from;
    private final InjectionPoint to;
    private final String name;
    
    private MethodSlice(final ISliceContext a1, final String a2, final InjectionPoint a3, final InjectionPoint a4) {
        super();
        if (a3 == null && a4 == null) {
            throw new InvalidSliceException(a1, String.format("%s is redundant. No 'from' or 'to' value specified", this));
        }
        this.owner = a1;
        this.id = Strings.nullToEmpty(a2);
        this.from = a3;
        this.to = a4;
        this.name = getSliceName(a2);
    }
    
    public String getId() {
        return this.id;
    }
    
    public ReadOnlyInsnList getSlice(final MethodNode a1) {
        final int v1 = a1.instructions.size() - 1;
        final int v2 = this.find(a1, this.from, 0, 0, this.name + "(from)");
        final int v3 = this.find(a1, this.to, v1, v2, this.name + "(to)");
        if (v2 > v3) {
            throw new InvalidSliceException(this.owner, String.format("%s is negative size. Range(%d -> %d)", this.describe(), v2, v3));
        }
        if (v2 < 0 || v3 < 0 || v2 > v1 || v3 > v1) {
            throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + v2 + " end=" + v3 + " lim=" + v1);
        }
        if (v2 == 0 && v3 == v1) {
            return new ReadOnlyInsnList(a1.instructions);
        }
        return new InsnListSlice(a1.instructions, v2, v3);
    }
    
    private int find(final MethodNode a1, final InjectionPoint a2, final int a3, final int a4, final String a5) {
        if (a2 == null) {
            return a3;
        }
        final Deque<AbstractInsnNode> v1 = new LinkedList<AbstractInsnNode>();
        final ReadOnlyInsnList v2 = new ReadOnlyInsnList(a1.instructions);
        final boolean v3 = a2.find(a1.desc, v2, v1);
        final InjectionPoint.Selector v4 = a2.getSelector();
        if (v1.size() != 1 && v4 == InjectionPoint.Selector.ONE) {
            throw new InvalidSliceException(this.owner, String.format("%s requires 1 result but found %d", this.describe(a5), v1.size()));
        }
        if (!v3) {
            if (this.owner.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                MethodSlice.logger.warn("{} did not match any instructions", new Object[] { this.describe(a5) });
            }
            return a4;
        }
        return a1.instructions.indexOf((v4 == InjectionPoint.Selector.FIRST) ? v1.getFirst() : v1.getLast());
    }
    
    @Override
    public String toString() {
        return this.describe();
    }
    
    private String describe() {
        return this.describe(this.name);
    }
    
    private String describe(final String a1) {
        return describeSlice(a1, this.owner);
    }
    
    private static String describeSlice(final String a1, final ISliceContext a2) {
        final String v1 = Bytecode.getSimpleName(a2.getAnnotation());
        final MethodNode v2 = a2.getMethod();
        return String.format("%s->%s(%s)::%s%s", a2.getContext(), v1, a1, v2.name, v2.desc);
    }
    
    private static String getSliceName(final String a1) {
        return String.format("@Slice[%s]", Strings.nullToEmpty(a1));
    }
    
    public static MethodSlice parse(final ISliceContext a1, final Slice a2) {
        final String v1 = a2.id();
        final At v2 = a2.from();
        final At v3 = a2.to();
        final InjectionPoint v4 = (v2 != null) ? InjectionPoint.parse(a1, v2) : null;
        final InjectionPoint v5 = (v3 != null) ? InjectionPoint.parse(a1, v3) : null;
        return new MethodSlice(a1, v1, v4, v5);
    }
    
    public static MethodSlice parse(final ISliceContext a1, final AnnotationNode a2) {
        final String v1 = Annotations.getValue(a2, "id");
        final AnnotationNode v2 = Annotations.getValue(a2, "from");
        final AnnotationNode v3 = Annotations.getValue(a2, "to");
        final InjectionPoint v4 = (v2 != null) ? InjectionPoint.parse(a1, v2) : null;
        final InjectionPoint v5 = (v3 != null) ? InjectionPoint.parse(a1, v3) : null;
        return new MethodSlice(a1, v1, v4, v5);
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    static final class InsnListSlice extends ReadOnlyInsnList
    {
        private final int start;
        private final int end;
        
        protected InsnListSlice(final InsnList a1, final int a2, final int a3) {
            super(a1);
            this.start = a2;
            this.end = a3;
        }
        
        @Override
        public ListIterator<AbstractInsnNode> iterator() {
            return this.iterator(0);
        }
        
        @Override
        public ListIterator<AbstractInsnNode> iterator(final int a1) {
            return new SliceIterator(super.iterator(this.start + a1), this.start, this.end, this.start + a1);
        }
        
        @Override
        public AbstractInsnNode[] toArray() {
            final AbstractInsnNode[] v1 = super.toArray();
            final AbstractInsnNode[] v2 = new AbstractInsnNode[this.size()];
            System.arraycopy(v1, this.start, v2, 0, v2.length);
            return v2;
        }
        
        @Override
        public int size() {
            return this.end - this.start + 1;
        }
        
        @Override
        public AbstractInsnNode getFirst() {
            return super.get(this.start);
        }
        
        @Override
        public AbstractInsnNode getLast() {
            return super.get(this.end);
        }
        
        @Override
        public AbstractInsnNode get(final int a1) {
            return super.get(this.start + a1);
        }
        
        @Override
        public boolean contains(final AbstractInsnNode v2) {
            for (final AbstractInsnNode a1 : this.toArray()) {
                if (a1 == v2) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int indexOf(final AbstractInsnNode a1) {
            final int v1 = super.indexOf(a1);
            return (v1 >= this.start && v1 <= this.end) ? (v1 - this.start) : -1;
        }
        
        public int realIndexOf(final AbstractInsnNode a1) {
            return super.indexOf(a1);
        }
        
        static class SliceIterator implements ListIterator<AbstractInsnNode>
        {
            private final ListIterator<AbstractInsnNode> iter;
            private int start;
            private int end;
            private int index;
            
            public SliceIterator(final ListIterator<AbstractInsnNode> a1, final int a2, final int a3, final int a4) {
                super();
                this.iter = a1;
                this.start = a2;
                this.end = a3;
                this.index = a4;
            }
            
            @Override
            public boolean hasNext() {
                return this.index <= this.end && this.iter.hasNext();
            }
            
            @Override
            public AbstractInsnNode next() {
                if (this.index > this.end) {
                    throw new NoSuchElementException();
                }
                ++this.index;
                return this.iter.next();
            }
            
            @Override
            public boolean hasPrevious() {
                return this.index > this.start;
            }
            
            @Override
            public AbstractInsnNode previous() {
                if (this.index <= this.start) {
                    throw new NoSuchElementException();
                }
                --this.index;
                return this.iter.previous();
            }
            
            @Override
            public int nextIndex() {
                return this.index - this.start;
            }
            
            @Override
            public int previousIndex() {
                return this.index - this.start - 1;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove insn from slice");
            }
            
            @Override
            public void set(final AbstractInsnNode a1) {
                throw new UnsupportedOperationException("Cannot set insn using slice");
            }
            
            @Override
            public void add(final AbstractInsnNode a1) {
                throw new UnsupportedOperationException("Cannot add insn using slice");
            }
            
            @Override
            public /* bridge */ void add(final Object o) {
                this.add((AbstractInsnNode)o);
            }
            
            @Override
            public /* bridge */ void set(final Object o) {
                this.set((AbstractInsnNode)o);
            }
            
            @Override
            public /* bridge */ Object previous() {
                return this.previous();
            }
            
            @Override
            public /* bridge */ Object next() {
                return this.next();
            }
        }
    }
}
