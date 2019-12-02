package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import javax.annotation.*;
import com.google.errorprone.annotations.*;
import java.util.*;

@GwtCompatible
abstract class AbstractIterator<T> implements Iterator<T>
{
    private State state;
    private T next;
    
    protected AbstractIterator() {
        super();
        this.state = State.NOT_READY;
    }
    
    protected abstract T computeNext();
    
    @Nullable
    @CanIgnoreReturnValue
    protected final T endOfData() {
        this.state = State.DONE;
        return null;
    }
    
    @Override
    public final boolean hasNext() {
        Preconditions.checkState(this.state != State.FAILED);
        switch (this.state) {
            case READY: {
                return true;
            }
            case DONE: {
                return false;
            }
            default: {
                return this.tryToComputeNext();
            }
        }
    }
    
    private boolean tryToComputeNext() {
        this.state = State.FAILED;
        this.next = this.computeNext();
        if (this.state != State.DONE) {
            this.state = State.READY;
            return true;
        }
        return false;
    }
    
    @Override
    public final T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.state = State.NOT_READY;
        final T v1 = this.next;
        this.next = null;
        return v1;
    }
    
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
    
    private enum State
    {
        READY, 
        NOT_READY, 
        DONE, 
        FAILED;
        
        private static final /* synthetic */ State[] $VALUES;
        
        public static State[] values() {
            return State.$VALUES.clone();
        }
        
        public static State valueOf(final String a1) {
            return Enum.valueOf(State.class, a1);
        }
        
        static {
            $VALUES = new State[] { State.READY, State.NOT_READY, State.DONE, State.FAILED };
        }
    }
}
